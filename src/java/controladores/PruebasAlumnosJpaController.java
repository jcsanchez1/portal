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
import entidad.PruebasAlumnos;
import entidad.PruebasSecciones;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author juanc
 */
public class PruebasAlumnosJpaController implements Serializable {

    public PruebasAlumnosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PruebasAlumnos pruebasAlumnos) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumnos fkAlumno = pruebasAlumnos.getFkAlumno();
            if (fkAlumno != null) {
                fkAlumno = em.getReference(fkAlumno.getClass(), fkAlumno.getId());
                pruebasAlumnos.setFkAlumno(fkAlumno);
            }
            PruebasSecciones fkPruebaSeccion = pruebasAlumnos.getFkPruebaSeccion();
            if (fkPruebaSeccion != null) {
                fkPruebaSeccion = em.getReference(fkPruebaSeccion.getClass(), fkPruebaSeccion.getId());
                pruebasAlumnos.setFkPruebaSeccion(fkPruebaSeccion);
            }
            em.persist(pruebasAlumnos);
            if (fkAlumno != null) {
                fkAlumno.getPruebasAlumnosList().add(pruebasAlumnos);
                fkAlumno = em.merge(fkAlumno);
            }
            if (fkPruebaSeccion != null) {
                fkPruebaSeccion.getPruebasAlumnosList().add(pruebasAlumnos);
                fkPruebaSeccion = em.merge(fkPruebaSeccion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPruebasAlumnos(pruebasAlumnos.getId()) != null) {
                throw new PreexistingEntityException("PruebasAlumnos " + pruebasAlumnos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PruebasAlumnos pruebasAlumnos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PruebasAlumnos persistentPruebasAlumnos = em.find(PruebasAlumnos.class, pruebasAlumnos.getId());
            Alumnos fkAlumnoOld = persistentPruebasAlumnos.getFkAlumno();
            Alumnos fkAlumnoNew = pruebasAlumnos.getFkAlumno();
            PruebasSecciones fkPruebaSeccionOld = persistentPruebasAlumnos.getFkPruebaSeccion();
            PruebasSecciones fkPruebaSeccionNew = pruebasAlumnos.getFkPruebaSeccion();
            if (fkAlumnoNew != null) {
                fkAlumnoNew = em.getReference(fkAlumnoNew.getClass(), fkAlumnoNew.getId());
                pruebasAlumnos.setFkAlumno(fkAlumnoNew);
            }
            if (fkPruebaSeccionNew != null) {
                fkPruebaSeccionNew = em.getReference(fkPruebaSeccionNew.getClass(), fkPruebaSeccionNew.getId());
                pruebasAlumnos.setFkPruebaSeccion(fkPruebaSeccionNew);
            }
            pruebasAlumnos = em.merge(pruebasAlumnos);
            if (fkAlumnoOld != null && !fkAlumnoOld.equals(fkAlumnoNew)) {
                fkAlumnoOld.getPruebasAlumnosList().remove(pruebasAlumnos);
                fkAlumnoOld = em.merge(fkAlumnoOld);
            }
            if (fkAlumnoNew != null && !fkAlumnoNew.equals(fkAlumnoOld)) {
                fkAlumnoNew.getPruebasAlumnosList().add(pruebasAlumnos);
                fkAlumnoNew = em.merge(fkAlumnoNew);
            }
            if (fkPruebaSeccionOld != null && !fkPruebaSeccionOld.equals(fkPruebaSeccionNew)) {
                fkPruebaSeccionOld.getPruebasAlumnosList().remove(pruebasAlumnos);
                fkPruebaSeccionOld = em.merge(fkPruebaSeccionOld);
            }
            if (fkPruebaSeccionNew != null && !fkPruebaSeccionNew.equals(fkPruebaSeccionOld)) {
                fkPruebaSeccionNew.getPruebasAlumnosList().add(pruebasAlumnos);
                fkPruebaSeccionNew = em.merge(fkPruebaSeccionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = pruebasAlumnos.getId();
                if (findPruebasAlumnos(id) == null) {
                    throw new NonexistentEntityException("The pruebasAlumnos with id " + id + " no longer exists.");
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
            PruebasAlumnos pruebasAlumnos;
            try {
                pruebasAlumnos = em.getReference(PruebasAlumnos.class, id);
                pruebasAlumnos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pruebasAlumnos with id " + id + " no longer exists.", enfe);
            }
            Alumnos fkAlumno = pruebasAlumnos.getFkAlumno();
            if (fkAlumno != null) {
                fkAlumno.getPruebasAlumnosList().remove(pruebasAlumnos);
                fkAlumno = em.merge(fkAlumno);
            }
            PruebasSecciones fkPruebaSeccion = pruebasAlumnos.getFkPruebaSeccion();
            if (fkPruebaSeccion != null) {
                fkPruebaSeccion.getPruebasAlumnosList().remove(pruebasAlumnos);
                fkPruebaSeccion = em.merge(fkPruebaSeccion);
            }
            em.remove(pruebasAlumnos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PruebasAlumnos> findPruebasAlumnosEntities() {
        return findPruebasAlumnosEntities(true, -1, -1);
    }

    public List<PruebasAlumnos> findPruebasAlumnosEntities(int maxResults, int firstResult) {
        return findPruebasAlumnosEntities(false, maxResults, firstResult);
    }

    private List<PruebasAlumnos> findPruebasAlumnosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PruebasAlumnos.class));
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

    public PruebasAlumnos findPruebasAlumnos(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PruebasAlumnos.class, id);
        } finally {
            em.close();
        }
    }

    public int getPruebasAlumnosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PruebasAlumnos> rt = cq.from(PruebasAlumnos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
