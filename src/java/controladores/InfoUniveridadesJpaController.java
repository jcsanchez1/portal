/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import controladores.exceptions.PreexistingEntityException;
import entidad.InfoUniveridades;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
public class InfoUniveridadesJpaController implements Serializable {

    public InfoUniveridadesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(InfoUniveridades infoUniveridades) throws PreexistingEntityException, Exception {
        if (infoUniveridades.getSedesList() == null) {
            infoUniveridades.setSedesList(new ArrayList<Sedes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Sedes> attachedSedesList = new ArrayList<Sedes>();
            for (Sedes sedesListSedesToAttach : infoUniveridades.getSedesList()) {
                sedesListSedesToAttach = em.getReference(sedesListSedesToAttach.getClass(), sedesListSedesToAttach.getId());
                attachedSedesList.add(sedesListSedesToAttach);
            }
            infoUniveridades.setSedesList(attachedSedesList);
            em.persist(infoUniveridades);
            for (Sedes sedesListSedes : infoUniveridades.getSedesList()) {
                InfoUniveridades oldFkIdUniversidadOfSedesListSedes = sedesListSedes.getFkIdUniversidad();
                sedesListSedes.setFkIdUniversidad(infoUniveridades);
                sedesListSedes = em.merge(sedesListSedes);
                if (oldFkIdUniversidadOfSedesListSedes != null) {
                    oldFkIdUniversidadOfSedesListSedes.getSedesList().remove(sedesListSedes);
                    oldFkIdUniversidadOfSedesListSedes = em.merge(oldFkIdUniversidadOfSedesListSedes);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findInfoUniveridades(infoUniveridades.getId()) != null) {
                throw new PreexistingEntityException("InfoUniveridades " + infoUniveridades + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(InfoUniveridades infoUniveridades) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            InfoUniveridades persistentInfoUniveridades = em.find(InfoUniveridades.class, infoUniveridades.getId());
            List<Sedes> sedesListOld = persistentInfoUniveridades.getSedesList();
            List<Sedes> sedesListNew = infoUniveridades.getSedesList();
            List<Sedes> attachedSedesListNew = new ArrayList<Sedes>();
            for (Sedes sedesListNewSedesToAttach : sedesListNew) {
                sedesListNewSedesToAttach = em.getReference(sedesListNewSedesToAttach.getClass(), sedesListNewSedesToAttach.getId());
                attachedSedesListNew.add(sedesListNewSedesToAttach);
            }
            sedesListNew = attachedSedesListNew;
            infoUniveridades.setSedesList(sedesListNew);
            infoUniveridades = em.merge(infoUniveridades);
            for (Sedes sedesListOldSedes : sedesListOld) {
                if (!sedesListNew.contains(sedesListOldSedes)) {
                    sedesListOldSedes.setFkIdUniversidad(null);
                    sedesListOldSedes = em.merge(sedesListOldSedes);
                }
            }
            for (Sedes sedesListNewSedes : sedesListNew) {
                if (!sedesListOld.contains(sedesListNewSedes)) {
                    InfoUniveridades oldFkIdUniversidadOfSedesListNewSedes = sedesListNewSedes.getFkIdUniversidad();
                    sedesListNewSedes.setFkIdUniversidad(infoUniveridades);
                    sedesListNewSedes = em.merge(sedesListNewSedes);
                    if (oldFkIdUniversidadOfSedesListNewSedes != null && !oldFkIdUniversidadOfSedesListNewSedes.equals(infoUniveridades)) {
                        oldFkIdUniversidadOfSedesListNewSedes.getSedesList().remove(sedesListNewSedes);
                        oldFkIdUniversidadOfSedesListNewSedes = em.merge(oldFkIdUniversidadOfSedesListNewSedes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = infoUniveridades.getId();
                if (findInfoUniveridades(id) == null) {
                    throw new NonexistentEntityException("The infoUniveridades with id " + id + " no longer exists.");
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
            InfoUniveridades infoUniveridades;
            try {
                infoUniveridades = em.getReference(InfoUniveridades.class, id);
                infoUniveridades.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The infoUniveridades with id " + id + " no longer exists.", enfe);
            }
            List<Sedes> sedesList = infoUniveridades.getSedesList();
            for (Sedes sedesListSedes : sedesList) {
                sedesListSedes.setFkIdUniversidad(null);
                sedesListSedes = em.merge(sedesListSedes);
            }
            em.remove(infoUniveridades);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<InfoUniveridades> findInfoUniveridadesEntities() {
        return findInfoUniveridadesEntities(true, -1, -1);
    }

    public List<InfoUniveridades> findInfoUniveridadesEntities(int maxResults, int firstResult) {
        return findInfoUniveridadesEntities(false, maxResults, firstResult);
    }

    private List<InfoUniveridades> findInfoUniveridadesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(InfoUniveridades.class));
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

    public InfoUniveridades findInfoUniveridades(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(InfoUniveridades.class, id);
        } finally {
            em.close();
        }
    }

    public int getInfoUniveridadesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<InfoUniveridades> rt = cq.from(InfoUniveridades.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
