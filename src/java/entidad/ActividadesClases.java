/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidad;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juanc
 */
@Entity
@Table(name = "ACTIVIDADES_CLASES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ActividadesClases.findAll", query = "SELECT a FROM ActividadesClases a")
    , @NamedQuery(name = "ActividadesClases.findById", query = "SELECT a FROM ActividadesClases a WHERE a.id = :id")
    , @NamedQuery(name = "ActividadesClases.findBySemana", query = "SELECT a FROM ActividadesClases a WHERE a.semana = :semana")
    , @NamedQuery(name = "ActividadesClases.findByFechaEntrega", query = "SELECT a FROM ActividadesClases a WHERE a.fechaEntrega = :fechaEntrega")
    , @NamedQuery(name = "ActividadesClases.findByActividad", query = "SELECT a FROM ActividadesClases a WHERE a.actividad = :actividad")
    , @NamedQuery(name = "ActividadesClases.findByValor", query = "SELECT a FROM ActividadesClases a WHERE a.valor = :valor")})
public class ActividadesClases implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "SEMANA")
    private BigDecimal semana;
    @Column(name = "FECHA_ENTREGA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEntrega;
    @Column(name = "ACTIVIDAD")
    private String actividad;
    @Column(name = "VALOR")
    private BigDecimal valor;
    @JoinColumn(name = "FK_MODALIDAD", referencedColumnName = "ID")
    @ManyToOne
    private Modalidades fkModalidad;
    @JoinColumn(name = "FK_SECCION", referencedColumnName = "ID")
    @ManyToOne
    private Secciones fkSeccion;

    public ActividadesClases() {
    }

    public ActividadesClases(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getSemana() {
        return semana;
    }

    public void setSemana(BigDecimal semana) {
        this.semana = semana;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Modalidades getFkModalidad() {
        return fkModalidad;
    }

    public void setFkModalidad(Modalidades fkModalidad) {
        this.fkModalidad = fkModalidad;
    }

    public Secciones getFkSeccion() {
        return fkSeccion;
    }

    public void setFkSeccion(Secciones fkSeccion) {
        this.fkSeccion = fkSeccion;
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
        if (!(object instanceof ActividadesClases)) {
            return false;
        }
        ActividadesClases other = (ActividadesClases) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.ActividadesClases[ id=" + id + " ]";
    }
    
}
