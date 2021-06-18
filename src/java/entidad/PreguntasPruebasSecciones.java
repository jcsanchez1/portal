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
@Table(name = "PREGUNTAS_PRUEBAS_SECCIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PreguntasPruebasSecciones.findAll", query = "SELECT p FROM PreguntasPruebasSecciones p")
    , @NamedQuery(name = "PreguntasPruebasSecciones.findById", query = "SELECT p FROM PreguntasPruebasSecciones p WHERE p.id = :id")})
public class PreguntasPruebasSecciones implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @JoinColumn(name = "FK_PREGUNTA_BREVE", referencedColumnName = "ID")
    @ManyToOne
    private PreguntasBreves fkPreguntaBreve;
    @JoinColumn(name = "FK_PREGUNTA_MULT", referencedColumnName = "ID")
    @ManyToOne
    private PreguntasMultiples fkPreguntaMult;
    @JoinColumn(name = "FK_PREGUNTA_VOF", referencedColumnName = "ID")
    @ManyToOne
    private PreguntasVofs fkPreguntaVof;
    @JoinColumn(name = "FK_PRUEBA_SECCION", referencedColumnName = "ID")
    @ManyToOne
    private PruebasSecciones fkPruebaSeccion;

    public PreguntasPruebasSecciones() {
    }

    public PreguntasPruebasSecciones(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public PreguntasBreves getFkPreguntaBreve() {
        return fkPreguntaBreve;
    }

    public void setFkPreguntaBreve(PreguntasBreves fkPreguntaBreve) {
        this.fkPreguntaBreve = fkPreguntaBreve;
    }

    public PreguntasMultiples getFkPreguntaMult() {
        return fkPreguntaMult;
    }

    public void setFkPreguntaMult(PreguntasMultiples fkPreguntaMult) {
        this.fkPreguntaMult = fkPreguntaMult;
    }

    public PreguntasVofs getFkPreguntaVof() {
        return fkPreguntaVof;
    }

    public void setFkPreguntaVof(PreguntasVofs fkPreguntaVof) {
        this.fkPreguntaVof = fkPreguntaVof;
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
        if (!(object instanceof PreguntasPruebasSecciones)) {
            return false;
        }
        PreguntasPruebasSecciones other = (PreguntasPruebasSecciones) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.PreguntasPruebasSecciones[ id=" + id + " ]";
    }
    
}
