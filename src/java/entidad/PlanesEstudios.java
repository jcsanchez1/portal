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
@Table(name = "PLANES_ESTUDIOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanesEstudios.findAll", query = "SELECT p FROM PlanesEstudios p")
    , @NamedQuery(name = "PlanesEstudios.findById", query = "SELECT p FROM PlanesEstudios p WHERE p.id = :id")
    , @NamedQuery(name = "PlanesEstudios.findByA\u00f1oVigencia", query = "SELECT p FROM PlanesEstudios p WHERE p.a\u00f1oVigencia = :a\u00f1oVigencia")
    , @NamedQuery(name = "PlanesEstudios.findByA\u00f1oCierre", query = "SELECT p FROM PlanesEstudios p WHERE p.a\u00f1oCierre = :a\u00f1oCierre")})
public class PlanesEstudios implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "A\u00d1O_VIGENCIA")
    private BigDecimal añoVigencia;
    @Column(name = "A\u00d1O_CIERRE")
    private BigDecimal añoCierre;
    @OneToMany(mappedBy = "fkPlan")
    private List<Carreras> carrerasList;
    @OneToMany(mappedBy = "fkPlan")
    private List<ClasesPlanesEstudio> clasesPlanesEstudioList;

    public PlanesEstudios() {
    }

    public PlanesEstudios(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getAñoVigencia() {
        return añoVigencia;
    }

    public void setAñoVigencia(BigDecimal añoVigencia) {
        this.añoVigencia = añoVigencia;
    }

    public BigDecimal getAñoCierre() {
        return añoCierre;
    }

    public void setAñoCierre(BigDecimal añoCierre) {
        this.añoCierre = añoCierre;
    }

    @XmlTransient
    public List<Carreras> getCarrerasList() {
        return carrerasList;
    }

    public void setCarrerasList(List<Carreras> carrerasList) {
        this.carrerasList = carrerasList;
    }

    @XmlTransient
    public List<ClasesPlanesEstudio> getClasesPlanesEstudioList() {
        return clasesPlanesEstudioList;
    }

    public void setClasesPlanesEstudioList(List<ClasesPlanesEstudio> clasesPlanesEstudioList) {
        this.clasesPlanesEstudioList = clasesPlanesEstudioList;
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
        if (!(object instanceof PlanesEstudios)) {
            return false;
        }
        PlanesEstudios other = (PlanesEstudios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.PlanesEstudios[ id=" + id + " ]";
    }
    
}
