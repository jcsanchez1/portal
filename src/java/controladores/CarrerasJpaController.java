/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import controladores.exceptions.PreexistingEntityException;
import entidad.Carreras;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidad.Orientaciones;
import entidad.PlanesEstudios;
import entidad.CarrerasAlumnos;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author juanc
 */
public class CarrerasJpaController implements Serializable {

    public CarrerasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Carreras carreras) throws PreexistingEntityException, Exception {
        if (carreras.getCarrerasAlumnosList() == null) {
            carreras.setCarrerasAlumnosList(new ArrayList<CarrerasAlumnos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Orientaciones fkOrientacion = carreras.getFkOrientacion();
            if (fkOrientacion != null) {
                fkOrientacion = em.getReference(fkOrientacion.getClass(), fkOrientacion.getId());
                carreras.setFkOrientacion(fkOrientacion);
            }
            PlanesEstudios fkPlan = carreras.getFkPlan();
            if (fkPlan != null) {
                fkPlan = em.getReference(fkPlan.getClass(), fkPlan.getId());
                carreras.setFkPlan(fkPlan);
            }
            List<CarrerasAlumnos> attachedCarrerasAlumnosList = new ArrayList<CarrerasAlumnos>();
            for (CarrerasAlumnos carrerasAlumnosListCarrerasAlumnosToAttach : carreras.getCarrerasAlumnosList()) {
                carrerasAlumnosListCarrerasAlumnosToAttach = em.getReference(carrerasAlumnosListCarrerasAlumnosToAttach.getClass(), carrerasAlumnosListCarrerasAlumnosToAttach.getId());
                attachedCarrerasAlumnosList.add(carrerasAlumnosListCarrerasAlumnosToAttach);
            }
            carreras.setCarrerasAlumnosList(attachedCarrerasAlumnosList);
            em.persist(carreras);
            if (fkOrientacion != null) {
                fkOrientacion.getCarrerasList().add(carreras);
                fkOrientacion = em.merge(fkOrientacion);
            }
            if (fkPlan != null) {
                fkPlan.getCarrerasList().add(carreras);
                fkPlan = em.merge(fkPlan);
            }
            for (CarrerasAlumnos carrerasAlumnosListCarrerasAlumnos : carreras.getCarrerasAlumnosList()) {
                Carreras oldFkCarreraOfCarrerasAlumnosListCarrerasAlumnos = carrerasAlumnosListCarrerasAlumnos.getFkCarrera();
                carrerasAlumnosListCarrerasAlumnos.setFkCarrera(carreras);
                carrerasAlumnosListCarrerasAlumnos = em.merge(carrerasAlumnosListCarrerasAlumnos);
                if (oldFkCarreraOfCarrerasAlumnosListCarrerasAlumnos != null) {
                    oldFkCarreraOfCarrerasAlumnosListCarrerasAlumnos.getCarrerasAlumnosList().remove(carrerasAlumnosListCarrerasAlumnos);
                    oldFkCarreraOfCarrerasAlumnosListCarrerasAlumnos = em.merge(oldFkCarreraOfCarrerasAlumnosListCarrerasAlumnos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCarreras(carreras.getId()) != null) {
                throw new PreexistingEntityException("Carreras " + carreras + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Carreras carreras) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Carreras persistentCarreras = em.find(Carreras.class, carreras.getId());
            Orientaciones fkOrientacionOld = persistentCarreras.getFkOrientacion();
            Orientaciones fkOrientacionNew = carreras.getFkOrientacion();
            PlanesEstudios fkPlanOld = persistentCarreras.getFkPlan();
            PlanesEstudios fkPlanNew = carreras.getFkPlan();
            List<CarrerasAlumnos> carrerasAlumnosListOld = persistentCarreras.getCarrerasAlumnosList();
            List<CarrerasAlumnos> carrerasAlumnosListNew = carreras.getCarrerasAlumnosList();
            if (fkOrientacionNew != null) {
                fkOrientacionNew = em.getReference(fkOrientacionNew.getClass(), fkOrientacionNew.getId());
                carreras.setFkOrientacion(fkOrientacionNew);
            }
            if (fkPlanNew != null) {
                fkPlanNew = em.getReference(fkPlanNew.getClass(), fkPlanNew.getId());
                carreras.setFkPlan(fkPlanNew);
            }
            List<CarrerasAlumnos> attachedCarrerasAlumnosListNew = new ArrayList<CarrerasAlumnos>();
            for (CarrerasAlumnos carrerasAlumnosListNewCarrerasAlumnosToAttach : carrerasAlumnosListNew) {
                carrerasAlumnosListNewCarrerasAlumnosToAttach = em.getReference(carrerasAlumnosListNewCarrerasAlumnosToAttach.getClass(), carrerasAlumnosListNewCarrerasAlumnosToAttach.getId());
                attachedCarrerasAlumnosListNew.add(carrerasAlumnosListNewCarrerasAlumnosToAttach);
            }
            carrerasAlumnosListNew = attachedCarrerasAlumnosListNew;
            carreras.setCarrerasAlumnosList(carrerasAlumnosListNew);
            carreras = em.merge(carreras);
            if (fkOrientacionOld != null && !fkOrientacionOld.equals(fkOrientacionNew)) {
                fkOrientacionOld.getCarrerasList().remove(carreras);
                fkOrientacionOld = em.merge(fkOrientacionOld);
            }
            if (fkOrientacionNew != null && !fkOrientacionNew.equals(fkOrientacionOld)) {
                fkOrientacionNew.getCarrerasList().add(carreras);
                fkOrientacionNew = em.merge(fkOrientacionNew);
            }
            if (fkPlanOld != null && !fkPlanOld.equals(fkPlanNew)) {
                fkPlanOld.getCarrerasList().remove(carreras);
                fkPlanOld = em.merge(fkPlanOld);
            }
            if (fkPlanNew != null && !fkPlanNew.equals(fkPlanOld)) {
                fkPlanNew.getCarrerasList().add(carreras);
                fkPlanNew = em.merge(fkPlanNew);
            }
            for (CarrerasAlumnos carrerasAlumnosListOldCarrerasAlumnos : carrerasAlumnosListOld) {
                if (!carrerasAlumnosListNew.contains(carrerasAlumnosListOldCarrerasAlumnos)) {
                    carrerasAlumnosListOldCarrerasAlumnos.setFkCarrera(null);
                    carrerasAlumnosListOldCarrerasAlumnos = em.merge(carrerasAlumnosListOldCarrerasAlumnos);
                }
            }
            for (CarrerasAlumnos carrerasAlumnosListNewCarrerasAlumnos : carrerasAlumnosListNew) {
                if (!carrerasAlumnosListOld.contains(carrerasAlumnosListNewCarrerasAlumnos)) {
                    Carreras oldFkCarreraOfCarrerasAlumnosListNewCarrerasAlumnos = carrerasAlumnosListNewCarrerasAlumnos.getFkCarrera();
                    carrerasAlumnosListNewCarrerasAlumnos.setFkCarrera(carreras);
                    carrerasAlumnosListNewCarrerasAlumnos = em.merge(carrerasAlumnosListNewCarrerasAlumnos);
                    if (oldFkCarreraOfCarrerasAlumnosListNewCarrerasAlumnos != null && !oldFkCarreraOfCarrerasAlumnosListNewCarrerasAlumnos.equals(carreras)) {
                        oldFkCarreraOfCarrerasAlumnosListNewCarrerasAlumnos.getCarrerasAlumnosList().remove(carrerasAlumnosListNewCarrerasAlumnos);
                        oldFkCarreraOfCarrerasAlumnosListNewCarrerasAlumnos = em.merge(oldFkCarreraOfCarrerasAlumnosListNewCarrerasAlumnos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = carreras.getId();
                if (findCarreras(id) == null) {
                    throw new NonexistentEntityException("The carreras with id " + id + " no longer exists.");
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
            Carreras carreras;
            try {
                carreras = em.getReference(Carreras.class, id);
                carreras.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The carreras with id " + id + " no longer exists.", enfe);
            }
            Orientaciones fkOrientacion = carreras.getFkOrientacion();
            if (fkOrientacion != null) {
                fkOrientacion.getCarrerasList().remove(carreras);
                fkOrientacion = em.merge(fkOrientacion);
            }
            PlanesEstudios fkPlan = carreras.getFkPlan();
            if (fkPlan != null) {
                fkPlan.getCarrerasList().remove(carreras);
                fkPlan = em.merge(fkPlan);
            }
            List<CarrerasAlumnos> carrerasAlumnosList = carreras.getCarrerasAlumnosList();
            for (CarrerasAlumnos carrerasAlumnosListCarrerasAlumnos : carrerasAlumnosList) {
                carrerasAlumnosListCarrerasAlumnos.setFkCarrera(null);
                carrerasAlumnosListCarrerasAlumnos = em.merge(carrerasAlumnosListCarrerasAlumnos);
            }
            em.remove(carreras);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Carreras> findCarrerasEntities() {
        return findCarrerasEntities(true, -1, -1);
    }

    public List<Carreras> findCarrerasEntities(int maxResults, int firstResult) {
        return findCarrerasEntities(false, maxResults, firstResult);
    }

    private List<Carreras> findCarrerasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Carreras.class));
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

    public Carreras findCarreras(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Carreras.class, id);
        } finally {
            em.close();
        }
    }

    public int getCarrerasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Carreras> rt = cq.from(Carreras.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
