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
import entidad.PreguntasBreves;
import entidad.PreguntasMultiples;
import entidad.PreguntasPruebasSecciones;
import entidad.PreguntasVofs;
import entidad.PruebasSecciones;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author juanc
 */
public class PreguntasPruebasSeccionesJpaController implements Serializable {

    public PreguntasPruebasSeccionesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PreguntasPruebasSecciones preguntasPruebasSecciones) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PreguntasBreves fkPreguntaBreve = preguntasPruebasSecciones.getFkPreguntaBreve();
            if (fkPreguntaBreve != null) {
                fkPreguntaBreve = em.getReference(fkPreguntaBreve.getClass(), fkPreguntaBreve.getId());
                preguntasPruebasSecciones.setFkPreguntaBreve(fkPreguntaBreve);
            }
            PreguntasMultiples fkPreguntaMult = preguntasPruebasSecciones.getFkPreguntaMult();
            if (fkPreguntaMult != null) {
                fkPreguntaMult = em.getReference(fkPreguntaMult.getClass(), fkPreguntaMult.getId());
                preguntasPruebasSecciones.setFkPreguntaMult(fkPreguntaMult);
            }
            PreguntasVofs fkPreguntaVof = preguntasPruebasSecciones.getFkPreguntaVof();
            if (fkPreguntaVof != null) {
                fkPreguntaVof = em.getReference(fkPreguntaVof.getClass(), fkPreguntaVof.getId());
                preguntasPruebasSecciones.setFkPreguntaVof(fkPreguntaVof);
            }
            PruebasSecciones fkPruebaSeccion = preguntasPruebasSecciones.getFkPruebaSeccion();
            if (fkPruebaSeccion != null) {
                fkPruebaSeccion = em.getReference(fkPruebaSeccion.getClass(), fkPruebaSeccion.getId());
                preguntasPruebasSecciones.setFkPruebaSeccion(fkPruebaSeccion);
            }
            em.persist(preguntasPruebasSecciones);
            if (fkPreguntaBreve != null) {
                fkPreguntaBreve.getPreguntasPruebasSeccionesList().add(preguntasPruebasSecciones);
                fkPreguntaBreve = em.merge(fkPreguntaBreve);
            }
            if (fkPreguntaMult != null) {
                fkPreguntaMult.getPreguntasPruebasSeccionesList().add(preguntasPruebasSecciones);
                fkPreguntaMult = em.merge(fkPreguntaMult);
            }
            if (fkPreguntaVof != null) {
                fkPreguntaVof.getPreguntasPruebasSeccionesList().add(preguntasPruebasSecciones);
                fkPreguntaVof = em.merge(fkPreguntaVof);
            }
            if (fkPruebaSeccion != null) {
                fkPruebaSeccion.getPreguntasPruebasSeccionesList().add(preguntasPruebasSecciones);
                fkPruebaSeccion = em.merge(fkPruebaSeccion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPreguntasPruebasSecciones(preguntasPruebasSecciones.getId()) != null) {
                throw new PreexistingEntityException("PreguntasPruebasSecciones " + preguntasPruebasSecciones + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PreguntasPruebasSecciones preguntasPruebasSecciones) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PreguntasPruebasSecciones persistentPreguntasPruebasSecciones = em.find(PreguntasPruebasSecciones.class, preguntasPruebasSecciones.getId());
            PreguntasBreves fkPreguntaBreveOld = persistentPreguntasPruebasSecciones.getFkPreguntaBreve();
            PreguntasBreves fkPreguntaBreveNew = preguntasPruebasSecciones.getFkPreguntaBreve();
            PreguntasMultiples fkPreguntaMultOld = persistentPreguntasPruebasSecciones.getFkPreguntaMult();
            PreguntasMultiples fkPreguntaMultNew = preguntasPruebasSecciones.getFkPreguntaMult();
            PreguntasVofs fkPreguntaVofOld = persistentPreguntasPruebasSecciones.getFkPreguntaVof();
            PreguntasVofs fkPreguntaVofNew = preguntasPruebasSecciones.getFkPreguntaVof();
            PruebasSecciones fkPruebaSeccionOld = persistentPreguntasPruebasSecciones.getFkPruebaSeccion();
            PruebasSecciones fkPruebaSeccionNew = preguntasPruebasSecciones.getFkPruebaSeccion();
            if (fkPreguntaBreveNew != null) {
                fkPreguntaBreveNew = em.getReference(fkPreguntaBreveNew.getClass(), fkPreguntaBreveNew.getId());
                preguntasPruebasSecciones.setFkPreguntaBreve(fkPreguntaBreveNew);
            }
            if (fkPreguntaMultNew != null) {
                fkPreguntaMultNew = em.getReference(fkPreguntaMultNew.getClass(), fkPreguntaMultNew.getId());
                preguntasPruebasSecciones.setFkPreguntaMult(fkPreguntaMultNew);
            }
            if (fkPreguntaVofNew != null) {
                fkPreguntaVofNew = em.getReference(fkPreguntaVofNew.getClass(), fkPreguntaVofNew.getId());
                preguntasPruebasSecciones.setFkPreguntaVof(fkPreguntaVofNew);
            }
            if (fkPruebaSeccionNew != null) {
                fkPruebaSeccionNew = em.getReference(fkPruebaSeccionNew.getClass(), fkPruebaSeccionNew.getId());
                preguntasPruebasSecciones.setFkPruebaSeccion(fkPruebaSeccionNew);
            }
            preguntasPruebasSecciones = em.merge(preguntasPruebasSecciones);
            if (fkPreguntaBreveOld != null && !fkPreguntaBreveOld.equals(fkPreguntaBreveNew)) {
                fkPreguntaBreveOld.getPreguntasPruebasSeccionesList().remove(preguntasPruebasSecciones);
                fkPreguntaBreveOld = em.merge(fkPreguntaBreveOld);
            }
            if (fkPreguntaBreveNew != null && !fkPreguntaBreveNew.equals(fkPreguntaBreveOld)) {
                fkPreguntaBreveNew.getPreguntasPruebasSeccionesList().add(preguntasPruebasSecciones);
                fkPreguntaBreveNew = em.merge(fkPreguntaBreveNew);
            }
            if (fkPreguntaMultOld != null && !fkPreguntaMultOld.equals(fkPreguntaMultNew)) {
                fkPreguntaMultOld.getPreguntasPruebasSeccionesList().remove(preguntasPruebasSecciones);
                fkPreguntaMultOld = em.merge(fkPreguntaMultOld);
            }
            if (fkPreguntaMultNew != null && !fkPreguntaMultNew.equals(fkPreguntaMultOld)) {
                fkPreguntaMultNew.getPreguntasPruebasSeccionesList().add(preguntasPruebasSecciones);
                fkPreguntaMultNew = em.merge(fkPreguntaMultNew);
            }
            if (fkPreguntaVofOld != null && !fkPreguntaVofOld.equals(fkPreguntaVofNew)) {
                fkPreguntaVofOld.getPreguntasPruebasSeccionesList().remove(preguntasPruebasSecciones);
                fkPreguntaVofOld = em.merge(fkPreguntaVofOld);
            }
            if (fkPreguntaVofNew != null && !fkPreguntaVofNew.equals(fkPreguntaVofOld)) {
                fkPreguntaVofNew.getPreguntasPruebasSeccionesList().add(preguntasPruebasSecciones);
                fkPreguntaVofNew = em.merge(fkPreguntaVofNew);
            }
            if (fkPruebaSeccionOld != null && !fkPruebaSeccionOld.equals(fkPruebaSeccionNew)) {
                fkPruebaSeccionOld.getPreguntasPruebasSeccionesList().remove(preguntasPruebasSecciones);
                fkPruebaSeccionOld = em.merge(fkPruebaSeccionOld);
            }
            if (fkPruebaSeccionNew != null && !fkPruebaSeccionNew.equals(fkPruebaSeccionOld)) {
                fkPruebaSeccionNew.getPreguntasPruebasSeccionesList().add(preguntasPruebasSecciones);
                fkPruebaSeccionNew = em.merge(fkPruebaSeccionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = preguntasPruebasSecciones.getId();
                if (findPreguntasPruebasSecciones(id) == null) {
                    throw new NonexistentEntityException("The preguntasPruebasSecciones with id " + id + " no longer exists.");
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
            PreguntasPruebasSecciones preguntasPruebasSecciones;
            try {
                preguntasPruebasSecciones = em.getReference(PreguntasPruebasSecciones.class, id);
                preguntasPruebasSecciones.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The preguntasPruebasSecciones with id " + id + " no longer exists.", enfe);
            }
            PreguntasBreves fkPreguntaBreve = preguntasPruebasSecciones.getFkPreguntaBreve();
            if (fkPreguntaBreve != null) {
                fkPreguntaBreve.getPreguntasPruebasSeccionesList().remove(preguntasPruebasSecciones);
                fkPreguntaBreve = em.merge(fkPreguntaBreve);
            }
            PreguntasMultiples fkPreguntaMult = preguntasPruebasSecciones.getFkPreguntaMult();
            if (fkPreguntaMult != null) {
                fkPreguntaMult.getPreguntasPruebasSeccionesList().remove(preguntasPruebasSecciones);
                fkPreguntaMult = em.merge(fkPreguntaMult);
            }
            PreguntasVofs fkPreguntaVof = preguntasPruebasSecciones.getFkPreguntaVof();
            if (fkPreguntaVof != null) {
                fkPreguntaVof.getPreguntasPruebasSeccionesList().remove(preguntasPruebasSecciones);
                fkPreguntaVof = em.merge(fkPreguntaVof);
            }
            PruebasSecciones fkPruebaSeccion = preguntasPruebasSecciones.getFkPruebaSeccion();
            if (fkPruebaSeccion != null) {
                fkPruebaSeccion.getPreguntasPruebasSeccionesList().remove(preguntasPruebasSecciones);
                fkPruebaSeccion = em.merge(fkPruebaSeccion);
            }
            em.remove(preguntasPruebasSecciones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PreguntasPruebasSecciones> findPreguntasPruebasSeccionesEntities() {
        return findPreguntasPruebasSeccionesEntities(true, -1, -1);
    }

    public List<PreguntasPruebasSecciones> findPreguntasPruebasSeccionesEntities(int maxResults, int firstResult) {
        return findPreguntasPruebasSeccionesEntities(false, maxResults, firstResult);
    }

    private List<PreguntasPruebasSecciones> findPreguntasPruebasSeccionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PreguntasPruebasSecciones.class));
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

    public PreguntasPruebasSecciones findPreguntasPruebasSecciones(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PreguntasPruebasSecciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getPreguntasPruebasSeccionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PreguntasPruebasSecciones> rt = cq.from(PreguntasPruebasSecciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
