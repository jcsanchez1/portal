/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidad;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juanc
 */
@Entity
@Table(name = "ALUMNOS_SECCIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AlumnosSecciones.findAll", query = "SELECT a FROM AlumnosSecciones a")
    , @NamedQuery(name = "AlumnosSecciones.findById", query = "SELECT a FROM AlumnosSecciones a WHERE a.id = :id")})
public class AlumnosSecciones implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Lob
    @Column(name = "ACUMULATIVO")
    private Object acumulativo;
    @Lob
    @Column(name = "EXAMEN")
    private Object examen;
    @JoinColumn(name = "FK_ALUMNO", referencedColumnName = "ID")
    @ManyToOne
    private Alumnos fkAlumno;
    @JoinColumn(name = "FK_SEECION", referencedColumnName = "ID")
    @ManyToOne
    private Secciones fkSeecion;

    public AlumnosSecciones() {
    }

    public AlumnosSecciones(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Object getAcumulativo() {
        return acumulativo;
    }

    public void setAcumulativo(Object acumulativo) {
        this.acumulativo = acumulativo;
    }

    public Object getExamen() {
        return examen;
    }

    public void setExamen(Object examen) {
        this.examen = examen;
    }

    public Alumnos getFkAlumno() {
        return fkAlumno;
    }

    public void setFkAlumno(Alumnos fkAlumno) {
        this.fkAlumno = fkAlumno;
    }

    public Secciones getFkSeecion() {
        return fkSeecion;
    }

    public void setFkSeecion(Secciones fkSeecion) {
        this.fkSeecion = fkSeecion;
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
        if (!(object instanceof AlumnosSecciones)) {
            return false;
        }
        AlumnosSecciones other = (AlumnosSecciones) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.AlumnosSecciones[ id=" + id + " ]";
    }
    
}
