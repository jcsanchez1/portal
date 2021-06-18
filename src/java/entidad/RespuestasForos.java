/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidad;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juanc
 */
@Entity
@Table(name = "RESPUESTAS_FOROS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RespuestasForos.findAll", query = "SELECT r FROM RespuestasForos r")
    , @NamedQuery(name = "RespuestasForos.findById", query = "SELECT r FROM RespuestasForos r WHERE r.id = :id")
    , @NamedQuery(name = "RespuestasForos.findByTitulo", query = "SELECT r FROM RespuestasForos r WHERE r.titulo = :titulo")
    , @NamedQuery(name = "RespuestasForos.findByDescripcion", query = "SELECT r FROM RespuestasForos r WHERE r.descripcion = :descripcion")
    , @NamedQuery(name = "RespuestasForos.findByValor", query = "SELECT r FROM RespuestasForos r WHERE r.valor = :valor")})
public class RespuestasForos implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "TITULO")
    private String titulo;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "VALOR")
    private BigDecimal valor;
    @JoinColumn(name = "FK_ALUMNO", referencedColumnName = "ID")
    @ManyToOne
    private Alumnos fkAlumno;
    @JoinColumn(name = "FK_FORO", referencedColumnName = "ID")
    @ManyToOne
    private ForoSecciones fkForo;

    public RespuestasForos() {
    }

    public RespuestasForos(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Alumnos getFkAlumno() {
        return fkAlumno;
    }

    public void setFkAlumno(Alumnos fkAlumno) {
        this.fkAlumno = fkAlumno;
    }

    public ForoSecciones getFkForo() {
        return fkForo;
    }

    public void setFkForo(ForoSecciones fkForo) {
        this.fkForo = fkForo;
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
        if (!(object instanceof RespuestasForos)) {
            return false;
        }
        RespuestasForos other = (RespuestasForos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.RespuestasForos[ id=" + id + " ]";
    }
    
}
