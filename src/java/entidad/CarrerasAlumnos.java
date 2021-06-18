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
@Table(name = "CARRERAS_ALUMNOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CarrerasAlumnos.findAll", query = "SELECT c FROM CarrerasAlumnos c")
    , @NamedQuery(name = "CarrerasAlumnos.findByCuenta", query = "SELECT c FROM CarrerasAlumnos c WHERE c.cuenta = :cuenta")
    , @NamedQuery(name = "CarrerasAlumnos.findByFechaIngreso", query = "SELECT c FROM CarrerasAlumnos c WHERE c.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "CarrerasAlumnos.findByFkModalidad", query = "SELECT c FROM CarrerasAlumnos c WHERE c.fkModalidad = :fkModalidad")
    , @NamedQuery(name = "CarrerasAlumnos.findById", query = "SELECT c FROM CarrerasAlumnos c WHERE c.id = :id")})
public class CarrerasAlumnos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "CUENTA")
    private String cuenta;
    @Column(name = "FECHA_INGRESO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIngreso;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "FK_MODALIDAD")
    private BigDecimal fkModalidad;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @JoinColumn(name = "FK_IDENTIDAD_ALUMNO", referencedColumnName = "ID")
    @ManyToOne
    private Alumnos fkIdentidadAlumno;
    @JoinColumn(name = "FK_CARRERA", referencedColumnName = "ID")
    @ManyToOne
    private Carreras fkCarrera;

    public CarrerasAlumnos() {
    }

    public CarrerasAlumnos(BigDecimal id) {
        this.id = id;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public BigDecimal getFkModalidad() {
        return fkModalidad;
    }

    public void setFkModalidad(BigDecimal fkModalidad) {
        this.fkModalidad = fkModalidad;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Alumnos getFkIdentidadAlumno() {
        return fkIdentidadAlumno;
    }

    public void setFkIdentidadAlumno(Alumnos fkIdentidadAlumno) {
        this.fkIdentidadAlumno = fkIdentidadAlumno;
    }

    public Carreras getFkCarrera() {
        return fkCarrera;
    }

    public void setFkCarrera(Carreras fkCarrera) {
        this.fkCarrera = fkCarrera;
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
        if (!(object instanceof CarrerasAlumnos)) {
            return false;
        }
        CarrerasAlumnos other = (CarrerasAlumnos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.CarrerasAlumnos[ id=" + id + " ]";
    }
    
}
