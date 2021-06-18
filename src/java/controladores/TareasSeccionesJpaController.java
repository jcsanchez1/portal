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
import entidad.TareasAlumnos;
import entidad.TareasSecciones;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author juanc
 */
public class TareasSeccionesJpaController implements Serializable {

    public TareasSeccionesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TareasSecciones tareasSecciones) throws PreexistingEntityException, Exception {
        if (tareasSecciones.getTareasAlumnosList() == null) {
            tareasSecciones.setTareasAlumnosList(new ArrayList<TareasAlumnos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Maestros fkDocente = tareasSecciones.getFkDocente();
            if (fkDocente != null) {
                fkDocente = em.getReference(fkDocente.getClass(), fkDocente.getId());
                tareasSecciones.setFkDocente(fkDocente);
            }
            Secciones fkSeccion = tareasSecciones.getFkSeccion();
            if (fkSeccion != null) {
                fkSeccion = em.getReference(fkSeccion.getClass(), fkSeccion.getId());
                tareasSecciones.setFkSeccion(fkSeccion);
            }
            List<TareasAlumnos> attachedTareasAlumnosList = new ArrayList<TareasAlumnos>();
            for (TareasAlumnos tareasAlumnosListTareasAlumnosToAttach : tareasSecciones.getTareasAlumnosList()) {
                tareasAlumnosListTareasAlumnosToAttach = em.getReference(tareasAlumnosListTareasAlumnosToAttach.getClass(), tareasAlumnosListTareasAlumnosToAttach.getId());
                attachedTareasAlumnosList.add(tareasAlumnosListTareasAlumnosToAttach);
            }
            tareasSecciones.setTareasAlumnosList(attachedTareasAlumnosList);
            em.persist(tareasSecciones);
            if (fkDocente != null) {
                fkDocente.getTareasSeccionesList().add(tareasSecciones);
                fkDocente = em.merge(fkDocente);
            }
            if (fkSeccion != null) {
                fkSeccion.getTareasSeccionesList().add(tareasSecciones);
                fkSeccion = em.merge(fkSeccion);
            }
            for (TareasAlumnos tareasAlumnosListTareasAlumnos : tareasSecciones.getTareasAlumnosList()) {
                TareasSecciones oldFkTareaOfTareasAlumnosListTareasAlumnos = tareasAlumnosListTareasAlumnos.getFkTarea();
                tareasAlumnosListTareasAlumnos.setFkTarea(tareasSecciones);
                tareasAlumnosListTareasAlumnos = em.merge(tareasAlumnosListTareasAlumnos);
                if (oldFkTareaOfTareasAlumnosListTareasAlumnos != null) {
                    oldFkTareaOfTareasAlumnosListTareasAlumnos.getTareasAlumnosList().remove(tareasAlumnosListTareasAlumnos);
                    oldFkTareaOfTareasAlumnosListTareasAlumnos = em.merge(oldFkTareaOfTareasAlumnosListTareasAlumnos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTareasSecciones(tareasSecciones.getId()) != null) {
                throw new PreexistingEntityException("TareasSecciones " + tareasSecciones + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TareasSecciones tareasSecciones) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TareasSecciones persistentTareasSecciones = em.find(TareasSecciones.class, tareasSecciones.getId());
            Maestros fkDocenteOld = persistentTareasSecciones.getFkDocente();
            Maestros fkDocenteNew = tareasSecciones.getFkDocente();
            Secciones fkSeccionOld = persistentTareasSecciones.getFkSeccion();
            Secciones fkSeccionNew = tareasSecciones.getFkSeccion();
            List<TareasAlumnos> tareasAlumnosListOld = persistentTareasSecciones.getTareasAlumnosList();
            List<TareasAlumnos> tareasAlumnosListNew = tareasSecciones.getTareasAlumnosList();
            if (fkDocenteNew != null) {
                fkDocenteNew = em.getReference(fkDocenteNew.getClass(), fkDocenteNew.getId());
                tareasSecciones.setFkDocente(fkDocenteNew);
            }
            if (fkSeccionNew != null) {
                fkSeccionNew = em.getReference(fkSeccionNew.getClass(), fkSeccionNew.getId());
                tareasSecciones.setFkSeccion(fkSeccionNew);
            }
            List<TareasAlumnos> attachedTareasAlumnosListNew = new ArrayList<TareasAlumnos>();
            for (TareasAlumnos tareasAlumnosListNewTareasAlumnosToAttach : tareasAlumnosListNew) {
                tareasAlumnosListNewTareasAlumnosToAttach = em.getReference(tareasAlumnosListNewTareasAlumnosToAttach.getClass(), tareasAlumnosListNewTareasAlumnosToAttach.getId());
                attachedTareasAlumnosListNew.add(tareasAlumnosListNewTareasAlumnosToAttach);
            }
            tareasAlumnosListNew = attachedTareasAlumnosListNew;
            tareasSecciones.setTareasAlumnosList(tareasAlumnosListNew);
            tareasSecciones = em.merge(tareasSecciones);
            if (fkDocenteOld != null && !fkDocenteOld.equals(fkDocenteNew)) {
                fkDocenteOld.getTareasSeccionesList().remove(tareasSecciones);
                fkDocenteOld = em.merge(fkDocenteOld);
            }
            if (fkDocenteNew != null && !fkDocenteNew.equals(fkDocenteOld)) {
                fkDocenteNew.getTareasSeccionesList().add(tareasSecciones);
                fkDocenteNew = em.merge(fkDocenteNew);
            }
            if (fkSeccionOld != null && !fkSeccionOld.equals(fkSeccionNew)) {
                fkSeccionOld.getTareasSeccionesList().remove(tareasSecciones);
                fkSeccionOld = em.merge(fkSeccionOld);
            }
            if (fkSeccionNew != null && !fkSeccionNew.equals(fkSeccionOld)) {
                fkSeccionNew.getTareasSeccionesList().add(tareasSecciones);
                fkSeccionNew = em.merge(fkSeccionNew);
            }
            for (TareasAlumnos tareasAlumnosListOldTareasAlumnos : tareasAlumnosListOld) {
                if (!tareasAlumnosListNew.contains(tareasAlumnosListOldTareasAlumnos)) {
                    tareasAlumnosListOldTareasAlumnos.setFkTarea(null);
                    tareasAlumnosListOldTareasAlumnos = em.merge(tareasAlumnosListOldTareasAlumnos);
                }
            }
            for (TareasAlumnos tareasAlumnosListNewTareasAlumnos : tareasAlumnosListNew) {
                if (!tareasAlumnosListOld.contains(tareasAlumnosListNewTareasAlumnos)) {
                    TareasSecciones oldFkTareaOfTareasAlumnosListNewTareasAlumnos = tareasAlumnosListNewTareasAlumnos.getFkTarea();
                    tareasAlumnosListNewTareasAlumnos.setFkTarea(tareasSecciones);
                    tareasAlumnosListNewTareasAlumnos = em.merge(tareasAlumnosListNewTareasAlumnos);
                    if (oldFkTareaOfTareasAlumnosListNewTareasAlumnos != null && !oldFkTareaOfTareasAlumnosListNewTareasAlumnos.equals(tareasSecciones)) {
                        oldFkTareaOfTareasAlumnosListNewTareasAlumnos.getTareasAlumnosList().remove(tareasAlumnosListNewTareasAlumnos);
                        oldFkTareaOfTareasAlumnosListNewTareasAlumnos = em.merge(oldFkTareaOfTareasAlumnosListNewTareasAlumnos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = tareasSecciones.getId();
                if (findTareasSecciones(id) == null) {
                    throw new NonexistentEntityException("The tareasSecciones with id " + id + " no longer exists.");
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
            TareasSecciones tareasSecciones;
            try {
                tareasSecciones = em.getReference(TareasSecciones.class, id);
                tareasSecciones.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tareasSecciones with id " + id + " no longer exists.", enfe);
            }
            Maestros fkDocente = tareasSecciones.getFkDocente();
            if (fkDocente != null) {
                fkDocente.getTareasSeccionesList().remove(tareasSecciones);
                fkDocente = em.merge(fkDocente);
            }
            Secciones fkSeccion = tareasSecciones.getFkSeccion();
            if (fkSeccion != null) {
                fkSeccion.getTareasSeccionesList().remove(tareasSecciones);
                fkSeccion = em.merge(fkSeccion);
            }
            List<TareasAlumnos> tareasAlumnosList = tareasSecciones.getTareasAlumnosList();
            for (TareasAlumnos tareasAlumnosListTareasAlumnos : tareasAlumnosList) {
                tareasAlumnosListTareasAlumnos.setFkTarea(null);
                tareasAlumnosListTareasAlumnos = em.merge(tareasAlumnosListTareasAlumnos);
            }
            em.remove(tareasSecciones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TareasSecciones> findTareasSeccionesEntities() {
        return findTareasSeccionesEntities(true, -1, -1);
    }

    public List<TareasSecciones> findTareasSeccionesEntities(int maxResults, int firstResult) {
        return findTareasSeccionesEntities(false, maxResults, firstResult);
    }

    private List<TareasSecciones> findTareasSeccionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TareasSecciones.class));
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

    public TareasSecciones findTareasSecciones(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TareasSecciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getTareasSeccionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TareasSecciones> rt = cq.from(TareasSecciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
