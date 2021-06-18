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
@Table(name = "CLASES_PLANES_ESTUDIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClasesPlanesEstudio.findAll", query = "SELECT c FROM ClasesPlanesEstudio c")
    , @NamedQuery(name = "ClasesPlanesEstudio.findById", query = "SELECT c FROM ClasesPlanesEstudio c WHERE c.id = :id")})
public class ClasesPlanesEstudio implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @JoinColumn(name = "FK_CLASE", referencedColumnName = "ID")
    @ManyToOne
    private Clases fkClase;
    @JoinColumn(name = "FK_PLAN", referencedColumnName = "ID")
    @ManyToOne
    private PlanesEstudios fkPlan;

    public ClasesPlanesEstudio() {
    }

    public ClasesPlanesEstudio(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Clases getFkClase() {
        return fkClase;
    }

    public void setFkClase(Clases fkClase) {
        this.fkClase = fkClase;
    }

    public PlanesEstudios getFkPlan() {
        return fkPlan;
    }

    public void setFkPlan(PlanesEstudios fkPlan) {
        this.fkPlan = fkPlan;
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
        if (!(object instanceof ClasesPlanesEstudio)) {
            return false;
        }
        ClasesPlanesEstudio other = (ClasesPlanesEstudio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.ClasesPlanesEstudio[ id=" + id + " ]";
    }
    
}
