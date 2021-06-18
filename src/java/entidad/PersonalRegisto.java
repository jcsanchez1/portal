/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidad;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juanc
 */
@Entity
@Table(name = "PERSONAL_REGISTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PersonalRegisto.findAll", query = "SELECT p FROM PersonalRegisto p")
    , @NamedQuery(name = "PersonalRegisto.findById", query = "SELECT p FROM PersonalRegisto p WHERE p.id = :id")
    , @NamedQuery(name = "PersonalRegisto.findByIdentidad", query = "SELECT p FROM PersonalRegisto p WHERE p.identidad = :identidad")
    , @NamedQuery(name = "PersonalRegisto.findByNombres", query = "SELECT p FROM PersonalRegisto p WHERE p.nombres = :nombres")
    , @NamedQuery(name = "PersonalRegisto.findByApellidos", query = "SELECT p FROM PersonalRegisto p WHERE p.apellidos = :apellidos")
    , @NamedQuery(name = "PersonalRegisto.findByTelefono", query = "SELECT p FROM PersonalRegisto p WHERE p.telefono = :telefono")
    , @NamedQuery(name = "PersonalRegisto.findByDireccion", query = "SELECT p FROM PersonalRegisto p WHERE p.direccion = :direccion")
    , @NamedQuery(name = "PersonalRegisto.findByFechaNacimiento", query = "SELECT p FROM PersonalRegisto p WHERE p.fechaNacimiento = :fechaNacimiento")
    , @NamedQuery(name = "PersonalRegisto.findByFotoUrl", query = "SELECT p FROM PersonalRegisto p WHERE p.fotoUrl = :fotoUrl")})
public class PersonalRegisto implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @Column(name = "IDENTIDAD")
    private String identidad;
    @Basic(optional = false)
    @Column(name = "NOMBRES")
    private String nombres;
    @Basic(optional = false)
    @Column(name = "APELLIDOS")
    private String apellidos;
    @Basic(optional = false)
    @Column(name = "TELEFONO")
    private String telefono;
    @Basic(optional = false)
    @Column(name = "DIRECCION")
    private String direccion;
    @Basic(optional = false)
    @Lob
    @Column(name = "LATITUD")
    private Object latitud;
    @Basic(optional = false)
    @Lob
    @Column(name = "LONGITUD")
    private Object longitud;
    @Basic(optional = false)
    @Column(name = "FECHA_NACIMIENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNacimiento;
    @Column(name = "FOTO_URL")
    private String fotoUrl;
    @JoinColumn(name = "FK_USUARIO", referencedColumnName = "ID")
    @ManyToOne
    private Usuarios fkUsuario;

    public PersonalRegisto() {
    }

    public PersonalRegisto(BigDecimal id) {
        this.id = id;
    }

    public PersonalRegisto(BigDecimal id, String identidad, String nombres, String apellidos, String telefono, String direccion, Object latitud, Object longitud, Date fechaNacimiento) {
        this.id = id;
        this.identidad = identidad;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.fechaNacimiento = fechaNacimiento;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getIdentidad() {
        return identidad;
    }

    public void setIdentidad(String identidad) {
        this.identidad = identidad;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Object getLatitud() {
        return latitud;
    }

    public void setLatitud(Object latitud) {
        this.latitud = latitud;
    }

    public Object getLongitud() {
        return longitud;
    }

    public void setLongitud(Object longitud) {
        this.longitud = longitud;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public Usuarios getFkUsuario() {
        return fkUsuario;
    }

    public void setFkUsuario(Usuarios fkUsuario) {
        this.fkUsuario = fkUsuario;
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
        if (!(object instanceof PersonalRegisto)) {
            return false;
        }
        PersonalRegisto other = (PersonalRegisto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.PersonalRegisto[ id=" + id + " ]";
    }
    
}
