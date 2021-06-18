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
@Table(name = "TIPOS_RECLAMOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TiposReclamos.findAll", query = "SELECT t FROM TiposReclamos t")
    , @NamedQuery(name = "TiposReclamos.findById", query = "SELECT t FROM TiposReclamos t WHERE t.id = :id")
    , @NamedQuery(name = "TiposReclamos.findByDescripcion", query = "SELECT t FROM TiposReclamos t WHERE t.descripcion = :descripcion")})
public class TiposReclamos implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(mappedBy = "fkTipoReclamo")
    private List<Reclamos> reclamosList;

    public TiposReclamos() {
    }

    public TiposReclamos(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Reclamos> getReclamosList() {
        return reclamosList;
    }

    public void setReclamosList(List<Reclamos> reclamosList) {
        this.reclamosList = reclamosList;
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
        if (!(object instanceof TiposReclamos)) {
            return false;
        }
        TiposReclamos other = (TiposReclamos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.TiposReclamos[ id=" + id + " ]";
    }
    
}
