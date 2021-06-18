/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import controladores.exceptions.PreexistingEntityException;
import entidad.PersonalRegisto;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidad.Usuarios;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author juanc
 */
public class PersonalRegistoJpaController implements Serializable {

    public PersonalRegistoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PersonalRegisto personalRegisto) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios fkUsuario = personalRegisto.getFkUsuario();
            if (fkUsuario != null) {
                fkUsuario = em.getReference(fkUsuario.getClass(), fkUsuario.getId());
                personalRegisto.setFkUsuario(fkUsuario);
            }
            em.persist(personalRegisto);
            if (fkUsuario != null) {
                fkUsuario.getPersonalRegistoList().add(personalRegisto);
                fkUsuario = em.merge(fkUsuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPersonalRegisto(personalRegisto.getId()) != null) {
                throw new PreexistingEntityException("PersonalRegisto " + personalRegisto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PersonalRegisto personalRegisto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PersonalRegisto persistentPersonalRegisto = em.find(PersonalRegisto.class, personalRegisto.getId());
            Usuarios fkUsuarioOld = persistentPersonalRegisto.getFkUsuario();
            Usuarios fkUsuarioNew = personalRegisto.getFkUsuario();
            if (fkUsuarioNew != null) {
                fkUsuarioNew = em.getReference(fkUsuarioNew.getClass(), fkUsuarioNew.getId());
                personalRegisto.setFkUsuario(fkUsuarioNew);
            }
            personalRegisto = em.merge(personalRegisto);
            if (fkUsuarioOld != null && !fkUsuarioOld.equals(fkUsuarioNew)) {
                fkUsuarioOld.getPersonalRegistoList().remove(personalRegisto);
                fkUsuarioOld = em.merge(fkUsuarioOld);
            }
            if (fkUsuarioNew != null && !fkUsuarioNew.equals(fkUsuarioOld)) {
                fkUsuarioNew.getPersonalRegistoList().add(personalRegisto);
                fkUsuarioNew = em.merge(fkUsuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = personalRegisto.getId();
                if (findPersonalRegisto(id) == null) {
                    throw new NonexistentEntityException("The personalRegisto with id " + id + " no longer exists.");
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
            PersonalRegisto personalRegisto;
            try {
                personalRegisto = em.getReference(PersonalRegisto.class, id);
                personalRegisto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The personalRegisto with id " + id + " no longer exists.", enfe);
            }
            Usuarios fkUsuario = personalRegisto.getFkUsuario();
            if (fkUsuario != null) {
                fkUsuario.getPersonalRegistoList().remove(personalRegisto);
                fkUsuario = em.merge(fkUsuario);
            }
            em.remove(personalRegisto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PersonalRegisto> findPersonalRegistoEntities() {
        return findPersonalRegistoEntities(true, -1, -1);
    }

    public List<PersonalRegisto> findPersonalRegistoEntities(int maxResults, int firstResult) {
        return findPersonalRegistoEntities(false, maxResults, firstResult);
    }

    private List<PersonalRegisto> findPersonalRegistoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PersonalRegisto.class));
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

    public PersonalRegisto findPersonalRegisto(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PersonalRegisto.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonalRegistoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PersonalRegisto> rt = cq.from(PersonalRegisto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
