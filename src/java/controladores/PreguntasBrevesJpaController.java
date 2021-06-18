/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import controladores.exceptions.PreexistingEntityException;
import entidad.PreguntasBreves;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidad.PreguntasPruebasSecciones;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author juanc
 */
public class PreguntasBrevesJpaController implements Serializable {

    public PreguntasBrevesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PreguntasBreves preguntasBreves) throws PreexistingEntityException, Exception {
        if (preguntasBreves.getPreguntasPruebasSeccionesList() == null) {
            preguntasBreves.setPreguntasPruebasSeccionesList(new ArrayList<PreguntasPruebasSecciones>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<PreguntasPruebasSecciones> attachedPreguntasPruebasSeccionesList = new ArrayList<PreguntasPruebasSecciones>();
            for (PreguntasPruebasSecciones preguntasPruebasSeccionesListPreguntasPruebasSeccionesToAttach : preguntasBreves.getPreguntasPruebasSeccionesList()) {
                preguntasPruebasSeccionesListPreguntasPruebasSeccionesToAttach = em.getReference(preguntasPruebasSeccionesListPreguntasPruebasSeccionesToAttach.getClass(), preguntasPruebasSeccionesListPreguntasPruebasSeccionesToAttach.getId());
                attachedPreguntasPruebasSeccionesList.add(preguntasPruebasSeccionesListPreguntasPruebasSeccionesToAttach);
            }
            preguntasBreves.setPreguntasPruebasSeccionesList(attachedPreguntasPruebasSeccionesList);
            em.persist(preguntasBreves);
            for (PreguntasPruebasSecciones preguntasPruebasSeccionesListPreguntasPruebasSecciones : preguntasBreves.getPreguntasPruebasSeccionesList()) {
                PreguntasBreves oldFkPreguntaBreveOfPreguntasPruebasSeccionesListPreguntasPruebasSecciones = preguntasPruebasSeccionesListPreguntasPruebasSecciones.getFkPreguntaBreve();
                preguntasPruebasSeccionesListPreguntasPruebasSecciones.setFkPreguntaBreve(preguntasBreves);
                preguntasPruebasSeccionesListPreguntasPruebasSecciones = em.merge(preguntasPruebasSeccionesListPreguntasPruebasSecciones);
                if (oldFkPreguntaBreveOfPreguntasPruebasSeccionesListPreguntasPruebasSecciones != null) {
                    oldFkPreguntaBreveOfPreguntasPruebasSeccionesListPreguntasPruebasSecciones.getPreguntasPruebasSeccionesList().remove(preguntasPruebasSeccionesListPreguntasPruebasSecciones);
                    oldFkPreguntaBreveOfPreguntasPruebasSeccionesListPreguntasPruebasSecciones = em.merge(oldFkPreguntaBreveOfPreguntasPruebasSeccionesListPreguntasPruebasSecciones);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPreguntasBreves(preguntasBreves.getId()) != null) {
                throw new PreexistingEntityException("PreguntasBreves " + preguntasBreves + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PreguntasBreves preguntasBreves) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PreguntasBreves persistentPreguntasBreves = em.find(PreguntasBreves.class, preguntasBreves.getId());
            List<PreguntasPruebasSecciones> preguntasPruebasSeccionesListOld = persistentPreguntasBreves.getPreguntasPruebasSeccionesList();
            List<PreguntasPruebasSecciones> preguntasPruebasSeccionesListNew = preguntasBreves.getPreguntasPruebasSeccionesList();
            List<PreguntasPruebasSecciones> attachedPreguntasPruebasSeccionesListNew = new ArrayList<PreguntasPruebasSecciones>();
            for (PreguntasPruebasSecciones preguntasPruebasSeccionesListNewPreguntasPruebasSeccionesToAttach : preguntasPruebasSeccionesListNew) {
                preguntasPruebasSeccionesListNewPreguntasPruebasSeccionesToAttach = em.getReference(preguntasPruebasSeccionesListNewPreguntasPruebasSeccionesToAttach.getClass(), preguntasPruebasSeccionesListNewPreguntasPruebasSeccionesToAttach.getId());
                attachedPreguntasPruebasSeccionesListNew.add(preguntasPruebasSeccionesListNewPreguntasPruebasSeccionesToAttach);
            }
            preguntasPruebasSeccionesListNew = attachedPreguntasPruebasSeccionesListNew;
            preguntasBreves.setPreguntasPruebasSeccionesList(preguntasPruebasSeccionesListNew);
            preguntasBreves = em.merge(preguntasBreves);
            for (PreguntasPruebasSecciones preguntasPruebasSeccionesListOldPreguntasPruebasSecciones : preguntasPruebasSeccionesListOld) {
                if (!preguntasPruebasSeccionesListNew.contains(preguntasPruebasSeccionesListOldPreguntasPruebasSecciones)) {
                    preguntasPruebasSeccionesListOldPreguntasPruebasSecciones.setFkPreguntaBreve(null);
                    preguntasPruebasSeccionesListOldPreguntasPruebasSecciones = em.merge(preguntasPruebasSeccionesListOldPreguntasPruebasSecciones);
                }
            }
            for (PreguntasPruebasSecciones preguntasPruebasSeccionesListNewPreguntasPruebasSecciones : preguntasPruebasSeccionesListNew) {
                if (!preguntasPruebasSeccionesListOld.contains(preguntasPruebasSeccionesListNewPreguntasPruebasSecciones)) {
                    PreguntasBreves oldFkPreguntaBreveOfPreguntasPruebasSeccionesListNewPreguntasPruebasSecciones = preguntasPruebasSeccionesListNewPreguntasPruebasSecciones.getFkPreguntaBreve();
                    preguntasPruebasSeccionesListNewPreguntasPruebasSecciones.setFkPreguntaBreve(preguntasBreves);
                    preguntasPruebasSeccionesListNewPreguntasPruebasSecciones = em.merge(preguntasPruebasSeccionesListNewPreguntasPruebasSecciones);
                    if (oldFkPreguntaBreveOfPreguntasPruebasSeccionesListNewPreguntasPruebasSecciones != null && !oldFkPreguntaBreveOfPreguntasPruebasSeccionesListNewPreguntasPruebasSecciones.equals(preguntasBreves)) {
                        oldFkPreguntaBreveOfPreguntasPruebasSeccionesListNewPreguntasPruebasSecciones.getPreguntasPruebasSeccionesList().remove(preguntasPruebasSeccionesListNewPreguntasPruebasSecciones);
                        oldFkPreguntaBreveOfPreguntasPruebasSeccionesListNewPreguntasPruebasSecciones = em.merge(oldFkPreguntaBreveOfPreguntasPruebasSeccionesListNewPreguntasPruebasSecciones);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = preguntasBreves.getId();
                if (findPreguntasBreves(id) == null) {
                    throw new NonexistentEntityException("The preguntasBreves with id " + id + " no longer exists.");
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
            PreguntasBreves preguntasBreves;
            try {
                preguntasBreves = em.getReference(PreguntasBreves.class, id);
                preguntasBreves.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The preguntasBreves with id " + id + " no longer exists.", enfe);
            }
            List<PreguntasPruebasSecciones> preguntasPruebasSeccionesList = preguntasBreves.getPreguntasPruebasSeccionesList();
            for (PreguntasPruebasSecciones preguntasPruebasSeccionesListPreguntasPruebasSecciones : preguntasPruebasSeccionesList) {
                preguntasPruebasSeccionesListPreguntasPruebasSecciones.setFkPreguntaBreve(null);
                preguntasPruebasSeccionesListPreguntasPruebasSecciones = em.merge(preguntasPruebasSeccionesListPreguntasPruebasSecciones);
            }
            em.remove(preguntasBreves);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PreguntasBreves> findPreguntasBrevesEntities() {
        return findPreguntasBrevesEntities(true, -1, -1);
    }

    public List<PreguntasBreves> findPreguntasBrevesEntities(int maxResults, int firstResult) {
        return findPreguntasBrevesEntities(false, maxResults, firstResult);
    }

    private List<PreguntasBreves> findPreguntasBrevesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PreguntasBreves.class));
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

    public PreguntasBreves findPreguntasBreves(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PreguntasBreves.class, id);
        } finally {
            em.close();
        }
    }

    public int getPreguntasBrevesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PreguntasBreves> rt = cq.from(PreguntasBreves.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
