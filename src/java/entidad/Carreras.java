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
@Table(name = "CARRERAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Carreras.findAll", query = "SELECT c FROM Carreras c")
    , @NamedQuery(name = "Carreras.findById", query = "SELECT c FROM Carreras c WHERE c.id = :id")
    , @NamedQuery(name = "Carreras.findByNombreCarrera", query = "SELECT c FROM Carreras c WHERE c.nombreCarrera = :nombreCarrera")})
public class Carreras implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @Column(name = "NOMBRE_CARRERA")
    private String nombreCarrera;
    @JoinColumn(name = "FK_ORIENTACION", referencedColumnName = "ID")
    @ManyToOne
    private Orientaciones fkOrientacion;
    @JoinColumn(name = "FK_PLAN", referencedColumnName = "ID")
    @ManyToOne
    private PlanesEstudios fkPlan;
    @OneToMany(mappedBy = "fkCarrera")
    private List<CarrerasAlumnos> carrerasAlumnosList;

    public Carreras() {
    }

    public Carreras(BigDecimal id) {
        this.id = id;
    }

    public Carreras(BigDecimal id, String nombreCarrera) {
        this.id = id;
        this.nombreCarrera = nombreCarrera;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public void setNombreCarrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }

    public Orientaciones getFkOrientacion() {
        return fkOrientacion;
    }

    public void setFkOrientacion(Orientaciones fkOrientacion) {
        this.fkOrientacion = fkOrientacion;
    }

    public PlanesEstudios getFkPlan() {
        return fkPlan;
    }

    public void setFkPlan(PlanesEstudios fkPlan) {
        this.fkPlan = fkPlan;
    }

    @XmlTransient
    public List<CarrerasAlumnos> getCarrerasAlumnosList() {
        return carrerasAlumnosList;
    }

    public void setCarrerasAlumnosList(List<CarrerasAlumnos> carrerasAlumnosList) {
        this.carrerasAlumnosList = carrerasAlumnosList;
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
        if (!(object instanceof Carreras)) {
            return false;
        }
        Carreras other = (Carreras) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.Carreras[ id=" + id + " ]";
    }
    
}
