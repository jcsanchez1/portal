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
@Table(name = "PREGUNTAS_VOFS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PreguntasVofs.findAll", query = "SELECT p FROM PreguntasVofs p")
    , @NamedQuery(name = "PreguntasVofs.findById", query = "SELECT p FROM PreguntasVofs p WHERE p.id = :id")
    , @NamedQuery(name = "PreguntasVofs.findByVerdadero", query = "SELECT p FROM PreguntasVofs p WHERE p.verdadero = :verdadero")
    , @NamedQuery(name = "PreguntasVofs.findByFalso", query = "SELECT p FROM PreguntasVofs p WHERE p.falso = :falso")
    , @NamedQuery(name = "PreguntasVofs.findByCorrecta", query = "SELECT p FROM PreguntasVofs p WHERE p.correcta = :correcta")
    , @NamedQuery(name = "PreguntasVofs.findByPregunta", query = "SELECT p FROM PreguntasVofs p WHERE p.pregunta = :pregunta")
    , @NamedQuery(name = "PreguntasVofs.findByValor", query = "SELECT p FROM PreguntasVofs p WHERE p.valor = :valor")})
public class PreguntasVofs implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "VERDADERO")
    private BigDecimal verdadero;
    @Column(name = "FALSO")
    private BigDecimal falso;
    @Column(name = "CORRECTA")
    private BigDecimal correcta;
    @Column(name = "PREGUNTA")
    private String pregunta;
    @Column(name = "VALOR")
    private BigDecimal valor;
    @OneToMany(mappedBy = "fkPreguntaVof")
    private List<PreguntasPruebasSecciones> preguntasPruebasSeccionesList;

    public PreguntasVofs() {
    }

    public PreguntasVofs(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getVerdadero() {
        return verdadero;
    }

    public void setVerdadero(BigDecimal verdadero) {
        this.verdadero = verdadero;
    }

    public BigDecimal getFalso() {
        return falso;
    }

    public void setFalso(BigDecimal falso) {
        this.falso = falso;
    }

    public BigDecimal getCorrecta() {
        return correcta;
    }

    public void setCorrecta(BigDecimal correcta) {
        this.correcta = correcta;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
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
        if (!(object instanceof PreguntasVofs)) {
            return false;
        }
        PreguntasVofs other = (PreguntasVofs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.PreguntasVofs[ id=" + id + " ]";
    }
    
}
