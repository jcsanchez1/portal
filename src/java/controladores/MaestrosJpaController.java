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
import entidad.Usuarios;
import entidad.Secciones;
import java.util.ArrayList;
import java.util.List;
import entidad.ForoSecciones;
import entidad.Maestros;
import entidad.PruebasSecciones;
import entidad.TareasSecciones;
import entidad.Reclamos;
import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author juanc
 */
public class MaestrosJpaController implements Serializable {

    public MaestrosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Maestros maestros) throws PreexistingEntityException, Exception {
        if (maestros.getSeccionesList() == null) {
            maestros.setSeccionesList(new ArrayList<Secciones>());
        }
        if (maestros.getForoSeccionesList() == null) {
            maestros.setForoSeccionesList(new ArrayList<ForoSecciones>());
        }
        if (maestros.getPruebasSeccionesList() == null) {
            maestros.setPruebasSeccionesList(new ArrayList<PruebasSecciones>());
        }
        if (maestros.getTareasSeccionesList() == null) {
            maestros.setTareasSeccionesList(new ArrayList<TareasSecciones>());
        }
        if (maestros.getReclamosList() == null) {
            maestros.setReclamosList(new ArrayList<Reclamos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios fkUsuario = maestros.getFkUsuario();
            if (fkUsuario != null) {
                fkUsuario = em.getReference(fkUsuario.getClass(), fkUsuario.getId());
                maestros.setFkUsuario(fkUsuario);
            }
            List<Secciones> attachedSeccionesList = new ArrayList<Secciones>();
            for (Secciones seccionesListSeccionesToAttach : maestros.getSeccionesList()) {
                seccionesListSeccionesToAttach = em.getReference(seccionesListSeccionesToAttach.getClass(), seccionesListSeccionesToAttach.getId());
                attachedSeccionesList.add(seccionesListSeccionesToAttach);
            }
            maestros.setSeccionesList(attachedSeccionesList);
            List<ForoSecciones> attachedForoSeccionesList = new ArrayList<ForoSecciones>();
            for (ForoSecciones foroSeccionesListForoSeccionesToAttach : maestros.getForoSeccionesList()) {
                foroSeccionesListForoSeccionesToAttach = em.getReference(foroSeccionesListForoSeccionesToAttach.getClass(), foroSeccionesListForoSeccionesToAttach.getId());
                attachedForoSeccionesList.add(foroSeccionesListForoSeccionesToAttach);
            }
            maestros.setForoSeccionesList(attachedForoSeccionesList);
            List<PruebasSecciones> attachedPruebasSeccionesList = new ArrayList<PruebasSecciones>();
            for (PruebasSecciones pruebasSeccionesListPruebasSeccionesToAttach : maestros.getPruebasSeccionesList()) {
                pruebasSeccionesListPruebasSeccionesToAttach = em.getReference(pruebasSeccionesListPruebasSeccionesToAttach.getClass(), pruebasSeccionesListPruebasSeccionesToAttach.getId());
                attachedPruebasSeccionesList.add(pruebasSeccionesListPruebasSeccionesToAttach);
            }
            maestros.setPruebasSeccionesList(attachedPruebasSeccionesList);
            List<TareasSecciones> attachedTareasSeccionesList = new ArrayList<TareasSecciones>();
            for (TareasSecciones tareasSeccionesListTareasSeccionesToAttach : maestros.getTareasSeccionesList()) {
                tareasSeccionesListTareasSeccionesToAttach = em.getReference(tareasSeccionesListTareasSeccionesToAttach.getClass(), tareasSeccionesListTareasSeccionesToAttach.getId());
                attachedTareasSeccionesList.add(tareasSeccionesListTareasSeccionesToAttach);
            }
            maestros.setTareasSeccionesList(attachedTareasSeccionesList);
            List<Reclamos> attachedReclamosList = new ArrayList<Reclamos>();
            for (Reclamos reclamosListReclamosToAttach : maestros.getReclamosList()) {
                reclamosListReclamosToAttach = em.getReference(reclamosListReclamosToAttach.getClass(), reclamosListReclamosToAttach.getId());
                attachedReclamosList.add(reclamosListReclamosToAttach);
            }
            maestros.setReclamosList(attachedReclamosList);
            em.persist(maestros);
            if (fkUsuario != null) {
                fkUsuario.getMaestrosList().add(maestros);
                fkUsuario = em.merge(fkUsuario);
            }
            for (Secciones seccionesListSecciones : maestros.getSeccionesList()) {
                Maestros oldFkDocenteOfSeccionesListSecciones = seccionesListSecciones.getFkDocente();
                seccionesListSecciones.setFkDocente(maestros);
                seccionesListSecciones = em.merge(seccionesListSecciones);
                if (oldFkDocenteOfSeccionesListSecciones != null) {
                    oldFkDocenteOfSeccionesListSecciones.getSeccionesList().remove(seccionesListSecciones);
                    oldFkDocenteOfSeccionesListSecciones = em.merge(oldFkDocenteOfSeccionesListSecciones);
                }
            }
            for (ForoSecciones foroSeccionesListForoSecciones : maestros.getForoSeccionesList()) {
                Maestros oldFkMaestroOfForoSeccionesListForoSecciones = foroSeccionesListForoSecciones.getFkMaestro();
                foroSeccionesListForoSecciones.setFkMaestro(maestros);
                foroSeccionesListForoSecciones = em.merge(foroSeccionesListForoSecciones);
                if (oldFkMaestroOfForoSeccionesListForoSecciones != null) {
                    oldFkMaestroOfForoSeccionesListForoSecciones.getForoSeccionesList().remove(foroSeccionesListForoSecciones);
                    oldFkMaestroOfForoSeccionesListForoSecciones = em.merge(oldFkMaestroOfForoSeccionesListForoSecciones);
                }
            }
            for (PruebasSecciones pruebasSeccionesListPruebasSecciones : maestros.getPruebasSeccionesList()) {
                Maestros oldFkMaestroOfPruebasSeccionesListPruebasSecciones = pruebasSeccionesListPruebasSecciones.getFkMaestro();
                pruebasSeccionesListPruebasSecciones.setFkMaestro(maestros);
                pruebasSeccionesListPruebasSecciones = em.merge(pruebasSeccionesListPruebasSecciones);
                if (oldFkMaestroOfPruebasSeccionesListPruebasSecciones != null) {
                    oldFkMaestroOfPruebasSeccionesListPruebasSecciones.getPruebasSeccionesList().remove(pruebasSeccionesListPruebasSecciones);
                    oldFkMaestroOfPruebasSeccionesListPruebasSecciones = em.merge(oldFkMaestroOfPruebasSeccionesListPruebasSecciones);
                }
            }
            for (TareasSecciones tareasSeccionesListTareasSecciones : maestros.getTareasSeccionesList()) {
                Maestros oldFkDocenteOfTareasSeccionesListTareasSecciones = tareasSeccionesListTareasSecciones.getFkDocente();
                tareasSeccionesListTareasSecciones.setFkDocente(maestros);
                tareasSeccionesListTareasSecciones = em.merge(tareasSeccionesListTareasSecciones);
                if (oldFkDocenteOfTareasSeccionesListTareasSecciones != null) {
                    oldFkDocenteOfTareasSeccionesListTareasSecciones.getTareasSeccionesList().remove(tareasSeccionesListTareasSecciones);
                    oldFkDocenteOfTareasSeccionesListTareasSecciones = em.merge(oldFkDocenteOfTareasSeccionesListTareasSecciones);
                }
            }
            for (Reclamos reclamosListReclamos : maestros.getReclamosList()) {
                Maestros oldFkDocenteOfReclamosListReclamos = reclamosListReclamos.getFkDocente();
                reclamosListReclamos.setFkDocente(maestros);
                reclamosListReclamos = em.merge(reclamosListReclamos);
                if (oldFkDocenteOfReclamosListReclamos != null) {
                    oldFkDocenteOfReclamosListReclamos.getReclamosList().remove(reclamosListReclamos);
                    oldFkDocenteOfReclamosListReclamos = em.merge(oldFkDocenteOfReclamosListReclamos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMaestros(maestros.getId()) != null) {
                throw new PreexistingEntityException("Maestros " + maestros + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Maestros maestros) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Maestros persistentMaestros = em.find(Maestros.class, maestros.getId());
            Usuarios fkUsuarioOld = persistentMaestros.getFkUsuario();
            Usuarios fkUsuarioNew = maestros.getFkUsuario();
            List<Secciones> seccionesListOld = persistentMaestros.getSeccionesList();
            List<Secciones> seccionesListNew = maestros.getSeccionesList();
            List<ForoSecciones> foroSeccionesListOld = persistentMaestros.getForoSeccionesList();
            List<ForoSecciones> foroSeccionesListNew = maestros.getForoSeccionesList();
            List<PruebasSecciones> pruebasSeccionesListOld = persistentMaestros.getPruebasSeccionesList();
            List<PruebasSecciones> pruebasSeccionesListNew = maestros.getPruebasSeccionesList();
            List<TareasSecciones> tareasSeccionesListOld = persistentMaestros.getTareasSeccionesList();
            List<TareasSecciones> tareasSeccionesListNew = maestros.getTareasSeccionesList();
            List<Reclamos> reclamosListOld = persistentMaestros.getReclamosList();
            List<Reclamos> reclamosListNew = maestros.getReclamosList();
            if (fkUsuarioNew != null) {
                fkUsuarioNew = em.getReference(fkUsuarioNew.getClass(), fkUsuarioNew.getId());
                maestros.setFkUsuario(fkUsuarioNew);
            }
            List<Secciones> attachedSeccionesListNew = new ArrayList<Secciones>();
            for (Secciones seccionesListNewSeccionesToAttach : seccionesListNew) {
                seccionesListNewSeccionesToAttach = em.getReference(seccionesListNewSeccionesToAttach.getClass(), seccionesListNewSeccionesToAttach.getId());
                attachedSeccionesListNew.add(seccionesListNewSeccionesToAttach);
            }
            seccionesListNew = attachedSeccionesListNew;
            maestros.setSeccionesList(seccionesListNew);
            List<ForoSecciones> attachedForoSeccionesListNew = new ArrayList<ForoSecciones>();
            for (ForoSecciones foroSeccionesListNewForoSeccionesToAttach : foroSeccionesListNew) {
                foroSeccionesListNewForoSeccionesToAttach = em.getReference(foroSeccionesListNewForoSeccionesToAttach.getClass(), foroSeccionesListNewForoSeccionesToAttach.getId());
                attachedForoSeccionesListNew.add(foroSeccionesListNewForoSeccionesToAttach);
            }
            foroSeccionesListNew = attachedForoSeccionesListNew;
            maestros.setForoSeccionesList(foroSeccionesListNew);
            List<PruebasSecciones> attachedPruebasSeccionesListNew = new ArrayList<PruebasSecciones>();
            for (PruebasSecciones pruebasSeccionesListNewPruebasSeccionesToAttach : pruebasSeccionesListNew) {
                pruebasSeccionesListNewPruebasSeccionesToAttach = em.getReference(pruebasSeccionesListNewPruebasSeccionesToAttach.getClass(), pruebasSeccionesListNewPruebasSeccionesToAttach.getId());
                attachedPruebasSeccionesListNew.add(pruebasSeccionesListNewPruebasSeccionesToAttach);
            }
            pruebasSeccionesListNew = attachedPruebasSeccionesListNew;
            maestros.setPruebasSeccionesList(pruebasSeccionesListNew);
            List<TareasSecciones> attachedTareasSeccionesListNew = new ArrayList<TareasSecciones>();
            for (TareasSecciones tareasSeccionesListNewTareasSeccionesToAttach : tareasSeccionesListNew) {
                tareasSeccionesListNewTareasSeccionesToAttach = em.getReference(tareasSeccionesListNewTareasSeccionesToAttach.getClass(), tareasSeccionesListNewTareasSeccionesToAttach.getId());
                attachedTareasSeccionesListNew.add(tareasSeccionesListNewTareasSeccionesToAttach);
            }
            tareasSeccionesListNew = attachedTareasSeccionesListNew;
            maestros.setTareasSeccionesList(tareasSeccionesListNew);
            List<Reclamos> attachedReclamosListNew = new ArrayList<Reclamos>();
            for (Reclamos reclamosListNewReclamosToAttach : reclamosListNew) {
                reclamosListNewReclamosToAttach = em.getReference(reclamosListNewReclamosToAttach.getClass(), reclamosListNewReclamosToAttach.getId());
                attachedReclamosListNew.add(reclamosListNewReclamosToAttach);
            }
            reclamosListNew = attachedReclamosListNew;
            maestros.setReclamosList(reclamosListNew);
            maestros = em.merge(maestros);
            if (fkUsuarioOld != null && !fkUsuarioOld.equals(fkUsuarioNew)) {
                fkUsuarioOld.getMaestrosList().remove(maestros);
                fkUsuarioOld = em.merge(fkUsuarioOld);
            }
            if (fkUsuarioNew != null && !fkUsuarioNew.equals(fkUsuarioOld)) {
                fkUsuarioNew.getMaestrosList().add(maestros);
                fkUsuarioNew = em.merge(fkUsuarioNew);
            }
            for (Secciones seccionesListOldSecciones : seccionesListOld) {
                if (!seccionesListNew.contains(seccionesListOldSecciones)) {
                    seccionesListOldSecciones.setFkDocente(null);
                    seccionesListOldSecciones = em.merge(seccionesListOldSecciones);
                }
            }
            for (Secciones seccionesListNewSecciones : seccionesListNew) {
                if (!seccionesListOld.contains(seccionesListNewSecciones)) {
                    Maestros oldFkDocenteOfSeccionesListNewSecciones = seccionesListNewSecciones.getFkDocente();
                    seccionesListNewSecciones.setFkDocente(maestros);
                    seccionesListNewSecciones = em.merge(seccionesListNewSecciones);
                    if (oldFkDocenteOfSeccionesListNewSecciones != null && !oldFkDocenteOfSeccionesListNewSecciones.equals(maestros)) {
                        oldFkDocenteOfSeccionesListNewSecciones.getSeccionesList().remove(seccionesListNewSecciones);
                        oldFkDocenteOfSeccionesListNewSecciones = em.merge(oldFkDocenteOfSeccionesListNewSecciones);
                    }
                }
            }
            for (ForoSecciones foroSeccionesListOldForoSecciones : foroSeccionesListOld) {
                if (!foroSeccionesListNew.contains(foroSeccionesListOldForoSecciones)) {
                    foroSeccionesListOldForoSecciones.setFkMaestro(null);
                    foroSeccionesListOldForoSecciones = em.merge(foroSeccionesListOldForoSecciones);
                }
            }
            for (ForoSecciones foroSeccionesListNewForoSecciones : foroSeccionesListNew) {
                if (!foroSeccionesListOld.contains(foroSeccionesListNewForoSecciones)) {
                    Maestros oldFkMaestroOfForoSeccionesListNewForoSecciones = foroSeccionesListNewForoSecciones.getFkMaestro();
                    foroSeccionesListNewForoSecciones.setFkMaestro(maestros);
                    foroSeccionesListNewForoSecciones = em.merge(foroSeccionesListNewForoSecciones);
                    if (oldFkMaestroOfForoSeccionesListNewForoSecciones != null && !oldFkMaestroOfForoSeccionesListNewForoSecciones.equals(maestros)) {
                        oldFkMaestroOfForoSeccionesListNewForoSecciones.getForoSeccionesList().remove(foroSeccionesListNewForoSecciones);
                        oldFkMaestroOfForoSeccionesListNewForoSecciones = em.merge(oldFkMaestroOfForoSeccionesListNewForoSecciones);
                    }
                }
            }
            for (PruebasSecciones pruebasSeccionesListOldPruebasSecciones : pruebasSeccionesListOld) {
                if (!pruebasSeccionesListNew.contains(pruebasSeccionesListOldPruebasSecciones)) {
                    pruebasSeccionesListOldPruebasSecciones.setFkMaestro(null);
                    pruebasSeccionesListOldPruebasSecciones = em.merge(pruebasSeccionesListOldPruebasSecciones);
                }
            }
            for (PruebasSecciones pruebasSeccionesListNewPruebasSecciones : pruebasSeccionesListNew) {
                if (!pruebasSeccionesListOld.contains(pruebasSeccionesListNewPruebasSecciones)) {
                    Maestros oldFkMaestroOfPruebasSeccionesListNewPruebasSecciones = pruebasSeccionesListNewPruebasSecciones.getFkMaestro();
                    pruebasSeccionesListNewPruebasSecciones.setFkMaestro(maestros);
                    pruebasSeccionesListNewPruebasSecciones = em.merge(pruebasSeccionesListNewPruebasSecciones);
                    if (oldFkMaestroOfPruebasSeccionesListNewPruebasSecciones != null && !oldFkMaestroOfPruebasSeccionesListNewPruebasSecciones.equals(maestros)) {
                        oldFkMaestroOfPruebasSeccionesListNewPruebasSecciones.getPruebasSeccionesList().remove(pruebasSeccionesListNewPruebasSecciones);
                        oldFkMaestroOfPruebasSeccionesListNewPruebasSecciones = em.merge(oldFkMaestroOfPruebasSeccionesListNewPruebasSecciones);
                    }
                }
            }
            for (TareasSecciones tareasSeccionesListOldTareasSecciones : tareasSeccionesListOld) {
                if (!tareasSeccionesListNew.contains(tareasSeccionesListOldTareasSecciones)) {
                    tareasSeccionesListOldTareasSecciones.setFkDocente(null);
                    tareasSeccionesListOldTareasSecciones = em.merge(tareasSeccionesListOldTareasSecciones);
                }
            }
            for (TareasSecciones tareasSeccionesListNewTareasSecciones : tareasSeccionesListNew) {
                if (!tareasSeccionesListOld.contains(tareasSeccionesListNewTareasSecciones)) {
                    Maestros oldFkDocenteOfTareasSeccionesListNewTareasSecciones = tareasSeccionesListNewTareasSecciones.getFkDocente();
                    tareasSeccionesListNewTareasSecciones.setFkDocente(maestros);
                    tareasSeccionesListNewTareasSecciones = em.merge(tareasSeccionesListNewTareasSecciones);
                    if (oldFkDocenteOfTareasSeccionesListNewTareasSecciones != null && !oldFkDocenteOfTareasSeccionesListNewTareasSecciones.equals(maestros)) {
                        oldFkDocenteOfTareasSeccionesListNewTareasSecciones.getTareasSeccionesList().remove(tareasSeccionesListNewTareasSecciones);
                        oldFkDocenteOfTareasSeccionesListNewTareasSecciones = em.merge(oldFkDocenteOfTareasSeccionesListNewTareasSecciones);
                    }
                }
            }
            for (Reclamos reclamosListOldReclamos : reclamosListOld) {
                if (!reclamosListNew.contains(reclamosListOldReclamos)) {
                    reclamosListOldReclamos.setFkDocente(null);
                    reclamosListOldReclamos = em.merge(reclamosListOldReclamos);
                }
            }
            for (Reclamos reclamosListNewReclamos : reclamosListNew) {
                if (!reclamosListOld.contains(reclamosListNewReclamos)) {
                    Maestros oldFkDocenteOfReclamosListNewReclamos = reclamosListNewReclamos.getFkDocente();
                    reclamosListNewReclamos.setFkDocente(maestros);
                    reclamosListNewReclamos = em.merge(reclamosListNewReclamos);
                    if (oldFkDocenteOfReclamosListNewReclamos != null && !oldFkDocenteOfReclamosListNewReclamos.equals(maestros)) {
                        oldFkDocenteOfReclamosListNewReclamos.getReclamosList().remove(reclamosListNewReclamos);
                        oldFkDocenteOfReclamosListNewReclamos = em.merge(oldFkDocenteOfReclamosListNewReclamos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = maestros.getId();
                if (findMaestros(id) == null) {
                    throw new NonexistentEntityException("The maestros with id " + id + " no longer exists.");
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
            Maestros maestros;
            try {
                maestros = em.getReference(Maestros.class, id);
                maestros.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The maestros with id " + id + " no longer exists.", enfe);
            }
            Usuarios fkUsuario = maestros.getFkUsuario();
            if (fkUsuario != null) {
                fkUsuario.getMaestrosList().remove(maestros);
                fkUsuario = em.merge(fkUsuario);
            }
            List<Secciones> seccionesList = maestros.getSeccionesList();
            for (Secciones seccionesListSecciones : seccionesList) {
                seccionesListSecciones.setFkDocente(null);
                seccionesListSecciones = em.merge(seccionesListSecciones);
            }
            List<ForoSecciones> foroSeccionesList = maestros.getForoSeccionesList();
            for (ForoSecciones foroSeccionesListForoSecciones : foroSeccionesList) {
                foroSeccionesListForoSecciones.setFkMaestro(null);
                foroSeccionesListForoSecciones = em.merge(foroSeccionesListForoSecciones);
            }
            List<PruebasSecciones> pruebasSeccionesList = maestros.getPruebasSeccionesList();
            for (PruebasSecciones pruebasSeccionesListPruebasSecciones : pruebasSeccionesList) {
                pruebasSeccionesListPruebasSecciones.setFkMaestro(null);
                pruebasSeccionesListPruebasSecciones = em.merge(pruebasSeccionesListPruebasSecciones);
            }
            List<TareasSecciones> tareasSeccionesList = maestros.getTareasSeccionesList();
            for (TareasSecciones tareasSeccionesListTareasSecciones : tareasSeccionesList) {
                tareasSeccionesListTareasSecciones.setFkDocente(null);
                tareasSeccionesListTareasSecciones = em.merge(tareasSeccionesListTareasSecciones);
            }
            List<Reclamos> reclamosList = maestros.getReclamosList();
            for (Reclamos reclamosListReclamos : reclamosList) {
                reclamosListReclamos.setFkDocente(null);
                reclamosListReclamos = em.merge(reclamosListReclamos);
            }
            em.remove(maestros);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Maestros> findMaestrosEntities() {
        return findMaestrosEntities(true, -1, -1);
    }

    public List<Maestros> findMaestrosEntities(int maxResults, int firstResult) {
        return findMaestrosEntities(false, maxResults, firstResult);
    }

    private List<Maestros> findMaestrosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Maestros.class));
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

    public Maestros findMaestros(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Maestros.class, id);
        } finally {
            em.close();
        }
    }

    public int getMaestrosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Maestros> rt = cq.from(Maestros.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
