/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import controladores.exceptions.PreexistingEntityException;
import entidad.RespuestasMultiples;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidad.RespuestasPruebasAlumnos;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author juanc
 */
public class RespuestasMultiplesJpaController implements Serializable {

    public RespuestasMultiplesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RespuestasMultiples respuestasMultiples) throws PreexistingEntityException, Exception {
        if (respuestasMultiples.getRespuestasPruebasAlumnosList() == null) {
            respuestasMultiples.setRespuestasPruebasAlumnosList(new ArrayList<RespuestasPruebasAlumnos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<RespuestasPruebasAlumnos> attachedRespuestasPruebasAlumnosList = new ArrayList<RespuestasPruebasAlumnos>();
            for (RespuestasPruebasAlumnos respuestasPruebasAlumnosListRespuestasPruebasAlumnosToAttach : respuestasMultiples.getRespuestasPruebasAlumnosList()) {
                respuestasPruebasAlumnosListRespuestasPruebasAlumnosToAttach = em.getReference(respuestasPruebasAlumnosListRespuestasPruebasAlumnosToAttach.getClass(), respuestasPruebasAlumnosListRespuestasPruebasAlumnosToAttach.getId());
                attachedRespuestasPruebasAlumnosList.add(respuestasPruebasAlumnosListRespuestasPruebasAlumnosToAttach);
            }
            respuestasMultiples.setRespuestasPruebasAlumnosList(attachedRespuestasPruebasAlumnosList);
            em.persist(respuestasMultiples);
            for (RespuestasPruebasAlumnos respuestasPruebasAlumnosListRespuestasPruebasAlumnos : respuestasMultiples.getRespuestasPruebasAlumnosList()) {
                RespuestasMultiples oldFkRespuestaMultiOfRespuestasPruebasAlumnosListRespuestasPruebasAlumnos = respuestasPruebasAlumnosListRespuestasPruebasAlumnos.getFkRespuestaMulti();
                respuestasPruebasAlumnosListRespuestasPruebasAlumnos.setFkRespuestaMulti(respuestasMultiples);
                respuestasPruebasAlumnosListRespuestasPruebasAlumnos = em.merge(respuestasPruebasAlumnosListRespuestasPruebasAlumnos);
                if (oldFkRespuestaMultiOfRespuestasPruebasAlumnosListRespuestasPruebasAlumnos != null) {
                    oldFkRespuestaMultiOfRespuestasPruebasAlumnosListRespuestasPruebasAlumnos.getRespuestasPruebasAlumnosList().remove(respuestasPruebasAlumnosListRespuestasPruebasAlumnos);
                    oldFkRespuestaMultiOfRespuestasPruebasAlumnosListRespuestasPruebasAlumnos = em.merge(oldFkRespuestaMultiOfRespuestasPruebasAlumnosListRespuestasPruebasAlumnos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRespuestasMultiples(respuestasMultiples.getId()) != null) {
                throw new PreexistingEntityException("RespuestasMultiples " + respuestasMultiples + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RespuestasMultiples respuestasMultiples) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RespuestasMultiples persistentRespuestasMultiples = em.find(RespuestasMultiples.class, respuestasMultiples.getId());
            List<RespuestasPruebasAlumnos> respuestasPruebasAlumnosListOld = persistentRespuestasMultiples.getRespuestasPruebasAlumnosList();
            List<RespuestasPruebasAlumnos> respuestasPruebasAlumnosListNew = respuestasMultiples.getRespuestasPruebasAlumnosList();
            List<RespuestasPruebasAlumnos> attachedRespuestasPruebasAlumnosListNew = new ArrayList<RespuestasPruebasAlumnos>();
            for (RespuestasPruebasAlumnos respuestasPruebasAlumnosListNewRespuestasPruebasAlumnosToAttach : respuestasPruebasAlumnosListNew) {
                respuestasPruebasAlumnosListNewRespuestasPruebasAlumnosToAttach = em.getReference(respuestasPruebasAlumnosListNewRespuestasPruebasAlumnosToAttach.getClass(), respuestasPruebasAlumnosListNewRespuestasPruebasAlumnosToAttach.getId());
                attachedRespuestasPruebasAlumnosListNew.add(respuestasPruebasAlumnosListNewRespuestasPruebasAlumnosToAttach);
            }
            respuestasPruebasAlumnosListNew = attachedRespuestasPruebasAlumnosListNew;
            respuestasMultiples.setRespuestasPruebasAlumnosList(respuestasPruebasAlumnosListNew);
            respuestasMultiples = em.merge(respuestasMultiples);
            for (RespuestasPruebasAlumnos respuestasPruebasAlumnosListOldRespuestasPruebasAlumnos : respuestasPruebasAlumnosListOld) {
                if (!respuestasPruebasAlumnosListNew.contains(respuestasPruebasAlumnosListOldRespuestasPruebasAlumnos)) {
                    respuestasPruebasAlumnosListOldRespuestasPruebasAlumnos.setFkRespuestaMulti(null);
                    respuestasPruebasAlumnosListOldRespuestasPruebasAlumnos = em.merge(respuestasPruebasAlumnosListOldRespuestasPruebasAlumnos);
                }
            }
            for (RespuestasPruebasAlumnos respuestasPruebasAlumnosListNewRespuestasPruebasAlumnos : respuestasPruebasAlumnosListNew) {
                if (!respuestasPruebasAlumnosListOld.contains(respuestasPruebasAlumnosListNewRespuestasPruebasAlumnos)) {
                    RespuestasMultiples oldFkRespuestaMultiOfRespuestasPruebasAlumnosListNewRespuestasPruebasAlumnos = respuestasPruebasAlumnosListNewRespuestasPruebasAlumnos.getFkRespuestaMulti();
                    respuestasPruebasAlumnosListNewRespuestasPruebasAlumnos.setFkRespuestaMulti(respuestasMultiples);
                    respuestasPruebasAlumnosListNewRespuestasPruebasAlumnos = em.merge(respuestasPruebasAlumnosListNewRespuestasPruebasAlumnos);
                    if (oldFkRespuestaMultiOfRespuestasPruebasAlumnosListNewRespuestasPruebasAlumnos != null && !oldFkRespuestaMultiOfRespuestasPruebasAlumnosListNewRespuestasPruebasAlumnos.equals(respuestasMultiples)) {
                        oldFkRespuestaMultiOfRespuestasPruebasAlumnosListNewRespuestasPruebasAlumnos.getRespuestasPruebasAlumnosList().remove(respuestasPruebasAlumnosListNewRespuestasPruebasAlumnos);
                        oldFkRespuestaMultiOfRespuestasPruebasAlumnosListNewRespuestasPruebasAlumnos = em.merge(oldFkRespuestaMultiOfRespuestasPruebasAlumnosListNewRespuestasPruebasAlumnos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = respuestasMultiples.getId();
                if (findRespuestasMultiples(id) == null) {
                    throw new NonexistentEntityException("The respuestasMultiples with id " + id + " no longer exists.");
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
            RespuestasMultiples respuestasMultiples;
            try {
                respuestasMultiples = em.getReference(RespuestasMultiples.class, id);
                respuestasMultiples.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The respuestasMultiples with id " + id + " no longer exists.", enfe);
            }
            List<RespuestasPruebasAlumnos> respuestasPruebasAlumnosList = respuestasMultiples.getRespuestasPruebasAlumnosList();
            for (RespuestasPruebasAlumnos respuestasPruebasAlumnosListRespuestasPruebasAlumnos : respuestasPruebasAlumnosList) {
                respuestasPruebasAlumnosListRespuestasPruebasAlumnos.setFkRespuestaMulti(null);
                respuestasPruebasAlumnosListRespuestasPruebasAlumnos = em.merge(respuestasPruebasAlumnosListRespuestasPruebasAlumnos);
            }
            em.remove(respuestasMultiples);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RespuestasMultiples> findRespuestasMultiplesEntities() {
        return findRespuestasMultiplesEntities(true, -1, -1);
    }

    public List<RespuestasMultiples> findRespuestasMultiplesEntities(int maxResults, int firstResult) {
        return findRespuestasMultiplesEntities(false, maxResults, firstResult);
    }

    private List<RespuestasMultiples> findRespuestasMultiplesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RespuestasMultiples.class));
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

    public RespuestasMultiples findRespuestasMultiples(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RespuestasMultiples.class, id);
        } finally {
            em.close();
        }
    }

    public int getRespuestasMultiplesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RespuestasMultiples> rt = cq.from(RespuestasMultiples.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
