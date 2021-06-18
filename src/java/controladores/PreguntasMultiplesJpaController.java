/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import controladores.exceptions.PreexistingEntityException;
import entidad.PreguntasMultiples;
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
public class PreguntasMultiplesJpaController implements Serializable {

    public PreguntasMultiplesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PreguntasMultiples preguntasMultiples) throws PreexistingEntityException, Exception {
        if (preguntasMultiples.getPreguntasPruebasSeccionesList() == null) {
            preguntasMultiples.setPreguntasPruebasSeccionesList(new ArrayList<PreguntasPruebasSecciones>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<PreguntasPruebasSecciones> attachedPreguntasPruebasSeccionesList = new ArrayList<PreguntasPruebasSecciones>();
            for (PreguntasPruebasSecciones preguntasPruebasSeccionesListPreguntasPruebasSeccionesToAttach : preguntasMultiples.getPreguntasPruebasSeccionesList()) {
                preguntasPruebasSeccionesListPreguntasPruebasSeccionesToAttach = em.getReference(preguntasPruebasSeccionesListPreguntasPruebasSeccionesToAttach.getClass(), preguntasPruebasSeccionesListPreguntasPruebasSeccionesToAttach.getId());
                attachedPreguntasPruebasSeccionesList.add(preguntasPruebasSeccionesListPreguntasPruebasSeccionesToAttach);
            }
            preguntasMultiples.setPreguntasPruebasSeccionesList(attachedPreguntasPruebasSeccionesList);
            em.persist(preguntasMultiples);
            for (PreguntasPruebasSecciones preguntasPruebasSeccionesListPreguntasPruebasSecciones : preguntasMultiples.getPreguntasPruebasSeccionesList()) {
                PreguntasMultiples oldFkPreguntaMultOfPreguntasPruebasSeccionesListPreguntasPruebasSecciones = preguntasPruebasSeccionesListPreguntasPruebasSecciones.getFkPreguntaMult();
                preguntasPruebasSeccionesListPreguntasPruebasSecciones.setFkPreguntaMult(preguntasMultiples);
                preguntasPruebasSeccionesListPreguntasPruebasSecciones = em.merge(preguntasPruebasSeccionesListPreguntasPruebasSecciones);
                if (oldFkPreguntaMultOfPreguntasPruebasSeccionesListPreguntasPruebasSecciones != null) {
                    oldFkPreguntaMultOfPreguntasPruebasSeccionesListPreguntasPruebasSecciones.getPreguntasPruebasSeccionesList().remove(preguntasPruebasSeccionesListPreguntasPruebasSecciones);
                    oldFkPreguntaMultOfPreguntasPruebasSeccionesListPreguntasPruebasSecciones = em.merge(oldFkPreguntaMultOfPreguntasPruebasSeccionesListPreguntasPruebasSecciones);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPreguntasMultiples(preguntasMultiples.getId()) != null) {
                throw new PreexistingEntityException("PreguntasMultiples " + preguntasMultiples + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PreguntasMultiples preguntasMultiples) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PreguntasMultiples persistentPreguntasMultiples = em.find(PreguntasMultiples.class, preguntasMultiples.getId());
            List<PreguntasPruebasSecciones> preguntasPruebasSeccionesListOld = persistentPreguntasMultiples.getPreguntasPruebasSeccionesList();
            List<PreguntasPruebasSecciones> preguntasPruebasSeccionesListNew = preguntasMultiples.getPreguntasPruebasSeccionesList();
            List<PreguntasPruebasSecciones> attachedPreguntasPruebasSeccionesListNew = new ArrayList<PreguntasPruebasSecciones>();
            for (PreguntasPruebasSecciones preguntasPruebasSeccionesListNewPreguntasPruebasSeccionesToAttach : preguntasPruebasSeccionesListNew) {
                preguntasPruebasSeccionesListNewPreguntasPruebasSeccionesToAttach = em.getReference(preguntasPruebasSeccionesListNewPreguntasPruebasSeccionesToAttach.getClass(), preguntasPruebasSeccionesListNewPreguntasPruebasSeccionesToAttach.getId());
                attachedPreguntasPruebasSeccionesListNew.add(preguntasPruebasSeccionesListNewPreguntasPruebasSeccionesToAttach);
            }
            preguntasPruebasSeccionesListNew = attachedPreguntasPruebasSeccionesListNew;
            preguntasMultiples.setPreguntasPruebasSeccionesList(preguntasPruebasSeccionesListNew);
            preguntasMultiples = em.merge(preguntasMultiples);
            for (PreguntasPruebasSecciones preguntasPruebasSeccionesListOldPreguntasPruebasSecciones : preguntasPruebasSeccionesListOld) {
                if (!preguntasPruebasSeccionesListNew.contains(preguntasPruebasSeccionesListOldPreguntasPruebasSecciones)) {
                    preguntasPruebasSeccionesListOldPreguntasPruebasSecciones.setFkPreguntaMult(null);
                    preguntasPruebasSeccionesListOldPreguntasPruebasSecciones = em.merge(preguntasPruebasSeccionesListOldPreguntasPruebasSecciones);
                }
            }
            for (PreguntasPruebasSecciones preguntasPruebasSeccionesListNewPreguntasPruebasSecciones : preguntasPruebasSeccionesListNew) {
                if (!preguntasPruebasSeccionesListOld.contains(preguntasPruebasSeccionesListNewPreguntasPruebasSecciones)) {
                    PreguntasMultiples oldFkPreguntaMultOfPreguntasPruebasSeccionesListNewPreguntasPruebasSecciones = preguntasPruebasSeccionesListNewPreguntasPruebasSecciones.getFkPreguntaMult();
                    preguntasPruebasSeccionesListNewPreguntasPruebasSecciones.setFkPreguntaMult(preguntasMultiples);
                    preguntasPruebasSeccionesListNewPreguntasPruebasSecciones = em.merge(preguntasPruebasSeccionesListNewPreguntasPruebasSecciones);
                    if (oldFkPreguntaMultOfPreguntasPruebasSeccionesListNewPreguntasPruebasSecciones != null && !oldFkPreguntaMultOfPreguntasPruebasSeccionesListNewPreguntasPruebasSecciones.equals(preguntasMultiples)) {
                        oldFkPreguntaMultOfPreguntasPruebasSeccionesListNewPreguntasPruebasSecciones.getPreguntasPruebasSeccionesList().remove(preguntasPruebasSeccionesListNewPreguntasPruebasSecciones);
                        oldFkPreguntaMultOfPreguntasPruebasSeccionesListNewPreguntasPruebasSecciones = em.merge(oldFkPreguntaMultOfPreguntasPruebasSeccionesListNewPreguntasPruebasSecciones);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = preguntasMultiples.getId();
                if (findPreguntasMultiples(id) == null) {
                    throw new NonexistentEntityException("The preguntasMultiples with id " + id + " no longer exists.");
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
            PreguntasMultiples preguntasMultiples;
            try {
                preguntasMultiples = em.getReference(PreguntasMultiples.class, id);
                preguntasMultiples.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The preguntasMultiples with id " + id + " no longer exists.", enfe);
            }
            List<PreguntasPruebasSecciones> preguntasPruebasSeccionesList = preguntasMultiples.getPreguntasPruebasSeccionesList();
            for (PreguntasPruebasSecciones preguntasPruebasSeccionesListPreguntasPruebasSecciones : preguntasPruebasSeccionesList) {
                preguntasPruebasSeccionesListPreguntasPruebasSecciones.setFkPreguntaMult(null);
                preguntasPruebasSeccionesListPreguntasPruebasSecciones = em.merge(preguntasPruebasSeccionesListPreguntasPruebasSecciones);
            }
            em.remove(preguntasMultiples);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PreguntasMultiples> findPreguntasMultiplesEntities() {
        return findPreguntasMultiplesEntities(true, -1, -1);
    }

    public List<PreguntasMultiples> findPreguntasMultiplesEntities(int maxResults, int firstResult) {
        return findPreguntasMultiplesEntities(false, maxResults, firstResult);
    }

    private List<PreguntasMultiples> findPreguntasMultiplesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PreguntasMultiples.class));
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

    public PreguntasMultiples findPreguntasMultiples(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PreguntasMultiples.class, id);
        } finally {
            em.close();
        }
    }

    public int getPreguntasMultiplesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PreguntasMultiples> rt = cq.from(PreguntasMultiples.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
