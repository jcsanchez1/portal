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
@Table(name = "MODALIDADES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Modalidades.findAll", query = "SELECT m FROM Modalidades m")
    , @NamedQuery(name = "Modalidades.findById", query = "SELECT m FROM Modalidades m WHERE m.id = :id")
    , @NamedQuery(name = "Modalidades.findByModalidad", query = "SELECT m FROM Modalidades m WHERE m.modalidad = :modalidad")})
public class Modalidades implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "MODALIDAD")
    private String modalidad;
    @OneToMany(mappedBy = "fkModalidad")
    private List<Secciones> seccionesList;
    @OneToMany(mappedBy = "fkModalidad")
    private List<ActividadesClases> actividadesClasesList;

    public Modalidades() {
    }

    public Modalidades(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    @XmlTransient
    public List<Secciones> getSeccionesList() {
        return seccionesList;
    }

    public void setSeccionesList(List<Secciones> seccionesList) {
        this.seccionesList = seccionesList;
    }

    @XmlTransient
    public List<ActividadesClases> getActividadesClasesList() {
        return actividadesClasesList;
    }

    public void setActividadesClasesList(List<ActividadesClases> actividadesClasesList) {
        this.actividadesClasesList = actividadesClasesList;
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
        if (!(object instanceof Modalidades)) {
            return false;
        }
        Modalidades other = (Modalidades) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.Modalidades[ id=" + id + " ]";
    }
    
}
