/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidad;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author juanc
 */
@Entity
@Table(name = "MAESTROS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Maestros.findAll", query = "SELECT m FROM Maestros m")
    , @NamedQuery(name = "Maestros.findById", query = "SELECT m FROM Maestros m WHERE m.id = :id")
    , @NamedQuery(name = "Maestros.findByIdentidad", query = "SELECT m FROM Maestros m WHERE m.identidad = :identidad")
    , @NamedQuery(name = "Maestros.findByNombres", query = "SELECT m FROM Maestros m WHERE m.nombres = :nombres")
    , @NamedQuery(name = "Maestros.findByApellidos", query = "SELECT m FROM Maestros m WHERE m.apellidos = :apellidos")
    , @NamedQuery(name = "Maestros.findByTelefono", query = "SELECT m FROM Maestros m WHERE m.telefono = :telefono")
    , @NamedQuery(name = "Maestros.findByDireccion", query = "SELECT m FROM Maestros m WHERE m.direccion = :direccion")
    , @NamedQuery(name = "Maestros.findByFechaNacimiento", query = "SELECT m FROM Maestros m WHERE m.fechaNacimiento = :fechaNacimiento")
    , @NamedQuery(name = "Maestros.findByFotoUrl", query = "SELECT m FROM Maestros m WHERE m.fotoUrl = :fotoUrl")})
public class Maestros implements Serializable {

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
    @OneToMany(mappedBy = "fkDocente")
    private List<Secciones> seccionesList;
    @OneToMany(mappedBy = "fkMaestro")
    private List<ForoSecciones> foroSeccionesList;
    @OneToMany(mappedBy = "fkMaestro")
    private List<PruebasSecciones> pruebasSeccionesList;
    @OneToMany(mappedBy = "fkDocente")
    private List<TareasSecciones> tareasSeccionesList;
    @OneToMany(mappedBy = "fkDocente")
    private List<Reclamos> reclamosList;

    public Maestros() {
    }

    public Maestros(BigDecimal id) {
        this.id = id;
    }

    public Maestros(BigDecimal id, String identidad, String nombres, String apellidos, String telefono, String direccion, Object latitud, Object longitud, Date fechaNacimiento) {
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

    @XmlTransient
    public List<Secciones> getSeccionesList() {
        return seccionesList;
    }

    public void setSeccionesList(List<Secciones> seccionesList) {
        this.seccionesList = seccionesList;
    }

    @XmlTransient
    public List<ForoSecciones> getForoSeccionesList() {
        return foroSeccionesList;
    }

    public void setForoSeccionesList(List<ForoSecciones> foroSeccionesList) {
        this.foroSeccionesList = foroSeccionesList;
    }

    @XmlTransient
    public List<PruebasSecciones> getPruebasSeccionesList() {
        return pruebasSeccionesList;
    }

    public void setPruebasSeccionesList(List<PruebasSecciones> pruebasSeccionesList) {
        this.pruebasSeccionesList = pruebasSeccionesList;
    }

    @XmlTransient
    public List<TareasSecciones> getTareasSeccionesList() {
        return tareasSeccionesList;
    }

    public void setTareasSeccionesList(List<TareasSecciones> tareasSeccionesList) {
        this.tareasSeccionesList = tareasSeccionesList;
    }

    @XmlTransient
    public List<Reclamos> getReclamosList() {
        return reclamosList;
    }

    public void setReclamosList(List<Reclamos> reclamosList) {
        this.reclamosList = reclamosList;
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
        if (!(object instanceof Maestros)) {
            return false;
        }
        Maestros other = (Maestros) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.Maestros[ id=" + id + " ]";
    }
    
}
