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
import entidad.Reclamos;
import entidad.TiposReclamos;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author juanc
 */
public class TiposReclamosJpaController implements Serializable {

    public TiposReclamosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TiposReclamos tiposReclamos) throws PreexistingEntityException, Exception {
        if (tiposReclamos.getReclamosList() == null) {
            tiposReclamos.setReclamosList(new ArrayList<Reclamos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Reclamos> attachedReclamosList = new ArrayList<Reclamos>();
            for (Reclamos reclamosListReclamosToAttach : tiposReclamos.getReclamosList()) {
                reclamosListReclamosToAttach = em.getReference(reclamosListReclamosToAttach.getClass(), reclamosListReclamosToAttach.getId());
                attachedReclamosList.add(reclamosListReclamosToAttach);
            }
            tiposReclamos.setReclamosList(attachedReclamosList);
            em.persist(tiposReclamos);
            for (Reclamos reclamosListReclamos : tiposReclamos.getReclamosList()) {
                TiposReclamos oldFkTipoReclamoOfReclamosListReclamos = reclamosListReclamos.getFkTipoReclamo();
                reclamosListReclamos.setFkTipoReclamo(tiposReclamos);
                reclamosListReclamos = em.merge(reclamosListReclamos);
                if (oldFkTipoReclamoOfReclamosListReclamos != null) {
                    oldFkTipoReclamoOfReclamosListReclamos.getReclamosList().remove(reclamosListReclamos);
                    oldFkTipoReclamoOfReclamosListReclamos = em.merge(oldFkTipoReclamoOfReclamosListReclamos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTiposReclamos(tiposReclamos.getId()) != null) {
                throw new PreexistingEntityException("TiposReclamos " + tiposReclamos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TiposReclamos tiposReclamos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TiposReclamos persistentTiposReclamos = em.find(TiposReclamos.class, tiposReclamos.getId());
            List<Reclamos> reclamosListOld = persistentTiposReclamos.getReclamosList();
            List<Reclamos> reclamosListNew = tiposReclamos.getReclamosList();
            List<Reclamos> attachedReclamosListNew = new ArrayList<Reclamos>();
            for (Reclamos reclamosListNewReclamosToAttach : reclamosListNew) {
                reclamosListNewReclamosToAttach = em.getReference(reclamosListNewReclamosToAttach.getClass(), reclamosListNewReclamosToAttach.getId());
                attachedReclamosListNew.add(reclamosListNewReclamosToAttach);
            }
            reclamosListNew = attachedReclamosListNew;
            tiposReclamos.setReclamosList(reclamosListNew);
            tiposReclamos = em.merge(tiposReclamos);
            for (Reclamos reclamosListOldReclamos : reclamosListOld) {
                if (!reclamosListNew.contains(reclamosListOldReclamos)) {
                    reclamosListOldReclamos.setFkTipoReclamo(null);
                    reclamosListOldReclamos = em.merge(reclamosListOldReclamos);
                }
            }
            for (Reclamos reclamosListNewReclamos : reclamosListNew) {
                if (!reclamosListOld.contains(reclamosListNewReclamos)) {
                    TiposReclamos oldFkTipoReclamoOfReclamosListNewReclamos = reclamosListNewReclamos.getFkTipoReclamo();
                    reclamosListNewReclamos.setFkTipoReclamo(tiposReclamos);
                    reclamosListNewReclamos = em.merge(reclamosListNewReclamos);
                    if (oldFkTipoReclamoOfReclamosListNewReclamos != null && !oldFkTipoReclamoOfReclamosListNewReclamos.equals(tiposReclamos)) {
                        oldFkTipoReclamoOfReclamosListNewReclamos.getReclamosList().remove(reclamosListNewReclamos);
                        oldFkTipoReclamoOfReclamosListNewReclamos = em.merge(oldFkTipoReclamoOfReclamosListNewReclamos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = tiposReclamos.getId();
                if (findTiposReclamos(id) == null) {
                    throw new NonexistentEntityException("The tiposReclamos with id " + id + " no longer exists.");
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
            TiposReclamos tiposReclamos;
            try {
                tiposReclamos = em.getReference(TiposReclamos.class, id);
                tiposReclamos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tiposReclamos with id " + id + " no longer exists.", enfe);
            }
            List<Reclamos> reclamosList = tiposReclamos.getReclamosList();
            for (Reclamos reclamosListReclamos : reclamosList) {
                reclamosListReclamos.setFkTipoReclamo(null);
                reclamosListReclamos = em.merge(reclamosListReclamos);
            }
            em.remove(tiposReclamos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TiposReclamos> findTiposReclamosEntities() {
        return findTiposReclamosEntities(true, -1, -1);
    }

    public List<TiposReclamos> findTiposReclamosEntities(int maxResults, int firstResult) {
        return findTiposReclamosEntities(false, maxResults, firstResult);
    }

    private List<TiposReclamos> findTiposReclamosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TiposReclamos.class));
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

    public TiposReclamos findTiposReclamos(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TiposReclamos.class, id);
        } finally {
            em.close();
        }
    }

    public int getTiposReclamosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TiposReclamos> rt = cq.from(TiposReclamos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
