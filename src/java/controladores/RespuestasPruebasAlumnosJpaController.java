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
import entidad.RespuestasBreves;
import entidad.RespuestasMultiples;
import entidad.RespuestasPruebasAlumnos;
import entidad.RespuestasVofs;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author juanc
 */
public class RespuestasPruebasAlumnosJpaController implements Serializable {

    public RespuestasPruebasAlumnosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RespuestasPruebasAlumnos respuestasPruebasAlumnos) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumnos fkRespuestaAlumno = respuestasPruebasAlumnos.getFkRespuestaAlumno();
            if (fkRespuestaAlumno != null) {
                fkRespuestaAlumno = em.getReference(fkRespuestaAlumno.getClass(), fkRespuestaAlumno.getId());
                respuestasPruebasAlumnos.setFkRespuestaAlumno(fkRespuestaAlumno);
            }
            RespuestasBreves fkRespuestaBreve = respuestasPruebasAlumnos.getFkRespuestaBreve();
            if (fkRespuestaBreve != null) {
                fkRespuestaBreve = em.getReference(fkRespuestaBreve.getClass(), fkRespuestaBreve.getId());
                respuestasPruebasAlumnos.setFkRespuestaBreve(fkRespuestaBreve);
            }
            RespuestasMultiples fkRespuestaMulti = respuestasPruebasAlumnos.getFkRespuestaMulti();
            if (fkRespuestaMulti != null) {
                fkRespuestaMulti = em.getReference(fkRespuestaMulti.getClass(), fkRespuestaMulti.getId());
                respuestasPruebasAlumnos.setFkRespuestaMulti(fkRespuestaMulti);
            }
            RespuestasVofs fkRespuestaVof = respuestasPruebasAlumnos.getFkRespuestaVof();
            if (fkRespuestaVof != null) {
                fkRespuestaVof = em.getReference(fkRespuestaVof.getClass(), fkRespuestaVof.getId());
                respuestasPruebasAlumnos.setFkRespuestaVof(fkRespuestaVof);
            }
            em.persist(respuestasPruebasAlumnos);
            if (fkRespuestaAlumno != null) {
                fkRespuestaAlumno.getRespuestasPruebasAlumnosList().add(respuestasPruebasAlumnos);
                fkRespuestaAlumno = em.merge(fkRespuestaAlumno);
            }
            if (fkRespuestaBreve != null) {
                fkRespuestaBreve.getRespuestasPruebasAlumnosList().add(respuestasPruebasAlumnos);
                fkRespuestaBreve = em.merge(fkRespuestaBreve);
            }
            if (fkRespuestaMulti != null) {
                fkRespuestaMulti.getRespuestasPruebasAlumnosList().add(respuestasPruebasAlumnos);
                fkRespuestaMulti = em.merge(fkRespuestaMulti);
            }
            if (fkRespuestaVof != null) {
                fkRespuestaVof.getRespuestasPruebasAlumnosList().add(respuestasPruebasAlumnos);
                fkRespuestaVof = em.merge(fkRespuestaVof);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRespuestasPruebasAlumnos(respuestasPruebasAlumnos.getId()) != null) {
                throw new PreexistingEntityException("RespuestasPruebasAlumnos " + respuestasPruebasAlumnos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RespuestasPruebasAlumnos respuestasPruebasAlumnos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RespuestasPruebasAlumnos persistentRespuestasPruebasAlumnos = em.find(RespuestasPruebasAlumnos.class, respuestasPruebasAlumnos.getId());
            Alumnos fkRespuestaAlumnoOld = persistentRespuestasPruebasAlumnos.getFkRespuestaAlumno();
            Alumnos fkRespuestaAlumnoNew = respuestasPruebasAlumnos.getFkRespuestaAlumno();
            RespuestasBreves fkRespuestaBreveOld = persistentRespuestasPruebasAlumnos.getFkRespuestaBreve();
            RespuestasBreves fkRespuestaBreveNew = respuestasPruebasAlumnos.getFkRespuestaBreve();
            RespuestasMultiples fkRespuestaMultiOld = persistentRespuestasPruebasAlumnos.getFkRespuestaMulti();
            RespuestasMultiples fkRespuestaMultiNew = respuestasPruebasAlumnos.getFkRespuestaMulti();
            RespuestasVofs fkRespuestaVofOld = persistentRespuestasPruebasAlumnos.getFkRespuestaVof();
            RespuestasVofs fkRespuestaVofNew = respuestasPruebasAlumnos.getFkRespuestaVof();
            if (fkRespuestaAlumnoNew != null) {
                fkRespuestaAlumnoNew = em.getReference(fkRespuestaAlumnoNew.getClass(), fkRespuestaAlumnoNew.getId());
                respuestasPruebasAlumnos.setFkRespuestaAlumno(fkRespuestaAlumnoNew);
            }
            if (fkRespuestaBreveNew != null) {
                fkRespuestaBreveNew = em.getReference(fkRespuestaBreveNew.getClass(), fkRespuestaBreveNew.getId());
                respuestasPruebasAlumnos.setFkRespuestaBreve(fkRespuestaBreveNew);
            }
            if (fkRespuestaMultiNew != null) {
                fkRespuestaMultiNew = em.getReference(fkRespuestaMultiNew.getClass(), fkRespuestaMultiNew.getId());
                respuestasPruebasAlumnos.setFkRespuestaMulti(fkRespuestaMultiNew);
            }
            if (fkRespuestaVofNew != null) {
                fkRespuestaVofNew = em.getReference(fkRespuestaVofNew.getClass(), fkRespuestaVofNew.getId());
                respuestasPruebasAlumnos.setFkRespuestaVof(fkRespuestaVofNew);
            }
            respuestasPruebasAlumnos = em.merge(respuestasPruebasAlumnos);
            if (fkRespuestaAlumnoOld != null && !fkRespuestaAlumnoOld.equals(fkRespuestaAlumnoNew)) {
                fkRespuestaAlumnoOld.getRespuestasPruebasAlumnosList().remove(respuestasPruebasAlumnos);
                fkRespuestaAlumnoOld = em.merge(fkRespuestaAlumnoOld);
            }
            if (fkRespuestaAlumnoNew != null && !fkRespuestaAlumnoNew.equals(fkRespuestaAlumnoOld)) {
                fkRespuestaAlumnoNew.getRespuestasPruebasAlumnosList().add(respuestasPruebasAlumnos);
                fkRespuestaAlumnoNew = em.merge(fkRespuestaAlumnoNew);
            }
            if (fkRespuestaBreveOld != null && !fkRespuestaBreveOld.equals(fkRespuestaBreveNew)) {
                fkRespuestaBreveOld.getRespuestasPruebasAlumnosList().remove(respuestasPruebasAlumnos);
                fkRespuestaBreveOld = em.merge(fkRespuestaBreveOld);
            }
            if (fkRespuestaBreveNew != null && !fkRespuestaBreveNew.equals(fkRespuestaBreveOld)) {
                fkRespuestaBreveNew.getRespuestasPruebasAlumnosList().add(respuestasPruebasAlumnos);
                fkRespuestaBreveNew = em.merge(fkRespuestaBreveNew);
            }
            if (fkRespuestaMultiOld != null && !fkRespuestaMultiOld.equals(fkRespuestaMultiNew)) {
                fkRespuestaMultiOld.getRespuestasPruebasAlumnosList().remove(respuestasPruebasAlumnos);
                fkRespuestaMultiOld = em.merge(fkRespuestaMultiOld);
            }
            if (fkRespuestaMultiNew != null && !fkRespuestaMultiNew.equals(fkRespuestaMultiOld)) {
                fkRespuestaMultiNew.getRespuestasPruebasAlumnosList().add(respuestasPruebasAlumnos);
                fkRespuestaMultiNew = em.merge(fkRespuestaMultiNew);
            }
            if (fkRespuestaVofOld != null && !fkRespuestaVofOld.equals(fkRespuestaVofNew)) {
                fkRespuestaVofOld.getRespuestasPruebasAlumnosList().remove(respuestasPruebasAlumnos);
                fkRespuestaVofOld = em.merge(fkRespuestaVofOld);
            }
            if (fkRespuestaVofNew != null && !fkRespuestaVofNew.equals(fkRespuestaVofOld)) {
                fkRespuestaVofNew.getRespuestasPruebasAlumnosList().add(respuestasPruebasAlumnos);
                fkRespuestaVofNew = em.merge(fkRespuestaVofNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = respuestasPruebasAlumnos.getId();
                if (findRespuestasPruebasAlumnos(id) == null) {
                    throw new NonexistentEntityException("The respuestasPruebasAlumnos with id " + id + " no longer exists.");
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
            RespuestasPruebasAlumnos respuestasPruebasAlumnos;
            try {
                respuestasPruebasAlumnos = em.getReference(RespuestasPruebasAlumnos.class, id);
                respuestasPruebasAlumnos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The respuestasPruebasAlumnos with id " + id + " no longer exists.", enfe);
            }
            Alumnos fkRespuestaAlumno = respuestasPruebasAlumnos.getFkRespuestaAlumno();
            if (fkRespuestaAlumno != null) {
                fkRespuestaAlumno.getRespuestasPruebasAlumnosList().remove(respuestasPruebasAlumnos);
                fkRespuestaAlumno = em.merge(fkRespuestaAlumno);
            }
            RespuestasBreves fkRespuestaBreve = respuestasPruebasAlumnos.getFkRespuestaBreve();
            if (fkRespuestaBreve != null) {
                fkRespuestaBreve.getRespuestasPruebasAlumnosList().remove(respuestasPruebasAlumnos);
                fkRespuestaBreve = em.merge(fkRespuestaBreve);
            }
            RespuestasMultiples fkRespuestaMulti = respuestasPruebasAlumnos.getFkRespuestaMulti();
            if (fkRespuestaMulti != null) {
                fkRespuestaMulti.getRespuestasPruebasAlumnosList().remove(respuestasPruebasAlumnos);
                fkRespuestaMulti = em.merge(fkRespuestaMulti);
            }
            RespuestasVofs fkRespuestaVof = respuestasPruebasAlumnos.getFkRespuestaVof();
            if (fkRespuestaVof != null) {
                fkRespuestaVof.getRespuestasPruebasAlumnosList().remove(respuestasPruebasAlumnos);
                fkRespuestaVof = em.merge(fkRespuestaVof);
            }
            em.remove(respuestasPruebasAlumnos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RespuestasPruebasAlumnos> findRespuestasPruebasAlumnosEntities() {
        return findRespuestasPruebasAlumnosEntities(true, -1, -1);
    }

    public List<RespuestasPruebasAlumnos> findRespuestasPruebasAlumnosEntities(int maxResults, int firstResult) {
        return findRespuestasPruebasAlumnosEntities(false, maxResults, firstResult);
    }

    private List<RespuestasPruebasAlumnos> findRespuestasPruebasAlumnosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RespuestasPruebasAlumnos.class));
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

    public RespuestasPruebasAlumnos findRespuestasPruebasAlumnos(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RespuestasPruebasAlumnos.class, id);
        } finally {
            em.close();
        }
    }

    public int getRespuestasPruebasAlumnosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RespuestasPruebasAlumnos> rt = cq.from(RespuestasPruebasAlumnos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
