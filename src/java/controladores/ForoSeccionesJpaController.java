/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import controladores.exceptions.PreexistingEntityException;
import entidad.ForoSecciones;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidad.Maestros;
import entidad.Secciones;
import entidad.RespuestasForos;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author juanc
 */
public class ForoSeccionesJpaController implements Serializable {

    public ForoSeccionesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ForoSecciones foroSecciones) throws PreexistingEntityException, Exception {
        if (foroSecciones.getRespuestasForosList() == null) {
            foroSecciones.setRespuestasForosList(new ArrayList<RespuestasForos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Maestros fkMaestro = foroSecciones.getFkMaestro();
            if (fkMaestro != null) {
                fkMaestro = em.getReference(fkMaestro.getClass(), fkMaestro.getId());
                foroSecciones.setFkMaestro(fkMaestro);
            }
            Secciones fkSeccion = foroSecciones.getFkSeccion();
            if (fkSeccion != null) {
                fkSeccion = em.getReference(fkSeccion.getClass(), fkSeccion.getId());
                foroSecciones.setFkSeccion(fkSeccion);
            }
            List<RespuestasForos> attachedRespuestasForosList = new ArrayList<RespuestasForos>();
            for (RespuestasForos respuestasForosListRespuestasForosToAttach : foroSecciones.getRespuestasForosList()) {
                respuestasForosListRespuestasForosToAttach = em.getReference(respuestasForosListRespuestasForosToAttach.getClass(), respuestasForosListRespuestasForosToAttach.getId());
                attachedRespuestasForosList.add(respuestasForosListRespuestasForosToAttach);
            }
            foroSecciones.setRespuestasForosList(attachedRespuestasForosList);
            em.persist(foroSecciones);
            if (fkMaestro != null) {
                fkMaestro.getForoSeccionesList().add(foroSecciones);
                fkMaestro = em.merge(fkMaestro);
            }
            if (fkSeccion != null) {
                fkSeccion.getForoSeccionesList().add(foroSecciones);
                fkSeccion = em.merge(fkSeccion);
            }
            for (RespuestasForos respuestasForosListRespuestasForos : foroSecciones.getRespuestasForosList()) {
                ForoSecciones oldFkForoOfRespuestasForosListRespuestasForos = respuestasForosListRespuestasForos.getFkForo();
                respuestasForosListRespuestasForos.setFkForo(foroSecciones);
                respuestasForosListRespuestasForos = em.merge(respuestasForosListRespuestasForos);
                if (oldFkForoOfRespuestasForosListRespuestasForos != null) {
                    oldFkForoOfRespuestasForosListRespuestasForos.getRespuestasForosList().remove(respuestasForosListRespuestasForos);
                    oldFkForoOfRespuestasForosListRespuestasForos = em.merge(oldFkForoOfRespuestasForosListRespuestasForos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findForoSecciones(foroSecciones.getId()) != null) {
                throw new PreexistingEntityException("ForoSecciones " + foroSecciones + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ForoSecciones foroSecciones) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ForoSecciones persistentForoSecciones = em.find(ForoSecciones.class, foroSecciones.getId());
            Maestros fkMaestroOld = persistentForoSecciones.getFkMaestro();
            Maestros fkMaestroNew = foroSecciones.getFkMaestro();
            Secciones fkSeccionOld = persistentForoSecciones.getFkSeccion();
            Secciones fkSeccionNew = foroSecciones.getFkSeccion();
            List<RespuestasForos> respuestasForosListOld = persistentForoSecciones.getRespuestasForosList();
            List<RespuestasForos> respuestasForosListNew = foroSecciones.getRespuestasForosList();
            if (fkMaestroNew != null) {
                fkMaestroNew = em.getReference(fkMaestroNew.getClass(), fkMaestroNew.getId());
                foroSecciones.setFkMaestro(fkMaestroNew);
            }
            if (fkSeccionNew != null) {
                fkSeccionNew = em.getReference(fkSeccionNew.getClass(), fkSeccionNew.getId());
                foroSecciones.setFkSeccion(fkSeccionNew);
            }
            List<RespuestasForos> attachedRespuestasForosListNew = new ArrayList<RespuestasForos>();
            for (RespuestasForos respuestasForosListNewRespuestasForosToAttach : respuestasForosListNew) {
                respuestasForosListNewRespuestasForosToAttach = em.getReference(respuestasForosListNewRespuestasForosToAttach.getClass(), respuestasForosListNewRespuestasForosToAttach.getId());
                attachedRespuestasForosListNew.add(respuestasForosListNewRespuestasForosToAttach);
            }
            respuestasForosListNew = attachedRespuestasForosListNew;
            foroSecciones.setRespuestasForosList(respuestasForosListNew);
            foroSecciones = em.merge(foroSecciones);
            if (fkMaestroOld != null && !fkMaestroOld.equals(fkMaestroNew)) {
                fkMaestroOld.getForoSeccionesList().remove(foroSecciones);
                fkMaestroOld = em.merge(fkMaestroOld);
            }
            if (fkMaestroNew != null && !fkMaestroNew.equals(fkMaestroOld)) {
                fkMaestroNew.getForoSeccionesList().add(foroSecciones);
                fkMaestroNew = em.merge(fkMaestroNew);
            }
            if (fkSeccionOld != null && !fkSeccionOld.equals(fkSeccionNew)) {
                fkSeccionOld.getForoSeccionesList().remove(foroSecciones);
                fkSeccionOld = em.merge(fkSeccionOld);
            }
            if (fkSeccionNew != null && !fkSeccionNew.equals(fkSeccionOld)) {
                fkSeccionNew.getForoSeccionesList().add(foroSecciones);
                fkSeccionNew = em.merge(fkSeccionNew);
            }
            for (RespuestasForos respuestasForosListOldRespuestasForos : respuestasForosListOld) {
                if (!respuestasForosListNew.contains(respuestasForosListOldRespuestasForos)) {
                    respuestasForosListOldRespuestasForos.setFkForo(null);
                    respuestasForosListOldRespuestasForos = em.merge(respuestasForosListOldRespuestasForos);
                }
            }
            for (RespuestasForos respuestasForosListNewRespuestasForos : respuestasForosListNew) {
                if (!respuestasForosListOld.contains(respuestasForosListNewRespuestasForos)) {
                    ForoSecciones oldFkForoOfRespuestasForosListNewRespuestasForos = respuestasForosListNewRespuestasForos.getFkForo();
                    respuestasForosListNewRespuestasForos.setFkForo(foroSecciones);
                    respuestasForosListNewRespuestasForos = em.merge(respuestasForosListNewRespuestasForos);
                    if (oldFkForoOfRespuestasForosListNewRespuestasForos != null && !oldFkForoOfRespuestasForosListNewRespuestasForos.equals(foroSecciones)) {
                        oldFkForoOfRespuestasForosListNewRespuestasForos.getRespuestasForosList().remove(respuestasForosListNewRespuestasForos);
                        oldFkForoOfRespuestasForosListNewRespuestasForos = em.merge(oldFkForoOfRespuestasForosListNewRespuestasForos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = foroSecciones.getId();
                if (findForoSecciones(id) == null) {
                    throw new NonexistentEntityException("The foroSecciones with id " + id + " no longer exists.");
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
            ForoSecciones foroSecciones;
            try {
                foroSecciones = em.getReference(ForoSecciones.class, id);
                foroSecciones.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The foroSecciones with id " + id + " no longer exists.", enfe);
            }
            Maestros fkMaestro = foroSecciones.getFkMaestro();
            if (fkMaestro != null) {
                fkMaestro.getForoSeccionesList().remove(foroSecciones);
                fkMaestro = em.merge(fkMaestro);
            }
            Secciones fkSeccion = foroSecciones.getFkSeccion();
            if (fkSeccion != null) {
                fkSeccion.getForoSeccionesList().remove(foroSecciones);
                fkSeccion = em.merge(fkSeccion);
            }
            List<RespuestasForos> respuestasForosList = foroSecciones.getRespuestasForosList();
            for (RespuestasForos respuestasForosListRespuestasForos : respuestasForosList) {
                respuestasForosListRespuestasForos.setFkForo(null);
                respuestasForosListRespuestasForos = em.merge(respuestasForosListRespuestasForos);
            }
            em.remove(foroSecciones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ForoSecciones> findForoSeccionesEntities() {
        return findForoSeccionesEntities(true, -1, -1);
    }

    public List<ForoSecciones> findForoSeccionesEntities(int maxResults, int firstResult) {
        return findForoSeccionesEntities(false, maxResults, firstResult);
    }

    private List<ForoSecciones> findForoSeccionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ForoSecciones.class));
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

    public ForoSecciones findForoSecciones(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ForoSecciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getForoSeccionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ForoSecciones> rt = cq.from(ForoSecciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
