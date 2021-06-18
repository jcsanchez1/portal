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
import entidad.Carreras;
import entidad.Orientaciones;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author juanc
 */
public class OrientacionesJpaController implements Serializable {

    public OrientacionesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Orientaciones orientaciones) throws PreexistingEntityException, Exception {
        if (orientaciones.getCarrerasList() == null) {
            orientaciones.setCarrerasList(new ArrayList<Carreras>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Carreras> attachedCarrerasList = new ArrayList<Carreras>();
            for (Carreras carrerasListCarrerasToAttach : orientaciones.getCarrerasList()) {
                carrerasListCarrerasToAttach = em.getReference(carrerasListCarrerasToAttach.getClass(), carrerasListCarrerasToAttach.getId());
                attachedCarrerasList.add(carrerasListCarrerasToAttach);
            }
            orientaciones.setCarrerasList(attachedCarrerasList);
            em.persist(orientaciones);
            for (Carreras carrerasListCarreras : orientaciones.getCarrerasList()) {
                Orientaciones oldFkOrientacionOfCarrerasListCarreras = carrerasListCarreras.getFkOrientacion();
                carrerasListCarreras.setFkOrientacion(orientaciones);
                carrerasListCarreras = em.merge(carrerasListCarreras);
                if (oldFkOrientacionOfCarrerasListCarreras != null) {
                    oldFkOrientacionOfCarrerasListCarreras.getCarrerasList().remove(carrerasListCarreras);
                    oldFkOrientacionOfCarrerasListCarreras = em.merge(oldFkOrientacionOfCarrerasListCarreras);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOrientaciones(orientaciones.getId()) != null) {
                throw new PreexistingEntityException("Orientaciones " + orientaciones + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Orientaciones orientaciones) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Orientaciones persistentOrientaciones = em.find(Orientaciones.class, orientaciones.getId());
            List<Carreras> carrerasListOld = persistentOrientaciones.getCarrerasList();
            List<Carreras> carrerasListNew = orientaciones.getCarrerasList();
            List<Carreras> attachedCarrerasListNew = new ArrayList<Carreras>();
            for (Carreras carrerasListNewCarrerasToAttach : carrerasListNew) {
                carrerasListNewCarrerasToAttach = em.getReference(carrerasListNewCarrerasToAttach.getClass(), carrerasListNewCarrerasToAttach.getId());
                attachedCarrerasListNew.add(carrerasListNewCarrerasToAttach);
            }
            carrerasListNew = attachedCarrerasListNew;
            orientaciones.setCarrerasList(carrerasListNew);
            orientaciones = em.merge(orientaciones);
            for (Carreras carrerasListOldCarreras : carrerasListOld) {
                if (!carrerasListNew.contains(carrerasListOldCarreras)) {
                    carrerasListOldCarreras.setFkOrientacion(null);
                    carrerasListOldCarreras = em.merge(carrerasListOldCarreras);
                }
            }
            for (Carreras carrerasListNewCarreras : carrerasListNew) {
                if (!carrerasListOld.contains(carrerasListNewCarreras)) {
                    Orientaciones oldFkOrientacionOfCarrerasListNewCarreras = carrerasListNewCarreras.getFkOrientacion();
                    carrerasListNewCarreras.setFkOrientacion(orientaciones);
                    carrerasListNewCarreras = em.merge(carrerasListNewCarreras);
                    if (oldFkOrientacionOfCarrerasListNewCarreras != null && !oldFkOrientacionOfCarrerasListNewCarreras.equals(orientaciones)) {
                        oldFkOrientacionOfCarrerasListNewCarreras.getCarrerasList().remove(carrerasListNewCarreras);
                        oldFkOrientacionOfCarrerasListNewCarreras = em.merge(oldFkOrientacionOfCarrerasListNewCarreras);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = orientaciones.getId();
                if (findOrientaciones(id) == null) {
                    throw new NonexistentEntityException("The orientaciones with id " + id + " no longer exists.");
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
            Orientaciones orientaciones;
            try {
                orientaciones = em.getReference(Orientaciones.class, id);
                orientaciones.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orientaciones with id " + id + " no longer exists.", enfe);
            }
            List<Carreras> carrerasList = orientaciones.getCarrerasList();
            for (Carreras carrerasListCarreras : carrerasList) {
                carrerasListCarreras.setFkOrientacion(null);
                carrerasListCarreras = em.merge(carrerasListCarreras);
            }
            em.remove(orientaciones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Orientaciones> findOrientacionesEntities() {
        return findOrientacionesEntities(true, -1, -1);
    }

    public List<Orientaciones> findOrientacionesEntities(int maxResults, int firstResult) {
        return findOrientacionesEntities(false, maxResults, firstResult);
    }

    private List<Orientaciones> findOrientacionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Orientaciones.class));
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

    public Orientaciones findOrientaciones(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Orientaciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrientacionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Orientaciones> rt = cq.from(Orientaciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
