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
import java.util.ArrayList;
import java.util.List;
import entidad.ClasesPlanesEstudio;
import entidad.PlanesEstudios;
import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author juanc
 */
public class PlanesEstudiosJpaController implements Serializable {

    public PlanesEstudiosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PlanesEstudios planesEstudios) throws PreexistingEntityException, Exception {
        if (planesEstudios.getCarrerasList() == null) {
            planesEstudios.setCarrerasList(new ArrayList<Carreras>());
        }
        if (planesEstudios.getClasesPlanesEstudioList() == null) {
            planesEstudios.setClasesPlanesEstudioList(new ArrayList<ClasesPlanesEstudio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Carreras> attachedCarrerasList = new ArrayList<Carreras>();
            for (Carreras carrerasListCarrerasToAttach : planesEstudios.getCarrerasList()) {
                carrerasListCarrerasToAttach = em.getReference(carrerasListCarrerasToAttach.getClass(), carrerasListCarrerasToAttach.getId());
                attachedCarrerasList.add(carrerasListCarrerasToAttach);
            }
            planesEstudios.setCarrerasList(attachedCarrerasList);
            List<ClasesPlanesEstudio> attachedClasesPlanesEstudioList = new ArrayList<ClasesPlanesEstudio>();
            for (ClasesPlanesEstudio clasesPlanesEstudioListClasesPlanesEstudioToAttach : planesEstudios.getClasesPlanesEstudioList()) {
                clasesPlanesEstudioListClasesPlanesEstudioToAttach = em.getReference(clasesPlanesEstudioListClasesPlanesEstudioToAttach.getClass(), clasesPlanesEstudioListClasesPlanesEstudioToAttach.getId());
                attachedClasesPlanesEstudioList.add(clasesPlanesEstudioListClasesPlanesEstudioToAttach);
            }
            planesEstudios.setClasesPlanesEstudioList(attachedClasesPlanesEstudioList);
            em.persist(planesEstudios);
            for (Carreras carrerasListCarreras : planesEstudios.getCarrerasList()) {
                PlanesEstudios oldFkPlanOfCarrerasListCarreras = carrerasListCarreras.getFkPlan();
                carrerasListCarreras.setFkPlan(planesEstudios);
                carrerasListCarreras = em.merge(carrerasListCarreras);
                if (oldFkPlanOfCarrerasListCarreras != null) {
                    oldFkPlanOfCarrerasListCarreras.getCarrerasList().remove(carrerasListCarreras);
                    oldFkPlanOfCarrerasListCarreras = em.merge(oldFkPlanOfCarrerasListCarreras);
                }
            }
            for (ClasesPlanesEstudio clasesPlanesEstudioListClasesPlanesEstudio : planesEstudios.getClasesPlanesEstudioList()) {
                PlanesEstudios oldFkPlanOfClasesPlanesEstudioListClasesPlanesEstudio = clasesPlanesEstudioListClasesPlanesEstudio.getFkPlan();
                clasesPlanesEstudioListClasesPlanesEstudio.setFkPlan(planesEstudios);
                clasesPlanesEstudioListClasesPlanesEstudio = em.merge(clasesPlanesEstudioListClasesPlanesEstudio);
                if (oldFkPlanOfClasesPlanesEstudioListClasesPlanesEstudio != null) {
                    oldFkPlanOfClasesPlanesEstudioListClasesPlanesEstudio.getClasesPlanesEstudioList().remove(clasesPlanesEstudioListClasesPlanesEstudio);
                    oldFkPlanOfClasesPlanesEstudioListClasesPlanesEstudio = em.merge(oldFkPlanOfClasesPlanesEstudioListClasesPlanesEstudio);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPlanesEstudios(planesEstudios.getId()) != null) {
                throw new PreexistingEntityException("PlanesEstudios " + planesEstudios + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PlanesEstudios planesEstudios) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PlanesEstudios persistentPlanesEstudios = em.find(PlanesEstudios.class, planesEstudios.getId());
            List<Carreras> carrerasListOld = persistentPlanesEstudios.getCarrerasList();
            List<Carreras> carrerasListNew = planesEstudios.getCarrerasList();
            List<ClasesPlanesEstudio> clasesPlanesEstudioListOld = persistentPlanesEstudios.getClasesPlanesEstudioList();
            List<ClasesPlanesEstudio> clasesPlanesEstudioListNew = planesEstudios.getClasesPlanesEstudioList();
            List<Carreras> attachedCarrerasListNew = new ArrayList<Carreras>();
            for (Carreras carrerasListNewCarrerasToAttach : carrerasListNew) {
                carrerasListNewCarrerasToAttach = em.getReference(carrerasListNewCarrerasToAttach.getClass(), carrerasListNewCarrerasToAttach.getId());
                attachedCarrerasListNew.add(carrerasListNewCarrerasToAttach);
            }
            carrerasListNew = attachedCarrerasListNew;
            planesEstudios.setCarrerasList(carrerasListNew);
            List<ClasesPlanesEstudio> attachedClasesPlanesEstudioListNew = new ArrayList<ClasesPlanesEstudio>();
            for (ClasesPlanesEstudio clasesPlanesEstudioListNewClasesPlanesEstudioToAttach : clasesPlanesEstudioListNew) {
                clasesPlanesEstudioListNewClasesPlanesEstudioToAttach = em.getReference(clasesPlanesEstudioListNewClasesPlanesEstudioToAttach.getClass(), clasesPlanesEstudioListNewClasesPlanesEstudioToAttach.getId());
                attachedClasesPlanesEstudioListNew.add(clasesPlanesEstudioListNewClasesPlanesEstudioToAttach);
            }
            clasesPlanesEstudioListNew = attachedClasesPlanesEstudioListNew;
            planesEstudios.setClasesPlanesEstudioList(clasesPlanesEstudioListNew);
            planesEstudios = em.merge(planesEstudios);
            for (Carreras carrerasListOldCarreras : carrerasListOld) {
                if (!carrerasListNew.contains(carrerasListOldCarreras)) {
                    carrerasListOldCarreras.setFkPlan(null);
                    carrerasListOldCarreras = em.merge(carrerasListOldCarreras);
                }
            }
            for (Carreras carrerasListNewCarreras : carrerasListNew) {
                if (!carrerasListOld.contains(carrerasListNewCarreras)) {
                    PlanesEstudios oldFkPlanOfCarrerasListNewCarreras = carrerasListNewCarreras.getFkPlan();
                    carrerasListNewCarreras.setFkPlan(planesEstudios);
                    carrerasListNewCarreras = em.merge(carrerasListNewCarreras);
                    if (oldFkPlanOfCarrerasListNewCarreras != null && !oldFkPlanOfCarrerasListNewCarreras.equals(planesEstudios)) {
                        oldFkPlanOfCarrerasListNewCarreras.getCarrerasList().remove(carrerasListNewCarreras);
                        oldFkPlanOfCarrerasListNewCarreras = em.merge(oldFkPlanOfCarrerasListNewCarreras);
                    }
                }
            }
            for (ClasesPlanesEstudio clasesPlanesEstudioListOldClasesPlanesEstudio : clasesPlanesEstudioListOld) {
                if (!clasesPlanesEstudioListNew.contains(clasesPlanesEstudioListOldClasesPlanesEstudio)) {
                    clasesPlanesEstudioListOldClasesPlanesEstudio.setFkPlan(null);
                    clasesPlanesEstudioListOldClasesPlanesEstudio = em.merge(clasesPlanesEstudioListOldClasesPlanesEstudio);
                }
            }
            for (ClasesPlanesEstudio clasesPlanesEstudioListNewClasesPlanesEstudio : clasesPlanesEstudioListNew) {
                if (!clasesPlanesEstudioListOld.contains(clasesPlanesEstudioListNewClasesPlanesEstudio)) {
                    PlanesEstudios oldFkPlanOfClasesPlanesEstudioListNewClasesPlanesEstudio = clasesPlanesEstudioListNewClasesPlanesEstudio.getFkPlan();
                    clasesPlanesEstudioListNewClasesPlanesEstudio.setFkPlan(planesEstudios);
                    clasesPlanesEstudioListNewClasesPlanesEstudio = em.merge(clasesPlanesEstudioListNewClasesPlanesEstudio);
                    if (oldFkPlanOfClasesPlanesEstudioListNewClasesPlanesEstudio != null && !oldFkPlanOfClasesPlanesEstudioListNewClasesPlanesEstudio.equals(planesEstudios)) {
                        oldFkPlanOfClasesPlanesEstudioListNewClasesPlanesEstudio.getClasesPlanesEstudioList().remove(clasesPlanesEstudioListNewClasesPlanesEstudio);
                        oldFkPlanOfClasesPlanesEstudioListNewClasesPlanesEstudio = em.merge(oldFkPlanOfClasesPlanesEstudioListNewClasesPlanesEstudio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = planesEstudios.getId();
                if (findPlanesEstudios(id) == null) {
                    throw new NonexistentEntityException("The planesEstudios with id " + id + " no longer exists.");
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
            PlanesEstudios planesEstudios;
            try {
                planesEstudios = em.getReference(PlanesEstudios.class, id);
                planesEstudios.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The planesEstudios with id " + id + " no longer exists.", enfe);
            }
            List<Carreras> carrerasList = planesEstudios.getCarrerasList();
            for (Carreras carrerasListCarreras : carrerasList) {
                carrerasListCarreras.setFkPlan(null);
                carrerasListCarreras = em.merge(carrerasListCarreras);
            }
            List<ClasesPlanesEstudio> clasesPlanesEstudioList = planesEstudios.getClasesPlanesEstudioList();
            for (ClasesPlanesEstudio clasesPlanesEstudioListClasesPlanesEstudio : clasesPlanesEstudioList) {
                clasesPlanesEstudioListClasesPlanesEstudio.setFkPlan(null);
                clasesPlanesEstudioListClasesPlanesEstudio = em.merge(clasesPlanesEstudioListClasesPlanesEstudio);
            }
            em.remove(planesEstudios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PlanesEstudios> findPlanesEstudiosEntities() {
        return findPlanesEstudiosEntities(true, -1, -1);
    }

    public List<PlanesEstudios> findPlanesEstudiosEntities(int maxResults, int firstResult) {
        return findPlanesEstudiosEntities(false, maxResults, firstResult);
    }

    private List<PlanesEstudios> findPlanesEstudiosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PlanesEstudios.class));
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

    public PlanesEstudios findPlanesEstudios(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PlanesEstudios.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanesEstudiosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PlanesEstudios> rt = cq.from(PlanesEstudios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
