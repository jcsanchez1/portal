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
import entidad.Clases;
import entidad.ClasesPlanesEstudio;
import entidad.PlanesEstudios;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author juanc
 */
public class ClasesPlanesEstudioJpaController implements Serializable {

    public ClasesPlanesEstudioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ClasesPlanesEstudio clasesPlanesEstudio) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clases fkClase = clasesPlanesEstudio.getFkClase();
            if (fkClase != null) {
                fkClase = em.getReference(fkClase.getClass(), fkClase.getId());
                clasesPlanesEstudio.setFkClase(fkClase);
            }
            PlanesEstudios fkPlan = clasesPlanesEstudio.getFkPlan();
            if (fkPlan != null) {
                fkPlan = em.getReference(fkPlan.getClass(), fkPlan.getId());
                clasesPlanesEstudio.setFkPlan(fkPlan);
            }
            em.persist(clasesPlanesEstudio);
            if (fkClase != null) {
                fkClase.getClasesPlanesEstudioList().add(clasesPlanesEstudio);
                fkClase = em.merge(fkClase);
            }
            if (fkPlan != null) {
                fkPlan.getClasesPlanesEstudioList().add(clasesPlanesEstudio);
                fkPlan = em.merge(fkPlan);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findClasesPlanesEstudio(clasesPlanesEstudio.getId()) != null) {
                throw new PreexistingEntityException("ClasesPlanesEstudio " + clasesPlanesEstudio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ClasesPlanesEstudio clasesPlanesEstudio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClasesPlanesEstudio persistentClasesPlanesEstudio = em.find(ClasesPlanesEstudio.class, clasesPlanesEstudio.getId());
            Clases fkClaseOld = persistentClasesPlanesEstudio.getFkClase();
            Clases fkClaseNew = clasesPlanesEstudio.getFkClase();
            PlanesEstudios fkPlanOld = persistentClasesPlanesEstudio.getFkPlan();
            PlanesEstudios fkPlanNew = clasesPlanesEstudio.getFkPlan();
            if (fkClaseNew != null) {
                fkClaseNew = em.getReference(fkClaseNew.getClass(), fkClaseNew.getId());
                clasesPlanesEstudio.setFkClase(fkClaseNew);
            }
            if (fkPlanNew != null) {
                fkPlanNew = em.getReference(fkPlanNew.getClass(), fkPlanNew.getId());
                clasesPlanesEstudio.setFkPlan(fkPlanNew);
            }
            clasesPlanesEstudio = em.merge(clasesPlanesEstudio);
            if (fkClaseOld != null && !fkClaseOld.equals(fkClaseNew)) {
                fkClaseOld.getClasesPlanesEstudioList().remove(clasesPlanesEstudio);
                fkClaseOld = em.merge(fkClaseOld);
            }
            if (fkClaseNew != null && !fkClaseNew.equals(fkClaseOld)) {
                fkClaseNew.getClasesPlanesEstudioList().add(clasesPlanesEstudio);
                fkClaseNew = em.merge(fkClaseNew);
            }
            if (fkPlanOld != null && !fkPlanOld.equals(fkPlanNew)) {
                fkPlanOld.getClasesPlanesEstudioList().remove(clasesPlanesEstudio);
                fkPlanOld = em.merge(fkPlanOld);
            }
            if (fkPlanNew != null && !fkPlanNew.equals(fkPlanOld)) {
                fkPlanNew.getClasesPlanesEstudioList().add(clasesPlanesEstudio);
                fkPlanNew = em.merge(fkPlanNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = clasesPlanesEstudio.getId();
                if (findClasesPlanesEstudio(id) == null) {
                    throw new NonexistentEntityException("The clasesPlanesEstudio with id " + id + " no longer exists.");
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
            ClasesPlanesEstudio clasesPlanesEstudio;
            try {
                clasesPlanesEstudio = em.getReference(ClasesPlanesEstudio.class, id);
                clasesPlanesEstudio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clasesPlanesEstudio with id " + id + " no longer exists.", enfe);
            }
            Clases fkClase = clasesPlanesEstudio.getFkClase();
            if (fkClase != null) {
                fkClase.getClasesPlanesEstudioList().remove(clasesPlanesEstudio);
                fkClase = em.merge(fkClase);
            }
            PlanesEstudios fkPlan = clasesPlanesEstudio.getFkPlan();
            if (fkPlan != null) {
                fkPlan.getClasesPlanesEstudioList().remove(clasesPlanesEstudio);
                fkPlan = em.merge(fkPlan);
            }
            em.remove(clasesPlanesEstudio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ClasesPlanesEstudio> findClasesPlanesEstudioEntities() {
        return findClasesPlanesEstudioEntities(true, -1, -1);
    }

    public List<ClasesPlanesEstudio> findClasesPlanesEstudioEntities(int maxResults, int firstResult) {
        return findClasesPlanesEstudioEntities(false, maxResults, firstResult);
    }

    private List<ClasesPlanesEstudio> findClasesPlanesEstudioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ClasesPlanesEstudio.class));
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

    public ClasesPlanesEstudio findClasesPlanesEstudio(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClasesPlanesEstudio.class, id);
        } finally {
            em.close();
        }
    }

    public int getClasesPlanesEstudioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ClasesPlanesEstudio> rt = cq.from(ClasesPlanesEstudio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
