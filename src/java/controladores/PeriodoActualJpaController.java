/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import controladores.exceptions.PreexistingEntityException;
import entidad.PeriodoActual;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidad.Secciones;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author juanc
 */
public class PeriodoActualJpaController implements Serializable {

    public PeriodoActualJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PeriodoActual periodoActual) throws PreexistingEntityException, Exception {
        if (periodoActual.getSeccionesList() == null) {
            periodoActual.setSeccionesList(new ArrayList<Secciones>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Secciones> attachedSeccionesList = new ArrayList<Secciones>();
            for (Secciones seccionesListSeccionesToAttach : periodoActual.getSeccionesList()) {
                seccionesListSeccionesToAttach = em.getReference(seccionesListSeccionesToAttach.getClass(), seccionesListSeccionesToAttach.getId());
                attachedSeccionesList.add(seccionesListSeccionesToAttach);
            }
            periodoActual.setSeccionesList(attachedSeccionesList);
            em.persist(periodoActual);
            for (Secciones seccionesListSecciones : periodoActual.getSeccionesList()) {
                PeriodoActual oldFkPeriodoActualOfSeccionesListSecciones = seccionesListSecciones.getFkPeriodoActual();
                seccionesListSecciones.setFkPeriodoActual(periodoActual);
                seccionesListSecciones = em.merge(seccionesListSecciones);
                if (oldFkPeriodoActualOfSeccionesListSecciones != null) {
                    oldFkPeriodoActualOfSeccionesListSecciones.getSeccionesList().remove(seccionesListSecciones);
                    oldFkPeriodoActualOfSeccionesListSecciones = em.merge(oldFkPeriodoActualOfSeccionesListSecciones);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPeriodoActual(periodoActual.getId()) != null) {
                throw new PreexistingEntityException("PeriodoActual " + periodoActual + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PeriodoActual periodoActual) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PeriodoActual persistentPeriodoActual = em.find(PeriodoActual.class, periodoActual.getId());
            List<Secciones> seccionesListOld = persistentPeriodoActual.getSeccionesList();
            List<Secciones> seccionesListNew = periodoActual.getSeccionesList();
            List<Secciones> attachedSeccionesListNew = new ArrayList<Secciones>();
            for (Secciones seccionesListNewSeccionesToAttach : seccionesListNew) {
                seccionesListNewSeccionesToAttach = em.getReference(seccionesListNewSeccionesToAttach.getClass(), seccionesListNewSeccionesToAttach.getId());
                attachedSeccionesListNew.add(seccionesListNewSeccionesToAttach);
            }
            seccionesListNew = attachedSeccionesListNew;
            periodoActual.setSeccionesList(seccionesListNew);
            periodoActual = em.merge(periodoActual);
            for (Secciones seccionesListOldSecciones : seccionesListOld) {
                if (!seccionesListNew.contains(seccionesListOldSecciones)) {
                    seccionesListOldSecciones.setFkPeriodoActual(null);
                    seccionesListOldSecciones = em.merge(seccionesListOldSecciones);
                }
            }
            for (Secciones seccionesListNewSecciones : seccionesListNew) {
                if (!seccionesListOld.contains(seccionesListNewSecciones)) {
                    PeriodoActual oldFkPeriodoActualOfSeccionesListNewSecciones = seccionesListNewSecciones.getFkPeriodoActual();
                    seccionesListNewSecciones.setFkPeriodoActual(periodoActual);
                    seccionesListNewSecciones = em.merge(seccionesListNewSecciones);
                    if (oldFkPeriodoActualOfSeccionesListNewSecciones != null && !oldFkPeriodoActualOfSeccionesListNewSecciones.equals(periodoActual)) {
                        oldFkPeriodoActualOfSeccionesListNewSecciones.getSeccionesList().remove(seccionesListNewSecciones);
                        oldFkPeriodoActualOfSeccionesListNewSecciones = em.merge(oldFkPeriodoActualOfSeccionesListNewSecciones);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = periodoActual.getId();
                if (findPeriodoActual(id) == null) {
                    throw new NonexistentEntityException("The periodoActual with id " + id + " no longer exists.");
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
            PeriodoActual periodoActual;
            try {
                periodoActual = em.getReference(PeriodoActual.class, id);
                periodoActual.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The periodoActual with id " + id + " no longer exists.", enfe);
            }
            List<Secciones> seccionesList = periodoActual.getSeccionesList();
            for (Secciones seccionesListSecciones : seccionesList) {
                seccionesListSecciones.setFkPeriodoActual(null);
                seccionesListSecciones = em.merge(seccionesListSecciones);
            }
            em.remove(periodoActual);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PeriodoActual> findPeriodoActualEntities() {
        return findPeriodoActualEntities(true, -1, -1);
    }

    public List<PeriodoActual> findPeriodoActualEntities(int maxResults, int firstResult) {
        return findPeriodoActualEntities(false, maxResults, firstResult);
    }

    private List<PeriodoActual> findPeriodoActualEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PeriodoActual.class));
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

    public PeriodoActual findPeriodoActual(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PeriodoActual.class, id);
        } finally {
            em.close();
        }
    }

    public int getPeriodoActualCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PeriodoActual> rt = cq.from(PeriodoActual.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
