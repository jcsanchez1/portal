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
import entidad.PreguntasPruebasSecciones;
import entidad.PreguntasVofs;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author juanc
 */
public class PreguntasVofsJpaController implements Serializable {

    public PreguntasVofsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PreguntasVofs preguntasVofs) throws PreexistingEntityException, Exception {
        if (preguntasVofs.getPreguntasPruebasSeccionesList() == null) {
            preguntasVofs.setPreguntasPruebasSeccionesList(new ArrayList<PreguntasPruebasSecciones>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<PreguntasPruebasSecciones> attachedPreguntasPruebasSeccionesList = new ArrayList<PreguntasPruebasSecciones>();
            for (PreguntasPruebasSecciones preguntasPruebasSeccionesListPreguntasPruebasSeccionesToAttach : preguntasVofs.getPreguntasPruebasSeccionesList()) {
                preguntasPruebasSeccionesListPreguntasPruebasSeccionesToAttach = em.getReference(preguntasPruebasSeccionesListPreguntasPruebasSeccionesToAttach.getClass(), preguntasPruebasSeccionesListPreguntasPruebasSeccionesToAttach.getId());
                attachedPreguntasPruebasSeccionesList.add(preguntasPruebasSeccionesListPreguntasPruebasSeccionesToAttach);
            }
            preguntasVofs.setPreguntasPruebasSeccionesList(attachedPreguntasPruebasSeccionesList);
            em.persist(preguntasVofs);
            for (PreguntasPruebasSecciones preguntasPruebasSeccionesListPreguntasPruebasSecciones : preguntasVofs.getPreguntasPruebasSeccionesList()) {
                PreguntasVofs oldFkPreguntaVofOfPreguntasPruebasSeccionesListPreguntasPruebasSecciones = preguntasPruebasSeccionesListPreguntasPruebasSecciones.getFkPreguntaVof();
                preguntasPruebasSeccionesListPreguntasPruebasSecciones.setFkPreguntaVof(preguntasVofs);
                preguntasPruebasSeccionesListPreguntasPruebasSecciones = em.merge(preguntasPruebasSeccionesListPreguntasPruebasSecciones);
                if (oldFkPreguntaVofOfPreguntasPruebasSeccionesListPreguntasPruebasSecciones != null) {
                    oldFkPreguntaVofOfPreguntasPruebasSeccionesListPreguntasPruebasSecciones.getPreguntasPruebasSeccionesList().remove(preguntasPruebasSeccionesListPreguntasPruebasSecciones);
                    oldFkPreguntaVofOfPreguntasPruebasSeccionesListPreguntasPruebasSecciones = em.merge(oldFkPreguntaVofOfPreguntasPruebasSeccionesListPreguntasPruebasSecciones);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPreguntasVofs(preguntasVofs.getId()) != null) {
                throw new PreexistingEntityException("PreguntasVofs " + preguntasVofs + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PreguntasVofs preguntasVofs) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PreguntasVofs persistentPreguntasVofs = em.find(PreguntasVofs.class, preguntasVofs.getId());
            List<PreguntasPruebasSecciones> preguntasPruebasSeccionesListOld = persistentPreguntasVofs.getPreguntasPruebasSeccionesList();
            List<PreguntasPruebasSecciones> preguntasPruebasSeccionesListNew = preguntasVofs.getPreguntasPruebasSeccionesList();
            List<PreguntasPruebasSecciones> attachedPreguntasPruebasSeccionesListNew = new ArrayList<PreguntasPruebasSecciones>();
            for (PreguntasPruebasSecciones preguntasPruebasSeccionesListNewPreguntasPruebasSeccionesToAttach : preguntasPruebasSeccionesListNew) {
                preguntasPruebasSeccionesListNewPreguntasPruebasSeccionesToAttach = em.getReference(preguntasPruebasSeccionesListNewPreguntasPruebasSeccionesToAttach.getClass(), preguntasPruebasSeccionesListNewPreguntasPruebasSeccionesToAttach.getId());
                attachedPreguntasPruebasSeccionesListNew.add(preguntasPruebasSeccionesListNewPreguntasPruebasSeccionesToAttach);
            }
            preguntasPruebasSeccionesListNew = attachedPreguntasPruebasSeccionesListNew;
            preguntasVofs.setPreguntasPruebasSeccionesList(preguntasPruebasSeccionesListNew);
            preguntasVofs = em.merge(preguntasVofs);
            for (PreguntasPruebasSecciones preguntasPruebasSeccionesListOldPreguntasPruebasSecciones : preguntasPruebasSeccionesListOld) {
                if (!preguntasPruebasSeccionesListNew.contains(preguntasPruebasSeccionesListOldPreguntasPruebasSecciones)) {
                    preguntasPruebasSeccionesListOldPreguntasPruebasSecciones.setFkPreguntaVof(null);
                    preguntasPruebasSeccionesListOldPreguntasPruebasSecciones = em.merge(preguntasPruebasSeccionesListOldPreguntasPruebasSecciones);
                }
            }
            for (PreguntasPruebasSecciones preguntasPruebasSeccionesListNewPreguntasPruebasSecciones : preguntasPruebasSeccionesListNew) {
                if (!preguntasPruebasSeccionesListOld.contains(preguntasPruebasSeccionesListNewPreguntasPruebasSecciones)) {
                    PreguntasVofs oldFkPreguntaVofOfPreguntasPruebasSeccionesListNewPreguntasPruebasSecciones = preguntasPruebasSeccionesListNewPreguntasPruebasSecciones.getFkPreguntaVof();
                    preguntasPruebasSeccionesListNewPreguntasPruebasSecciones.setFkPreguntaVof(preguntasVofs);
                    preguntasPruebasSeccionesListNewPreguntasPruebasSecciones = em.merge(preguntasPruebasSeccionesListNewPreguntasPruebasSecciones);
                    if (oldFkPreguntaVofOfPreguntasPruebasSeccionesListNewPreguntasPruebasSecciones != null && !oldFkPreguntaVofOfPreguntasPruebasSeccionesListNewPreguntasPruebasSecciones.equals(preguntasVofs)) {
                        oldFkPreguntaVofOfPreguntasPruebasSeccionesListNewPreguntasPruebasSecciones.getPreguntasPruebasSeccionesList().remove(preguntasPruebasSeccionesListNewPreguntasPruebasSecciones);
                        oldFkPreguntaVofOfPreguntasPruebasSeccionesListNewPreguntasPruebasSecciones = em.merge(oldFkPreguntaVofOfPreguntasPruebasSeccionesListNewPreguntasPruebasSecciones);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = preguntasVofs.getId();
                if (findPreguntasVofs(id) == null) {
                    throw new NonexistentEntityException("The preguntasVofs with id " + id + " no longer exists.");
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
            PreguntasVofs preguntasVofs;
            try {
                preguntasVofs = em.getReference(PreguntasVofs.class, id);
                preguntasVofs.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The preguntasVofs with id " + id + " no longer exists.", enfe);
            }
            List<PreguntasPruebasSecciones> preguntasPruebasSeccionesList = preguntasVofs.getPreguntasPruebasSeccionesList();
            for (PreguntasPruebasSecciones preguntasPruebasSeccionesListPreguntasPruebasSecciones : preguntasPruebasSeccionesList) {
                preguntasPruebasSeccionesListPreguntasPruebasSecciones.setFkPreguntaVof(null);
                preguntasPruebasSeccionesListPreguntasPruebasSecciones = em.merge(preguntasPruebasSeccionesListPreguntasPruebasSecciones);
            }
            em.remove(preguntasVofs);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PreguntasVofs> findPreguntasVofsEntities() {
        return findPreguntasVofsEntities(true, -1, -1);
    }

    public List<PreguntasVofs> findPreguntasVofsEntities(int maxResults, int firstResult) {
        return findPreguntasVofsEntities(false, maxResults, firstResult);
    }

    private List<PreguntasVofs> findPreguntasVofsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PreguntasVofs.class));
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

    public PreguntasVofs findPreguntasVofs(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PreguntasVofs.class, id);
        } finally {
            em.close();
        }
    }

    public int getPreguntasVofsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PreguntasVofs> rt = cq.from(PreguntasVofs.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
