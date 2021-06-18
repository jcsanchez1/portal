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
import entidad.Saldos;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author juanc
 */
public class SaldosJpaController implements Serializable {

    public SaldosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Saldos saldos) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumnos fkAlumno = saldos.getFkAlumno();
            if (fkAlumno != null) {
                fkAlumno = em.getReference(fkAlumno.getClass(), fkAlumno.getId());
                saldos.setFkAlumno(fkAlumno);
            }
            em.persist(saldos);
            if (fkAlumno != null) {
                fkAlumno.getSaldosList().add(saldos);
                fkAlumno = em.merge(fkAlumno);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSaldos(saldos.getId()) != null) {
                throw new PreexistingEntityException("Saldos " + saldos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Saldos saldos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Saldos persistentSaldos = em.find(Saldos.class, saldos.getId());
            Alumnos fkAlumnoOld = persistentSaldos.getFkAlumno();
            Alumnos fkAlumnoNew = saldos.getFkAlumno();
            if (fkAlumnoNew != null) {
                fkAlumnoNew = em.getReference(fkAlumnoNew.getClass(), fkAlumnoNew.getId());
                saldos.setFkAlumno(fkAlumnoNew);
            }
            saldos = em.merge(saldos);
            if (fkAlumnoOld != null && !fkAlumnoOld.equals(fkAlumnoNew)) {
                fkAlumnoOld.getSaldosList().remove(saldos);
                fkAlumnoOld = em.merge(fkAlumnoOld);
            }
            if (fkAlumnoNew != null && !fkAlumnoNew.equals(fkAlumnoOld)) {
                fkAlumnoNew.getSaldosList().add(saldos);
                fkAlumnoNew = em.merge(fkAlumnoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = saldos.getId();
                if (findSaldos(id) == null) {
                    throw new NonexistentEntityException("The saldos with id " + id + " no longer exists.");
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
            Saldos saldos;
            try {
                saldos = em.getReference(Saldos.class, id);
                saldos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The saldos with id " + id + " no longer exists.", enfe);
            }
            Alumnos fkAlumno = saldos.getFkAlumno();
            if (fkAlumno != null) {
                fkAlumno.getSaldosList().remove(saldos);
                fkAlumno = em.merge(fkAlumno);
            }
            em.remove(saldos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Saldos> findSaldosEntities() {
        return findSaldosEntities(true, -1, -1);
    }

    public List<Saldos> findSaldosEntities(int maxResults, int firstResult) {
        return findSaldosEntities(false, maxResults, firstResult);
    }

    private List<Saldos> findSaldosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Saldos.class));
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

    public Saldos findSaldos(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Saldos.class, id);
        } finally {
            em.close();
        }
    }

    public int getSaldosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Saldos> rt = cq.from(Saldos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
