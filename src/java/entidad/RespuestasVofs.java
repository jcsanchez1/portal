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
@Table(name = "RESPUESTAS_VOFS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RespuestasVofs.findAll", query = "SELECT r FROM RespuestasVofs r")
    , @NamedQuery(name = "RespuestasVofs.findById", query = "SELECT r FROM RespuestasVofs r WHERE r.id = :id")
    , @NamedQuery(name = "RespuestasVofs.findByRespuesta", query = "SELECT r FROM RespuestasVofs r WHERE r.respuesta = :respuesta")
    , @NamedQuery(name = "RespuestasVofs.findByNota", query = "SELECT r FROM RespuestasVofs r WHERE r.nota = :nota")})
public class RespuestasVofs implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "RESPUESTA")
    private BigDecimal respuesta;
    @Column(name = "NOTA")
    private BigDecimal nota;
    @OneToMany(mappedBy = "fkRespuestaVof")
    private List<RespuestasPruebasAlumnos> respuestasPruebasAlumnosList;

    public RespuestasVofs() {
    }

    public RespuestasVofs(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(BigDecimal respuesta) {
        this.respuesta = respuesta;
    }

    public BigDecimal getNota() {
        return nota;
    }

    public void setNota(BigDecimal nota) {
        this.nota = nota;
    }

    @XmlTransient
    public List<RespuestasPruebasAlumnos> getRespuestasPruebasAlumnosList() {
        return respuestasPruebasAlumnosList;
    }

    public void setRespuestasPruebasAlumnosList(List<RespuestasPruebasAlumnos> respuestasPruebasAlumnosList) {
        this.respuestasPruebasAlumnosList = respuestasPruebasAlumnosList;
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
        if (!(object instanceof RespuestasVofs)) {
            return false;
        }
        RespuestasVofs other = (RespuestasVofs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.RespuestasVofs[ id=" + id + " ]";
    }
    
}
