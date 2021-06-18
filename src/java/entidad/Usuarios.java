/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidad;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author juanc
 */
@Entity
@Table(name = "USUARIOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u")
    , @NamedQuery(name = "Usuarios.findByCorreo", query = "SELECT u FROM Usuarios u WHERE u.correo = :correo")
    , @NamedQuery(name = "Usuarios.findByPassword", query = "SELECT u FROM Usuarios u WHERE u.password = :password")
    , @NamedQuery(name = "Usuarios.findByEstado", query = "SELECT u FROM Usuarios u WHERE u.estado = :estado")
    , @NamedQuery(name = "Usuarios.findById", query = "SELECT u FROM Usuarios u WHERE u.id = :id")})
public class Usuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "CORREO")
    private String correo;
    @Basic(optional = false)
    @Column(name = "PASSWORD")
    private String password;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private Character estado;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @OneToMany(mappedBy = "fkUsuario")
    private List<Maestros> maestrosList;
    @OneToMany(mappedBy = "fkUsuario")
    private List<PersonalRegisto> personalRegistoList;
    @JoinColumn(name = "FK_TIPO_USUARIO", referencedColumnName = "TIPO_USUARIO")
    @ManyToOne(optional = false)
    private TiposDeUsuario fkTipoUsuario;
    @OneToMany(mappedBy = "fkUsuario")
    private List<Alumnos> alumnosList;

    public Usuarios() {
    }

    public Usuarios(BigDecimal id) {
        this.id = id;
    }

    public Usuarios(BigDecimal id, String correo, String password, Character estado) {
        this.id = id;
        this.correo = correo;
        this.password = password;
        this.estado = estado;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    @XmlTransient
    public List<Maestros> getMaestrosList() {
        return maestrosList;
    }

    public void setMaestrosList(List<Maestros> maestrosList) {
        this.maestrosList = maestrosList;
    }

    @XmlTransient
    public List<PersonalRegisto> getPersonalRegistoList() {
        return personalRegistoList;
    }

    public void setPersonalRegistoList(List<PersonalRegisto> personalRegistoList) {
        this.personalRegistoList = personalRegistoList;
    }

    public TiposDeUsuario getFkTipoUsuario() {
        return fkTipoUsuario;
    }

    public void setFkTipoUsuario(TiposDeUsuario fkTipoUsuario) {
        this.fkTipoUsuario = fkTipoUsuario;
    }

    @XmlTransient
    public List<Alumnos> getAlumnosList() {
        return alumnosList;
    }

    public void setAlumnosList(List<Alumnos> alumnosList) {
        this.alumnosList = alumnosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.Usuarios[ id=" + id + " ]";
    }
    
}
