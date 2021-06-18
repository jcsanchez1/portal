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
@Table(name = "ARREGLOS_PAGOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ArreglosPagos.findAll", query = "SELECT a FROM ArreglosPagos a")
    , @NamedQuery(name = "ArreglosPagos.findById", query = "SELECT a FROM ArreglosPagos a WHERE a.id = :id")
    , @NamedQuery(name = "ArreglosPagos.findByFecha", query = "SELECT a FROM ArreglosPagos a WHERE a.fecha = :fecha")
    , @NamedQuery(name = "ArreglosPagos.findByFechaPago", query = "SELECT a FROM ArreglosPagos a WHERE a.fechaPago = :fechaPago")})
public class ArreglosPagos implements Serializable {

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
    @Column(name = "VALOR_ADEUDADO")
    private Object valorAdeudado;
    @Lob
    @Column(name = "VALOR_PROMETIDO_PAGO")
    private Object valorPrometidoPago;
    @Lob
    @Column(name = "VALOR_PAGADO")
    private Object valorPagado;
    @Column(name = "FECHA_PAGO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPago;
    @JoinColumn(name = "FK_ESTUDIANTE", referencedColumnName = "ID")
    @ManyToOne
    private Alumnos fkEstudiante;

    public ArreglosPagos() {
    }

    public ArreglosPagos(BigDecimal id) {
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

    public Object getValorAdeudado() {
        return valorAdeudado;
    }

    public void setValorAdeudado(Object valorAdeudado) {
        this.valorAdeudado = valorAdeudado;
    }

    public Object getValorPrometidoPago() {
        return valorPrometidoPago;
    }

    public void setValorPrometidoPago(Object valorPrometidoPago) {
        this.valorPrometidoPago = valorPrometidoPago;
    }

    public Object getValorPagado() {
        return valorPagado;
    }

    public void setValorPagado(Object valorPagado) {
        this.valorPagado = valorPagado;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Alumnos getFkEstudiante() {
        return fkEstudiante;
    }

    public void setFkEstudiante(Alumnos fkEstudiante) {
        this.fkEstudiante = fkEstudiante;
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
        if (!(object instanceof ArreglosPagos)) {
            return false;
        }
        ArreglosPagos other = (ArreglosPagos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.ArreglosPagos[ id=" + id + " ]";
    }
    
}
