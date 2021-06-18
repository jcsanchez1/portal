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
import entidad.TiposDeUsuario;
import entidad.Maestros;
import java.util.ArrayList;
import java.util.List;
import entidad.PersonalRegisto;
import entidad.Alumnos;
import entidad.Usuarios;
import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author juanc
 */
public class UsuariosJpaController implements Serializable {

    public UsuariosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuarios usuarios) throws PreexistingEntityException, Exception {
        if (usuarios.getMaestrosList() == null) {
            usuarios.setMaestrosList(new ArrayList<Maestros>());
        }
        if (usuarios.getPersonalRegistoList() == null) {
            usuarios.setPersonalRegistoList(new ArrayList<PersonalRegisto>());
        }
        if (usuarios.getAlumnosList() == null) {
            usuarios.setAlumnosList(new ArrayList<Alumnos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TiposDeUsuario fkTipoUsuario = usuarios.getFkTipoUsuario();
            if (fkTipoUsuario != null) {
                fkTipoUsuario = em.getReference(fkTipoUsuario.getClass(), fkTipoUsuario.getTipoUsuario());
                usuarios.setFkTipoUsuario(fkTipoUsuario);
            }
            List<Maestros> attachedMaestrosList = new ArrayList<Maestros>();
            for (Maestros maestrosListMaestrosToAttach : usuarios.getMaestrosList()) {
                maestrosListMaestrosToAttach = em.getReference(maestrosListMaestrosToAttach.getClass(), maestrosListMaestrosToAttach.getId());
                attachedMaestrosList.add(maestrosListMaestrosToAttach);
            }
            usuarios.setMaestrosList(attachedMaestrosList);
            List<PersonalRegisto> attachedPersonalRegistoList = new ArrayList<PersonalRegisto>();
            for (PersonalRegisto personalRegistoListPersonalRegistoToAttach : usuarios.getPersonalRegistoList()) {
                personalRegistoListPersonalRegistoToAttach = em.getReference(personalRegistoListPersonalRegistoToAttach.getClass(), personalRegistoListPersonalRegistoToAttach.getId());
                attachedPersonalRegistoList.add(personalRegistoListPersonalRegistoToAttach);
            }
            usuarios.setPersonalRegistoList(attachedPersonalRegistoList);
            List<Alumnos> attachedAlumnosList = new ArrayList<Alumnos>();
            for (Alumnos alumnosListAlumnosToAttach : usuarios.getAlumnosList()) {
                alumnosListAlumnosToAttach = em.getReference(alumnosListAlumnosToAttach.getClass(), alumnosListAlumnosToAttach.getId());
                attachedAlumnosList.add(alumnosListAlumnosToAttach);
            }
            usuarios.setAlumnosList(attachedAlumnosList);
            em.persist(usuarios);
            if (fkTipoUsuario != null) {
                fkTipoUsuario.getUsuariosList().add(usuarios);
                fkTipoUsuario = em.merge(fkTipoUsuario);
            }
            for (Maestros maestrosListMaestros : usuarios.getMaestrosList()) {
                Usuarios oldFkUsuarioOfMaestrosListMaestros = maestrosListMaestros.getFkUsuario();
                maestrosListMaestros.setFkUsuario(usuarios);
                maestrosListMaestros = em.merge(maestrosListMaestros);
                if (oldFkUsuarioOfMaestrosListMaestros != null) {
                    oldFkUsuarioOfMaestrosListMaestros.getMaestrosList().remove(maestrosListMaestros);
                    oldFkUsuarioOfMaestrosListMaestros = em.merge(oldFkUsuarioOfMaestrosListMaestros);
                }
            }
            for (PersonalRegisto personalRegistoListPersonalRegisto : usuarios.getPersonalRegistoList()) {
                Usuarios oldFkUsuarioOfPersonalRegistoListPersonalRegisto = personalRegistoListPersonalRegisto.getFkUsuario();
                personalRegistoListPersonalRegisto.setFkUsuario(usuarios);
                personalRegistoListPersonalRegisto = em.merge(personalRegistoListPersonalRegisto);
                if (oldFkUsuarioOfPersonalRegistoListPersonalRegisto != null) {
                    oldFkUsuarioOfPersonalRegistoListPersonalRegisto.getPersonalRegistoList().remove(personalRegistoListPersonalRegisto);
                    oldFkUsuarioOfPersonalRegistoListPersonalRegisto = em.merge(oldFkUsuarioOfPersonalRegistoListPersonalRegisto);
                }
            }
            for (Alumnos alumnosListAlumnos : usuarios.getAlumnosList()) {
                Usuarios oldFkUsuarioOfAlumnosListAlumnos = alumnosListAlumnos.getFkUsuario();
                alumnosListAlumnos.setFkUsuario(usuarios);
                alumnosListAlumnos = em.merge(alumnosListAlumnos);
                if (oldFkUsuarioOfAlumnosListAlumnos != null) {
                    oldFkUsuarioOfAlumnosListAlumnos.getAlumnosList().remove(alumnosListAlumnos);
                    oldFkUsuarioOfAlumnosListAlumnos = em.merge(oldFkUsuarioOfAlumnosListAlumnos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuarios(usuarios.getId()) != null) {
                throw new PreexistingEntityException("Usuarios " + usuarios + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuarios usuarios) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios persistentUsuarios = em.find(Usuarios.class, usuarios.getId());
            TiposDeUsuario fkTipoUsuarioOld = persistentUsuarios.getFkTipoUsuario();
            TiposDeUsuario fkTipoUsuarioNew = usuarios.getFkTipoUsuario();
            List<Maestros> maestrosListOld = persistentUsuarios.getMaestrosList();
            List<Maestros> maestrosListNew = usuarios.getMaestrosList();
            List<PersonalRegisto> personalRegistoListOld = persistentUsuarios.getPersonalRegistoList();
            List<PersonalRegisto> personalRegistoListNew = usuarios.getPersonalRegistoList();
            List<Alumnos> alumnosListOld = persistentUsuarios.getAlumnosList();
            List<Alumnos> alumnosListNew = usuarios.getAlumnosList();
            if (fkTipoUsuarioNew != null) {
                fkTipoUsuarioNew = em.getReference(fkTipoUsuarioNew.getClass(), fkTipoUsuarioNew.getTipoUsuario());
                usuarios.setFkTipoUsuario(fkTipoUsuarioNew);
            }
            List<Maestros> attachedMaestrosListNew = new ArrayList<Maestros>();
            for (Maestros maestrosListNewMaestrosToAttach : maestrosListNew) {
                maestrosListNewMaestrosToAttach = em.getReference(maestrosListNewMaestrosToAttach.getClass(), maestrosListNewMaestrosToAttach.getId());
                attachedMaestrosListNew.add(maestrosListNewMaestrosToAttach);
            }
            maestrosListNew = attachedMaestrosListNew;
            usuarios.setMaestrosList(maestrosListNew);
            List<PersonalRegisto> attachedPersonalRegistoListNew = new ArrayList<PersonalRegisto>();
            for (PersonalRegisto personalRegistoListNewPersonalRegistoToAttach : personalRegistoListNew) {
                personalRegistoListNewPersonalRegistoToAttach = em.getReference(personalRegistoListNewPersonalRegistoToAttach.getClass(), personalRegistoListNewPersonalRegistoToAttach.getId());
                attachedPersonalRegistoListNew.add(personalRegistoListNewPersonalRegistoToAttach);
            }
            personalRegistoListNew = attachedPersonalRegistoListNew;
            usuarios.setPersonalRegistoList(personalRegistoListNew);
            List<Alumnos> attachedAlumnosListNew = new ArrayList<Alumnos>();
            for (Alumnos alumnosListNewAlumnosToAttach : alumnosListNew) {
                alumnosListNewAlumnosToAttach = em.getReference(alumnosListNewAlumnosToAttach.getClass(), alumnosListNewAlumnosToAttach.getId());
                attachedAlumnosListNew.add(alumnosListNewAlumnosToAttach);
            }
            alumnosListNew = attachedAlumnosListNew;
            usuarios.setAlumnosList(alumnosListNew);
            usuarios = em.merge(usuarios);
            if (fkTipoUsuarioOld != null && !fkTipoUsuarioOld.equals(fkTipoUsuarioNew)) {
                fkTipoUsuarioOld.getUsuariosList().remove(usuarios);
                fkTipoUsuarioOld = em.merge(fkTipoUsuarioOld);
            }
            if (fkTipoUsuarioNew != null && !fkTipoUsuarioNew.equals(fkTipoUsuarioOld)) {
                fkTipoUsuarioNew.getUsuariosList().add(usuarios);
                fkTipoUsuarioNew = em.merge(fkTipoUsuarioNew);
            }
            for (Maestros maestrosListOldMaestros : maestrosListOld) {
                if (!maestrosListNew.contains(maestrosListOldMaestros)) {
                    maestrosListOldMaestros.setFkUsuario(null);
                    maestrosListOldMaestros = em.merge(maestrosListOldMaestros);
                }
            }
            for (Maestros maestrosListNewMaestros : maestrosListNew) {
                if (!maestrosListOld.contains(maestrosListNewMaestros)) {
                    Usuarios oldFkUsuarioOfMaestrosListNewMaestros = maestrosListNewMaestros.getFkUsuario();
                    maestrosListNewMaestros.setFkUsuario(usuarios);
                    maestrosListNewMaestros = em.merge(maestrosListNewMaestros);
                    if (oldFkUsuarioOfMaestrosListNewMaestros != null && !oldFkUsuarioOfMaestrosListNewMaestros.equals(usuarios)) {
                        oldFkUsuarioOfMaestrosListNewMaestros.getMaestrosList().remove(maestrosListNewMaestros);
                        oldFkUsuarioOfMaestrosListNewMaestros = em.merge(oldFkUsuarioOfMaestrosListNewMaestros);
                    }
                }
            }
            for (PersonalRegisto personalRegistoListOldPersonalRegisto : personalRegistoListOld) {
                if (!personalRegistoListNew.contains(personalRegistoListOldPersonalRegisto)) {
                    personalRegistoListOldPersonalRegisto.setFkUsuario(null);
                    personalRegistoListOldPersonalRegisto = em.merge(personalRegistoListOldPersonalRegisto);
                }
            }
            for (PersonalRegisto personalRegistoListNewPersonalRegisto : personalRegistoListNew) {
                if (!personalRegistoListOld.contains(personalRegistoListNewPersonalRegisto)) {
                    Usuarios oldFkUsuarioOfPersonalRegistoListNewPersonalRegisto = personalRegistoListNewPersonalRegisto.getFkUsuario();
                    personalRegistoListNewPersonalRegisto.setFkUsuario(usuarios);
                    personalRegistoListNewPersonalRegisto = em.merge(personalRegistoListNewPersonalRegisto);
                    if (oldFkUsuarioOfPersonalRegistoListNewPersonalRegisto != null && !oldFkUsuarioOfPersonalRegistoListNewPersonalRegisto.equals(usuarios)) {
                        oldFkUsuarioOfPersonalRegistoListNewPersonalRegisto.getPersonalRegistoList().remove(personalRegistoListNewPersonalRegisto);
                        oldFkUsuarioOfPersonalRegistoListNewPersonalRegisto = em.merge(oldFkUsuarioOfPersonalRegistoListNewPersonalRegisto);
                    }
                }
            }
            for (Alumnos alumnosListOldAlumnos : alumnosListOld) {
                if (!alumnosListNew.contains(alumnosListOldAlumnos)) {
                    alumnosListOldAlumnos.setFkUsuario(null);
                    alumnosListOldAlumnos = em.merge(alumnosListOldAlumnos);
                }
            }
            for (Alumnos alumnosListNewAlumnos : alumnosListNew) {
                if (!alumnosListOld.contains(alumnosListNewAlumnos)) {
                    Usuarios oldFkUsuarioOfAlumnosListNewAlumnos = alumnosListNewAlumnos.getFkUsuario();
                    alumnosListNewAlumnos.setFkUsuario(usuarios);
                    alumnosListNewAlumnos = em.merge(alumnosListNewAlumnos);
                    if (oldFkUsuarioOfAlumnosListNewAlumnos != null && !oldFkUsuarioOfAlumnosListNewAlumnos.equals(usuarios)) {
                        oldFkUsuarioOfAlumnosListNewAlumnos.getAlumnosList().remove(alumnosListNewAlumnos);
                        oldFkUsuarioOfAlumnosListNewAlumnos = em.merge(oldFkUsuarioOfAlumnosListNewAlumnos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = usuarios.getId();
                if (findUsuarios(id) == null) {
                    throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.");
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
            Usuarios usuarios;
            try {
                usuarios = em.getReference(Usuarios.class, id);
                usuarios.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.", enfe);
            }
            TiposDeUsuario fkTipoUsuario = usuarios.getFkTipoUsuario();
            if (fkTipoUsuario != null) {
                fkTipoUsuario.getUsuariosList().remove(usuarios);
                fkTipoUsuario = em.merge(fkTipoUsuario);
            }
            List<Maestros> maestrosList = usuarios.getMaestrosList();
            for (Maestros maestrosListMaestros : maestrosList) {
                maestrosListMaestros.setFkUsuario(null);
                maestrosListMaestros = em.merge(maestrosListMaestros);
            }
            List<PersonalRegisto> personalRegistoList = usuarios.getPersonalRegistoList();
            for (PersonalRegisto personalRegistoListPersonalRegisto : personalRegistoList) {
                personalRegistoListPersonalRegisto.setFkUsuario(null);
                personalRegistoListPersonalRegisto = em.merge(personalRegistoListPersonalRegisto);
            }
            List<Alumnos> alumnosList = usuarios.getAlumnosList();
            for (Alumnos alumnosListAlumnos : alumnosList) {
                alumnosListAlumnos.setFkUsuario(null);
                alumnosListAlumnos = em.merge(alumnosListAlumnos);
            }
            em.remove(usuarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuarios> findUsuariosEntities() {
        return findUsuariosEntities(true, -1, -1);
    }

    public List<Usuarios> findUsuariosEntities(int maxResults, int firstResult) {
        return findUsuariosEntities(false, maxResults, firstResult);
    }

    private List<Usuarios> findUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuarios.class));
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

    public Usuarios findUsuarios(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuarios> rt = cq.from(Usuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
