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
import entidad.Secciones;
import java.util.ArrayList;
import java.util.List;
import entidad.ActividadesClases;
import entidad.Modalidades;
import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author juanc
 */
public class ModalidadesJpaController implements Serializable {

    public ModalidadesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Modalidades modalidades) throws PreexistingEntityException, Exception {
        if (modalidades.getSeccionesList() == null) {
            modalidades.setSeccionesList(new ArrayList<Secciones>());
        }
        if (modalidades.getActividadesClasesList() == null) {
            modalidades.setActividadesClasesList(new ArrayList<ActividadesClases>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Secciones> attachedSeccionesList = new ArrayList<Secciones>();
            for (Secciones seccionesListSeccionesToAttach : modalidades.getSeccionesList()) {
                seccionesListSeccionesToAttach = em.getReference(seccionesListSeccionesToAttach.getClass(), seccionesListSeccionesToAttach.getId());
                attachedSeccionesList.add(seccionesListSeccionesToAttach);
            }
            modalidades.setSeccionesList(attachedSeccionesList);
            List<ActividadesClases> attachedActividadesClasesList = new ArrayList<ActividadesClases>();
            for (ActividadesClases actividadesClasesListActividadesClasesToAttach : modalidades.getActividadesClasesList()) {
                actividadesClasesListActividadesClasesToAttach = em.getReference(actividadesClasesListActividadesClasesToAttach.getClass(), actividadesClasesListActividadesClasesToAttach.getId());
                attachedActividadesClasesList.add(actividadesClasesListActividadesClasesToAttach);
            }
            modalidades.setActividadesClasesList(attachedActividadesClasesList);
            em.persist(modalidades);
            for (Secciones seccionesListSecciones : modalidades.getSeccionesList()) {
                Modalidades oldFkModalidadOfSeccionesListSecciones = seccionesListSecciones.getFkModalidad();
                seccionesListSecciones.setFkModalidad(modalidades);
                seccionesListSecciones = em.merge(seccionesListSecciones);
                if (oldFkModalidadOfSeccionesListSecciones != null) {
                    oldFkModalidadOfSeccionesListSecciones.getSeccionesList().remove(seccionesListSecciones);
                    oldFkModalidadOfSeccionesListSecciones = em.merge(oldFkModalidadOfSeccionesListSecciones);
                }
            }
            for (ActividadesClases actividadesClasesListActividadesClases : modalidades.getActividadesClasesList()) {
                Modalidades oldFkModalidadOfActividadesClasesListActividadesClases = actividadesClasesListActividadesClases.getFkModalidad();
                actividadesClasesListActividadesClases.setFkModalidad(modalidades);
                actividadesClasesListActividadesClases = em.merge(actividadesClasesListActividadesClases);
                if (oldFkModalidadOfActividadesClasesListActividadesClases != null) {
                    oldFkModalidadOfActividadesClasesListActividadesClases.getActividadesClasesList().remove(actividadesClasesListActividadesClases);
                    oldFkModalidadOfActividadesClasesListActividadesClases = em.merge(oldFkModalidadOfActividadesClasesListActividadesClases);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findModalidades(modalidades.getId()) != null) {
                throw new PreexistingEntityException("Modalidades " + modalidades + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Modalidades modalidades) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Modalidades persistentModalidades = em.find(Modalidades.class, modalidades.getId());
            List<Secciones> seccionesListOld = persistentModalidades.getSeccionesList();
            List<Secciones> seccionesListNew = modalidades.getSeccionesList();
            List<ActividadesClases> actividadesClasesListOld = persistentModalidades.getActividadesClasesList();
            List<ActividadesClases> actividadesClasesListNew = modalidades.getActividadesClasesList();
            List<Secciones> attachedSeccionesListNew = new ArrayList<Secciones>();
            for (Secciones seccionesListNewSeccionesToAttach : seccionesListNew) {
                seccionesListNewSeccionesToAttach = em.getReference(seccionesListNewSeccionesToAttach.getClass(), seccionesListNewSeccionesToAttach.getId());
                attachedSeccionesListNew.add(seccionesListNewSeccionesToAttach);
            }
            seccionesListNew = attachedSeccionesListNew;
            modalidades.setSeccionesList(seccionesListNew);
            List<ActividadesClases> attachedActividadesClasesListNew = new ArrayList<ActividadesClases>();
            for (ActividadesClases actividadesClasesListNewActividadesClasesToAttach : actividadesClasesListNew) {
                actividadesClasesListNewActividadesClasesToAttach = em.getReference(actividadesClasesListNewActividadesClasesToAttach.getClass(), actividadesClasesListNewActividadesClasesToAttach.getId());
                attachedActividadesClasesListNew.add(actividadesClasesListNewActividadesClasesToAttach);
            }
            actividadesClasesListNew = attachedActividadesClasesListNew;
            modalidades.setActividadesClasesList(actividadesClasesListNew);
            modalidades = em.merge(modalidades);
            for (Secciones seccionesListOldSecciones : seccionesListOld) {
                if (!seccionesListNew.contains(seccionesListOldSecciones)) {
                    seccionesListOldSecciones.setFkModalidad(null);
                    seccionesListOldSecciones = em.merge(seccionesListOldSecciones);
                }
            }
            for (Secciones seccionesListNewSecciones : seccionesListNew) {
                if (!seccionesListOld.contains(seccionesListNewSecciones)) {
                    Modalidades oldFkModalidadOfSeccionesListNewSecciones = seccionesListNewSecciones.getFkModalidad();
                    seccionesListNewSecciones.setFkModalidad(modalidades);
                    seccionesListNewSecciones = em.merge(seccionesListNewSecciones);
                    if (oldFkModalidadOfSeccionesListNewSecciones != null && !oldFkModalidadOfSeccionesListNewSecciones.equals(modalidades)) {
                        oldFkModalidadOfSeccionesListNewSecciones.getSeccionesList().remove(seccionesListNewSecciones);
                        oldFkModalidadOfSeccionesListNewSecciones = em.merge(oldFkModalidadOfSeccionesListNewSecciones);
                    }
                }
            }
            for (ActividadesClases actividadesClasesListOldActividadesClases : actividadesClasesListOld) {
                if (!actividadesClasesListNew.contains(actividadesClasesListOldActividadesClases)) {
                    actividadesClasesListOldActividadesClases.setFkModalidad(null);
                    actividadesClasesListOldActividadesClases = em.merge(actividadesClasesListOldActividadesClases);
                }
            }
            for (ActividadesClases actividadesClasesListNewActividadesClases : actividadesClasesListNew) {
                if (!actividadesClasesListOld.contains(actividadesClasesListNewActividadesClases)) {
                    Modalidades oldFkModalidadOfActividadesClasesListNewActividadesClases = actividadesClasesListNewActividadesClases.getFkModalidad();
                    actividadesClasesListNewActividadesClases.setFkModalidad(modalidades);
                    actividadesClasesListNewActividadesClases = em.merge(actividadesClasesListNewActividadesClases);
                    if (oldFkModalidadOfActividadesClasesListNewActividadesClases != null && !oldFkModalidadOfActividadesClasesListNewActividadesClases.equals(modalidades)) {
                        oldFkModalidadOfActividadesClasesListNewActividadesClases.getActividadesClasesList().remove(actividadesClasesListNewActividadesClases);
                        oldFkModalidadOfActividadesClasesListNewActividadesClases = em.merge(oldFkModalidadOfActividadesClasesListNewActividadesClases);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = modalidades.getId();
                if (findModalidades(id) == null) {
                    throw new NonexistentEntityException("The modalidades with id " + id + " no longer exists.");
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
            Modalidades modalidades;
            try {
                modalidades = em.getReference(Modalidades.class, id);
                modalidades.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The modalidades with id " + id + " no longer exists.", enfe);
            }
            List<Secciones> seccionesList = modalidades.getSeccionesList();
            for (Secciones seccionesListSecciones : seccionesList) {
                seccionesListSecciones.setFkModalidad(null);
                seccionesListSecciones = em.merge(seccionesListSecciones);
            }
            List<ActividadesClases> actividadesClasesList = modalidades.getActividadesClasesList();
            for (ActividadesClases actividadesClasesListActividadesClases : actividadesClasesList) {
                actividadesClasesListActividadesClases.setFkModalidad(null);
                actividadesClasesListActividadesClases = em.merge(actividadesClasesListActividadesClases);
            }
            em.remove(modalidades);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Modalidades> findModalidadesEntities() {
        return findModalidadesEntities(true, -1, -1);
    }

    public List<Modalidades> findModalidadesEntities(int maxResults, int firstResult) {
        return findModalidadesEntities(false, maxResults, firstResult);
    }

    private List<Modalidades> findModalidadesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Modalidades.class));
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

    public Modalidades findModalidades(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Modalidades.class, id);
        } finally {
            em.close();
        }
    }

    public int getModalidadesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Modalidades> rt = cq.from(Modalidades.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
