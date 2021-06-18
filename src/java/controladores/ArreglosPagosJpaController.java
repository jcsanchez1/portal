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
import entidad.ArreglosPagos;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author juanc
 */
public class ArreglosPagosJpaController implements Serializable {

    public ArreglosPagosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ArreglosPagos arreglosPagos) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumnos fkEstudiante = arreglosPagos.getFkEstudiante();
            if (fkEstudiante != null) {
                fkEstudiante = em.getReference(fkEstudiante.getClass(), fkEstudiante.getId());
                arreglosPagos.setFkEstudiante(fkEstudiante);
            }
            em.persist(arreglosPagos);
            if (fkEstudiante != null) {
                fkEstudiante.getArreglosPagosList().add(arreglosPagos);
                fkEstudiante = em.merge(fkEstudiante);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findArreglosPagos(arreglosPagos.getId()) != null) {
                throw new PreexistingEntityException("ArreglosPagos " + arreglosPagos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ArreglosPagos arreglosPagos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ArreglosPagos persistentArreglosPagos = em.find(ArreglosPagos.class, arreglosPagos.getId());
            Alumnos fkEstudianteOld = persistentArreglosPagos.getFkEstudiante();
            Alumnos fkEstudianteNew = arreglosPagos.getFkEstudiante();
            if (fkEstudianteNew != null) {
                fkEstudianteNew = em.getReference(fkEstudianteNew.getClass(), fkEstudianteNew.getId());
                arreglosPagos.setFkEstudiante(fkEstudianteNew);
            }
            arreglosPagos = em.merge(arreglosPagos);
            if (fkEstudianteOld != null && !fkEstudianteOld.equals(fkEstudianteNew)) {
                fkEstudianteOld.getArreglosPagosList().remove(arreglosPagos);
                fkEstudianteOld = em.merge(fkEstudianteOld);
            }
            if (fkEstudianteNew != null && !fkEstudianteNew.equals(fkEstudianteOld)) {
                fkEstudianteNew.getArreglosPagosList().add(arreglosPagos);
                fkEstudianteNew = em.merge(fkEstudianteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = arreglosPagos.getId();
                if (findArreglosPagos(id) == null) {
                    throw new NonexistentEntityException("The arreglosPagos with id " + id + " no longer exists.");
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
            ArreglosPagos arreglosPagos;
            try {
                arreglosPagos = em.getReference(ArreglosPagos.class, id);
                arreglosPagos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The arreglosPagos with id " + id + " no longer exists.", enfe);
            }
            Alumnos fkEstudiante = arreglosPagos.getFkEstudiante();
            if (fkEstudiante != null) {
                fkEstudiante.getArreglosPagosList().remove(arreglosPagos);
                fkEstudiante = em.merge(fkEstudiante);
            }
            em.remove(arreglosPagos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ArreglosPagos> findArreglosPagosEntities() {
        return findArreglosPagosEntities(true, -1, -1);
    }

    public List<ArreglosPagos> findArreglosPagosEntities(int maxResults, int firstResult) {
        return findArreglosPagosEntities(false, maxResults, firstResult);
    }

    private List<ArreglosPagos> findArreglosPagosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ArreglosPagos.class));
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

    public ArreglosPagos findArreglosPagos(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ArreglosPagos.class, id);
        } finally {
            em.close();
        }
    }

    public int getArreglosPagosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ArreglosPagos> rt = cq.from(ArreglosPagos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
