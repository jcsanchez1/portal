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
@Table(name = "PRUEBAS_ALUMNOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PruebasAlumnos.findAll", query = "SELECT p FROM PruebasAlumnos p")
    , @NamedQuery(name = "PruebasAlumnos.findById", query = "SELECT p FROM PruebasAlumnos p WHERE p.id = :id")
    , @NamedQuery(name = "PruebasAlumnos.findByNota", query = "SELECT p FROM PruebasAlumnos p WHERE p.nota = :nota")})
public class PruebasAlumnos implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "NOTA")
    private BigDecimal nota;
    @JoinColumn(name = "FK_ALUMNO", referencedColumnName = "ID")
    @ManyToOne
    private Alumnos fkAlumno;
    @JoinColumn(name = "FK_PRUEBA_SECCION", referencedColumnName = "ID")
    @ManyToOne
    private PruebasSecciones fkPruebaSeccion;

    public PruebasAlumnos() {
    }

    public PruebasAlumnos(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getNota() {
        return nota;
    }

    public void setNota(BigDecimal nota) {
        this.nota = nota;
    }

    public Alumnos getFkAlumno() {
        return fkAlumno;
    }

    public void setFkAlumno(Alumnos fkAlumno) {
        this.fkAlumno = fkAlumno;
    }

    public PruebasSecciones getFkPruebaSeccion() {
        return fkPruebaSeccion;
    }

    public void setFkPruebaSeccion(PruebasSecciones fkPruebaSeccion) {
        this.fkPruebaSeccion = fkPruebaSeccion;
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
        if (!(object instanceof PruebasAlumnos)) {
            return false;
        }
        PruebasAlumnos other = (PruebasAlumnos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.PruebasAlumnos[ id=" + id + " ]";
    }
    
}
