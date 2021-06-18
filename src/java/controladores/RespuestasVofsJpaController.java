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
import entidad.RespuestasPruebasAlumnos;
import entidad.RespuestasVofs;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author juanc
 */
public class RespuestasVofsJpaController implements Serializable {

    public RespuestasVofsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RespuestasVofs respuestasVofs) throws PreexistingEntityException, Exception {
        if (respuestasVofs.getRespuestasPruebasAlumnosList() == null) {
            respuestasVofs.setRespuestasPruebasAlumnosList(new ArrayList<RespuestasPruebasAlumnos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<RespuestasPruebasAlumnos> attachedRespuestasPruebasAlumnosList = new ArrayList<RespuestasPruebasAlumnos>();
            for (RespuestasPruebasAlumnos respuestasPruebasAlumnosListRespuestasPruebasAlumnosToAttach : respuestasVofs.getRespuestasPruebasAlumnosList()) {
                respuestasPruebasAlumnosListRespuestasPruebasAlumnosToAttach = em.getReference(respuestasPruebasAlumnosListRespuestasPruebasAlumnosToAttach.getClass(), respuestasPruebasAlumnosListRespuestasPruebasAlumnosToAttach.getId());
                attachedRespuestasPruebasAlumnosList.add(respuestasPruebasAlumnosListRespuestasPruebasAlumnosToAttach);
            }
            respuestasVofs.setRespuestasPruebasAlumnosList(attachedRespuestasPruebasAlumnosList);
            em.persist(respuestasVofs);
            for (RespuestasPruebasAlumnos respuestasPruebasAlumnosListRespuestasPruebasAlumnos : respuestasVofs.getRespuestasPruebasAlumnosList()) {
                RespuestasVofs oldFkRespuestaVofOfRespuestasPruebasAlumnosListRespuestasPruebasAlumnos = respuestasPruebasAlumnosListRespuestasPruebasAlumnos.getFkRespuestaVof();
                respuestasPruebasAlumnosListRespuestasPruebasAlumnos.setFkRespuestaVof(respuestasVofs);
                respuestasPruebasAlumnosListRespuestasPruebasAlumnos = em.merge(respuestasPruebasAlumnosListRespuestasPruebasAlumnos);
                if (oldFkRespuestaVofOfRespuestasPruebasAlumnosListRespuestasPruebasAlumnos != null) {
                    oldFkRespuestaVofOfRespuestasPruebasAlumnosListRespuestasPruebasAlumnos.getRespuestasPruebasAlumnosList().remove(respuestasPruebasAlumnosListRespuestasPruebasAlumnos);
                    oldFkRespuestaVofOfRespuestasPruebasAlumnosListRespuestasPruebasAlumnos = em.merge(oldFkRespuestaVofOfRespuestasPruebasAlumnosListRespuestasPruebasAlumnos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRespuestasVofs(respuestasVofs.getId()) != null) {
                throw new PreexistingEntityException("RespuestasVofs " + respuestasVofs + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RespuestasVofs respuestasVofs) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RespuestasVofs persistentRespuestasVofs = em.find(RespuestasVofs.class, respuestasVofs.getId());
            List<RespuestasPruebasAlumnos> respuestasPruebasAlumnosListOld = persistentRespuestasVofs.getRespuestasPruebasAlumnosList();
            List<RespuestasPruebasAlumnos> respuestasPruebasAlumnosListNew = respuestasVofs.getRespuestasPruebasAlumnosList();
            List<RespuestasPruebasAlumnos> attachedRespuestasPruebasAlumnosListNew = new ArrayList<RespuestasPruebasAlumnos>();
            for (RespuestasPruebasAlumnos respuestasPruebasAlumnosListNewRespuestasPruebasAlumnosToAttach : respuestasPruebasAlumnosListNew) {
                respuestasPruebasAlumnosListNewRespuestasPruebasAlumnosToAttach = em.getReference(respuestasPruebasAlumnosListNewRespuestasPruebasAlumnosToAttach.getClass(), respuestasPruebasAlumnosListNewRespuestasPruebasAlumnosToAttach.getId());
                attachedRespuestasPruebasAlumnosListNew.add(respuestasPruebasAlumnosListNewRespuestasPruebasAlumnosToAttach);
            }
            respuestasPruebasAlumnosListNew = attachedRespuestasPruebasAlumnosListNew;
            respuestasVofs.setRespuestasPruebasAlumnosList(respuestasPruebasAlumnosListNew);
            respuestasVofs = em.merge(respuestasVofs);
            for (RespuestasPruebasAlumnos respuestasPruebasAlumnosListOldRespuestasPruebasAlumnos : respuestasPruebasAlumnosListOld) {
                if (!respuestasPruebasAlumnosListNew.contains(respuestasPruebasAlumnosListOldRespuestasPruebasAlumnos)) {
                    respuestasPruebasAlumnosListOldRespuestasPruebasAlumnos.setFkRespuestaVof(null);
                    respuestasPruebasAlumnosListOldRespuestasPruebasAlumnos = em.merge(respuestasPruebasAlumnosListOldRespuestasPruebasAlumnos);
                }
            }
            for (RespuestasPruebasAlumnos respuestasPruebasAlumnosListNewRespuestasPruebasAlumnos : respuestasPruebasAlumnosListNew) {
                if (!respuestasPruebasAlumnosListOld.contains(respuestasPruebasAlumnosListNewRespuestasPruebasAlumnos)) {
                    RespuestasVofs oldFkRespuestaVofOfRespuestasPruebasAlumnosListNewRespuestasPruebasAlumnos = respuestasPruebasAlumnosListNewRespuestasPruebasAlumnos.getFkRespuestaVof();
                    respuestasPruebasAlumnosListNewRespuestasPruebasAlumnos.setFkRespuestaVof(respuestasVofs);
                    respuestasPruebasAlumnosListNewRespuestasPruebasAlumnos = em.merge(respuestasPruebasAlumnosListNewRespuestasPruebasAlumnos);
                    if (oldFkRespuestaVofOfRespuestasPruebasAlumnosListNewRespuestasPruebasAlumnos != null && !oldFkRespuestaVofOfRespuestasPruebasAlumnosListNewRespuestasPruebasAlumnos.equals(respuestasVofs)) {
                        oldFkRespuestaVofOfRespuestasPruebasAlumnosListNewRespuestasPruebasAlumnos.getRespuestasPruebasAlumnosList().remove(respuestasPruebasAlumnosListNewRespuestasPruebasAlumnos);
                        oldFkRespuestaVofOfRespuestasPruebasAlumnosListNewRespuestasPruebasAlumnos = em.merge(oldFkRespuestaVofOfRespuestasPruebasAlumnosListNewRespuestasPruebasAlumnos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = respuestasVofs.getId();
                if (findRespuestasVofs(id) == null) {
                    throw new NonexistentEntityException("The respuestasVofs with id " + id + " no longer exists.");
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
            RespuestasVofs respuestasVofs;
            try {
                respuestasVofs = em.getReference(RespuestasVofs.class, id);
                respuestasVofs.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The respuestasVofs with id " + id + " no longer exists.", enfe);
            }
            List<RespuestasPruebasAlumnos> respuestasPruebasAlumnosList = respuestasVofs.getRespuestasPruebasAlumnosList();
            for (RespuestasPruebasAlumnos respuestasPruebasAlumnosListRespuestasPruebasAlumnos : respuestasPruebasAlumnosList) {
                respuestasPruebasAlumnosListRespuestasPruebasAlumnos.setFkRespuestaVof(null);
                respuestasPruebasAlumnosListRespuestasPruebasAlumnos = em.merge(respuestasPruebasAlumnosListRespuestasPruebasAlumnos);
            }
            em.remove(respuestasVofs);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RespuestasVofs> findRespuestasVofsEntities() {
        return findRespuestasVofsEntities(true, -1, -1);
    }

    public List<RespuestasVofs> findRespuestasVofsEntities(int maxResults, int firstResult) {
        return findRespuestasVofsEntities(false, maxResults, firstResult);
    }

    private List<RespuestasVofs> findRespuestasVofsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RespuestasVofs.class));
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

    public RespuestasVofs findRespuestasVofs(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RespuestasVofs.class, id);
        } finally {
            em.close();
        }
    }

    public int getRespuestasVofsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RespuestasVofs> rt = cq.from(RespuestasVofs.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
