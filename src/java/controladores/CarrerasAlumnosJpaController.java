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
import entidad.Carreras;
import entidad.CarrerasAlumnos;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author juanc
 */
public class CarrerasAlumnosJpaController implements Serializable {

    public CarrerasAlumnosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CarrerasAlumnos carrerasAlumnos) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumnos fkIdentidadAlumno = carrerasAlumnos.getFkIdentidadAlumno();
            if (fkIdentidadAlumno != null) {
                fkIdentidadAlumno = em.getReference(fkIdentidadAlumno.getClass(), fkIdentidadAlumno.getId());
                carrerasAlumnos.setFkIdentidadAlumno(fkIdentidadAlumno);
            }
            Carreras fkCarrera = carrerasAlumnos.getFkCarrera();
            if (fkCarrera != null) {
                fkCarrera = em.getReference(fkCarrera.getClass(), fkCarrera.getId());
                carrerasAlumnos.setFkCarrera(fkCarrera);
            }
            em.persist(carrerasAlumnos);
            if (fkIdentidadAlumno != null) {
                fkIdentidadAlumno.getCarrerasAlumnosList().add(carrerasAlumnos);
                fkIdentidadAlumno = em.merge(fkIdentidadAlumno);
            }
            if (fkCarrera != null) {
                fkCarrera.getCarrerasAlumnosList().add(carrerasAlumnos);
                fkCarrera = em.merge(fkCarrera);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCarrerasAlumnos(carrerasAlumnos.getId()) != null) {
                throw new PreexistingEntityException("CarrerasAlumnos " + carrerasAlumnos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CarrerasAlumnos carrerasAlumnos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CarrerasAlumnos persistentCarrerasAlumnos = em.find(CarrerasAlumnos.class, carrerasAlumnos.getId());
            Alumnos fkIdentidadAlumnoOld = persistentCarrerasAlumnos.getFkIdentidadAlumno();
            Alumnos fkIdentidadAlumnoNew = carrerasAlumnos.getFkIdentidadAlumno();
            Carreras fkCarreraOld = persistentCarrerasAlumnos.getFkCarrera();
            Carreras fkCarreraNew = carrerasAlumnos.getFkCarrera();
            if (fkIdentidadAlumnoNew != null) {
                fkIdentidadAlumnoNew = em.getReference(fkIdentidadAlumnoNew.getClass(), fkIdentidadAlumnoNew.getId());
                carrerasAlumnos.setFkIdentidadAlumno(fkIdentidadAlumnoNew);
            }
            if (fkCarreraNew != null) {
                fkCarreraNew = em.getReference(fkCarreraNew.getClass(), fkCarreraNew.getId());
                carrerasAlumnos.setFkCarrera(fkCarreraNew);
            }
            carrerasAlumnos = em.merge(carrerasAlumnos);
            if (fkIdentidadAlumnoOld != null && !fkIdentidadAlumnoOld.equals(fkIdentidadAlumnoNew)) {
                fkIdentidadAlumnoOld.getCarrerasAlumnosList().remove(carrerasAlumnos);
                fkIdentidadAlumnoOld = em.merge(fkIdentidadAlumnoOld);
            }
            if (fkIdentidadAlumnoNew != null && !fkIdentidadAlumnoNew.equals(fkIdentidadAlumnoOld)) {
                fkIdentidadAlumnoNew.getCarrerasAlumnosList().add(carrerasAlumnos);
                fkIdentidadAlumnoNew = em.merge(fkIdentidadAlumnoNew);
            }
            if (fkCarreraOld != null && !fkCarreraOld.equals(fkCarreraNew)) {
                fkCarreraOld.getCarrerasAlumnosList().remove(carrerasAlumnos);
                fkCarreraOld = em.merge(fkCarreraOld);
            }
            if (fkCarreraNew != null && !fkCarreraNew.equals(fkCarreraOld)) {
                fkCarreraNew.getCarrerasAlumnosList().add(carrerasAlumnos);
                fkCarreraNew = em.merge(fkCarreraNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = carrerasAlumnos.getId();
                if (findCarrerasAlumnos(id) == null) {
                    throw new NonexistentEntityException("The carrerasAlumnos with id " + id + " no longer exists.");
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
            CarrerasAlumnos carrerasAlumnos;
            try {
                carrerasAlumnos = em.getReference(CarrerasAlumnos.class, id);
                carrerasAlumnos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The carrerasAlumnos with id " + id + " no longer exists.", enfe);
            }
            Alumnos fkIdentidadAlumno = carrerasAlumnos.getFkIdentidadAlumno();
            if (fkIdentidadAlumno != null) {
                fkIdentidadAlumno.getCarrerasAlumnosList().remove(carrerasAlumnos);
                fkIdentidadAlumno = em.merge(fkIdentidadAlumno);
            }
            Carreras fkCarrera = carrerasAlumnos.getFkCarrera();
            if (fkCarrera != null) {
                fkCarrera.getCarrerasAlumnosList().remove(carrerasAlumnos);
                fkCarrera = em.merge(fkCarrera);
            }
            em.remove(carrerasAlumnos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CarrerasAlumnos> findCarrerasAlumnosEntities() {
        return findCarrerasAlumnosEntities(true, -1, -1);
    }

    public List<CarrerasAlumnos> findCarrerasAlumnosEntities(int maxResults, int firstResult) {
        return findCarrerasAlumnosEntities(false, maxResults, firstResult);
    }

    private List<CarrerasAlumnos> findCarrerasAlumnosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CarrerasAlumnos.class));
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

    public CarrerasAlumnos findCarrerasAlumnos(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CarrerasAlumnos.class, id);
        } finally {
            em.close();
        }
    }

    public int getCarrerasAlumnosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CarrerasAlumnos> rt = cq.from(CarrerasAlumnos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
