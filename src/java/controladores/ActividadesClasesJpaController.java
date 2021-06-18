/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import controladores.exceptions.PreexistingEntityException;
import entidad.ActividadesClases;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidad.Modalidades;
import entidad.Secciones;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author juanc
 */
public class ActividadesClasesJpaController implements Serializable {

    public ActividadesClasesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ActividadesClases actividadesClases) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Modalidades fkModalidad = actividadesClases.getFkModalidad();
            if (fkModalidad != null) {
                fkModalidad = em.getReference(fkModalidad.getClass(), fkModalidad.getId());
                actividadesClases.setFkModalidad(fkModalidad);
            }
            Secciones fkSeccion = actividadesClases.getFkSeccion();
            if (fkSeccion != null) {
                fkSeccion = em.getReference(fkSeccion.getClass(), fkSeccion.getId());
                actividadesClases.setFkSeccion(fkSeccion);
            }
            em.persist(actividadesClases);
            if (fkModalidad != null) {
                fkModalidad.getActividadesClasesList().add(actividadesClases);
                fkModalidad = em.merge(fkModalidad);
            }
            if (fkSeccion != null) {
                fkSeccion.getActividadesClasesList().add(actividadesClases);
                fkSeccion = em.merge(fkSeccion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findActividadesClases(actividadesClases.getId()) != null) {
                throw new PreexistingEntityException("ActividadesClases " + actividadesClases + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ActividadesClases actividadesClases) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ActividadesClases persistentActividadesClases = em.find(ActividadesClases.class, actividadesClases.getId());
            Modalidades fkModalidadOld = persistentActividadesClases.getFkModalidad();
            Modalidades fkModalidadNew = actividadesClases.getFkModalidad();
            Secciones fkSeccionOld = persistentActividadesClases.getFkSeccion();
            Secciones fkSeccionNew = actividadesClases.getFkSeccion();
            if (fkModalidadNew != null) {
                fkModalidadNew = em.getReference(fkModalidadNew.getClass(), fkModalidadNew.getId());
                actividadesClases.setFkModalidad(fkModalidadNew);
            }
            if (fkSeccionNew != null) {
                fkSeccionNew = em.getReference(fkSeccionNew.getClass(), fkSeccionNew.getId());
                actividadesClases.setFkSeccion(fkSeccionNew);
            }
            actividadesClases = em.merge(actividadesClases);
            if (fkModalidadOld != null && !fkModalidadOld.equals(fkModalidadNew)) {
                fkModalidadOld.getActividadesClasesList().remove(actividadesClases);
                fkModalidadOld = em.merge(fkModalidadOld);
            }
            if (fkModalidadNew != null && !fkModalidadNew.equals(fkModalidadOld)) {
                fkModalidadNew.getActividadesClasesList().add(actividadesClases);
                fkModalidadNew = em.merge(fkModalidadNew);
            }
            if (fkSeccionOld != null && !fkSeccionOld.equals(fkSeccionNew)) {
                fkSeccionOld.getActividadesClasesList().remove(actividadesClases);
                fkSeccionOld = em.merge(fkSeccionOld);
            }
            if (fkSeccionNew != null && !fkSeccionNew.equals(fkSeccionOld)) {
                fkSeccionNew.getActividadesClasesList().add(actividadesClases);
                fkSeccionNew = em.merge(fkSeccionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = actividadesClases.getId();
                if (findActividadesClases(id) == null) {
                    throw new NonexistentEntityException("The actividadesClases with id " + id + " no longer exists.");
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
            ActividadesClases actividadesClases;
            try {
                actividadesClases = em.getReference(ActividadesClases.class, id);
                actividadesClases.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The actividadesClases with id " + id + " no longer exists.", enfe);
            }
            Modalidades fkModalidad = actividadesClases.getFkModalidad();
            if (fkModalidad != null) {
                fkModalidad.getActividadesClasesList().remove(actividadesClases);
                fkModalidad = em.merge(fkModalidad);
            }
            Secciones fkSeccion = actividadesClases.getFkSeccion();
            if (fkSeccion != null) {
                fkSeccion.getActividadesClasesList().remove(actividadesClases);
                fkSeccion = em.merge(fkSeccion);
            }
            em.remove(actividadesClases);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ActividadesClases> findActividadesClasesEntities() {
        return findActividadesClasesEntities(true, -1, -1);
    }

    public List<ActividadesClases> findActividadesClasesEntities(int maxResults, int firstResult) {
        return findActividadesClasesEntities(false, maxResults, firstResult);
    }

    private List<ActividadesClases> findActividadesClasesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ActividadesClases.class));
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

    public ActividadesClases findActividadesClases(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ActividadesClases.class, id);
        } finally {
            em.close();
        }
    }

    public int getActividadesClasesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ActividadesClases> rt = cq.from(ActividadesClases.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
