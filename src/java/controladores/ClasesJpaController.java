/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import controladores.exceptions.PreexistingEntityException;
import entidad.Clases;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidad.Secciones;
import java.util.ArrayList;
import java.util.List;
import entidad.ClasesPlanesEstudio;
import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author juanc
 */
public class ClasesJpaController implements Serializable {

    public ClasesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Clases clases) throws PreexistingEntityException, Exception {
        if (clases.getSeccionesList() == null) {
            clases.setSeccionesList(new ArrayList<Secciones>());
        }
        if (clases.getClasesPlanesEstudioList() == null) {
            clases.setClasesPlanesEstudioList(new ArrayList<ClasesPlanesEstudio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Secciones> attachedSeccionesList = new ArrayList<Secciones>();
            for (Secciones seccionesListSeccionesToAttach : clases.getSeccionesList()) {
                seccionesListSeccionesToAttach = em.getReference(seccionesListSeccionesToAttach.getClass(), seccionesListSeccionesToAttach.getId());
                attachedSeccionesList.add(seccionesListSeccionesToAttach);
            }
            clases.setSeccionesList(attachedSeccionesList);
            List<ClasesPlanesEstudio> attachedClasesPlanesEstudioList = new ArrayList<ClasesPlanesEstudio>();
            for (ClasesPlanesEstudio clasesPlanesEstudioListClasesPlanesEstudioToAttach : clases.getClasesPlanesEstudioList()) {
                clasesPlanesEstudioListClasesPlanesEstudioToAttach = em.getReference(clasesPlanesEstudioListClasesPlanesEstudioToAttach.getClass(), clasesPlanesEstudioListClasesPlanesEstudioToAttach.getId());
                attachedClasesPlanesEstudioList.add(clasesPlanesEstudioListClasesPlanesEstudioToAttach);
            }
            clases.setClasesPlanesEstudioList(attachedClasesPlanesEstudioList);
            em.persist(clases);
            for (Secciones seccionesListSecciones : clases.getSeccionesList()) {
                Clases oldFkCodigoClaseOfSeccionesListSecciones = seccionesListSecciones.getFkCodigoClase();
                seccionesListSecciones.setFkCodigoClase(clases);
                seccionesListSecciones = em.merge(seccionesListSecciones);
                if (oldFkCodigoClaseOfSeccionesListSecciones != null) {
                    oldFkCodigoClaseOfSeccionesListSecciones.getSeccionesList().remove(seccionesListSecciones);
                    oldFkCodigoClaseOfSeccionesListSecciones = em.merge(oldFkCodigoClaseOfSeccionesListSecciones);
                }
            }
            for (ClasesPlanesEstudio clasesPlanesEstudioListClasesPlanesEstudio : clases.getClasesPlanesEstudioList()) {
                Clases oldFkClaseOfClasesPlanesEstudioListClasesPlanesEstudio = clasesPlanesEstudioListClasesPlanesEstudio.getFkClase();
                clasesPlanesEstudioListClasesPlanesEstudio.setFkClase(clases);
                clasesPlanesEstudioListClasesPlanesEstudio = em.merge(clasesPlanesEstudioListClasesPlanesEstudio);
                if (oldFkClaseOfClasesPlanesEstudioListClasesPlanesEstudio != null) {
                    oldFkClaseOfClasesPlanesEstudioListClasesPlanesEstudio.getClasesPlanesEstudioList().remove(clasesPlanesEstudioListClasesPlanesEstudio);
                    oldFkClaseOfClasesPlanesEstudioListClasesPlanesEstudio = em.merge(oldFkClaseOfClasesPlanesEstudioListClasesPlanesEstudio);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findClases(clases.getId()) != null) {
                throw new PreexistingEntityException("Clases " + clases + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Clases clases) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clases persistentClases = em.find(Clases.class, clases.getId());
            List<Secciones> seccionesListOld = persistentClases.getSeccionesList();
            List<Secciones> seccionesListNew = clases.getSeccionesList();
            List<ClasesPlanesEstudio> clasesPlanesEstudioListOld = persistentClases.getClasesPlanesEstudioList();
            List<ClasesPlanesEstudio> clasesPlanesEstudioListNew = clases.getClasesPlanesEstudioList();
            List<Secciones> attachedSeccionesListNew = new ArrayList<Secciones>();
            for (Secciones seccionesListNewSeccionesToAttach : seccionesListNew) {
                seccionesListNewSeccionesToAttach = em.getReference(seccionesListNewSeccionesToAttach.getClass(), seccionesListNewSeccionesToAttach.getId());
                attachedSeccionesListNew.add(seccionesListNewSeccionesToAttach);
            }
            seccionesListNew = attachedSeccionesListNew;
            clases.setSeccionesList(seccionesListNew);
            List<ClasesPlanesEstudio> attachedClasesPlanesEstudioListNew = new ArrayList<ClasesPlanesEstudio>();
            for (ClasesPlanesEstudio clasesPlanesEstudioListNewClasesPlanesEstudioToAttach : clasesPlanesEstudioListNew) {
                clasesPlanesEstudioListNewClasesPlanesEstudioToAttach = em.getReference(clasesPlanesEstudioListNewClasesPlanesEstudioToAttach.getClass(), clasesPlanesEstudioListNewClasesPlanesEstudioToAttach.getId());
                attachedClasesPlanesEstudioListNew.add(clasesPlanesEstudioListNewClasesPlanesEstudioToAttach);
            }
            clasesPlanesEstudioListNew = attachedClasesPlanesEstudioListNew;
            clases.setClasesPlanesEstudioList(clasesPlanesEstudioListNew);
            clases = em.merge(clases);
            for (Secciones seccionesListOldSecciones : seccionesListOld) {
                if (!seccionesListNew.contains(seccionesListOldSecciones)) {
                    seccionesListOldSecciones.setFkCodigoClase(null);
                    seccionesListOldSecciones = em.merge(seccionesListOldSecciones);
                }
            }
            for (Secciones seccionesListNewSecciones : seccionesListNew) {
                if (!seccionesListOld.contains(seccionesListNewSecciones)) {
                    Clases oldFkCodigoClaseOfSeccionesListNewSecciones = seccionesListNewSecciones.getFkCodigoClase();
                    seccionesListNewSecciones.setFkCodigoClase(clases);
                    seccionesListNewSecciones = em.merge(seccionesListNewSecciones);
                    if (oldFkCodigoClaseOfSeccionesListNewSecciones != null && !oldFkCodigoClaseOfSeccionesListNewSecciones.equals(clases)) {
                        oldFkCodigoClaseOfSeccionesListNewSecciones.getSeccionesList().remove(seccionesListNewSecciones);
                        oldFkCodigoClaseOfSeccionesListNewSecciones = em.merge(oldFkCodigoClaseOfSeccionesListNewSecciones);
                    }
                }
            }
            for (ClasesPlanesEstudio clasesPlanesEstudioListOldClasesPlanesEstudio : clasesPlanesEstudioListOld) {
                if (!clasesPlanesEstudioListNew.contains(clasesPlanesEstudioListOldClasesPlanesEstudio)) {
                    clasesPlanesEstudioListOldClasesPlanesEstudio.setFkClase(null);
                    clasesPlanesEstudioListOldClasesPlanesEstudio = em.merge(clasesPlanesEstudioListOldClasesPlanesEstudio);
                }
            }
            for (ClasesPlanesEstudio clasesPlanesEstudioListNewClasesPlanesEstudio : clasesPlanesEstudioListNew) {
                if (!clasesPlanesEstudioListOld.contains(clasesPlanesEstudioListNewClasesPlanesEstudio)) {
                    Clases oldFkClaseOfClasesPlanesEstudioListNewClasesPlanesEstudio = clasesPlanesEstudioListNewClasesPlanesEstudio.getFkClase();
                    clasesPlanesEstudioListNewClasesPlanesEstudio.setFkClase(clases);
                    clasesPlanesEstudioListNewClasesPlanesEstudio = em.merge(clasesPlanesEstudioListNewClasesPlanesEstudio);
                    if (oldFkClaseOfClasesPlanesEstudioListNewClasesPlanesEstudio != null && !oldFkClaseOfClasesPlanesEstudioListNewClasesPlanesEstudio.equals(clases)) {
                        oldFkClaseOfClasesPlanesEstudioListNewClasesPlanesEstudio.getClasesPlanesEstudioList().remove(clasesPlanesEstudioListNewClasesPlanesEstudio);
                        oldFkClaseOfClasesPlanesEstudioListNewClasesPlanesEstudio = em.merge(oldFkClaseOfClasesPlanesEstudioListNewClasesPlanesEstudio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = clases.getId();
                if (findClases(id) == null) {
                    throw new NonexistentEntityException("The clases with id " + id + " no longer exists.");
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
            Clases clases;
            try {
                clases = em.getReference(Clases.class, id);
                clases.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clases with id " + id + " no longer exists.", enfe);
            }
            List<Secciones> seccionesList = clases.getSeccionesList();
            for (Secciones seccionesListSecciones : seccionesList) {
                seccionesListSecciones.setFkCodigoClase(null);
                seccionesListSecciones = em.merge(seccionesListSecciones);
            }
            List<ClasesPlanesEstudio> clasesPlanesEstudioList = clases.getClasesPlanesEstudioList();
            for (ClasesPlanesEstudio clasesPlanesEstudioListClasesPlanesEstudio : clasesPlanesEstudioList) {
                clasesPlanesEstudioListClasesPlanesEstudio.setFkClase(null);
                clasesPlanesEstudioListClasesPlanesEstudio = em.merge(clasesPlanesEstudioListClasesPlanesEstudio);
            }
            em.remove(clases);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Clases> findClasesEntities() {
        return findClasesEntities(true, -1, -1);
    }

    public List<Clases> findClasesEntities(int maxResults, int firstResult) {
        return findClasesEntities(false, maxResults, firstResult);
    }

    private List<Clases> findClasesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Clases.class));
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

    public Clases findClases(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Clases.class, id);
        } finally {
            em.close();
        }
    }

    public int getClasesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Clases> rt = cq.from(Clases.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
