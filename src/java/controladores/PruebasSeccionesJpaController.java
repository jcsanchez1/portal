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
import entidad.Maestros;
import entidad.Secciones;
import entidad.PruebasAlumnos;
import java.util.ArrayList;
import java.util.List;
import entidad.PreguntasPruebasSecciones;
import entidad.PruebasSecciones;
import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author juanc
 */
public class PruebasSeccionesJpaController implements Serializable {

    public PruebasSeccionesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PruebasSecciones pruebasSecciones) throws PreexistingEntityException, Exception {
        if (pruebasSecciones.getPruebasAlumnosList() == null) {
            pruebasSecciones.setPruebasAlumnosList(new ArrayList<PruebasAlumnos>());
        }
        if (pruebasSecciones.getPreguntasPruebasSeccionesList() == null) {
            pruebasSecciones.setPreguntasPruebasSeccionesList(new ArrayList<PreguntasPruebasSecciones>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Maestros fkMaestro = pruebasSecciones.getFkMaestro();
            if (fkMaestro != null) {
                fkMaestro = em.getReference(fkMaestro.getClass(), fkMaestro.getId());
                pruebasSecciones.setFkMaestro(fkMaestro);
            }
            Secciones fkSeccion = pruebasSecciones.getFkSeccion();
            if (fkSeccion != null) {
                fkSeccion = em.getReference(fkSeccion.getClass(), fkSeccion.getId());
                pruebasSecciones.setFkSeccion(fkSeccion);
            }
            List<PruebasAlumnos> attachedPruebasAlumnosList = new ArrayList<PruebasAlumnos>();
            for (PruebasAlumnos pruebasAlumnosListPruebasAlumnosToAttach : pruebasSecciones.getPruebasAlumnosList()) {
                pruebasAlumnosListPruebasAlumnosToAttach = em.getReference(pruebasAlumnosListPruebasAlumnosToAttach.getClass(), pruebasAlumnosListPruebasAlumnosToAttach.getId());
                attachedPruebasAlumnosList.add(pruebasAlumnosListPruebasAlumnosToAttach);
            }
            pruebasSecciones.setPruebasAlumnosList(attachedPruebasAlumnosList);
            List<PreguntasPruebasSecciones> attachedPreguntasPruebasSeccionesList = new ArrayList<PreguntasPruebasSecciones>();
            for (PreguntasPruebasSecciones preguntasPruebasSeccionesListPreguntasPruebasSeccionesToAttach : pruebasSecciones.getPreguntasPruebasSeccionesList()) {
                preguntasPruebasSeccionesListPreguntasPruebasSeccionesToAttach = em.getReference(preguntasPruebasSeccionesListPreguntasPruebasSeccionesToAttach.getClass(), preguntasPruebasSeccionesListPreguntasPruebasSeccionesToAttach.getId());
                attachedPreguntasPruebasSeccionesList.add(preguntasPruebasSeccionesListPreguntasPruebasSeccionesToAttach);
            }
            pruebasSecciones.setPreguntasPruebasSeccionesList(attachedPreguntasPruebasSeccionesList);
            em.persist(pruebasSecciones);
            if (fkMaestro != null) {
                fkMaestro.getPruebasSeccionesList().add(pruebasSecciones);
                fkMaestro = em.merge(fkMaestro);
            }
            if (fkSeccion != null) {
                fkSeccion.getPruebasSeccionesList().add(pruebasSecciones);
                fkSeccion = em.merge(fkSeccion);
            }
            for (PruebasAlumnos pruebasAlumnosListPruebasAlumnos : pruebasSecciones.getPruebasAlumnosList()) {
                PruebasSecciones oldFkPruebaSeccionOfPruebasAlumnosListPruebasAlumnos = pruebasAlumnosListPruebasAlumnos.getFkPruebaSeccion();
                pruebasAlumnosListPruebasAlumnos.setFkPruebaSeccion(pruebasSecciones);
                pruebasAlumnosListPruebasAlumnos = em.merge(pruebasAlumnosListPruebasAlumnos);
                if (oldFkPruebaSeccionOfPruebasAlumnosListPruebasAlumnos != null) {
                    oldFkPruebaSeccionOfPruebasAlumnosListPruebasAlumnos.getPruebasAlumnosList().remove(pruebasAlumnosListPruebasAlumnos);
                    oldFkPruebaSeccionOfPruebasAlumnosListPruebasAlumnos = em.merge(oldFkPruebaSeccionOfPruebasAlumnosListPruebasAlumnos);
                }
            }
            for (PreguntasPruebasSecciones preguntasPruebasSeccionesListPreguntasPruebasSecciones : pruebasSecciones.getPreguntasPruebasSeccionesList()) {
                PruebasSecciones oldFkPruebaSeccionOfPreguntasPruebasSeccionesListPreguntasPruebasSecciones = preguntasPruebasSeccionesListPreguntasPruebasSecciones.getFkPruebaSeccion();
                preguntasPruebasSeccionesListPreguntasPruebasSecciones.setFkPruebaSeccion(pruebasSecciones);
                preguntasPruebasSeccionesListPreguntasPruebasSecciones = em.merge(preguntasPruebasSeccionesListPreguntasPruebasSecciones);
                if (oldFkPruebaSeccionOfPreguntasPruebasSeccionesListPreguntasPruebasSecciones != null) {
                    oldFkPruebaSeccionOfPreguntasPruebasSeccionesListPreguntasPruebasSecciones.getPreguntasPruebasSeccionesList().remove(preguntasPruebasSeccionesListPreguntasPruebasSecciones);
                    oldFkPruebaSeccionOfPreguntasPruebasSeccionesListPreguntasPruebasSecciones = em.merge(oldFkPruebaSeccionOfPreguntasPruebasSeccionesListPreguntasPruebasSecciones);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPruebasSecciones(pruebasSecciones.getId()) != null) {
                throw new PreexistingEntityException("PruebasSecciones " + pruebasSecciones + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PruebasSecciones pruebasSecciones) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PruebasSecciones persistentPruebasSecciones = em.find(PruebasSecciones.class, pruebasSecciones.getId());
            Maestros fkMaestroOld = persistentPruebasSecciones.getFkMaestro();
            Maestros fkMaestroNew = pruebasSecciones.getFkMaestro();
            Secciones fkSeccionOld = persistentPruebasSecciones.getFkSeccion();
            Secciones fkSeccionNew = pruebasSecciones.getFkSeccion();
            List<PruebasAlumnos> pruebasAlumnosListOld = persistentPruebasSecciones.getPruebasAlumnosList();
            List<PruebasAlumnos> pruebasAlumnosListNew = pruebasSecciones.getPruebasAlumnosList();
            List<PreguntasPruebasSecciones> preguntasPruebasSeccionesListOld = persistentPruebasSecciones.getPreguntasPruebasSeccionesList();
            List<PreguntasPruebasSecciones> preguntasPruebasSeccionesListNew = pruebasSecciones.getPreguntasPruebasSeccionesList();
            if (fkMaestroNew != null) {
                fkMaestroNew = em.getReference(fkMaestroNew.getClass(), fkMaestroNew.getId());
                pruebasSecciones.setFkMaestro(fkMaestroNew);
            }
            if (fkSeccionNew != null) {
                fkSeccionNew = em.getReference(fkSeccionNew.getClass(), fkSeccionNew.getId());
                pruebasSecciones.setFkSeccion(fkSeccionNew);
            }
            List<PruebasAlumnos> attachedPruebasAlumnosListNew = new ArrayList<PruebasAlumnos>();
            for (PruebasAlumnos pruebasAlumnosListNewPruebasAlumnosToAttach : pruebasAlumnosListNew) {
                pruebasAlumnosListNewPruebasAlumnosToAttach = em.getReference(pruebasAlumnosListNewPruebasAlumnosToAttach.getClass(), pruebasAlumnosListNewPruebasAlumnosToAttach.getId());
                attachedPruebasAlumnosListNew.add(pruebasAlumnosListNewPruebasAlumnosToAttach);
            }
            pruebasAlumnosListNew = attachedPruebasAlumnosListNew;
            pruebasSecciones.setPruebasAlumnosList(pruebasAlumnosListNew);
            List<PreguntasPruebasSecciones> attachedPreguntasPruebasSeccionesListNew = new ArrayList<PreguntasPruebasSecciones>();
            for (PreguntasPruebasSecciones preguntasPruebasSeccionesListNewPreguntasPruebasSeccionesToAttach : preguntasPruebasSeccionesListNew) {
                preguntasPruebasSeccionesListNewPreguntasPruebasSeccionesToAttach = em.getReference(preguntasPruebasSeccionesListNewPreguntasPruebasSeccionesToAttach.getClass(), preguntasPruebasSeccionesListNewPreguntasPruebasSeccionesToAttach.getId());
                attachedPreguntasPruebasSeccionesListNew.add(preguntasPruebasSeccionesListNewPreguntasPruebasSeccionesToAttach);
            }
            preguntasPruebasSeccionesListNew = attachedPreguntasPruebasSeccionesListNew;
            pruebasSecciones.setPreguntasPruebasSeccionesList(preguntasPruebasSeccionesListNew);
            pruebasSecciones = em.merge(pruebasSecciones);
            if (fkMaestroOld != null && !fkMaestroOld.equals(fkMaestroNew)) {
                fkMaestroOld.getPruebasSeccionesList().remove(pruebasSecciones);
                fkMaestroOld = em.merge(fkMaestroOld);
            }
            if (fkMaestroNew != null && !fkMaestroNew.equals(fkMaestroOld)) {
                fkMaestroNew.getPruebasSeccionesList().add(pruebasSecciones);
                fkMaestroNew = em.merge(fkMaestroNew);
            }
            if (fkSeccionOld != null && !fkSeccionOld.equals(fkSeccionNew)) {
                fkSeccionOld.getPruebasSeccionesList().remove(pruebasSecciones);
                fkSeccionOld = em.merge(fkSeccionOld);
            }
            if (fkSeccionNew != null && !fkSeccionNew.equals(fkSeccionOld)) {
                fkSeccionNew.getPruebasSeccionesList().add(pruebasSecciones);
                fkSeccionNew = em.merge(fkSeccionNew);
            }
            for (PruebasAlumnos pruebasAlumnosListOldPruebasAlumnos : pruebasAlumnosListOld) {
                if (!pruebasAlumnosListNew.contains(pruebasAlumnosListOldPruebasAlumnos)) {
                    pruebasAlumnosListOldPruebasAlumnos.setFkPruebaSeccion(null);
                    pruebasAlumnosListOldPruebasAlumnos = em.merge(pruebasAlumnosListOldPruebasAlumnos);
                }
            }
            for (PruebasAlumnos pruebasAlumnosListNewPruebasAlumnos : pruebasAlumnosListNew) {
                if (!pruebasAlumnosListOld.contains(pruebasAlumnosListNewPruebasAlumnos)) {
                    PruebasSecciones oldFkPruebaSeccionOfPruebasAlumnosListNewPruebasAlumnos = pruebasAlumnosListNewPruebasAlumnos.getFkPruebaSeccion();
                    pruebasAlumnosListNewPruebasAlumnos.setFkPruebaSeccion(pruebasSecciones);
                    pruebasAlumnosListNewPruebasAlumnos = em.merge(pruebasAlumnosListNewPruebasAlumnos);
                    if (oldFkPruebaSeccionOfPruebasAlumnosListNewPruebasAlumnos != null && !oldFkPruebaSeccionOfPruebasAlumnosListNewPruebasAlumnos.equals(pruebasSecciones)) {
                        oldFkPruebaSeccionOfPruebasAlumnosListNewPruebasAlumnos.getPruebasAlumnosList().remove(pruebasAlumnosListNewPruebasAlumnos);
                        oldFkPruebaSeccionOfPruebasAlumnosListNewPruebasAlumnos = em.merge(oldFkPruebaSeccionOfPruebasAlumnosListNewPruebasAlumnos);
                    }
                }
            }
            for (PreguntasPruebasSecciones preguntasPruebasSeccionesListOldPreguntasPruebasSecciones : preguntasPruebasSeccionesListOld) {
                if (!preguntasPruebasSeccionesListNew.contains(preguntasPruebasSeccionesListOldPreguntasPruebasSecciones)) {
                    preguntasPruebasSeccionesListOldPreguntasPruebasSecciones.setFkPruebaSeccion(null);
                    preguntasPruebasSeccionesListOldPreguntasPruebasSecciones = em.merge(preguntasPruebasSeccionesListOldPreguntasPruebasSecciones);
                }
            }
            for (PreguntasPruebasSecciones preguntasPruebasSeccionesListNewPreguntasPruebasSecciones : preguntasPruebasSeccionesListNew) {
                if (!preguntasPruebasSeccionesListOld.contains(preguntasPruebasSeccionesListNewPreguntasPruebasSecciones)) {
                    PruebasSecciones oldFkPruebaSeccionOfPreguntasPruebasSeccionesListNewPreguntasPruebasSecciones = preguntasPruebasSeccionesListNewPreguntasPruebasSecciones.getFkPruebaSeccion();
                    preguntasPruebasSeccionesListNewPreguntasPruebasSecciones.setFkPruebaSeccion(pruebasSecciones);
                    preguntasPruebasSeccionesListNewPreguntasPruebasSecciones = em.merge(preguntasPruebasSeccionesListNewPreguntasPruebasSecciones);
                    if (oldFkPruebaSeccionOfPreguntasPruebasSeccionesListNewPreguntasPruebasSecciones != null && !oldFkPruebaSeccionOfPreguntasPruebasSeccionesListNewPreguntasPruebasSecciones.equals(pruebasSecciones)) {
                        oldFkPruebaSeccionOfPreguntasPruebasSeccionesListNewPreguntasPruebasSecciones.getPreguntasPruebasSeccionesList().remove(preguntasPruebasSeccionesListNewPreguntasPruebasSecciones);
                        oldFkPruebaSeccionOfPreguntasPruebasSeccionesListNewPreguntasPruebasSecciones = em.merge(oldFkPruebaSeccionOfPreguntasPruebasSeccionesListNewPreguntasPruebasSecciones);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = pruebasSecciones.getId();
                if (findPruebasSecciones(id) == null) {
                    throw new NonexistentEntityException("The pruebasSecciones with id " + id + " no longer exists.");
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
            PruebasSecciones pruebasSecciones;
            try {
                pruebasSecciones = em.getReference(PruebasSecciones.class, id);
                pruebasSecciones.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pruebasSecciones with id " + id + " no longer exists.", enfe);
            }
            Maestros fkMaestro = pruebasSecciones.getFkMaestro();
            if (fkMaestro != null) {
                fkMaestro.getPruebasSeccionesList().remove(pruebasSecciones);
                fkMaestro = em.merge(fkMaestro);
            }
            Secciones fkSeccion = pruebasSecciones.getFkSeccion();
            if (fkSeccion != null) {
                fkSeccion.getPruebasSeccionesList().remove(pruebasSecciones);
                fkSeccion = em.merge(fkSeccion);
            }
            List<PruebasAlumnos> pruebasAlumnosList = pruebasSecciones.getPruebasAlumnosList();
            for (PruebasAlumnos pruebasAlumnosListPruebasAlumnos : pruebasAlumnosList) {
                pruebasAlumnosListPruebasAlumnos.setFkPruebaSeccion(null);
                pruebasAlumnosListPruebasAlumnos = em.merge(pruebasAlumnosListPruebasAlumnos);
            }
            List<PreguntasPruebasSecciones> preguntasPruebasSeccionesList = pruebasSecciones.getPreguntasPruebasSeccionesList();
            for (PreguntasPruebasSecciones preguntasPruebasSeccionesListPreguntasPruebasSecciones : preguntasPruebasSeccionesList) {
                preguntasPruebasSeccionesListPreguntasPruebasSecciones.setFkPruebaSeccion(null);
                preguntasPruebasSeccionesListPreguntasPruebasSecciones = em.merge(preguntasPruebasSeccionesListPreguntasPruebasSecciones);
            }
            em.remove(pruebasSecciones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PruebasSecciones> findPruebasSeccionesEntities() {
        return findPruebasSeccionesEntities(true, -1, -1);
    }

    public List<PruebasSecciones> findPruebasSeccionesEntities(int maxResults, int firstResult) {
        return findPruebasSeccionesEntities(false, maxResults, firstResult);
    }

    private List<PruebasSecciones> findPruebasSeccionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PruebasSecciones.class));
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

    public PruebasSecciones findPruebasSecciones(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PruebasSecciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getPruebasSeccionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PruebasSecciones> rt = cq.from(PruebasSecciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
