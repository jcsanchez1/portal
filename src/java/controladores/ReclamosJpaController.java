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
import entidad.Maestros;
import entidad.Reclamos;
import entidad.TiposReclamos;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author juanc
 */
public class ReclamosJpaController implements Serializable {

    public ReclamosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Reclamos reclamos) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Maestros fkDocente = reclamos.getFkDocente();
            if (fkDocente != null) {
                fkDocente = em.getReference(fkDocente.getClass(), fkDocente.getId());
                reclamos.setFkDocente(fkDocente);
            }
            TiposReclamos fkTipoReclamo = reclamos.getFkTipoReclamo();
            if (fkTipoReclamo != null) {
                fkTipoReclamo = em.getReference(fkTipoReclamo.getClass(), fkTipoReclamo.getId());
                reclamos.setFkTipoReclamo(fkTipoReclamo);
            }
            em.persist(reclamos);
            if (fkDocente != null) {
                fkDocente.getReclamosList().add(reclamos);
                fkDocente = em.merge(fkDocente);
            }
            if (fkTipoReclamo != null) {
                fkTipoReclamo.getReclamosList().add(reclamos);
                fkTipoReclamo = em.merge(fkTipoReclamo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findReclamos(reclamos.getId()) != null) {
                throw new PreexistingEntityException("Reclamos " + reclamos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Reclamos reclamos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reclamos persistentReclamos = em.find(Reclamos.class, reclamos.getId());
            Maestros fkDocenteOld = persistentReclamos.getFkDocente();
            Maestros fkDocenteNew = reclamos.getFkDocente();
            TiposReclamos fkTipoReclamoOld = persistentReclamos.getFkTipoReclamo();
            TiposReclamos fkTipoReclamoNew = reclamos.getFkTipoReclamo();
            if (fkDocenteNew != null) {
                fkDocenteNew = em.getReference(fkDocenteNew.getClass(), fkDocenteNew.getId());
                reclamos.setFkDocente(fkDocenteNew);
            }
            if (fkTipoReclamoNew != null) {
                fkTipoReclamoNew = em.getReference(fkTipoReclamoNew.getClass(), fkTipoReclamoNew.getId());
                reclamos.setFkTipoReclamo(fkTipoReclamoNew);
            }
            reclamos = em.merge(reclamos);
            if (fkDocenteOld != null && !fkDocenteOld.equals(fkDocenteNew)) {
                fkDocenteOld.getReclamosList().remove(reclamos);
                fkDocenteOld = em.merge(fkDocenteOld);
            }
            if (fkDocenteNew != null && !fkDocenteNew.equals(fkDocenteOld)) {
                fkDocenteNew.getReclamosList().add(reclamos);
                fkDocenteNew = em.merge(fkDocenteNew);
            }
            if (fkTipoReclamoOld != null && !fkTipoReclamoOld.equals(fkTipoReclamoNew)) {
                fkTipoReclamoOld.getReclamosList().remove(reclamos);
                fkTipoReclamoOld = em.merge(fkTipoReclamoOld);
            }
            if (fkTipoReclamoNew != null && !fkTipoReclamoNew.equals(fkTipoReclamoOld)) {
                fkTipoReclamoNew.getReclamosList().add(reclamos);
                fkTipoReclamoNew = em.merge(fkTipoReclamoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = reclamos.getId();
                if (findReclamos(id) == null) {
                    throw new NonexistentEntityException("The reclamos with id " + id + " no longer exists.");
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
            Reclamos reclamos;
            try {
                reclamos = em.getReference(Reclamos.class, id);
                reclamos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reclamos with id " + id + " no longer exists.", enfe);
            }
            Maestros fkDocente = reclamos.getFkDocente();
            if (fkDocente != null) {
                fkDocente.getReclamosList().remove(reclamos);
                fkDocente = em.merge(fkDocente);
            }
            TiposReclamos fkTipoReclamo = reclamos.getFkTipoReclamo();
            if (fkTipoReclamo != null) {
                fkTipoReclamo.getReclamosList().remove(reclamos);
                fkTipoReclamo = em.merge(fkTipoReclamo);
            }
            em.remove(reclamos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Reclamos> findReclamosEntities() {
        return findReclamosEntities(true, -1, -1);
    }

    public List<Reclamos> findReclamosEntities(int maxResults, int firstResult) {
        return findReclamosEntities(false, maxResults, firstResult);
    }

    private List<Reclamos> findReclamosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Reclamos.class));
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

    public Reclamos findReclamos(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Reclamos.class, id);
        } finally {
            em.close();
        }
    }

    public int getReclamosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Reclamos> rt = cq.from(Reclamos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
