/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.IllegalOrphanException;
import controladores.exceptions.NonexistentEntityException;
import controladores.exceptions.PreexistingEntityException;
import entidad.TiposDeUsuario;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidad.Usuarios;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author juanc
 */
public class TiposDeUsuarioJpaController implements Serializable {

    public TiposDeUsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TiposDeUsuario tiposDeUsuario) throws PreexistingEntityException, Exception {
        if (tiposDeUsuario.getUsuariosList() == null) {
            tiposDeUsuario.setUsuariosList(new ArrayList<Usuarios>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Usuarios> attachedUsuariosList = new ArrayList<Usuarios>();
            for (Usuarios usuariosListUsuariosToAttach : tiposDeUsuario.getUsuariosList()) {
                usuariosListUsuariosToAttach = em.getReference(usuariosListUsuariosToAttach.getClass(), usuariosListUsuariosToAttach.getId());
                attachedUsuariosList.add(usuariosListUsuariosToAttach);
            }
            tiposDeUsuario.setUsuariosList(attachedUsuariosList);
            em.persist(tiposDeUsuario);
            for (Usuarios usuariosListUsuarios : tiposDeUsuario.getUsuariosList()) {
                TiposDeUsuario oldFkTipoUsuarioOfUsuariosListUsuarios = usuariosListUsuarios.getFkTipoUsuario();
                usuariosListUsuarios.setFkTipoUsuario(tiposDeUsuario);
                usuariosListUsuarios = em.merge(usuariosListUsuarios);
                if (oldFkTipoUsuarioOfUsuariosListUsuarios != null) {
                    oldFkTipoUsuarioOfUsuariosListUsuarios.getUsuariosList().remove(usuariosListUsuarios);
                    oldFkTipoUsuarioOfUsuariosListUsuarios = em.merge(oldFkTipoUsuarioOfUsuariosListUsuarios);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTiposDeUsuario(tiposDeUsuario.getTipoUsuario()) != null) {
                throw new PreexistingEntityException("TiposDeUsuario " + tiposDeUsuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TiposDeUsuario tiposDeUsuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TiposDeUsuario persistentTiposDeUsuario = em.find(TiposDeUsuario.class, tiposDeUsuario.getTipoUsuario());
            List<Usuarios> usuariosListOld = persistentTiposDeUsuario.getUsuariosList();
            List<Usuarios> usuariosListNew = tiposDeUsuario.getUsuariosList();
            List<String> illegalOrphanMessages = null;
            for (Usuarios usuariosListOldUsuarios : usuariosListOld) {
                if (!usuariosListNew.contains(usuariosListOldUsuarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuarios " + usuariosListOldUsuarios + " since its fkTipoUsuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Usuarios> attachedUsuariosListNew = new ArrayList<Usuarios>();
            for (Usuarios usuariosListNewUsuariosToAttach : usuariosListNew) {
                usuariosListNewUsuariosToAttach = em.getReference(usuariosListNewUsuariosToAttach.getClass(), usuariosListNewUsuariosToAttach.getId());
                attachedUsuariosListNew.add(usuariosListNewUsuariosToAttach);
            }
            usuariosListNew = attachedUsuariosListNew;
            tiposDeUsuario.setUsuariosList(usuariosListNew);
            tiposDeUsuario = em.merge(tiposDeUsuario);
            for (Usuarios usuariosListNewUsuarios : usuariosListNew) {
                if (!usuariosListOld.contains(usuariosListNewUsuarios)) {
                    TiposDeUsuario oldFkTipoUsuarioOfUsuariosListNewUsuarios = usuariosListNewUsuarios.getFkTipoUsuario();
                    usuariosListNewUsuarios.setFkTipoUsuario(tiposDeUsuario);
                    usuariosListNewUsuarios = em.merge(usuariosListNewUsuarios);
                    if (oldFkTipoUsuarioOfUsuariosListNewUsuarios != null && !oldFkTipoUsuarioOfUsuariosListNewUsuarios.equals(tiposDeUsuario)) {
                        oldFkTipoUsuarioOfUsuariosListNewUsuarios.getUsuariosList().remove(usuariosListNewUsuarios);
                        oldFkTipoUsuarioOfUsuariosListNewUsuarios = em.merge(oldFkTipoUsuarioOfUsuariosListNewUsuarios);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = tiposDeUsuario.getTipoUsuario();
                if (findTiposDeUsuario(id) == null) {
                    throw new NonexistentEntityException("The tiposDeUsuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TiposDeUsuario tiposDeUsuario;
            try {
                tiposDeUsuario = em.getReference(TiposDeUsuario.class, id);
                tiposDeUsuario.getTipoUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tiposDeUsuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Usuarios> usuariosListOrphanCheck = tiposDeUsuario.getUsuariosList();
            for (Usuarios usuariosListOrphanCheckUsuarios : usuariosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TiposDeUsuario (" + tiposDeUsuario + ") cannot be destroyed since the Usuarios " + usuariosListOrphanCheckUsuarios + " in its usuariosList field has a non-nullable fkTipoUsuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tiposDeUsuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TiposDeUsuario> findTiposDeUsuarioEntities() {
        return findTiposDeUsuarioEntities(true, -1, -1);
    }

    public List<TiposDeUsuario> findTiposDeUsuarioEntities(int maxResults, int firstResult) {
        return findTiposDeUsuarioEntities(false, maxResults, firstResult);
    }

    private List<TiposDeUsuario> findTiposDeUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TiposDeUsuario.class));
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

    public TiposDeUsuario findTiposDeUsuario(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TiposDeUsuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getTiposDeUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TiposDeUsuario> rt = cq.from(TiposDeUsuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
