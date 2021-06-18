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
@Table(name = "RESPUESTAS_PRUEBAS_ALUMNOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RespuestasPruebasAlumnos.findAll", query = "SELECT r FROM RespuestasPruebasAlumnos r")
    , @NamedQuery(name = "RespuestasPruebasAlumnos.findById", query = "SELECT r FROM RespuestasPruebasAlumnos r WHERE r.id = :id")})
public class RespuestasPruebasAlumnos implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @JoinColumn(name = "FK_RESPUESTA_ALUMNO", referencedColumnName = "ID")
    @ManyToOne
    private Alumnos fkRespuestaAlumno;
    @JoinColumn(name = "FK_RESPUESTA_BREVE", referencedColumnName = "ID")
    @ManyToOne
    private RespuestasBreves fkRespuestaBreve;
    @JoinColumn(name = "FK_RESPUESTA_MULTI", referencedColumnName = "ID")
    @ManyToOne
    private RespuestasMultiples fkRespuestaMulti;
    @JoinColumn(name = "FK_RESPUESTA_VOF", referencedColumnName = "ID")
    @ManyToOne
    private RespuestasVofs fkRespuestaVof;

    public RespuestasPruebasAlumnos() {
    }

    public RespuestasPruebasAlumnos(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Alumnos getFkRespuestaAlumno() {
        return fkRespuestaAlumno;
    }

    public void setFkRespuestaAlumno(Alumnos fkRespuestaAlumno) {
        this.fkRespuestaAlumno = fkRespuestaAlumno;
    }

    public RespuestasBreves getFkRespuestaBreve() {
        return fkRespuestaBreve;
    }

    public void setFkRespuestaBreve(RespuestasBreves fkRespuestaBreve) {
        this.fkRespuestaBreve = fkRespuestaBreve;
    }

    public RespuestasMultiples getFkRespuestaMulti() {
        return fkRespuestaMulti;
    }

    public void setFkRespuestaMulti(RespuestasMultiples fkRespuestaMulti) {
        this.fkRespuestaMulti = fkRespuestaMulti;
    }

    public RespuestasVofs getFkRespuestaVof() {
        return fkRespuestaVof;
    }

    public void setFkRespuestaVof(RespuestasVofs fkRespuestaVof) {
        this.fkRespuestaVof = fkRespuestaVof;
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
        if (!(object instanceof RespuestasPruebasAlumnos)) {
            return false;
        }
        RespuestasPruebasAlumnos other = (RespuestasPruebasAlumnos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.RespuestasPruebasAlumnos[ id=" + id + " ]";
    }
    
}
