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
import entidad.Pagos;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author juanc
 */
public class PagosJpaController implements Serializable {

    public PagosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pagos pagos) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumnos fkAlumno = pagos.getFkAlumno();
            if (fkAlumno != null) {
                fkAlumno = em.getReference(fkAlumno.getClass(), fkAlumno.getId());
                pagos.setFkAlumno(fkAlumno);
            }
            em.persist(pagos);
            if (fkAlumno != null) {
                fkAlumno.getPagosList().add(pagos);
                fkAlumno = em.merge(fkAlumno);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPagos(pagos.getId()) != null) {
                throw new PreexistingEntityException("Pagos " + pagos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pagos pagos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pagos persistentPagos = em.find(Pagos.class, pagos.getId());
            Alumnos fkAlumnoOld = persistentPagos.getFkAlumno();
            Alumnos fkAlumnoNew = pagos.getFkAlumno();
            if (fkAlumnoNew != null) {
                fkAlumnoNew = em.getReference(fkAlumnoNew.getClass(), fkAlumnoNew.getId());
                pagos.setFkAlumno(fkAlumnoNew);
            }
            pagos = em.merge(pagos);
            if (fkAlumnoOld != null && !fkAlumnoOld.equals(fkAlumnoNew)) {
                fkAlumnoOld.getPagosList().remove(pagos);
                fkAlumnoOld = em.merge(fkAlumnoOld);
            }
            if (fkAlumnoNew != null && !fkAlumnoNew.equals(fkAlumnoOld)) {
                fkAlumnoNew.getPagosList().add(pagos);
                fkAlumnoNew = em.merge(fkAlumnoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = pagos.getId();
                if (findPagos(id) == null) {
                    throw new NonexistentEntityException("The pagos with id " + id + " no longer exists.");
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
            Pagos pagos;
            try {
                pagos = em.getReference(Pagos.class, id);
                pagos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pagos with id " + id + " no longer exists.", enfe);
            }
            Alumnos fkAlumno = pagos.getFkAlumno();
            if (fkAlumno != null) {
                fkAlumno.getPagosList().remove(pagos);
                fkAlumno = em.merge(fkAlumno);
            }
            em.remove(pagos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pagos> findPagosEntities() {
        return findPagosEntities(true, -1, -1);
    }

    public List<Pagos> findPagosEntities(int maxResults, int firstResult) {
        return findPagosEntities(false, maxResults, firstResult);
    }

    private List<Pagos> findPagosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pagos.class));
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

    public Pagos findPagos(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pagos.class, id);
        } finally {
            em.close();
        }
    }

    public int getPagosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pagos> rt = cq.from(Pagos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
