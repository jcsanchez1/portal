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
@Table(name = "PREGUNTAS_BREVES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PreguntasBreves.findAll", query = "SELECT p FROM PreguntasBreves p")
    , @NamedQuery(name = "PreguntasBreves.findById", query = "SELECT p FROM PreguntasBreves p WHERE p.id = :id")
    , @NamedQuery(name = "PreguntasBreves.findByPregunta", query = "SELECT p FROM PreguntasBreves p WHERE p.pregunta = :pregunta")
    , @NamedQuery(name = "PreguntasBreves.findByRespuesta", query = "SELECT p FROM PreguntasBreves p WHERE p.respuesta = :respuesta")
    , @NamedQuery(name = "PreguntasBreves.findByValor", query = "SELECT p FROM PreguntasBreves p WHERE p.valor = :valor")})
public class PreguntasBreves implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "PREGUNTA")
    private String pregunta;
    @Column(name = "RESPUESTA")
    private String respuesta;
    @Column(name = "VALOR")
    private BigDecimal valor;
    @OneToMany(mappedBy = "fkPreguntaBreve")
    private List<PreguntasPruebasSecciones> preguntasPruebasSeccionesList;

    public PreguntasBreves() {
    }

    public PreguntasBreves(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
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
        if (!(object instanceof PreguntasBreves)) {
            return false;
        }
        PreguntasBreves other = (PreguntasBreves) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.PreguntasBreves[ id=" + id + " ]";
    }
    
}
