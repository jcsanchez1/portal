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
import entidad.InfoUniveridades;
import entidad.Secciones;
import entidad.Sedes;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author juanc
 */
public class SedesJpaController implements Serializable {

    public SedesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sedes sedes) throws PreexistingEntityException, Exception {
        if (sedes.getSeccionesList() == null) {
            sedes.setSeccionesList(new ArrayList<Secciones>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            InfoUniveridades fkIdUniversidad = sedes.getFkIdUniversidad();
            if (fkIdUniversidad != null) {
                fkIdUniversidad = em.getReference(fkIdUniversidad.getClass(), fkIdUniversidad.getId());
                sedes.setFkIdUniversidad(fkIdUniversidad);
            }
            List<Secciones> attachedSeccionesList = new ArrayList<Secciones>();
            for (Secciones seccionesListSeccionesToAttach : sedes.getSeccionesList()) {
                seccionesListSeccionesToAttach = em.getReference(seccionesListSeccionesToAttach.getClass(), seccionesListSeccionesToAttach.getId());
                attachedSeccionesList.add(seccionesListSeccionesToAttach);
            }
            sedes.setSeccionesList(attachedSeccionesList);
            em.persist(sedes);
            if (fkIdUniversidad != null) {
                fkIdUniversidad.getSedesList().add(sedes);
                fkIdUniversidad = em.merge(fkIdUniversidad);
            }
            for (Secciones seccionesListSecciones : sedes.getSeccionesList()) {
                Sedes oldFkSedeOfSeccionesListSecciones = seccionesListSecciones.getFkSede();
                seccionesListSecciones.setFkSede(sedes);
                seccionesListSecciones = em.merge(seccionesListSecciones);
                if (oldFkSedeOfSeccionesListSecciones != null) {
                    oldFkSedeOfSeccionesListSecciones.getSeccionesList().remove(seccionesListSecciones);
                    oldFkSedeOfSeccionesListSecciones = em.merge(oldFkSedeOfSeccionesListSecciones);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSedes(sedes.getId()) != null) {
                throw new PreexistingEntityException("Sedes " + sedes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sedes sedes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sedes persistentSedes = em.find(Sedes.class, sedes.getId());
            InfoUniveridades fkIdUniversidadOld = persistentSedes.getFkIdUniversidad();
            InfoUniveridades fkIdUniversidadNew = sedes.getFkIdUniversidad();
            List<Secciones> seccionesListOld = persistentSedes.getSeccionesList();
            List<Secciones> seccionesListNew = sedes.getSeccionesList();
            if (fkIdUniversidadNew != null) {
                fkIdUniversidadNew = em.getReference(fkIdUniversidadNew.getClass(), fkIdUniversidadNew.getId());
                sedes.setFkIdUniversidad(fkIdUniversidadNew);
            }
            List<Secciones> attachedSeccionesListNew = new ArrayList<Secciones>();
            for (Secciones seccionesListNewSeccionesToAttach : seccionesListNew) {
                seccionesListNewSeccionesToAttach = em.getReference(seccionesListNewSeccionesToAttach.getClass(), seccionesListNewSeccionesToAttach.getId());
                attachedSeccionesListNew.add(seccionesListNewSeccionesToAttach);
            }
            seccionesListNew = attachedSeccionesListNew;
            sedes.setSeccionesList(seccionesListNew);
            sedes = em.merge(sedes);
            if (fkIdUniversidadOld != null && !fkIdUniversidadOld.equals(fkIdUniversidadNew)) {
                fkIdUniversidadOld.getSedesList().remove(sedes);
                fkIdUniversidadOld = em.merge(fkIdUniversidadOld);
            }
            if (fkIdUniversidadNew != null && !fkIdUniversidadNew.equals(fkIdUniversidadOld)) {
                fkIdUniversidadNew.getSedesList().add(sedes);
                fkIdUniversidadNew = em.merge(fkIdUniversidadNew);
            }
            for (Secciones seccionesListOldSecciones : seccionesListOld) {
                if (!seccionesListNew.contains(seccionesListOldSecciones)) {
                    seccionesListOldSecciones.setFkSede(null);
                    seccionesListOldSecciones = em.merge(seccionesListOldSecciones);
                }
            }
            for (Secciones seccionesListNewSecciones : seccionesListNew) {
                if (!seccionesListOld.contains(seccionesListNewSecciones)) {
                    Sedes oldFkSedeOfSeccionesListNewSecciones = seccionesListNewSecciones.getFkSede();
                    seccionesListNewSecciones.setFkSede(sedes);
                    seccionesListNewSecciones = em.merge(seccionesListNewSecciones);
                    if (oldFkSedeOfSeccionesListNewSecciones != null && !oldFkSedeOfSeccionesListNewSecciones.equals(sedes)) {
                        oldFkSedeOfSeccionesListNewSecciones.getSeccionesList().remove(seccionesListNewSecciones);
                        oldFkSedeOfSeccionesListNewSecciones = em.merge(oldFkSedeOfSeccionesListNewSecciones);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = sedes.getId();
                if (findSedes(id) == null) {
                    throw new NonexistentEntityException("The sedes with id " + id + " no longer exists.");
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
            Sedes sedes;
            try {
                sedes = em.getReference(Sedes.class, id);
                sedes.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sedes with id " + id + " no longer exists.", enfe);
            }
            InfoUniveridades fkIdUniversidad = sedes.getFkIdUniversidad();
            if (fkIdUniversidad != null) {
                fkIdUniversidad.getSedesList().remove(sedes);
                fkIdUniversidad = em.merge(fkIdUniversidad);
            }
            List<Secciones> seccionesList = sedes.getSeccionesList();
            for (Secciones seccionesListSecciones : seccionesList) {
                seccionesListSecciones.setFkSede(null);
                seccionesListSecciones = em.merge(seccionesListSecciones);
            }
            em.remove(sedes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sedes> findSedesEntities() {
        return findSedesEntities(true, -1, -1);
    }

    public List<Sedes> findSedesEntities(int maxResults, int firstResult) {
        return findSedesEntities(false, maxResults, firstResult);
    }

    private List<Sedes> findSedesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sedes.class));
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

    public Sedes findSedes(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sedes.class, id);
        } finally {
            em.close();
        }
    }

    public int getSedesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sedes> rt = cq.from(Sedes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
