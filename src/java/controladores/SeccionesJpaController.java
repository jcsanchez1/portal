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
import entidad.Clases;
import entidad.Maestros;
import entidad.Modalidades;
import entidad.PeriodoActual;
import entidad.Sedes;
import entidad.ForoSecciones;
import java.util.ArrayList;
import java.util.List;
import entidad.PruebasSecciones;
import entidad.TareasSecciones;
import entidad.ActividadesClases;
import entidad.AlumnosSecciones;
import entidad.Secciones;
import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author juanc
 */
public class SeccionesJpaController implements Serializable {

    public SeccionesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Secciones secciones) throws PreexistingEntityException, Exception {
        if (secciones.getForoSeccionesList() == null) {
            secciones.setForoSeccionesList(new ArrayList<ForoSecciones>());
        }
        if (secciones.getPruebasSeccionesList() == null) {
            secciones.setPruebasSeccionesList(new ArrayList<PruebasSecciones>());
        }
        if (secciones.getTareasSeccionesList() == null) {
            secciones.setTareasSeccionesList(new ArrayList<TareasSecciones>());
        }
        if (secciones.getActividadesClasesList() == null) {
            secciones.setActividadesClasesList(new ArrayList<ActividadesClases>());
        }
        if (secciones.getAlumnosSeccionesList() == null) {
            secciones.setAlumnosSeccionesList(new ArrayList<AlumnosSecciones>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clases fkCodigoClase = secciones.getFkCodigoClase();
            if (fkCodigoClase != null) {
                fkCodigoClase = em.getReference(fkCodigoClase.getClass(), fkCodigoClase.getId());
                secciones.setFkCodigoClase(fkCodigoClase);
            }
            Maestros fkDocente = secciones.getFkDocente();
            if (fkDocente != null) {
                fkDocente = em.getReference(fkDocente.getClass(), fkDocente.getId());
                secciones.setFkDocente(fkDocente);
            }
            Modalidades fkModalidad = secciones.getFkModalidad();
            if (fkModalidad != null) {
                fkModalidad = em.getReference(fkModalidad.getClass(), fkModalidad.getId());
                secciones.setFkModalidad(fkModalidad);
            }
            PeriodoActual fkPeriodoActual = secciones.getFkPeriodoActual();
            if (fkPeriodoActual != null) {
                fkPeriodoActual = em.getReference(fkPeriodoActual.getClass(), fkPeriodoActual.getId());
                secciones.setFkPeriodoActual(fkPeriodoActual);
            }
            Sedes fkSede = secciones.getFkSede();
            if (fkSede != null) {
                fkSede = em.getReference(fkSede.getClass(), fkSede.getId());
                secciones.setFkSede(fkSede);
            }
            List<ForoSecciones> attachedForoSeccionesList = new ArrayList<ForoSecciones>();
            for (ForoSecciones foroSeccionesListForoSeccionesToAttach : secciones.getForoSeccionesList()) {
                foroSeccionesListForoSeccionesToAttach = em.getReference(foroSeccionesListForoSeccionesToAttach.getClass(), foroSeccionesListForoSeccionesToAttach.getId());
                attachedForoSeccionesList.add(foroSeccionesListForoSeccionesToAttach);
            }
            secciones.setForoSeccionesList(attachedForoSeccionesList);
            List<PruebasSecciones> attachedPruebasSeccionesList = new ArrayList<PruebasSecciones>();
            for (PruebasSecciones pruebasSeccionesListPruebasSeccionesToAttach : secciones.getPruebasSeccionesList()) {
                pruebasSeccionesListPruebasSeccionesToAttach = em.getReference(pruebasSeccionesListPruebasSeccionesToAttach.getClass(), pruebasSeccionesListPruebasSeccionesToAttach.getId());
                attachedPruebasSeccionesList.add(pruebasSeccionesListPruebasSeccionesToAttach);
            }
            secciones.setPruebasSeccionesList(attachedPruebasSeccionesList);
            List<TareasSecciones> attachedTareasSeccionesList = new ArrayList<TareasSecciones>();
            for (TareasSecciones tareasSeccionesListTareasSeccionesToAttach : secciones.getTareasSeccionesList()) {
                tareasSeccionesListTareasSeccionesToAttach = em.getReference(tareasSeccionesListTareasSeccionesToAttach.getClass(), tareasSeccionesListTareasSeccionesToAttach.getId());
                attachedTareasSeccionesList.add(tareasSeccionesListTareasSeccionesToAttach);
            }
            secciones.setTareasSeccionesList(attachedTareasSeccionesList);
            List<ActividadesClases> attachedActividadesClasesList = new ArrayList<ActividadesClases>();
            for (ActividadesClases actividadesClasesListActividadesClasesToAttach : secciones.getActividadesClasesList()) {
                actividadesClasesListActividadesClasesToAttach = em.getReference(actividadesClasesListActividadesClasesToAttach.getClass(), actividadesClasesListActividadesClasesToAttach.getId());
                attachedActividadesClasesList.add(actividadesClasesListActividadesClasesToAttach);
            }
            secciones.setActividadesClasesList(attachedActividadesClasesList);
            List<AlumnosSecciones> attachedAlumnosSeccionesList = new ArrayList<AlumnosSecciones>();
            for (AlumnosSecciones alumnosSeccionesListAlumnosSeccionesToAttach : secciones.getAlumnosSeccionesList()) {
                alumnosSeccionesListAlumnosSeccionesToAttach = em.getReference(alumnosSeccionesListAlumnosSeccionesToAttach.getClass(), alumnosSeccionesListAlumnosSeccionesToAttach.getId());
                attachedAlumnosSeccionesList.add(alumnosSeccionesListAlumnosSeccionesToAttach);
            }
            secciones.setAlumnosSeccionesList(attachedAlumnosSeccionesList);
            em.persist(secciones);
            if (fkCodigoClase != null) {
                fkCodigoClase.getSeccionesList().add(secciones);
                fkCodigoClase = em.merge(fkCodigoClase);
            }
            if (fkDocente != null) {
                fkDocente.getSeccionesList().add(secciones);
                fkDocente = em.merge(fkDocente);
            }
            if (fkModalidad != null) {
                fkModalidad.getSeccionesList().add(secciones);
                fkModalidad = em.merge(fkModalidad);
            }
            if (fkPeriodoActual != null) {
                fkPeriodoActual.getSeccionesList().add(secciones);
                fkPeriodoActual = em.merge(fkPeriodoActual);
            }
            if (fkSede != null) {
                fkSede.getSeccionesList().add(secciones);
                fkSede = em.merge(fkSede);
            }
            for (ForoSecciones foroSeccionesListForoSecciones : secciones.getForoSeccionesList()) {
                Secciones oldFkSeccionOfForoSeccionesListForoSecciones = foroSeccionesListForoSecciones.getFkSeccion();
                foroSeccionesListForoSecciones.setFkSeccion(secciones);
                foroSeccionesListForoSecciones = em.merge(foroSeccionesListForoSecciones);
                if (oldFkSeccionOfForoSeccionesListForoSecciones != null) {
                    oldFkSeccionOfForoSeccionesListForoSecciones.getForoSeccionesList().remove(foroSeccionesListForoSecciones);
                    oldFkSeccionOfForoSeccionesListForoSecciones = em.merge(oldFkSeccionOfForoSeccionesListForoSecciones);
                }
            }
            for (PruebasSecciones pruebasSeccionesListPruebasSecciones : secciones.getPruebasSeccionesList()) {
                Secciones oldFkSeccionOfPruebasSeccionesListPruebasSecciones = pruebasSeccionesListPruebasSecciones.getFkSeccion();
                pruebasSeccionesListPruebasSecciones.setFkSeccion(secciones);
                pruebasSeccionesListPruebasSecciones = em.merge(pruebasSeccionesListPruebasSecciones);
                if (oldFkSeccionOfPruebasSeccionesListPruebasSecciones != null) {
                    oldFkSeccionOfPruebasSeccionesListPruebasSecciones.getPruebasSeccionesList().remove(pruebasSeccionesListPruebasSecciones);
                    oldFkSeccionOfPruebasSeccionesListPruebasSecciones = em.merge(oldFkSeccionOfPruebasSeccionesListPruebasSecciones);
                }
            }
            for (TareasSecciones tareasSeccionesListTareasSecciones : secciones.getTareasSeccionesList()) {
                Secciones oldFkSeccionOfTareasSeccionesListTareasSecciones = tareasSeccionesListTareasSecciones.getFkSeccion();
                tareasSeccionesListTareasSecciones.setFkSeccion(secciones);
                tareasSeccionesListTareasSecciones = em.merge(tareasSeccionesListTareasSecciones);
                if (oldFkSeccionOfTareasSeccionesListTareasSecciones != null) {
                    oldFkSeccionOfTareasSeccionesListTareasSecciones.getTareasSeccionesList().remove(tareasSeccionesListTareasSecciones);
                    oldFkSeccionOfTareasSeccionesListTareasSecciones = em.merge(oldFkSeccionOfTareasSeccionesListTareasSecciones);
                }
            }
            for (ActividadesClases actividadesClasesListActividadesClases : secciones.getActividadesClasesList()) {
                Secciones oldFkSeccionOfActividadesClasesListActividadesClases = actividadesClasesListActividadesClases.getFkSeccion();
                actividadesClasesListActividadesClases.setFkSeccion(secciones);
                actividadesClasesListActividadesClases = em.merge(actividadesClasesListActividadesClases);
                if (oldFkSeccionOfActividadesClasesListActividadesClases != null) {
                    oldFkSeccionOfActividadesClasesListActividadesClases.getActividadesClasesList().remove(actividadesClasesListActividadesClases);
                    oldFkSeccionOfActividadesClasesListActividadesClases = em.merge(oldFkSeccionOfActividadesClasesListActividadesClases);
                }
            }
            for (AlumnosSecciones alumnosSeccionesListAlumnosSecciones : secciones.getAlumnosSeccionesList()) {
                Secciones oldFkSeecionOfAlumnosSeccionesListAlumnosSecciones = alumnosSeccionesListAlumnosSecciones.getFkSeecion();
                alumnosSeccionesListAlumnosSecciones.setFkSeecion(secciones);
                alumnosSeccionesListAlumnosSecciones = em.merge(alumnosSeccionesListAlumnosSecciones);
                if (oldFkSeecionOfAlumnosSeccionesListAlumnosSecciones != null) {
                    oldFkSeecionOfAlumnosSeccionesListAlumnosSecciones.getAlumnosSeccionesList().remove(alumnosSeccionesListAlumnosSecciones);
                    oldFkSeecionOfAlumnosSeccionesListAlumnosSecciones = em.merge(oldFkSeecionOfAlumnosSeccionesListAlumnosSecciones);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSecciones(secciones.getId()) != null) {
                throw new PreexistingEntityException("Secciones " + secciones + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Secciones secciones) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Secciones persistentSecciones = em.find(Secciones.class, secciones.getId());
            Clases fkCodigoClaseOld = persistentSecciones.getFkCodigoClase();
            Clases fkCodigoClaseNew = secciones.getFkCodigoClase();
            Maestros fkDocenteOld = persistentSecciones.getFkDocente();
            Maestros fkDocenteNew = secciones.getFkDocente();
            Modalidades fkModalidadOld = persistentSecciones.getFkModalidad();
            Modalidades fkModalidadNew = secciones.getFkModalidad();
            PeriodoActual fkPeriodoActualOld = persistentSecciones.getFkPeriodoActual();
            PeriodoActual fkPeriodoActualNew = secciones.getFkPeriodoActual();
            Sedes fkSedeOld = persistentSecciones.getFkSede();
            Sedes fkSedeNew = secciones.getFkSede();
            List<ForoSecciones> foroSeccionesListOld = persistentSecciones.getForoSeccionesList();
            List<ForoSecciones> foroSeccionesListNew = secciones.getForoSeccionesList();
            List<PruebasSecciones> pruebasSeccionesListOld = persistentSecciones.getPruebasSeccionesList();
            List<PruebasSecciones> pruebasSeccionesListNew = secciones.getPruebasSeccionesList();
            List<TareasSecciones> tareasSeccionesListOld = persistentSecciones.getTareasSeccionesList();
            List<TareasSecciones> tareasSeccionesListNew = secciones.getTareasSeccionesList();
            List<ActividadesClases> actividadesClasesListOld = persistentSecciones.getActividadesClasesList();
            List<ActividadesClases> actividadesClasesListNew = secciones.getActividadesClasesList();
            List<AlumnosSecciones> alumnosSeccionesListOld = persistentSecciones.getAlumnosSeccionesList();
            List<AlumnosSecciones> alumnosSeccionesListNew = secciones.getAlumnosSeccionesList();
            if (fkCodigoClaseNew != null) {
                fkCodigoClaseNew = em.getReference(fkCodigoClaseNew.getClass(), fkCodigoClaseNew.getId());
                secciones.setFkCodigoClase(fkCodigoClaseNew);
            }
            if (fkDocenteNew != null) {
                fkDocenteNew = em.getReference(fkDocenteNew.getClass(), fkDocenteNew.getId());
                secciones.setFkDocente(fkDocenteNew);
            }
            if (fkModalidadNew != null) {
                fkModalidadNew = em.getReference(fkModalidadNew.getClass(), fkModalidadNew.getId());
                secciones.setFkModalidad(fkModalidadNew);
            }
            if (fkPeriodoActualNew != null) {
                fkPeriodoActualNew = em.getReference(fkPeriodoActualNew.getClass(), fkPeriodoActualNew.getId());
                secciones.setFkPeriodoActual(fkPeriodoActualNew);
            }
            if (fkSedeNew != null) {
                fkSedeNew = em.getReference(fkSedeNew.getClass(), fkSedeNew.getId());
                secciones.setFkSede(fkSedeNew);
            }
            List<ForoSecciones> attachedForoSeccionesListNew = new ArrayList<ForoSecciones>();
            for (ForoSecciones foroSeccionesListNewForoSeccionesToAttach : foroSeccionesListNew) {
                foroSeccionesListNewForoSeccionesToAttach = em.getReference(foroSeccionesListNewForoSeccionesToAttach.getClass(), foroSeccionesListNewForoSeccionesToAttach.getId());
                attachedForoSeccionesListNew.add(foroSeccionesListNewForoSeccionesToAttach);
            }
            foroSeccionesListNew = attachedForoSeccionesListNew;
            secciones.setForoSeccionesList(foroSeccionesListNew);
            List<PruebasSecciones> attachedPruebasSeccionesListNew = new ArrayList<PruebasSecciones>();
            for (PruebasSecciones pruebasSeccionesListNewPruebasSeccionesToAttach : pruebasSeccionesListNew) {
                pruebasSeccionesListNewPruebasSeccionesToAttach = em.getReference(pruebasSeccionesListNewPruebasSeccionesToAttach.getClass(), pruebasSeccionesListNewPruebasSeccionesToAttach.getId());
                attachedPruebasSeccionesListNew.add(pruebasSeccionesListNewPruebasSeccionesToAttach);
            }
            pruebasSeccionesListNew = attachedPruebasSeccionesListNew;
            secciones.setPruebasSeccionesList(pruebasSeccionesListNew);
            List<TareasSecciones> attachedTareasSeccionesListNew = new ArrayList<TareasSecciones>();
            for (TareasSecciones tareasSeccionesListNewTareasSeccionesToAttach : tareasSeccionesListNew) {
                tareasSeccionesListNewTareasSeccionesToAttach = em.getReference(tareasSeccionesListNewTareasSeccionesToAttach.getClass(), tareasSeccionesListNewTareasSeccionesToAttach.getId());
                attachedTareasSeccionesListNew.add(tareasSeccionesListNewTareasSeccionesToAttach);
            }
            tareasSeccionesListNew = attachedTareasSeccionesListNew;
            secciones.setTareasSeccionesList(tareasSeccionesListNew);
            List<ActividadesClases> attachedActividadesClasesListNew = new ArrayList<ActividadesClases>();
            for (ActividadesClases actividadesClasesListNewActividadesClasesToAttach : actividadesClasesListNew) {
                actividadesClasesListNewActividadesClasesToAttach = em.getReference(actividadesClasesListNewActividadesClasesToAttach.getClass(), actividadesClasesListNewActividadesClasesToAttach.getId());
                attachedActividadesClasesListNew.add(actividadesClasesListNewActividadesClasesToAttach);
            }
            actividadesClasesListNew = attachedActividadesClasesListNew;
            secciones.setActividadesClasesList(actividadesClasesListNew);
            List<AlumnosSecciones> attachedAlumnosSeccionesListNew = new ArrayList<AlumnosSecciones>();
            for (AlumnosSecciones alumnosSeccionesListNewAlumnosSeccionesToAttach : alumnosSeccionesListNew) {
                alumnosSeccionesListNewAlumnosSeccionesToAttach = em.getReference(alumnosSeccionesListNewAlumnosSeccionesToAttach.getClass(), alumnosSeccionesListNewAlumnosSeccionesToAttach.getId());
                attachedAlumnosSeccionesListNew.add(alumnosSeccionesListNewAlumnosSeccionesToAttach);
            }
            alumnosSeccionesListNew = attachedAlumnosSeccionesListNew;
            secciones.setAlumnosSeccionesList(alumnosSeccionesListNew);
            secciones = em.merge(secciones);
            if (fkCodigoClaseOld != null && !fkCodigoClaseOld.equals(fkCodigoClaseNew)) {
                fkCodigoClaseOld.getSeccionesList().remove(secciones);
                fkCodigoClaseOld = em.merge(fkCodigoClaseOld);
            }
            if (fkCodigoClaseNew != null && !fkCodigoClaseNew.equals(fkCodigoClaseOld)) {
                fkCodigoClaseNew.getSeccionesList().add(secciones);
                fkCodigoClaseNew = em.merge(fkCodigoClaseNew);
            }
            if (fkDocenteOld != null && !fkDocenteOld.equals(fkDocenteNew)) {
                fkDocenteOld.getSeccionesList().remove(secciones);
                fkDocenteOld = em.merge(fkDocenteOld);
            }
            if (fkDocenteNew != null && !fkDocenteNew.equals(fkDocenteOld)) {
                fkDocenteNew.getSeccionesList().add(secciones);
                fkDocenteNew = em.merge(fkDocenteNew);
            }
            if (fkModalidadOld != null && !fkModalidadOld.equals(fkModalidadNew)) {
                fkModalidadOld.getSeccionesList().remove(secciones);
                fkModalidadOld = em.merge(fkModalidadOld);
            }
            if (fkModalidadNew != null && !fkModalidadNew.equals(fkModalidadOld)) {
                fkModalidadNew.getSeccionesList().add(secciones);
                fkModalidadNew = em.merge(fkModalidadNew);
            }
            if (fkPeriodoActualOld != null && !fkPeriodoActualOld.equals(fkPeriodoActualNew)) {
                fkPeriodoActualOld.getSeccionesList().remove(secciones);
                fkPeriodoActualOld = em.merge(fkPeriodoActualOld);
            }
            if (fkPeriodoActualNew != null && !fkPeriodoActualNew.equals(fkPeriodoActualOld)) {
                fkPeriodoActualNew.getSeccionesList().add(secciones);
                fkPeriodoActualNew = em.merge(fkPeriodoActualNew);
            }
            if (fkSedeOld != null && !fkSedeOld.equals(fkSedeNew)) {
                fkSedeOld.getSeccionesList().remove(secciones);
                fkSedeOld = em.merge(fkSedeOld);
            }
            if (fkSedeNew != null && !fkSedeNew.equals(fkSedeOld)) {
                fkSedeNew.getSeccionesList().add(secciones);
                fkSedeNew = em.merge(fkSedeNew);
            }
            for (ForoSecciones foroSeccionesListOldForoSecciones : foroSeccionesListOld) {
                if (!foroSeccionesListNew.contains(foroSeccionesListOldForoSecciones)) {
                    foroSeccionesListOldForoSecciones.setFkSeccion(null);
                    foroSeccionesListOldForoSecciones = em.merge(foroSeccionesListOldForoSecciones);
                }
            }
            for (ForoSecciones foroSeccionesListNewForoSecciones : foroSeccionesListNew) {
                if (!foroSeccionesListOld.contains(foroSeccionesListNewForoSecciones)) {
                    Secciones oldFkSeccionOfForoSeccionesListNewForoSecciones = foroSeccionesListNewForoSecciones.getFkSeccion();
                    foroSeccionesListNewForoSecciones.setFkSeccion(secciones);
                    foroSeccionesListNewForoSecciones = em.merge(foroSeccionesListNewForoSecciones);
                    if (oldFkSeccionOfForoSeccionesListNewForoSecciones != null && !oldFkSeccionOfForoSeccionesListNewForoSecciones.equals(secciones)) {
                        oldFkSeccionOfForoSeccionesListNewForoSecciones.getForoSeccionesList().remove(foroSeccionesListNewForoSecciones);
                        oldFkSeccionOfForoSeccionesListNewForoSecciones = em.merge(oldFkSeccionOfForoSeccionesListNewForoSecciones);
                    }
                }
            }
            for (PruebasSecciones pruebasSeccionesListOldPruebasSecciones : pruebasSeccionesListOld) {
                if (!pruebasSeccionesListNew.contains(pruebasSeccionesListOldPruebasSecciones)) {
                    pruebasSeccionesListOldPruebasSecciones.setFkSeccion(null);
                    pruebasSeccionesListOldPruebasSecciones = em.merge(pruebasSeccionesListOldPruebasSecciones);
                }
            }
            for (PruebasSecciones pruebasSeccionesListNewPruebasSecciones : pruebasSeccionesListNew) {
                if (!pruebasSeccionesListOld.contains(pruebasSeccionesListNewPruebasSecciones)) {
                    Secciones oldFkSeccionOfPruebasSeccionesListNewPruebasSecciones = pruebasSeccionesListNewPruebasSecciones.getFkSeccion();
                    pruebasSeccionesListNewPruebasSecciones.setFkSeccion(secciones);
                    pruebasSeccionesListNewPruebasSecciones = em.merge(pruebasSeccionesListNewPruebasSecciones);
                    if (oldFkSeccionOfPruebasSeccionesListNewPruebasSecciones != null && !oldFkSeccionOfPruebasSeccionesListNewPruebasSecciones.equals(secciones)) {
                        oldFkSeccionOfPruebasSeccionesListNewPruebasSecciones.getPruebasSeccionesList().remove(pruebasSeccionesListNewPruebasSecciones);
                        oldFkSeccionOfPruebasSeccionesListNewPruebasSecciones = em.merge(oldFkSeccionOfPruebasSeccionesListNewPruebasSecciones);
                    }
                }
            }
            for (TareasSecciones tareasSeccionesListOldTareasSecciones : tareasSeccionesListOld) {
                if (!tareasSeccionesListNew.contains(tareasSeccionesListOldTareasSecciones)) {
                    tareasSeccionesListOldTareasSecciones.setFkSeccion(null);
                    tareasSeccionesListOldTareasSecciones = em.merge(tareasSeccionesListOldTareasSecciones);
                }
            }
            for (TareasSecciones tareasSeccionesListNewTareasSecciones : tareasSeccionesListNew) {
                if (!tareasSeccionesListOld.contains(tareasSeccionesListNewTareasSecciones)) {
                    Secciones oldFkSeccionOfTareasSeccionesListNewTareasSecciones = tareasSeccionesListNewTareasSecciones.getFkSeccion();
                    tareasSeccionesListNewTareasSecciones.setFkSeccion(secciones);
                    tareasSeccionesListNewTareasSecciones = em.merge(tareasSeccionesListNewTareasSecciones);
                    if (oldFkSeccionOfTareasSeccionesListNewTareasSecciones != null && !oldFkSeccionOfTareasSeccionesListNewTareasSecciones.equals(secciones)) {
                        oldFkSeccionOfTareasSeccionesListNewTareasSecciones.getTareasSeccionesList().remove(tareasSeccionesListNewTareasSecciones);
                        oldFkSeccionOfTareasSeccionesListNewTareasSecciones = em.merge(oldFkSeccionOfTareasSeccionesListNewTareasSecciones);
                    }
                }
            }
            for (ActividadesClases actividadesClasesListOldActividadesClases : actividadesClasesListOld) {
                if (!actividadesClasesListNew.contains(actividadesClasesListOldActividadesClases)) {
                    actividadesClasesListOldActividadesClases.setFkSeccion(null);
                    actividadesClasesListOldActividadesClases = em.merge(actividadesClasesListOldActividadesClases);
                }
            }
            for (ActividadesClases actividadesClasesListNewActividadesClases : actividadesClasesListNew) {
                if (!actividadesClasesListOld.contains(actividadesClasesListNewActividadesClases)) {
                    Secciones oldFkSeccionOfActividadesClasesListNewActividadesClases = actividadesClasesListNewActividadesClases.getFkSeccion();
                    actividadesClasesListNewActividadesClases.setFkSeccion(secciones);
                    actividadesClasesListNewActividadesClases = em.merge(actividadesClasesListNewActividadesClases);
                    if (oldFkSeccionOfActividadesClasesListNewActividadesClases != null && !oldFkSeccionOfActividadesClasesListNewActividadesClases.equals(secciones)) {
                        oldFkSeccionOfActividadesClasesListNewActividadesClases.getActividadesClasesList().remove(actividadesClasesListNewActividadesClases);
                        oldFkSeccionOfActividadesClasesListNewActividadesClases = em.merge(oldFkSeccionOfActividadesClasesListNewActividadesClases);
                    }
                }
            }
            for (AlumnosSecciones alumnosSeccionesListOldAlumnosSecciones : alumnosSeccionesListOld) {
                if (!alumnosSeccionesListNew.contains(alumnosSeccionesListOldAlumnosSecciones)) {
                    alumnosSeccionesListOldAlumnosSecciones.setFkSeecion(null);
                    alumnosSeccionesListOldAlumnosSecciones = em.merge(alumnosSeccionesListOldAlumnosSecciones);
                }
            }
            for (AlumnosSecciones alumnosSeccionesListNewAlumnosSecciones : alumnosSeccionesListNew) {
                if (!alumnosSeccionesListOld.contains(alumnosSeccionesListNewAlumnosSecciones)) {
                    Secciones oldFkSeecionOfAlumnosSeccionesListNewAlumnosSecciones = alumnosSeccionesListNewAlumnosSecciones.getFkSeecion();
                    alumnosSeccionesListNewAlumnosSecciones.setFkSeecion(secciones);
                    alumnosSeccionesListNewAlumnosSecciones = em.merge(alumnosSeccionesListNewAlumnosSecciones);
                    if (oldFkSeecionOfAlumnosSeccionesListNewAlumnosSecciones != null && !oldFkSeecionOfAlumnosSeccionesListNewAlumnosSecciones.equals(secciones)) {
                        oldFkSeecionOfAlumnosSeccionesListNewAlumnosSecciones.getAlumnosSeccionesList().remove(alumnosSeccionesListNewAlumnosSecciones);
                        oldFkSeecionOfAlumnosSeccionesListNewAlumnosSecciones = em.merge(oldFkSeecionOfAlumnosSeccionesListNewAlumnosSecciones);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = secciones.getId();
                if (findSecciones(id) == null) {
                    throw new NonexistentEntityException("The secciones with id " + id + " no longer exists.");
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
            Secciones secciones;
            try {
                secciones = em.getReference(Secciones.class, id);
                secciones.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The secciones with id " + id + " no longer exists.", enfe);
            }
            Clases fkCodigoClase = secciones.getFkCodigoClase();
            if (fkCodigoClase != null) {
                fkCodigoClase.getSeccionesList().remove(secciones);
                fkCodigoClase = em.merge(fkCodigoClase);
            }
            Maestros fkDocente = secciones.getFkDocente();
            if (fkDocente != null) {
                fkDocente.getSeccionesList().remove(secciones);
                fkDocente = em.merge(fkDocente);
            }
            Modalidades fkModalidad = secciones.getFkModalidad();
            if (fkModalidad != null) {
                fkModalidad.getSeccionesList().remove(secciones);
                fkModalidad = em.merge(fkModalidad);
            }
            PeriodoActual fkPeriodoActual = secciones.getFkPeriodoActual();
            if (fkPeriodoActual != null) {
                fkPeriodoActual.getSeccionesList().remove(secciones);
                fkPeriodoActual = em.merge(fkPeriodoActual);
            }
            Sedes fkSede = secciones.getFkSede();
            if (fkSede != null) {
                fkSede.getSeccionesList().remove(secciones);
                fkSede = em.merge(fkSede);
            }
            List<ForoSecciones> foroSeccionesList = secciones.getForoSeccionesList();
            for (ForoSecciones foroSeccionesListForoSecciones : foroSeccionesList) {
                foroSeccionesListForoSecciones.setFkSeccion(null);
                foroSeccionesListForoSecciones = em.merge(foroSeccionesListForoSecciones);
            }
            List<PruebasSecciones> pruebasSeccionesList = secciones.getPruebasSeccionesList();
            for (PruebasSecciones pruebasSeccionesListPruebasSecciones : pruebasSeccionesList) {
                pruebasSeccionesListPruebasSecciones.setFkSeccion(null);
                pruebasSeccionesListPruebasSecciones = em.merge(pruebasSeccionesListPruebasSecciones);
            }
            List<TareasSecciones> tareasSeccionesList = secciones.getTareasSeccionesList();
            for (TareasSecciones tareasSeccionesListTareasSecciones : tareasSeccionesList) {
                tareasSeccionesListTareasSecciones.setFkSeccion(null);
                tareasSeccionesListTareasSecciones = em.merge(tareasSeccionesListTareasSecciones);
            }
            List<ActividadesClases> actividadesClasesList = secciones.getActividadesClasesList();
            for (ActividadesClases actividadesClasesListActividadesClases : actividadesClasesList) {
                actividadesClasesListActividadesClases.setFkSeccion(null);
                actividadesClasesListActividadesClases = em.merge(actividadesClasesListActividadesClases);
            }
            List<AlumnosSecciones> alumnosSeccionesList = secciones.getAlumnosSeccionesList();
            for (AlumnosSecciones alumnosSeccionesListAlumnosSecciones : alumnosSeccionesList) {
                alumnosSeccionesListAlumnosSecciones.setFkSeecion(null);
                alumnosSeccionesListAlumnosSecciones = em.merge(alumnosSeccionesListAlumnosSecciones);
            }
            em.remove(secciones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Secciones> findSeccionesEntities() {
        return findSeccionesEntities(true, -1, -1);
    }

    public List<Secciones> findSeccionesEntities(int maxResults, int firstResult) {
        return findSeccionesEntities(false, maxResults, firstResult);
    }

    private List<Secciones> findSeccionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Secciones.class));
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

    public Secciones findSecciones(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Secciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getSeccionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Secciones> rt = cq.from(Secciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
