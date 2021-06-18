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
@Table(name = "PREGUNTAS_MULTIPLES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PreguntasMultiples.findAll", query = "SELECT p FROM PreguntasMultiples p")
    , @NamedQuery(name = "PreguntasMultiples.findById", query = "SELECT p FROM PreguntasMultiples p WHERE p.id = :id")
    , @NamedQuery(name = "PreguntasMultiples.findByPregunta", query = "SELECT p FROM PreguntasMultiples p WHERE p.pregunta = :pregunta")
    , @NamedQuery(name = "PreguntasMultiples.findByOpcionA", query = "SELECT p FROM PreguntasMultiples p WHERE p.opcionA = :opcionA")
    , @NamedQuery(name = "PreguntasMultiples.findByOpcionB", query = "SELECT p FROM PreguntasMultiples p WHERE p.opcionB = :opcionB")
    , @NamedQuery(name = "PreguntasMultiples.findByOpcionC", query = "SELECT p FROM PreguntasMultiples p WHERE p.opcionC = :opcionC")
    , @NamedQuery(name = "PreguntasMultiples.findByOpcionD", query = "SELECT p FROM PreguntasMultiples p WHERE p.opcionD = :opcionD")
    , @NamedQuery(name = "PreguntasMultiples.findByRespuesta", query = "SELECT p FROM PreguntasMultiples p WHERE p.respuesta = :respuesta")})
public class PreguntasMultiples implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "PREGUNTA")
    private String pregunta;
    @Column(name = "OPCION_A")
    private String opcionA;
    @Column(name = "OPCION_B")
    private String opcionB;
    @Column(name = "OPCION_C")
    private String opcionC;
    @Column(name = "OPCION_D")
    private String opcionD;
    @Column(name = "RESPUESTA")
    private BigDecimal respuesta;
    @OneToMany(mappedBy = "fkPreguntaMult")
    private List<PreguntasPruebasSecciones> preguntasPruebasSeccionesList;

    public PreguntasMultiples() {
    }

    public PreguntasMultiples(BigDecimal id) {
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

    public String getOpcionA() {
        return opcionA;
    }

    public void setOpcionA(String opcionA) {
        this.opcionA = opcionA;
    }

    public String getOpcionB() {
        return opcionB;
    }

    public void setOpcionB(String opcionB) {
        this.opcionB = opcionB;
    }

    public String getOpcionC() {
        return opcionC;
    }

    public void setOpcionC(String opcionC) {
        this.opcionC = opcionC;
    }

    public String getOpcionD() {
        return opcionD;
    }

    public void setOpcionD(String opcionD) {
        this.opcionD = opcionD;
    }

    public BigDecimal getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(BigDecimal respuesta) {
        this.respuesta = respuesta;
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
        if (!(object instanceof PreguntasMultiples)) {
            return false;
        }
        PreguntasMultiples other = (PreguntasMultiples) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.PreguntasMultiples[ id=" + id + " ]";
    }
    
}
