/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import controladores.exceptions.PreexistingEntityException;
import entidad.RespuestasBreves;
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
public class RespuestasBrevesJpaController implements Serializable {

    public RespuestasBrevesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RespuestasBreves respuestasBreves) throws PreexistingEntityException, Exception {
        if (respuestasBreves.getRespuestasPruebasAlumnosList() == null) {
            respuestasBreves.setRespuestasPruebasAlumnosList(new ArrayList<RespuestasPruebasAlumnos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<RespuestasPruebasAlumnos> attachedRespuestasPruebasAlumnosList = new ArrayList<RespuestasPruebasAlumnos>();
            for (RespuestasPruebasAlumnos respuestasPruebasAlumnosListRespuestasPruebasAlumnosToAttach : respuestasBreves.getRespuestasPruebasAlumnosList()) {
                respuestasPruebasAlumnosListRespuestasPruebasAlumnosToAttach = em.getReference(respuestasPruebasAlumnosListRespuestasPruebasAlumnosToAttach.getClass(), respuestasPruebasAlumnosListRespuestasPruebasAlumnosToAttach.getId());
                attachedRespuestasPruebasAlumnosList.add(respuestasPruebasAlumnosListRespuestasPruebasAlumnosToAttach);
            }
            respuestasBreves.setRespuestasPruebasAlumnosList(attachedRespuestasPruebasAlumnosList);
            em.persist(respuestasBreves);
            for (RespuestasPruebasAlumnos respuestasPruebasAlumnosListRespuestasPruebasAlumnos : respuestasBreves.getRespuestasPruebasAlumnosList()) {
                RespuestasBreves oldFkRespuestaBreveOfRespuestasPruebasAlumnosListRespuestasPruebasAlumnos = respuestasPruebasAlumnosListRespuestasPruebasAlumnos.getFkRespuestaBreve();
                respuestasPruebasAlumnosListRespuestasPruebasAlumnos.setFkRespuestaBreve(respuestasBreves);
                respuestasPruebasAlumnosListRespuestasPruebasAlumnos = em.merge(respuestasPruebasAlumnosListRespuestasPruebasAlumnos);
                if (oldFkRespuestaBreveOfRespuestasPruebasAlumnosListRespuestasPruebasAlumnos != null) {
                    oldFkRespuestaBreveOfRespuestasPruebasAlumnosListRespuestasPruebasAlumnos.getRespuestasPruebasAlumnosList().remove(respuestasPruebasAlumnosListRespuestasPruebasAlumnos);
                    oldFkRespuestaBreveOfRespuestasPruebasAlumnosListRespuestasPruebasAlumnos = em.merge(oldFkRespuestaBreveOfRespuestasPruebasAlumnosListRespuestasPruebasAlumnos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRespuestasBreves(respuestasBreves.getId()) != null) {
                throw new PreexistingEntityException("RespuestasBreves " + respuestasBreves + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RespuestasBreves respuestasBreves) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RespuestasBreves persistentRespuestasBreves = em.find(RespuestasBreves.class, respuestasBreves.getId());
            List<RespuestasPruebasAlumnos> respuestasPruebasAlumnosListOld = persistentRespuestasBreves.getRespuestasPruebasAlumnosList();
            List<RespuestasPruebasAlumnos> respuestasPruebasAlumnosListNew = respuestasBreves.getRespuestasPruebasAlumnosList();
            List<RespuestasPruebasAlumnos> attachedRespuestasPruebasAlumnosListNew = new ArrayList<RespuestasPruebasAlumnos>();
            for (RespuestasPruebasAlumnos respuestasPruebasAlumnosListNewRespuestasPruebasAlumnosToAttach : respuestasPruebasAlumnosListNew) {
                respuestasPruebasAlumnosListNewRespuestasPruebasAlumnosToAttach = em.getReference(respuestasPruebasAlumnosListNewRespuestasPruebasAlumnosToAttach.getClass(), respuestasPruebasAlumnosListNewRespuestasPruebasAlumnosToAttach.getId());
                attachedRespuestasPruebasAlumnosListNew.add(respuestasPruebasAlumnosListNewRespuestasPruebasAlumnosToAttach);
            }
            respuestasPruebasAlumnosListNew = attachedRespuestasPruebasAlumnosListNew;
            respuestasBreves.setRespuestasPruebasAlumnosList(respuestasPruebasAlumnosListNew);
            respuestasBreves = em.merge(respuestasBreves);
            for (RespuestasPruebasAlumnos respuestasPruebasAlumnosListOldRespuestasPruebasAlumnos : respuestasPruebasAlumnosListOld) {
                if (!respuestasPruebasAlumnosListNew.contains(respuestasPruebasAlumnosListOldRespuestasPruebasAlumnos)) {
                    respuestasPruebasAlumnosListOldRespuestasPruebasAlumnos.setFkRespuestaBreve(null);
                    respuestasPruebasAlumnosListOldRespuestasPruebasAlumnos = em.merge(respuestasPruebasAlumnosListOldRespuestasPruebasAlumnos);
                }
            }
            for (RespuestasPruebasAlumnos respuestasPruebasAlumnosListNewRespuestasPruebasAlumnos : respuestasPruebasAlumnosListNew) {
                if (!respuestasPruebasAlumnosListOld.contains(respuestasPruebasAlumnosListNewRespuestasPruebasAlumnos)) {
                    RespuestasBreves oldFkRespuestaBreveOfRespuestasPruebasAlumnosListNewRespuestasPruebasAlumnos = respuestasPruebasAlumnosListNewRespuestasPruebasAlumnos.getFkRespuestaBreve();
                    respuestasPruebasAlumnosListNewRespuestasPruebasAlumnos.setFkRespuestaBreve(respuestasBreves);
                    respuestasPruebasAlumnosListNewRespuestasPruebasAlumnos = em.merge(respuestasPruebasAlumnosListNewRespuestasPruebasAlumnos);
                    if (oldFkRespuestaBreveOfRespuestasPruebasAlumnosListNewRespuestasPruebasAlumnos != null && !oldFkRespuestaBreveOfRespuestasPruebasAlumnosListNewRespuestasPruebasAlumnos.equals(respuestasBreves)) {
                        oldFkRespuestaBreveOfRespuestasPruebasAlumnosListNewRespuestasPruebasAlumnos.getRespuestasPruebasAlumnosList().remove(respuestasPruebasAlumnosListNewRespuestasPruebasAlumnos);
                        oldFkRespuestaBreveOfRespuestasPruebasAlumnosListNewRespuestasPruebasAlumnos = em.merge(oldFkRespuestaBreveOfRespuestasPruebasAlumnosListNewRespuestasPruebasAlumnos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = respuestasBreves.getId();
                if (findRespuestasBreves(id) == null) {
                    throw new NonexistentEntityException("The respuestasBreves with id " + id + " no longer exists.");
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
            RespuestasBreves respuestasBreves;
            try {
                respuestasBreves = em.getReference(RespuestasBreves.class, id);
                respuestasBreves.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The respuestasBreves with id " + id + " no longer exists.", enfe);
            }
            List<RespuestasPruebasAlumnos> respuestasPruebasAlumnosList = respuestasBreves.getRespuestasPruebasAlumnosList();
            for (RespuestasPruebasAlumnos respuestasPruebasAlumnosListRespuestasPruebasAlumnos : respuestasPruebasAlumnosList) {
                respuestasPruebasAlumnosListRespuestasPruebasAlumnos.setFkRespuestaBreve(null);
                respuestasPruebasAlumnosListRespuestasPruebasAlumnos = em.merge(respuestasPruebasAlumnosListRespuestasPruebasAlumnos);
            }
            em.remove(respuestasBreves);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RespuestasBreves> findRespuestasBrevesEntities() {
        return findRespuestasBrevesEntities(true, -1, -1);
    }

    public List<RespuestasBreves> findRespuestasBrevesEntities(int maxResults, int firstResult) {
        return findRespuestasBrevesEntities(false, maxResults, firstResult);
    }

    private List<RespuestasBreves> findRespuestasBrevesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RespuestasBreves.class));
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

    public RespuestasBreves findRespuestasBreves(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RespuestasBreves.class, id);
        } finally {
            em.close();
        }
    }

    public int getRespuestasBrevesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RespuestasBreves> rt = cq.from(RespuestasBreves.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
