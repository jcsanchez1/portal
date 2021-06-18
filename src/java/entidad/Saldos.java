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
import javax.persistence.Lob;
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
@Table(name = "SALDOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Saldos.findAll", query = "SELECT s FROM Saldos s")
    , @NamedQuery(name = "Saldos.findById", query = "SELECT s FROM Saldos s WHERE s.id = :id")
    , @NamedQuery(name = "Saldos.findByFecha", query = "SELECT s FROM Saldos s WHERE s.fecha = :fecha")
    , @NamedQuery(name = "Saldos.findByDescripcion", query = "SELECT s FROM Saldos s WHERE s.descripcion = :descripcion")})
public class Saldos implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Lob
    @Column(name = "SALDO")
    private Object saldo;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Lob
    @Column(name = "TOTAL")
    private Object total;
    @JoinColumn(name = "FK_ALUMNO", referencedColumnName = "ID")
    @ManyToOne
    private Alumnos fkAlumno;

    public Saldos() {
    }

    public Saldos(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Object getSaldo() {
        return saldo;
    }

    public void setSaldo(Object saldo) {
        this.saldo = saldo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Object getTotal() {
        return total;
    }

    public void setTotal(Object total) {
        this.total = total;
    }

    public Alumnos getFkAlumno() {
        return fkAlumno;
    }

    public void setFkAlumno(Alumnos fkAlumno) {
        this.fkAlumno = fkAlumno;
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
        if (!(object instanceof Saldos)) {
            return false;
        }
        Saldos other = (Saldos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.Saldos[ id=" + id + " ]";
    }
    
}
