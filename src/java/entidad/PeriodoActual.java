/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidad;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
@Table(name = "PERIODO_ACTUAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PeriodoActual.findAll", query = "SELECT p FROM PeriodoActual p")
    , @NamedQuery(name = "PeriodoActual.findById", query = "SELECT p FROM PeriodoActual p WHERE p.id = :id")
    , @NamedQuery(name = "PeriodoActual.findByAno", query = "SELECT p FROM PeriodoActual p WHERE p.ano = :ano")
    , @NamedQuery(name = "PeriodoActual.findByModulo", query = "SELECT p FROM PeriodoActual p WHERE p.modulo = :modulo")})
public class PeriodoActual implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "ANO")
    private BigInteger ano;
    @Column(name = "MODULO")
    private BigInteger modulo;
    @OneToMany(mappedBy = "fkPeriodoActual")
    private List<Secciones> seccionesList;

    public PeriodoActual() {
    }

    public PeriodoActual(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigInteger getAno() {
        return ano;
    }

    public void setAno(BigInteger ano) {
        this.ano = ano;
    }

    public BigInteger getModulo() {
        return modulo;
    }

    public void setModulo(BigInteger modulo) {
        this.modulo = modulo;
    }

    @XmlTransient
    public List<Secciones> getSeccionesList() {
        return seccionesList;
    }

    public void setSeccionesList(List<Secciones> seccionesList) {
        this.seccionesList = seccionesList;
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
        if (!(object instanceof PeriodoActual)) {
            return false;
        }
        PeriodoActual other = (PeriodoActual) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.PeriodoActual[ id=" + id + " ]";
    }
    
}
