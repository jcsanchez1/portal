/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidad.Alumnos;
import entidad.TareasAlumnos;
import entidad.TareasSecciones;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author juanc
 */
public class TareasAlumnosJpaController implements Serializable {

    public TareasAlumnosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TareasAlumnos tareasAlumnos) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumnos fkAlumno = tareasAlumnos.getFkAlumno();
            if (fkAlumno != null) {
                fkAlumno = em.getReference(fkAlumno.getClass(), fkAlumno.getId());
                tareasAlumnos.setFkAlumno(fkAlumno);
            }
            TareasSecciones fkTarea = tareasAlumnos.getFkTarea();
            if (fkTarea != null) {
                fkTarea = em.getReference(fkTarea.getClass(), fkTarea.getId());
                tareasAlumnos.setFkTarea(fkTarea);
            }
            em.persist(tareasAlumnos);
            if (fkAlumno != null) {
                fkAlumno.getTareasAlumnosList().add(tareasAlumnos);
                fkAlumno = em.merge(fkAlumno);
            }
            if (fkTarea != null) {
                fkTarea.getTareasAlumnosList().add(tareasAlumnos);
                fkTarea = em.merge(fkTarea);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTareasAlumnos(tareasAlumnos.getId()) != null) {
                throw new PreexistingEntityException("TareasAlumnos " + tareasAlumnos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TareasAlumnos tareasAlumnos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TareasAlumnos persistentTareasAlumnos = em.find(TareasAlumnos.class, tareasAlumnos.getId());
            Alumnos fkAlumnoOld = persistentTareasAlumnos.getFkAlumno();
            Alumnos fkAlumnoNew = tareasAlumnos.getFkAlumno();
            TareasSecciones fkTareaOld = persistentTareasAlumnos.getFkTarea();
            TareasSecciones fkTareaNew = tareasAlumnos.getFkTarea();
            if (fkAlumnoNew != null) {
                fkAlumnoNew = em.getReference(fkAlumnoNew.getClass(), fkAlumnoNew.getId());
                tareasAlumnos.setFkAlumno(fkAlumnoNew);
            }
            if (fkTareaNew != null) {
                fkTareaNew = em.getReference(fkTareaNew.getClass(), fkTareaNew.getId());
                tareasAlumnos.setFkTarea(fkTareaNew);
            }
            tareasAlumnos = em.merge(tareasAlumnos);
            if (fkAlumnoOld != null && !fkAlumnoOld.equals(fkAlumnoNew)) {
                fkAlumnoOld.getTareasAlumnosList().remove(tareasAlumnos);
                fkAlumnoOld = em.merge(fkAlumnoOld);
            }
            if (fkAlumnoNew != null && !fkAlumnoNew.equals(fkAlumnoOld)) {
                fkAlumnoNew.getTareasAlumnosList().add(tareasAlumnos);
                fkAlumnoNew = em.merge(fkAlumnoNew);
            }
            if (fkTareaOld != null && !fkTareaOld.equals(fkTareaNew)) {
                fkTareaOld.getTareasAlumnosList().remove(tareasAlumnos);
                fkTareaOld = em.merge(fkTareaOld);
            }
            if (fkTareaNew != null && !fkTareaNew.equals(fkTareaOld)) {
                fkTareaNew.getTareasAlumnosList().add(tareasAlumnos);
                fkTareaNew = em.merge(fkTareaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = tareasAlumnos.getId();
                if (findTareasAlumnos(id) == null) {
                    throw new NonexistentEntityException("The tareasAlumnos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TareasAlumnos tareasAlumnos;
            try {
                tareasAlumnos = em.getReference(TareasAlumnos.class, id);
                tareasAlumnos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tareasAlumnos with id " + id + " no longer exists.", enfe);
            }
            Alumnos fkAlumno = tareasAlumnos.getFkAlumno();
            if (fkAlumno != null) {
                fkAlumno.getTareasAlumnosList().remove(tareasAlumnos);
                fkAlumno = em.merge(fkAlumno);
            }
            TareasSecciones fkTarea = tareasAlumnos.getFkTarea();
            if (fkTarea != null) {
                fkTarea.getTareasAlumnosList().remove(tareasAlumnos);
                fkTarea = em.merge(fkTarea);
            }
            em.remove(tareasAlumnos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TareasAlumnos> findTareasAlumnosEntities() {
        return findTareasAlumnosEntities(true, -1, -1);
    }

    public List<TareasAlumnos> findTareasAlumnosEntities(int maxResults, int firstResult) {
        return findTareasAlumnosEntities(false, maxResults, firstResult);
    }

    private List<TareasAlumnos> findTareasAlumnosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TareasAlumnos.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TareasAlumnos findTareasAlumnos(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TareasAlumnos.class, id);
        } finally {
            em.close();
        }
    }

    public int getTareasAlumnosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TareasAlumnos> rt = cq.from(TareasAlumnos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
