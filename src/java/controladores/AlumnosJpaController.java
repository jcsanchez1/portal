/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import controladores.exceptions.PreexistingEntityException;
import entidad.Alumnos;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidad.Usuarios;
import entidad.RespuestasForos;
import java.util.ArrayList;
import java.util.List;
import entidad.ArreglosPagos;
import entidad.TareasAlumnos;
import entidad.RespuestasPruebasAlumnos;
import entidad.Pagos;
import entidad.PruebasAlumnos;
import entidad.CarrerasAlumnos;
import entidad.AlumnosSecciones;
import entidad.Saldos;
import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author juanc
 */
public class AlumnosJpaController implements Serializable {

    public AlumnosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Alumnos alumnos) throws PreexistingEntityException, Exception {
        if (alumnos.getRespuestasForosList() == null) {
            alumnos.setRespuestasForosList(new ArrayList<RespuestasForos>());
        }
        if (alumnos.getArreglosPagosList() == null) {
            alumnos.setArreglosPagosList(new ArrayList<ArreglosPagos>());
        }
        if (alumnos.getTareasAlumnosList() == null) {
            alumnos.setTareasAlumnosList(new ArrayList<TareasAlumnos>());
        }
        if (alumnos.getRespuestasPruebasAlumnosList() == null) {
            alumnos.setRespuestasPruebasAlumnosList(new ArrayList<RespuestasPruebasAlumnos>());
        }
        if (alumnos.getPagosList() == null) {
            alumnos.setPagosList(new ArrayList<Pagos>());
        }
        if (alumnos.getPruebasAlumnosList() == null) {
            alumnos.setPruebasAlumnosList(new ArrayList<PruebasAlumnos>());
        }
        if (alumnos.getCarrerasAlumnosList() == null) {
            alumnos.setCarrerasAlumnosList(new ArrayList<CarrerasAlumnos>());
        }
        if (alumnos.getAlumnosSeccionesList() == null) {
            alumnos.setAlumnosSeccionesList(new ArrayList<AlumnosSecciones>());
        }
        if (alumnos.getSaldosList() == null) {
            alumnos.setSaldosList(new ArrayList<Saldos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios fkUsuario = alumnos.getFkUsuario();
            if (fkUsuario != null) {
                fkUsuario = em.getReference(fkUsuario.getClass(), fkUsuario.getId());
                alumnos.setFkUsuario(fkUsuario);
            }
            List<RespuestasForos> attachedRespuestasForosList = new ArrayList<RespuestasForos>();
            for (RespuestasForos respuestasForosListRespuestasForosToAttach : alumnos.getRespuestasForosList()) {
                respuestasForosListRespuestasForosToAttach = em.getReference(respuestasForosListRespuestasForosToAttach.getClass(), respuestasForosListRespuestasForosToAttach.getId());
                attachedRespuestasForosList.add(respuestasForosListRespuestasForosToAttach);
            }
            alumnos.setRespuestasForosList(attachedRespuestasForosList);
            List<ArreglosPagos> attachedArreglosPagosList = new ArrayList<ArreglosPagos>();
            for (ArreglosPagos arreglosPagosListArreglosPagosToAttach : alumnos.getArreglosPagosList()) {
                arreglosPagosListArreglosPagosToAttach = em.getReference(arreglosPagosListArreglosPagosToAttach.getClass(), arreglosPagosListArreglosPagosToAttach.getId());
                attachedArreglosPagosList.add(arreglosPagosListArreglosPagosToAttach);
            }
            alumnos.setArreglosPagosList(attachedArreglosPagosList);
            List<TareasAlumnos> attachedTareasAlumnosList = new ArrayList<TareasAlumnos>();
            for (TareasAlumnos tareasAlumnosListTareasAlumnosToAttach : alumnos.getTareasAlumnosList()) {
                tareasAlumnosListTareasAlumnosToAttach = em.getReference(tareasAlumnosListTareasAlumnosToAttach.getClass(), tareasAlumnosListTareasAlumnosToAttach.getId());
                attachedTareasAlumnosList.add(tareasAlumnosListTareasAlumnosToAttach);
            }
            alumnos.setTareasAlumnosList(attachedTareasAlumnosList);
            List<RespuestasPruebasAlumnos> attachedRespuestasPruebasAlumnosList = new ArrayList<RespuestasPruebasAlumnos>();
            for (RespuestasPruebasAlumnos respuestasPruebasAlumnosListRespuestasPruebasAlumnosToAttach : alumnos.getRespuestasPruebasAlumnosList()) {
                respuestasPruebasAlumnosListRespuestasPruebasAlumnosToAttach = em.getReference(respuestasPruebasAlumnosListRespuestasPruebasAlumnosToAttach.getClass(), respuestasPruebasAlumnosListRespuestasPruebasAlumnosToAttach.getId());
                attachedRespuestasPruebasAlumnosList.add(respuestasPruebasAlumnosListRespuestasPruebasAlumnosToAttach);
            }
            alumnos.setRespuestasPruebasAlumnosList(attachedRespuestasPruebasAlumnosList);
            List<Pagos> attachedPagosList = new ArrayList<Pagos>();
            for (Pagos pagosListPagosToAttach : alumnos.getPagosList()) {
                pagosListPagosToAttach = em.getReference(pagosListPagosToAttach.getClass(), pagosListPagosToAttach.getId());
                attachedPagosList.add(pagosListPagosToAttach);
            }
            alumnos.setPagosList(attachedPagosList);
            List<PruebasAlumnos> attachedPruebasAlumnosList = new ArrayList<PruebasAlumnos>();
            for (PruebasAlumnos pruebasAlumnosListPruebasAlumnosToAttach : alumnos.getPruebasAlumnosList()) {
                pruebasAlumnosListPruebasAlumnosToAttach = em.getReference(pruebasAlumnosListPruebasAlumnosToAttach.getClass(), pruebasAlumnosListPruebasAlumnosToAttach.getId());
                attachedPruebasAlumnosList.add(pruebasAlumnosListPruebasAlumnosToAttach);
            }
            alumnos.setPruebasAlumnosList(attachedPruebasAlumnosList);
            List<CarrerasAlumnos> attachedCarrerasAlumnosList = new ArrayList<CarrerasAlumnos>();
            for (CarrerasAlumnos carrerasAlumnosListCarrerasAlumnosToAttach : alumnos.getCarrerasAlumnosList()) {
                carrerasAlumnosListCarrerasAlumnosToAttach = em.getReference(carrerasAlumnosListCarrerasAlumnosToAttach.getClass(), carrerasAlumnosListCarrerasAlumnosToAttach.getId());
                attachedCarrerasAlumnosList.add(carrerasAlumnosListCarrerasAlumnosToAttach);
            }
            alumnos.setCarrerasAlumnosList(attachedCarrerasAlumnosList);
            List<AlumnosSecciones> attachedAlumnosSeccionesList = new ArrayList<AlumnosSecciones>();
            for (AlumnosSecciones alumnosSeccionesListAlumnosSeccionesToAttach : alumnos.getAlumnosSeccionesList()) {
                alumnosSeccionesListAlumnosSeccionesToAttach = em.getReference(alumnosSeccionesListAlumnosSeccionesToAttach.getClass(), alumnosSeccionesListAlumnosSeccionesToAttach.getId());
                attachedAlumnosSeccionesList.add(alumnosSeccionesListAlumnosSeccionesToAttach);
            }
            alumnos.setAlumnosSeccionesList(attachedAlumnosSeccionesList);
            List<Saldos> attachedSaldosList = new ArrayList<Saldos>();
            for (Saldos saldosListSaldosToAttach : alumnos.getSaldosList()) {
                saldosListSaldosToAttach = em.getReference(saldosListSaldosToAttach.getClass(), saldosListSaldosToAttach.getId());
                attachedSaldosList.add(saldosListSaldosToAttach);
            }
            alumnos.setSaldosList(attachedSaldosList);
            em.persist(alumnos);
            if (fkUsuario != null) {
                fkUsuario.getAlumnosList().add(alumnos);
                fkUsuario = em.merge(fkUsuario);
            }
            for (RespuestasForos respuestasForosListRespuestasForos : alumnos.getRespuestasForosList()) {
                Alumnos oldFkAlumnoOfRespuestasForosListRespuestasForos = respuestasForosListRespuestasForos.getFkAlumno();
                respuestasForosListRespuestasForos.setFkAlumno(alumnos);
                respuestasForosListRespuestasForos = em.merge(respuestasForosListRespuestasForos);
                if (oldFkAlumnoOfRespuestasForosListRespuestasForos != null) {
                    oldFkAlumnoOfRespuestasForosListRespuestasForos.getRespuestasForosList().remove(respuestasForosListRespuestasForos);
                    oldFkAlumnoOfRespuestasForosListRespuestasForos = em.merge(oldFkAlumnoOfRespuestasForosListRespuestasForos);
                }
            }
            for (ArreglosPagos arreglosPagosListArreglosPagos : alumnos.getArreglosPagosList()) {
                Alumnos oldFkEstudianteOfArreglosPagosListArreglosPagos = arreglosPagosListArreglosPagos.getFkEstudiante();
                arreglosPagosListArreglosPagos.setFkEstudiante(alumnos);
                arreglosPagosListArreglosPagos = em.merge(arreglosPagosListArreglosPagos);
                if (oldFkEstudianteOfArreglosPagosListArreglosPagos != null) {
                    oldFkEstudianteOfArreglosPagosListArreglosPagos.getArreglosPagosList().remove(arreglosPagosListArreglosPagos);
                    oldFkEstudianteOfArreglosPagosListArreglosPagos = em.merge(oldFkEstudianteOfArreglosPagosListArreglosPagos);
                }
            }
            for (TareasAlumnos tareasAlumnosListTareasAlumnos : alumnos.getTareasAlumnosList()) {
                Alumnos oldFkAlumnoOfTareasAlumnosListTareasAlumnos = tareasAlumnosListTareasAlumnos.getFkAlumno();
                tareasAlumnosListTareasAlumnos.setFkAlumno(alumnos);
                tareasAlumnosListTareasAlumnos = em.merge(tareasAlumnosListTareasAlumnos);
                if (oldFkAlumnoOfTareasAlumnosListTareasAlumnos != null) {
                    oldFkAlumnoOfTareasAlumnosListTareasAlumnos.getTareasAlumnosList().remove(tareasAlumnosListTareasAlumnos);
                    oldFkAlumnoOfTareasAlumnosListTareasAlumnos = em.merge(oldFkAlumnoOfTareasAlumnosListTareasAlumnos);
                }
            }
            for (RespuestasPruebasAlumnos respuestasPruebasAlumnosListRespuestasPruebasAlumnos : alumnos.getRespuestasPruebasAlumnosList()) {
                Alumnos oldFkRespuestaAlumnoOfRespuestasPruebasAlumnosListRespuestasPruebasAlumnos = respuestasPruebasAlumnosListRespuestasPruebasAlumnos.getFkRespuestaAlumno();
                respuestasPruebasAlumnosListRespuestasPruebasAlumnos.setFkRespuestaAlumno(alumnos);
                respuestasPruebasAlumnosListRespuestasPruebasAlumnos = em.merge(respuestasPruebasAlumnosListRespuestasPruebasAlumnos);
                if (oldFkRespuestaAlumnoOfRespuestasPruebasAlumnosListRespuestasPruebasAlumnos != null) {
                    oldFkRespuestaAlumnoOfRespuestasPruebasAlumnosListRespuestasPruebasAlumnos.getRespuestasPruebasAlumnosList().remove(respuestasPruebasAlumnosListRespuestasPruebasAlumnos);
                    oldFkRespuestaAlumnoOfRespuestasPruebasAlumnosListRespuestasPruebasAlumnos = em.merge(oldFkRespuestaAlumnoOfRespuestasPruebasAlumnosListRespuestasPruebasAlumnos);
                }
            }
            for (Pagos pagosListPagos : alumnos.getPagosList()) {
                Alumnos oldFkAlumnoOfPagosListPagos = pagosListPagos.getFkAlumno();
                pagosListPagos.setFkAlumno(alumnos);
                pagosListPagos = em.merge(pagosListPagos);
                if (oldFkAlumnoOfPagosListPagos != null) {
                    oldFkAlumnoOfPagosListPagos.getPagosList().remove(pagosListPagos);
                    oldFkAlumnoOfPagosListPagos = em.merge(oldFkAlumnoOfPagosListPagos);
                }
            }
            for (PruebasAlumnos pruebasAlumnosListPruebasAlumnos : alumnos.getPruebasAlumnosList()) {
                Alumnos oldFkAlumnoOfPruebasAlumnosListPruebasAlumnos = pruebasAlumnosListPruebasAlumnos.getFkAlumno();
                pruebasAlumnosListPruebasAlumnos.setFkAlumno(alumnos);
                pruebasAlumnosListPruebasAlumnos = em.merge(pruebasAlumnosListPruebasAlumnos);
                if (oldFkAlumnoOfPruebasAlumnosListPruebasAlumnos != null) {
                    oldFkAlumnoOfPruebasAlumnosListPruebasAlumnos.getPruebasAlumnosList().remove(pruebasAlumnosListPruebasAlumnos);
                    oldFkAlumnoOfPruebasAlumnosListPruebasAlumnos = em.merge(oldFkAlumnoOfPruebasAlumnosListPruebasAlumnos);
                }
            }
            for (CarrerasAlumnos carrerasAlumnosListCarrerasAlumnos : alumnos.getCarrerasAlumnosList()) {
                Alumnos oldFkIdentidadAlumnoOfCarrerasAlumnosListCarrerasAlumnos = carrerasAlumnosListCarrerasAlumnos.getFkIdentidadAlumno();
                carrerasAlumnosListCarrerasAlumnos.setFkIdentidadAlumno(alumnos);
                carrerasAlumnosListCarrerasAlumnos = em.merge(carrerasAlumnosListCarrerasAlumnos);
                if (oldFkIdentidadAlumnoOfCarrerasAlumnosListCarrerasAlumnos != null) {
                    oldFkIdentidadAlumnoOfCarrerasAlumnosListCarrerasAlumnos.getCarrerasAlumnosList().remove(carrerasAlumnosListCarrerasAlumnos);
                    oldFkIdentidadAlumnoOfCarrerasAlumnosListCarrerasAlumnos = em.merge(oldFkIdentidadAlumnoOfCarrerasAlumnosListCarrerasAlumnos);
                }
            }
            for (AlumnosSecciones alumnosSeccionesListAlumnosSecciones : alumnos.getAlumnosSeccionesList()) {
                Alumnos oldFkAlumnoOfAlumnosSeccionesListAlumnosSecciones = alumnosSeccionesListAlumnosSecciones.getFkAlumno();
                alumnosSeccionesListAlumnosSecciones.setFkAlumno(alumnos);
                alumnosSeccionesListAlumnosSecciones = em.merge(alumnosSeccionesListAlumnosSecciones);
                if (oldFkAlumnoOfAlumnosSeccionesListAlumnosSecciones != null) {
                    oldFkAlumnoOfAlumnosSeccionesListAlumnosSecciones.getAlumnosSeccionesList().remove(alumnosSeccionesListAlumnosSecciones);
                    oldFkAlumnoOfAlumnosSeccionesListAlumnosSecciones = em.merge(oldFkAlumnoOfAlumnosSeccionesListAlumnosSecciones);
                }
            }
            for (Saldos saldosListSaldos : alumnos.getSaldosList()) {
                Alumnos oldFkAlumnoOfSaldosListSaldos = saldosListSaldos.getFkAlumno();
                saldosListSaldos.setFkAlumno(alumnos);
                saldosListSaldos = em.merge(saldosListSaldos);
                if (oldFkAlumnoOfSaldosListSaldos != null) {
                    oldFkAlumnoOfSaldosListSaldos.getSaldosList().remove(saldosListSaldos);
                    oldFkAlumnoOfSaldosListSaldos = em.merge(oldFkAlumnoOfSaldosListSaldos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAlumnos(alumnos.getId()) != null) {
                throw new PreexistingEntityException("Alumnos " + alumnos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Alumnos alumnos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumnos persistentAlumnos = em.find(Alumnos.class, alumnos.getId());
            Usuarios fkUsuarioOld = persistentAlumnos.getFkUsuario();
            Usuarios fkUsuarioNew = alumnos.getFkUsuario();
            List<RespuestasForos> respuestasForosListOld = persistentAlumnos.getRespuestasForosList();
            List<RespuestasForos> respuestasForosListNew = alumnos.getRespuestasForosList();
            List<ArreglosPagos> arreglosPagosListOld = persistentAlumnos.getArreglosPagosList();
            List<ArreglosPagos> arreglosPagosListNew = alumnos.getArreglosPagosList();
            List<TareasAlumnos> tareasAlumnosListOld = persistentAlumnos.getTareasAlumnosList();
            List<TareasAlumnos> tareasAlumnosListNew = alumnos.getTareasAlumnosList();
            List<RespuestasPruebasAlumnos> respuestasPruebasAlumnosListOld = persistentAlumnos.getRespuestasPruebasAlumnosList();
            List<RespuestasPruebasAlumnos> respuestasPruebasAlumnosListNew = alumnos.getRespuestasPruebasAlumnosList();
            List<Pagos> pagosListOld = persistentAlumnos.getPagosList();
            List<Pagos> pagosListNew = alumnos.getPagosList();
            List<PruebasAlumnos> pruebasAlumnosListOld = persistentAlumnos.getPruebasAlumnosList();
            List<PruebasAlumnos> pruebasAlumnosListNew = alumnos.getPruebasAlumnosList();
            List<CarrerasAlumnos> carrerasAlumnosListOld = persistentAlumnos.getCarrerasAlumnosList();
            List<CarrerasAlumnos> carrerasAlumnosListNew = alumnos.getCarrerasAlumnosList();
            List<AlumnosSecciones> alumnosSeccionesListOld = persistentAlumnos.getAlumnosSeccionesList();
            List<AlumnosSecciones> alumnosSeccionesListNew = alumnos.getAlumnosSeccionesList();
            List<Saldos> saldosListOld = persistentAlumnos.getSaldosList();
            List<Saldos> saldosListNew = alumnos.getSaldosList();
            if (fkUsuarioNew != null) {
                fkUsuarioNew = em.getReference(fkUsuarioNew.getClass(), fkUsuarioNew.getId());
                alumnos.setFkUsuario(fkUsuarioNew);
            }
            List<RespuestasForos> attachedRespuestasForosListNew = new ArrayList<RespuestasForos>();
            for (RespuestasForos respuestasForosListNewRespuestasForosToAttach : respuestasForosListNew) {
                respuestasForosListNewRespuestasForosToAttach = em.getReference(respuestasForosListNewRespuestasForosToAttach.getClass(), respuestasForosListNewRespuestasForosToAttach.getId());
                attachedRespuestasForosListNew.add(respuestasForosListNewRespuestasForosToAttach);
            }
            respuestasForosListNew = attachedRespuestasForosListNew;
            alumnos.setRespuestasForosList(respuestasForosListNew);
            List<ArreglosPagos> attachedArreglosPagosListNew = new ArrayList<ArreglosPagos>();
            for (ArreglosPagos arreglosPagosListNewArreglosPagosToAttach : arreglosPagosListNew) {
                arreglosPagosListNewArreglosPagosToAttach = em.getReference(arreglosPagosListNewArreglosPagosToAttach.getClass(), arreglosPagosListNewArreglosPagosToAttach.getId());
                attachedArreglosPagosListNew.add(arreglosPagosListNewArreglosPagosToAttach);
            }
            arreglosPagosListNew = attachedArreglosPagosListNew;
            alumnos.setArreglosPagosList(arreglosPagosListNew);
            List<TareasAlumnos> attachedTareasAlumnosListNew = new ArrayList<TareasAlumnos>();
            for (TareasAlumnos tareasAlumnosListNewTareasAlumnosToAttach : tareasAlumnosListNew) {
                tareasAlumnosListNewTareasAlumnosToAttach = em.getReference(tareasAlumnosListNewTareasAlumnosToAttach.getClass(), tareasAlumnosListNewTareasAlumnosToAttach.getId());
                attachedTareasAlumnosListNew.add(tareasAlumnosListNewTareasAlumnosToAttach);
            }
            tareasAlumnosListNew = attachedTareasAlumnosListNew;
            alumnos.setTareasAlumnosList(tareasAlumnosListNew);
            List<RespuestasPruebasAlumnos> attachedRespuestasPruebasAlumnosListNew = new ArrayList<RespuestasPruebasAlumnos>();
            for (RespuestasPruebasAlumnos respuestasPruebasAlumnosListNewRespuestasPruebasAlumnosToAttach : respuestasPruebasAlumnosListNew) {
                respuestasPruebasAlumnosListNewRespuestasPruebasAlumnosToAttach = em.getReference(respuestasPruebasAlumnosListNewRespuestasPruebasAlumnosToAttach.getClass(), respuestasPruebasAlumnosListNewRespuestasPruebasAlumnosToAttach.getId());
                attachedRespuestasPruebasAlumnosListNew.add(respuestasPruebasAlumnosListNewRespuestasPruebasAlumnosToAttach);
            }
            respuestasPruebasAlumnosListNew = attachedRespuestasPruebasAlumnosListNew;
            alumnos.setRespuestasPruebasAlumnosList(respuestasPruebasAlumnosListNew);
            List<Pagos> attachedPagosListNew = new ArrayList<Pagos>();
            for (Pagos pagosListNewPagosToAttach : pagosListNew) {
                pagosListNewPagosToAttach = em.getReference(pagosListNewPagosToAttach.getClass(), pagosListNewPagosToAttach.getId());
                attachedPagosListNew.add(pagosListNewPagosToAttach);
            }
            pagosListNew = attachedPagosListNew;
            alumnos.setPagosList(pagosListNew);
            List<PruebasAlumnos> attachedPruebasAlumnosListNew = new ArrayList<PruebasAlumnos>();
            for (PruebasAlumnos pruebasAlumnosListNewPruebasAlumnosToAttach : pruebasAlumnosListNew) {
                pruebasAlumnosListNewPruebasAlumnosToAttach = em.getReference(pruebasAlumnosListNewPruebasAlumnosToAttach.getClass(), pruebasAlumnosListNewPruebasAlumnosToAttach.getId());
                attachedPruebasAlumnosListNew.add(pruebasAlumnosListNewPruebasAlumnosToAttach);
            }
            pruebasAlumnosListNew = attachedPruebasAlumnosListNew;
            alumnos.setPruebasAlumnosList(pruebasAlumnosListNew);
            List<CarrerasAlumnos> attachedCarrerasAlumnosListNew = new ArrayList<CarrerasAlumnos>();
            for (CarrerasAlumnos carrerasAlumnosListNewCarrerasAlumnosToAttach : carrerasAlumnosListNew) {
                carrerasAlumnosListNewCarrerasAlumnosToAttach = em.getReference(carrerasAlumnosListNewCarrerasAlumnosToAttach.getClass(), carrerasAlumnosListNewCarrerasAlumnosToAttach.getId());
                attachedCarrerasAlumnosListNew.add(carrerasAlumnosListNewCarrerasAlumnosToAttach);
            }
            carrerasAlumnosListNew = attachedCarrerasAlumnosListNew;
            alumnos.setCarrerasAlumnosList(carrerasAlumnosListNew);
            List<AlumnosSecciones> attachedAlumnosSeccionesListNew = new ArrayList<AlumnosSecciones>();
            for (AlumnosSecciones alumnosSeccionesListNewAlumnosSeccionesToAttach : alumnosSeccionesListNew) {
                alumnosSeccionesListNewAlumnosSeccionesToAttach = em.getReference(alumnosSeccionesListNewAlumnosSeccionesToAttach.getClass(), alumnosSeccionesListNewAlumnosSeccionesToAttach.getId());
                attachedAlumnosSeccionesListNew.add(alumnosSeccionesListNewAlumnosSeccionesToAttach);
            }
            alumnosSeccionesListNew = attachedAlumnosSeccionesListNew;
            alumnos.setAlumnosSeccionesList(alumnosSeccionesListNew);
            List<Saldos> attachedSaldosListNew = new ArrayList<Saldos>();
            for (Saldos saldosListNewSaldosToAttach : saldosListNew) {
                saldosListNewSaldosToAttach = em.getReference(saldosListNewSaldosToAttach.getClass(), saldosListNewSaldosToAttach.getId());
                attachedSaldosListNew.add(saldosListNewSaldosToAttach);
            }
            saldosListNew = attachedSaldosListNew;
            alumnos.setSaldosList(saldosListNew);
            alumnos = em.merge(alumnos);
            if (fkUsuarioOld != null && !fkUsuarioOld.equals(fkUsuarioNew)) {
                fkUsuarioOld.getAlumnosList().remove(alumnos);
                fkUsuarioOld = em.merge(fkUsuarioOld);
            }
            if (fkUsuarioNew != null && !fkUsuarioNew.equals(fkUsuarioOld)) {
                fkUsuarioNew.getAlumnosList().add(alumnos);
                fkUsuarioNew = em.merge(fkUsuarioNew);
            }
            for (RespuestasForos respuestasForosListOldRespuestasForos : respuestasForosListOld) {
                if (!respuestasForosListNew.contains(respuestasForosListOldRespuestasForos)) {
                    respuestasForosListOldRespuestasForos.setFkAlumno(null);
                    respuestasForosListOldRespuestasForos = em.merge(respuestasForosListOldRespuestasForos);
                }
            }
            for (RespuestasForos respuestasForosListNewRespuestasForos : respuestasForosListNew) {
                if (!respuestasForosListOld.contains(respuestasForosListNewRespuestasForos)) {
                    Alumnos oldFkAlumnoOfRespuestasForosListNewRespuestasForos = respuestasForosListNewRespuestasForos.getFkAlumno();
                    respuestasForosListNewRespuestasForos.setFkAlumno(alumnos);
                    respuestasForosListNewRespuestasForos = em.merge(respuestasForosListNewRespuestasForos);
                    if (oldFkAlumnoOfRespuestasForosListNewRespuestasForos != null && !oldFkAlumnoOfRespuestasForosListNewRespuestasForos.equals(alumnos)) {
                        oldFkAlumnoOfRespuestasForosListNewRespuestasForos.getRespuestasForosList().remove(respuestasForosListNewRespuestasForos);
                        oldFkAlumnoOfRespuestasForosListNewRespuestasForos = em.merge(oldFkAlumnoOfRespuestasForosListNewRespuestasForos);
                    }
                }
            }
            for (ArreglosPagos arreglosPagosListOldArreglosPagos : arreglosPagosListOld) {
                if (!arreglosPagosListNew.contains(arreglosPagosListOldArreglosPagos)) {
                    arreglosPagosListOldArreglosPagos.setFkEstudiante(null);
                    arreglosPagosListOldArreglosPagos = em.merge(arreglosPagosListOldArreglosPagos);
                }
            }
            for (ArreglosPagos arreglosPagosListNewArreglosPagos : arreglosPagosListNew) {
                if (!arreglosPagosListOld.contains(arreglosPagosListNewArreglosPagos)) {
                    Alumnos oldFkEstudianteOfArreglosPagosListNewArreglosPagos = arreglosPagosListNewArreglosPagos.getFkEstudiante();
                    arreglosPagosListNewArreglosPagos.setFkEstudiante(alumnos);
                    arreglosPagosListNewArreglosPagos = em.merge(arreglosPagosListNewArreglosPagos);
                    if (oldFkEstudianteOfArreglosPagosListNewArreglosPagos != null && !oldFkEstudianteOfArreglosPagosListNewArreglosPagos.equals(alumnos)) {
                        oldFkEstudianteOfArreglosPagosListNewArreglosPagos.getArreglosPagosList().remove(arreglosPagosListNewArreglosPagos);
                        oldFkEstudianteOfArreglosPagosListNewArreglosPagos = em.merge(oldFkEstudianteOfArreglosPagosListNewArreglosPagos);
                    }
                }
            }
            for (TareasAlumnos tareasAlumnosListOldTareasAlumnos : tareasAlumnosListOld) {
                if (!tareasAlumnosListNew.contains(tareasAlumnosListOldTareasAlumnos)) {
                    tareasAlumnosListOldTareasAlumnos.setFkAlumno(null);
                    tareasAlumnosListOldTareasAlumnos = em.merge(tareasAlumnosListOldTareasAlumnos);
                }
            }
            for (TareasAlumnos tareasAlumnosListNewTareasAlumnos : tareasAlumnosListNew) {
                if (!tareasAlumnosListOld.contains(tareasAlumnosListNewTareasAlumnos)) {
                    Alumnos oldFkAlumnoOfTareasAlumnosListNewTareasAlumnos = tareasAlumnosListNewTareasAlumnos.getFkAlumno();
                    tareasAlumnosListNewTareasAlumnos.setFkAlumno(alumnos);
                    tareasAlumnosListNewTareasAlumnos = em.merge(tareasAlumnosListNewTareasAlumnos);
                    if (oldFkAlumnoOfTareasAlumnosListNewTareasAlumnos != null && !oldFkAlumnoOfTareasAlumnosListNewTareasAlumnos.equals(alumnos)) {
                        oldFkAlumnoOfTareasAlumnosListNewTareasAlumnos.getTareasAlumnosList().remove(tareasAlumnosListNewTareasAlumnos);
                        oldFkAlumnoOfTareasAlumnosListNewTareasAlumnos = em.merge(oldFkAlumnoOfTareasAlumnosListNewTareasAlumnos);
                    }
                }
            }
            for (RespuestasPruebasAlumnos respuestasPruebasAlumnosListOldRespuestasPruebasAlumnos : respuestasPruebasAlumnosListOld) {
                if (!respuestasPruebasAlumnosListNew.contains(respuestasPruebasAlumnosListOldRespuestasPruebasAlumnos)) {
                    respuestasPruebasAlumnosListOldRespuestasPruebasAlumnos.setFkRespuestaAlumno(null);
                    respuestasPruebasAlumnosListOldRespuestasPruebasAlumnos = em.merge(respuestasPruebasAlumnosListOldRespuestasPruebasAlumnos);
                }
            }
            for (RespuestasPruebasAlumnos respuestasPruebasAlumnosListNewRespuestasPruebasAlumnos : respuestasPruebasAlumnosListNew) {
                if (!respuestasPruebasAlumnosListOld.contains(respuestasPruebasAlumnosListNewRespuestasPruebasAlumnos)) {
                    Alumnos oldFkRespuestaAlumnoOfRespuestasPruebasAlumnosListNewRespuestasPruebasAlumnos = respuestasPruebasAlumnosListNewRespuestasPruebasAlumnos.getFkRespuestaAlumno();
                    respuestasPruebasAlumnosListNewRespuestasPruebasAlumnos.setFkRespuestaAlumno(alumnos);
                    respuestasPruebasAlumnosListNewRespuestasPruebasAlumnos = em.merge(respuestasPruebasAlumnosListNewRespuestasPruebasAlumnos);
                    if (oldFkRespuestaAlumnoOfRespuestasPruebasAlumnosListNewRespuestasPruebasAlumnos != null && !oldFkRespuestaAlumnoOfRespuestasPruebasAlumnosListNewRespuestasPruebasAlumnos.equals(alumnos)) {
                        oldFkRespuestaAlumnoOfRespuestasPruebasAlumnosListNewRespuestasPruebasAlumnos.getRespuestasPruebasAlumnosList().remove(respuestasPruebasAlumnosListNewRespuestasPruebasAlumnos);
                        oldFkRespuestaAlumnoOfRespuestasPruebasAlumnosListNewRespuestasPruebasAlumnos = em.merge(oldFkRespuestaAlumnoOfRespuestasPruebasAlumnosListNewRespuestasPruebasAlumnos);
                    }
                }
            }
            for (Pagos pagosListOldPagos : pagosListOld) {
                if (!pagosListNew.contains(pagosListOldPagos)) {
                    pagosListOldPagos.setFkAlumno(null);
                    pagosListOldPagos = em.merge(pagosListOldPagos);
                }
            }
            for (Pagos pagosListNewPagos : pagosListNew) {
                if (!pagosListOld.contains(pagosListNewPagos)) {
                    Alumnos oldFkAlumnoOfPagosListNewPagos = pagosListNewPagos.getFkAlumno();
                    pagosListNewPagos.setFkAlumno(alumnos);
                    pagosListNewPagos = em.merge(pagosListNewPagos);
                    if (oldFkAlumnoOfPagosListNewPagos != null && !oldFkAlumnoOfPagosListNewPagos.equals(alumnos)) {
                        oldFkAlumnoOfPagosListNewPagos.getPagosList().remove(pagosListNewPagos);
                        oldFkAlumnoOfPagosListNewPagos = em.merge(oldFkAlumnoOfPagosListNewPagos);
                    }
                }
            }
            for (PruebasAlumnos pruebasAlumnosListOldPruebasAlumnos : pruebasAlumnosListOld) {
                if (!pruebasAlumnosListNew.contains(pruebasAlumnosListOldPruebasAlumnos)) {
                    pruebasAlumnosListOldPruebasAlumnos.setFkAlumno(null);
                    pruebasAlumnosListOldPruebasAlumnos = em.merge(pruebasAlumnosListOldPruebasAlumnos);
                }
            }
            for (PruebasAlumnos pruebasAlumnosListNewPruebasAlumnos : pruebasAlumnosListNew) {
                if (!pruebasAlumnosListOld.contains(pruebasAlumnosListNewPruebasAlumnos)) {
                    Alumnos oldFkAlumnoOfPruebasAlumnosListNewPruebasAlumnos = pruebasAlumnosListNewPruebasAlumnos.getFkAlumno();
                    pruebasAlumnosListNewPruebasAlumnos.setFkAlumno(alumnos);
                    pruebasAlumnosListNewPruebasAlumnos = em.merge(pruebasAlumnosListNewPruebasAlumnos);
                    if (oldFkAlumnoOfPruebasAlumnosListNewPruebasAlumnos != null && !oldFkAlumnoOfPruebasAlumnosListNewPruebasAlumnos.equals(alumnos)) {
                        oldFkAlumnoOfPruebasAlumnosListNewPruebasAlumnos.getPruebasAlumnosList().remove(pruebasAlumnosListNewPruebasAlumnos);
                        oldFkAlumnoOfPruebasAlumnosListNewPruebasAlumnos = em.merge(oldFkAlumnoOfPruebasAlumnosListNewPruebasAlumnos);
                    }
                }
            }
            for (CarrerasAlumnos carrerasAlumnosListOldCarrerasAlumnos : carrerasAlumnosListOld) {
                if (!carrerasAlumnosListNew.contains(carrerasAlumnosListOldCarrerasAlumnos)) {
                    carrerasAlumnosListOldCarrerasAlumnos.setFkIdentidadAlumno(null);
                    carrerasAlumnosListOldCarrerasAlumnos = em.merge(carrerasAlumnosListOldCarrerasAlumnos);
                }
            }
            for (CarrerasAlumnos carrerasAlumnosListNewCarrerasAlumnos : carrerasAlumnosListNew) {
                if (!carrerasAlumnosListOld.contains(carrerasAlumnosListNewCarrerasAlumnos)) {
                    Alumnos oldFkIdentidadAlumnoOfCarrerasAlumnosListNewCarrerasAlumnos = carrerasAlumnosListNewCarrerasAlumnos.getFkIdentidadAlumno();
                    carrerasAlumnosListNewCarrerasAlumnos.setFkIdentidadAlumno(alumnos);
                    carrerasAlumnosListNewCarrerasAlumnos = em.merge(carrerasAlumnosListNewCarrerasAlumnos);
                    if (oldFkIdentidadAlumnoOfCarrerasAlumnosListNewCarrerasAlumnos != null && !oldFkIdentidadAlumnoOfCarrerasAlumnosListNewCarrerasAlumnos.equals(alumnos)) {
                        oldFkIdentidadAlumnoOfCarrerasAlumnosListNewCarrerasAlumnos.getCarrerasAlumnosList().remove(carrerasAlumnosListNewCarrerasAlumnos);
                        oldFkIdentidadAlumnoOfCarrerasAlumnosListNewCarrerasAlumnos = em.merge(oldFkIdentidadAlumnoOfCarrerasAlumnosListNewCarrerasAlumnos);
                    }
                }
            }
            for (AlumnosSecciones alumnosSeccionesListOldAlumnosSecciones : alumnosSeccionesListOld) {
                if (!alumnosSeccionesListNew.contains(alumnosSeccionesListOldAlumnosSecciones)) {
                    alumnosSeccionesListOldAlumnosSecciones.setFkAlumno(null);
                    alumnosSeccionesListOldAlumnosSecciones = em.merge(alumnosSeccionesListOldAlumnosSecciones);
                }
            }
            for (AlumnosSecciones alumnosSeccionesListNewAlumnosSecciones : alumnosSeccionesListNew) {
                if (!alumnosSeccionesListOld.contains(alumnosSeccionesListNewAlumnosSecciones)) {
                    Alumnos oldFkAlumnoOfAlumnosSeccionesListNewAlumnosSecciones = alumnosSeccionesListNewAlumnosSecciones.getFkAlumno();
                    alumnosSeccionesListNewAlumnosSecciones.setFkAlumno(alumnos);
                    alumnosSeccionesListNewAlumnosSecciones = em.merge(alumnosSeccionesListNewAlumnosSecciones);
                    if (oldFkAlumnoOfAlumnosSeccionesListNewAlumnosSecciones != null && !oldFkAlumnoOfAlumnosSeccionesListNewAlumnosSecciones.equals(alumnos)) {
                        oldFkAlumnoOfAlumnosSeccionesListNewAlumnosSecciones.getAlumnosSeccionesList().remove(alumnosSeccionesListNewAlumnosSecciones);
                        oldFkAlumnoOfAlumnosSeccionesListNewAlumnosSecciones = em.merge(oldFkAlumnoOfAlumnosSeccionesListNewAlumnosSecciones);
                    }
                }
            }
            for (Saldos saldosListOldSaldos : saldosListOld) {
                if (!saldosListNew.contains(saldosListOldSaldos)) {
                    saldosListOldSaldos.setFkAlumno(null);
                    saldosListOldSaldos = em.merge(saldosListOldSaldos);
                }
            }
            for (Saldos saldosListNewSaldos : saldosListNew) {
                if (!saldosListOld.contains(saldosListNewSaldos)) {
                    Alumnos oldFkAlumnoOfSaldosListNewSaldos = saldosListNewSaldos.getFkAlumno();
                    saldosListNewSaldos.setFkAlumno(alumnos);
                    saldosListNewSaldos = em.merge(saldosListNewSaldos);
                    if (oldFkAlumnoOfSaldosListNewSaldos != null && !oldFkAlumnoOfSaldosListNewSaldos.equals(alumnos)) {
                        oldFkAlumnoOfSaldosListNewSaldos.getSaldosList().remove(saldosListNewSaldos);
                        oldFkAlumnoOfSaldosListNewSaldos = em.merge(oldFkAlumnoOfSaldosListNewSaldos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = alumnos.getId();
                if (findAlumnos(id) == null) {
                    throw new NonexistentEntityException("The alumnos with id " + id + " no longer exists.");
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
            Alumnos alumnos;
            try {
                alumnos = em.getReference(Alumnos.class, id);
                alumnos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The alumnos with id " + id + " no longer exists.", enfe);
            }
            Usuarios fkUsuario = alumnos.getFkUsuario();
            if (fkUsuario != null) {
                fkUsuario.getAlumnosList().remove(alumnos);
                fkUsuario = em.merge(fkUsuario);
            }
            List<RespuestasForos> respuestasForosList = alumnos.getRespuestasForosList();
            for (RespuestasForos respuestasForosListRespuestasForos : respuestasForosList) {
                respuestasForosListRespuestasForos.setFkAlumno(null);
                respuestasForosListRespuestasForos = em.merge(respuestasForosListRespuestasForos);
            }
            List<ArreglosPagos> arreglosPagosList = alumnos.getArreglosPagosList();
            for (ArreglosPagos arreglosPagosListArreglosPagos : arreglosPagosList) {
                arreglosPagosListArreglosPagos.setFkEstudiante(null);
                arreglosPagosListArreglosPagos = em.merge(arreglosPagosListArreglosPagos);
            }
            List<TareasAlumnos> tareasAlumnosList = alumnos.getTareasAlumnosList();
            for (TareasAlumnos tareasAlumnosListTareasAlumnos : tareasAlumnosList) {
                tareasAlumnosListTareasAlumnos.setFkAlumno(null);
                tareasAlumnosListTareasAlumnos = em.merge(tareasAlumnosListTareasAlumnos);
            }
            List<RespuestasPruebasAlumnos> respuestasPruebasAlumnosList = alumnos.getRespuestasPruebasAlumnosList();
            for (RespuestasPruebasAlumnos respuestasPruebasAlumnosListRespuestasPruebasAlumnos : respuestasPruebasAlumnosList) {
                respuestasPruebasAlumnosListRespuestasPruebasAlumnos.setFkRespuestaAlumno(null);
                respuestasPruebasAlumnosListRespuestasPruebasAlumnos = em.merge(respuestasPruebasAlumnosListRespuestasPruebasAlumnos);
            }
            List<Pagos> pagosList = alumnos.getPagosList();
            for (Pagos pagosListPagos : pagosList) {
                pagosListPagos.setFkAlumno(null);
                pagosListPagos = em.merge(pagosListPagos);
            }
            List<PruebasAlumnos> pruebasAlumnosList = alumnos.getPruebasAlumnosList();
            for (PruebasAlumnos pruebasAlumnosListPruebasAlumnos : pruebasAlumnosList) {
                pruebasAlumnosListPruebasAlumnos.setFkAlumno(null);
                pruebasAlumnosListPruebasAlumnos = em.merge(pruebasAlumnosListPruebasAlumnos);
            }
            List<CarrerasAlumnos> carrerasAlumnosList = alumnos.getCarrerasAlumnosList();
            for (CarrerasAlumnos carrerasAlumnosListCarrerasAlumnos : carrerasAlumnosList) {
                carrerasAlumnosListCarrerasAlumnos.setFkIdentidadAlumno(null);
                carrerasAlumnosListCarrerasAlumnos = em.merge(carrerasAlumnosListCarrerasAlumnos);
            }
            List<AlumnosSecciones> alumnosSeccionesList = alumnos.getAlumnosSeccionesList();
            for (AlumnosSecciones alumnosSeccionesListAlumnosSecciones : alumnosSeccionesList) {
                alumnosSeccionesListAlumnosSecciones.setFkAlumno(null);
                alumnosSeccionesListAlumnosSecciones = em.merge(alumnosSeccionesListAlumnosSecciones);
            }
            List<Saldos> saldosList = alumnos.getSaldosList();
            for (Saldos saldosListSaldos : saldosList) {
                saldosListSaldos.setFkAlumno(null);
                saldosListSaldos = em.merge(saldosListSaldos);
            }
            em.remove(alumnos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Alumnos> findAlumnosEntities() {
        return findAlumnosEntities(true, -1, -1);
    }

    public List<Alumnos> findAlumnosEntities(int maxResults, int firstResult) {
        return findAlumnosEntities(false, maxResults, firstResult);
    }

    private List<Alumnos> findAlumnosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Alumnos.class));
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

    public Alumnos findAlumnos(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Alumnos.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlumnosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Alumnos> rt = cq.from(Alumnos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
