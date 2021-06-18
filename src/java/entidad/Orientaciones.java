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
@Table(name = "ORIENTACIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orientaciones.findAll", query = "SELECT o FROM Orientaciones o")
    , @NamedQuery(name = "Orientaciones.findById", query = "SELECT o FROM Orientaciones o WHERE o.id = :id")
    , @NamedQuery(name = "Orientaciones.findByOrientacion", query = "SELECT o FROM Orientaciones o WHERE o.orientacion = :orientacion")})
public class Orientaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "ORIENTACION")
    private String orientacion;
    @OneToMany(mappedBy = "fkOrientacion")
    private List<Carreras> carrerasList;

    public Orientaciones() {
    }

    public Orientaciones(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getOrientacion() {
        return orientacion;
    }

    public void setOrientacion(String orientacion) {
        this.orientacion = orientacion;
    }

    @XmlTransient
    public List<Carreras> getCarrerasList() {
        return carrerasList;
    }

    public void setCarrerasList(List<Carreras> carrerasList) {
        this.carrerasList = carrerasList;
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
        if (!(object instanceof Orientaciones)) {
            return false;
        }
        Orientaciones other = (Orientaciones) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.Orientaciones[ id=" + id + " ]";
    }
    
}
