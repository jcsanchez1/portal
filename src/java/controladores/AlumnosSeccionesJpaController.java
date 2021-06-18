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
import entidad.AlumnosSecciones;
import entidad.Secciones;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author juanc
 */
public class AlumnosSeccionesJpaController implements Serializable {

    public AlumnosSeccionesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AlumnosSecciones alumnosSecciones) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumnos fkAlumno = alumnosSecciones.getFkAlumno();
            if (fkAlumno != null) {
                fkAlumno = em.getReference(fkAlumno.getClass(), fkAlumno.getId());
                alumnosSecciones.setFkAlumno(fkAlumno);
            }
            Secciones fkSeecion = alumnosSecciones.getFkSeecion();
            if (fkSeecion != null) {
                fkSeecion = em.getReference(fkSeecion.getClass(), fkSeecion.getId());
                alumnosSecciones.setFkSeecion(fkSeecion);
            }
            em.persist(alumnosSecciones);
            if (fkAlumno != null) {
                fkAlumno.getAlumnosSeccionesList().add(alumnosSecciones);
                fkAlumno = em.merge(fkAlumno);
            }
            if (fkSeecion != null) {
                fkSeecion.getAlumnosSeccionesList().add(alumnosSecciones);
                fkSeecion = em.merge(fkSeecion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAlumnosSecciones(alumnosSecciones.getId()) != null) {
                throw new PreexistingEntityException("AlumnosSecciones " + alumnosSecciones + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AlumnosSecciones alumnosSecciones) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AlumnosSecciones persistentAlumnosSecciones = em.find(AlumnosSecciones.class, alumnosSecciones.getId());
            Alumnos fkAlumnoOld = persistentAlumnosSecciones.getFkAlumno();
            Alumnos fkAlumnoNew = alumnosSecciones.getFkAlumno();
            Secciones fkSeecionOld = persistentAlumnosSecciones.getFkSeecion();
            Secciones fkSeecionNew = alumnosSecciones.getFkSeecion();
            if (fkAlumnoNew != null) {
                fkAlumnoNew = em.getReference(fkAlumnoNew.getClass(), fkAlumnoNew.getId());
                alumnosSecciones.setFkAlumno(fkAlumnoNew);
            }
            if (fkSeecionNew != null) {
                fkSeecionNew = em.getReference(fkSeecionNew.getClass(), fkSeecionNew.getId());
                alumnosSecciones.setFkSeecion(fkSeecionNew);
            }
            alumnosSecciones = em.merge(alumnosSecciones);
            if (fkAlumnoOld != null && !fkAlumnoOld.equals(fkAlumnoNew)) {
                fkAlumnoOld.getAlumnosSeccionesList().remove(alumnosSecciones);
                fkAlumnoOld = em.merge(fkAlumnoOld);
            }
            if (fkAlumnoNew != null && !fkAlumnoNew.equals(fkAlumnoOld)) {
                fkAlumnoNew.getAlumnosSeccionesList().add(alumnosSecciones);
                fkAlumnoNew = em.merge(fkAlumnoNew);
            }
            if (fkSeecionOld != null && !fkSeecionOld.equals(fkSeecionNew)) {
                fkSeecionOld.getAlumnosSeccionesList().remove(alumnosSecciones);
                fkSeecionOld = em.merge(fkSeecionOld);
            }
            if (fkSeecionNew != null && !fkSeecionNew.equals(fkSeecionOld)) {
                fkSeecionNew.getAlumnosSeccionesList().add(alumnosSecciones);
                fkSeecionNew = em.merge(fkSeecionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = alumnosSecciones.getId();
                if (findAlumnosSecciones(id) == null) {
                    throw new NonexistentEntityException("The alumnosSecciones with id " + id + " no longer exists.");
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
            AlumnosSecciones alumnosSecciones;
            try {
                alumnosSecciones = em.getReference(AlumnosSecciones.class, id);
                alumnosSecciones.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The alumnosSecciones with id " + id + " no longer exists.", enfe);
            }
            Alumnos fkAlumno = alumnosSecciones.getFkAlumno();
            if (fkAlumno != null) {
                fkAlumno.getAlumnosSeccionesList().remove(alumnosSecciones);
                fkAlumno = em.merge(fkAlumno);
            }
            Secciones fkSeecion = alumnosSecciones.getFkSeecion();
            if (fkSeecion != null) {
                fkSeecion.getAlumnosSeccionesList().remove(alumnosSecciones);
                fkSeecion = em.merge(fkSeecion);
            }
            em.remove(alumnosSecciones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AlumnosSecciones> findAlumnosSeccionesEntities() {
        return findAlumnosSeccionesEntities(true, -1, -1);
    }

    public List<AlumnosSecciones> findAlumnosSeccionesEntities(int maxResults, int firstResult) {
        return findAlumnosSeccionesEntities(false, maxResults, firstResult);
    }

    private List<AlumnosSecciones> findAlumnosSeccionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AlumnosSecciones.class));
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

    public AlumnosSecciones findAlumnosSecciones(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AlumnosSecciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlumnosSeccionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AlumnosSecciones> rt = cq.from(AlumnosSecciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
