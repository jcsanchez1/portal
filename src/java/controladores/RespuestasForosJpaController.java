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
import entidad.ForoSecciones;
import entidad.RespuestasForos;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author juanc
 */
public class RespuestasForosJpaController implements Serializable {

    public RespuestasForosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RespuestasForos respuestasForos) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumnos fkAlumno = respuestasForos.getFkAlumno();
            if (fkAlumno != null) {
                fkAlumno = em.getReference(fkAlumno.getClass(), fkAlumno.getId());
                respuestasForos.setFkAlumno(fkAlumno);
            }
            ForoSecciones fkForo = respuestasForos.getFkForo();
            if (fkForo != null) {
                fkForo = em.getReference(fkForo.getClass(), fkForo.getId());
                respuestasForos.setFkForo(fkForo);
            }
            em.persist(respuestasForos);
            if (fkAlumno != null) {
                fkAlumno.getRespuestasForosList().add(respuestasForos);
                fkAlumno = em.merge(fkAlumno);
            }
            if (fkForo != null) {
                fkForo.getRespuestasForosList().add(respuestasForos);
                fkForo = em.merge(fkForo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRespuestasForos(respuestasForos.getId()) != null) {
                throw new PreexistingEntityException("RespuestasForos " + respuestasForos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RespuestasForos respuestasForos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RespuestasForos persistentRespuestasForos = em.find(RespuestasForos.class, respuestasForos.getId());
            Alumnos fkAlumnoOld = persistentRespuestasForos.getFkAlumno();
            Alumnos fkAlumnoNew = respuestasForos.getFkAlumno();
            ForoSecciones fkForoOld = persistentRespuestasForos.getFkForo();
            ForoSecciones fkForoNew = respuestasForos.getFkForo();
            if (fkAlumnoNew != null) {
                fkAlumnoNew = em.getReference(fkAlumnoNew.getClass(), fkAlumnoNew.getId());
                respuestasForos.setFkAlumno(fkAlumnoNew);
            }
            if (fkForoNew != null) {
                fkForoNew = em.getReference(fkForoNew.getClass(), fkForoNew.getId());
                respuestasForos.setFkForo(fkForoNew);
            }
            respuestasForos = em.merge(respuestasForos);
            if (fkAlumnoOld != null && !fkAlumnoOld.equals(fkAlumnoNew)) {
                fkAlumnoOld.getRespuestasForosList().remove(respuestasForos);
                fkAlumnoOld = em.merge(fkAlumnoOld);
            }
            if (fkAlumnoNew != null && !fkAlumnoNew.equals(fkAlumnoOld)) {
                fkAlumnoNew.getRespuestasForosList().add(respuestasForos);
                fkAlumnoNew = em.merge(fkAlumnoNew);
            }
            if (fkForoOld != null && !fkForoOld.equals(fkForoNew)) {
                fkForoOld.getRespuestasForosList().remove(respuestasForos);
                fkForoOld = em.merge(fkForoOld);
            }
            if (fkForoNew != null && !fkForoNew.equals(fkForoOld)) {
                fkForoNew.getRespuestasForosList().add(respuestasForos);
                fkForoNew = em.merge(fkForoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = respuestasForos.getId();
                if (findRespuestasForos(id) == null) {
                    throw new NonexistentEntityException("The respuestasForos with id " + id + " no longer exists.");
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
            RespuestasForos respuestasForos;
            try {
                respuestasForos = em.getReference(RespuestasForos.class, id);
                respuestasForos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The respuestasForos with id " + id + " no longer exists.", enfe);
            }
            Alumnos fkAlumno = respuestasForos.getFkAlumno();
            if (fkAlumno != null) {
                fkAlumno.getRespuestasForosList().remove(respuestasForos);
                fkAlumno = em.merge(fkAlumno);
            }
            ForoSecciones fkForo = respuestasForos.getFkForo();
            if (fkForo != null) {
                fkForo.getRespuestasForosList().remove(respuestasForos);
                fkForo = em.merge(fkForo);
            }
            em.remove(respuestasForos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RespuestasForos> findRespuestasForosEntities() {
        return findRespuestasForosEntities(true, -1, -1);
    }

    public List<RespuestasForos> findRespuestasForosEntities(int maxResults, int firstResult) {
        return findRespuestasForosEntities(false, maxResults, firstResult);
    }

    private List<RespuestasForos> findRespuestasForosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RespuestasForos.class));
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

    public RespuestasForos findRespuestasForos(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RespuestasForos.class, id);
        } finally {
            em.close();
        }
    }

    public int getRespuestasForosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RespuestasForos> rt = cq.from(RespuestasForos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
