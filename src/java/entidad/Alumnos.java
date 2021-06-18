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
@Table(name = "ALUMNOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Alumnos.findAll", query = "SELECT a FROM Alumnos a")
    , @NamedQuery(name = "Alumnos.findById", query = "SELECT a FROM Alumnos a WHERE a.id = :id")
    , @NamedQuery(name = "Alumnos.findByIdentidad", query = "SELECT a FROM Alumnos a WHERE a.identidad = :identidad")
    , @NamedQuery(name = "Alumnos.findByNombres", query = "SELECT a FROM Alumnos a WHERE a.nombres = :nombres")
    , @NamedQuery(name = "Alumnos.findByApellidos", query = "SELECT a FROM Alumnos a WHERE a.apellidos = :apellidos")
    , @NamedQuery(name = "Alumnos.findByTelefono", query = "SELECT a FROM Alumnos a WHERE a.telefono = :telefono")
    , @NamedQuery(name = "Alumnos.findByDireccion", query = "SELECT a FROM Alumnos a WHERE a.direccion = :direccion")
    , @NamedQuery(name = "Alumnos.findByFechaNacimiento", query = "SELECT a FROM Alumnos a WHERE a.fechaNacimiento = :fechaNacimiento")})
public class Alumnos implements Serializable {

    @Basic(optional = false)
    @Column(name = "FOTO_URL")
    private String fotoUrl;

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
    @OneToMany(mappedBy = "fkAlumno")
    private List<RespuestasForos> respuestasForosList;
    @OneToMany(mappedBy = "fkEstudiante")
    private List<ArreglosPagos> arreglosPagosList;
    @OneToMany(mappedBy = "fkAlumno")
    private List<TareasAlumnos> tareasAlumnosList;
    @OneToMany(mappedBy = "fkRespuestaAlumno")
    private List<RespuestasPruebasAlumnos> respuestasPruebasAlumnosList;
    @OneToMany(mappedBy = "fkAlumno")
    private List<Pagos> pagosList;
    @OneToMany(mappedBy = "fkAlumno")
    private List<PruebasAlumnos> pruebasAlumnosList;
    @OneToMany(mappedBy = "fkIdentidadAlumno")
    private List<CarrerasAlumnos> carrerasAlumnosList;
    @OneToMany(mappedBy = "fkAlumno")
    private List<AlumnosSecciones> alumnosSeccionesList;
    @OneToMany(mappedBy = "fkAlumno")
    private List<Saldos> saldosList;
    @JoinColumn(name = "FK_USUARIO", referencedColumnName = "ID")
    @ManyToOne
    private Usuarios fkUsuario;

    public Alumnos() {
    }

    public Alumnos(BigDecimal id) {
        this.id = id;
    }

    public Alumnos(BigDecimal id, String identidad, String nombres, String apellidos, String telefono, String direccion, Object latitud, Object longitud, Date fechaNacimiento) {
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

    @XmlTransient
    public List<RespuestasForos> getRespuestasForosList() {
        return respuestasForosList;
    }

    public void setRespuestasForosList(List<RespuestasForos> respuestasForosList) {
        this.respuestasForosList = respuestasForosList;
    }

    @XmlTransient
    public List<ArreglosPagos> getArreglosPagosList() {
        return arreglosPagosList;
    }

    public void setArreglosPagosList(List<ArreglosPagos> arreglosPagosList) {
        this.arreglosPagosList = arreglosPagosList;
    }

    @XmlTransient
    public List<TareasAlumnos> getTareasAlumnosList() {
        return tareasAlumnosList;
    }

    public void setTareasAlumnosList(List<TareasAlumnos> tareasAlumnosList) {
        this.tareasAlumnosList = tareasAlumnosList;
    }

    @XmlTransient
    public List<RespuestasPruebasAlumnos> getRespuestasPruebasAlumnosList() {
        return respuestasPruebasAlumnosList;
    }

    public void setRespuestasPruebasAlumnosList(List<RespuestasPruebasAlumnos> respuestasPruebasAlumnosList) {
        this.respuestasPruebasAlumnosList = respuestasPruebasAlumnosList;
    }

    @XmlTransient
    public List<Pagos> getPagosList() {
        return pagosList;
    }

    public void setPagosList(List<Pagos> pagosList) {
        this.pagosList = pagosList;
    }

    @XmlTransient
    public List<PruebasAlumnos> getPruebasAlumnosList() {
        return pruebasAlumnosList;
    }

    public void setPruebasAlumnosList(List<PruebasAlumnos> pruebasAlumnosList) {
        this.pruebasAlumnosList = pruebasAlumnosList;
    }

    @XmlTransient
    public List<CarrerasAlumnos> getCarrerasAlumnosList() {
        return carrerasAlumnosList;
    }

    public void setCarrerasAlumnosList(List<CarrerasAlumnos> carrerasAlumnosList) {
        this.carrerasAlumnosList = carrerasAlumnosList;
    }

    @XmlTransient
    public List<AlumnosSecciones> getAlumnosSeccionesList() {
        return alumnosSeccionesList;
    }

    public void setAlumnosSeccionesList(List<AlumnosSecciones> alumnosSeccionesList) {
        this.alumnosSeccionesList = alumnosSeccionesList;
    }

    @XmlTransient
    public List<Saldos> getSaldosList() {
        return saldosList;
    }

    public void setSaldosList(List<Saldos> saldosList) {
        this.saldosList = saldosList;
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
        if (!(object instanceof Alumnos)) {
            return false;
        }
        Alumnos other = (Alumnos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.Alumnos[ id=" + id + " ]";
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }
    
}
