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
@Table(name = "RECLAMOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reclamos.findAll", query = "SELECT r FROM Reclamos r")
    , @NamedQuery(name = "Reclamos.findById", query = "SELECT r FROM Reclamos r WHERE r.id = :id")
    , @NamedQuery(name = "Reclamos.findByDescripcion", query = "SELECT r FROM Reclamos r WHERE r.descripcion = :descripcion")
    , @NamedQuery(name = "Reclamos.findByFechaReclamo", query = "SELECT r FROM Reclamos r WHERE r.fechaReclamo = :fechaReclamo")
    , @NamedQuery(name = "Reclamos.findByRespuesta", query = "SELECT r FROM Reclamos r WHERE r.respuesta = :respuesta")
    , @NamedQuery(name = "Reclamos.findByFechaRespuesta", query = "SELECT r FROM Reclamos r WHERE r.fechaRespuesta = :fechaRespuesta")})
public class Reclamos implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "FECHA_RECLAMO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaReclamo;
    @Column(name = "RESPUESTA")
    private String respuesta;
    @Column(name = "FECHA_RESPUESTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRespuesta;
    @JoinColumn(name = "FK_DOCENTE", referencedColumnName = "ID")
    @ManyToOne
    private Maestros fkDocente;
    @JoinColumn(name = "FK_TIPO_RECLAMO", referencedColumnName = "ID")
    @ManyToOne
    private TiposReclamos fkTipoReclamo;

    public Reclamos() {
    }

    public Reclamos(BigDecimal id) {
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

    public Date getFechaReclamo() {
        return fechaReclamo;
    }

    public void setFechaReclamo(Date fechaReclamo) {
        this.fechaReclamo = fechaReclamo;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public Date getFechaRespuesta() {
        return fechaRespuesta;
    }

    public void setFechaRespuesta(Date fechaRespuesta) {
        this.fechaRespuesta = fechaRespuesta;
    }

    public Maestros getFkDocente() {
        return fkDocente;
    }

    public void setFkDocente(Maestros fkDocente) {
        this.fkDocente = fkDocente;
    }

    public TiposReclamos getFkTipoReclamo() {
        return fkTipoReclamo;
    }

    public void setFkTipoReclamo(TiposReclamos fkTipoReclamo) {
        this.fkTipoReclamo = fkTipoReclamo;
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
        if (!(object instanceof Reclamos)) {
            return false;
        }
        Reclamos other = (Reclamos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.Reclamos[ id=" + id + " ]";
    }
    
}
