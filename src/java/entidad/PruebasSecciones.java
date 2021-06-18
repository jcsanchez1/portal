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
@Table(name = "PRUEBAS_SECCIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PruebasSecciones.findAll", query = "SELECT p FROM PruebasSecciones p")
    , @NamedQuery(name = "PruebasSecciones.findById", query = "SELECT p FROM PruebasSecciones p WHERE p.id = :id")
    , @NamedQuery(name = "PruebasSecciones.findByFecha", query = "SELECT p FROM PruebasSecciones p WHERE p.fecha = :fecha")
    , @NamedQuery(name = "PruebasSecciones.findByHoraInicio", query = "SELECT p FROM PruebasSecciones p WHERE p.horaInicio = :horaInicio")
    , @NamedQuery(name = "PruebasSecciones.findByHoraFinal", query = "SELECT p FROM PruebasSecciones p WHERE p.horaFinal = :horaFinal")
    , @NamedQuery(name = "PruebasSecciones.findByNombre", query = "SELECT p FROM PruebasSecciones p WHERE p.nombre = :nombre")})
public class PruebasSecciones implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Lob
    @Column(name = "DURACION")
    private Object duracion;
    @Column(name = "HORA_INICIO")
    private BigDecimal horaInicio;
    @Column(name = "HORA_FINAL")
    private BigDecimal horaFinal;
    @Column(name = "NOMBRE")
    private String nombre;
    @JoinColumn(name = "FK_MAESTRO", referencedColumnName = "ID")
    @ManyToOne
    private Maestros fkMaestro;
    @JoinColumn(name = "FK_SECCION", referencedColumnName = "ID")
    @ManyToOne
    private Secciones fkSeccion;
    @OneToMany(mappedBy = "fkPruebaSeccion")
    private List<PruebasAlumnos> pruebasAlumnosList;
    @OneToMany(mappedBy = "fkPruebaSeccion")
    private List<PreguntasPruebasSecciones> preguntasPruebasSeccionesList;

    public PruebasSecciones() {
    }

    public PruebasSecciones(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Object getDuracion() {
        return duracion;
    }

    public void setDuracion(Object duracion) {
        this.duracion = duracion;
    }

    public BigDecimal getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(BigDecimal horaInicio) {
        this.horaInicio = horaInicio;
    }

    public BigDecimal getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(BigDecimal horaFinal) {
        this.horaFinal = horaFinal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Maestros getFkMaestro() {
        return fkMaestro;
    }

    public void setFkMaestro(Maestros fkMaestro) {
        this.fkMaestro = fkMaestro;
    }

    public Secciones getFkSeccion() {
        return fkSeccion;
    }

    public void setFkSeccion(Secciones fkSeccion) {
        this.fkSeccion = fkSeccion;
    }

    @XmlTransient
    public List<PruebasAlumnos> getPruebasAlumnosList() {
        return pruebasAlumnosList;
    }

    public void setPruebasAlumnosList(List<PruebasAlumnos> pruebasAlumnosList) {
        this.pruebasAlumnosList = pruebasAlumnosList;
    }

    @XmlTransient
    public List<PreguntasPruebasSecciones> getPreguntasPruebasSeccionesList() {
        return preguntasPruebasSeccionesList;
    }

    public void setPreguntasPruebasSeccionesList(List<PreguntasPruebasSecciones> preguntasPruebasSeccionesList) {
        this.preguntasPruebasSeccionesList = preguntasPruebasSeccionesList;
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
        if (!(object instanceof PruebasSecciones)) {
            return false;
        }
        PruebasSecciones other = (PruebasSecciones) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.PruebasSecciones[ id=" + id + " ]";
    }
    
}
