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
@Table(name = "RESPUESTAS_BREVES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RespuestasBreves.findAll", query = "SELECT r FROM RespuestasBreves r")
    , @NamedQuery(name = "RespuestasBreves.findById", query = "SELECT r FROM RespuestasBreves r WHERE r.id = :id")
    , @NamedQuery(name = "RespuestasBreves.findByRespuesta", query = "SELECT r FROM RespuestasBreves r WHERE r.respuesta = :respuesta")
    , @NamedQuery(name = "RespuestasBreves.findByNota", query = "SELECT r FROM RespuestasBreves r WHERE r.nota = :nota")})
public class RespuestasBreves implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "RESPUESTA")
    private String respuesta;
    @Column(name = "NOTA")
    private BigDecimal nota;
    @OneToMany(mappedBy = "fkRespuestaBreve")
    private List<RespuestasPruebasAlumnos> respuestasPruebasAlumnosList;

    public RespuestasBreves() {
    }

    public RespuestasBreves(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
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
        if (!(object instanceof RespuestasBreves)) {
            return false;
        }
        RespuestasBreves other = (RespuestasBreves) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.RespuestasBreves[ id=" + id + " ]";
    }
    
}
