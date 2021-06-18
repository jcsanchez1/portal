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
@Table(name = "CLASES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clases.findAll", query = "SELECT c FROM Clases c")
    , @NamedQuery(name = "Clases.findById", query = "SELECT c FROM Clases c WHERE c.id = :id")
    , @NamedQuery(name = "Clases.findByNombreClase", query = "SELECT c FROM Clases c WHERE c.nombreClase = :nombreClase")
    , @NamedQuery(name = "Clases.findByUnidadesValorativas", query = "SELECT c FROM Clases c WHERE c.unidadesValorativas = :unidadesValorativas")
    , @NamedQuery(name = "Clases.findByCodigoClase", query = "SELECT c FROM Clases c WHERE c.codigoClase = :codigoClase")
    , @NamedQuery(name = "Clases.findByValorClase", query = "SELECT c FROM Clases c WHERE c.valorClase = :valorClase")})
public class Clases implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "NOMBRE_CLASE")
    private String nombreClase;
    @Column(name = "UNIDADES_VALORATIVAS")
    private BigDecimal unidadesValorativas;
    @Column(name = "CODIGO_CLASE")
    private String codigoClase;
    @Column(name = "VALOR_CLASE")
    private BigDecimal valorClase;
    @OneToMany(mappedBy = "fkCodigoClase")
    private List<Secciones> seccionesList;
    @OneToMany(mappedBy = "fkClase")
    private List<ClasesPlanesEstudio> clasesPlanesEstudioList;

    public Clases() {
    }

    public Clases(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getNombreClase() {
        return nombreClase;
    }

    public void setNombreClase(String nombreClase) {
        this.nombreClase = nombreClase;
    }

    public BigDecimal getUnidadesValorativas() {
        return unidadesValorativas;
    }

    public void setUnidadesValorativas(BigDecimal unidadesValorativas) {
        this.unidadesValorativas = unidadesValorativas;
    }

    public String getCodigoClase() {
        return codigoClase;
    }

    public void setCodigoClase(String codigoClase) {
        this.codigoClase = codigoClase;
    }

    public BigDecimal getValorClase() {
        return valorClase;
    }

    public void setValorClase(BigDecimal valorClase) {
        this.valorClase = valorClase;
    }

    @XmlTransient
    public List<Secciones> getSeccionesList() {
        return seccionesList;
    }

    public void setSeccionesList(List<Secciones> seccionesList) {
        this.seccionesList = seccionesList;
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
        if (!(object instanceof Clases)) {
            return false;
        }
        Clases other = (Clases) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.Clases[ id=" + id + " ]";
    }
    
}
