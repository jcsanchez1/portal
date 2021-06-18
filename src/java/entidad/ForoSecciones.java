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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "FORO_SECCIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ForoSecciones.findAll", query = "SELECT f FROM ForoSecciones f")
    , @NamedQuery(name = "ForoSecciones.findById", query = "SELECT f FROM ForoSecciones f WHERE f.id = :id")
    , @NamedQuery(name = "ForoSecciones.findByTitulo", query = "SELECT f FROM ForoSecciones f WHERE f.titulo = :titulo")
    , @NamedQuery(name = "ForoSecciones.findByDescripcion", query = "SELECT f FROM ForoSecciones f WHERE f.descripcion = :descripcion")
    , @NamedQuery(name = "ForoSecciones.findByValor", query = "SELECT f FROM ForoSecciones f WHERE f.valor = :valor")})
public class ForoSecciones implements Serializable {

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
    @OneToMany(mappedBy = "fkForo")
    private List<RespuestasForos> respuestasForosList;
    @JoinColumn(name = "FK_MAESTRO", referencedColumnName = "ID")
    @ManyToOne
    private Maestros fkMaestro;
    @JoinColumn(name = "FK_SECCION", referencedColumnName = "ID")
    @ManyToOne
    private Secciones fkSeccion;

    public ForoSecciones() {
    }

    public ForoSecciones(BigDecimal id) {
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

    @XmlTransient
    public List<RespuestasForos> getRespuestasForosList() {
        return respuestasForosList;
    }

    public void setRespuestasForosList(List<RespuestasForos> respuestasForosList) {
        this.respuestasForosList = respuestasForosList;
    }

    public Maestros getFkMaestro() {
        return fkMaestro;
    }

    public void setFkMaestro(Maestros fkMaestro) {
        this.fkMaestro = fkMaestro;
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
        if (!(object instanceof ForoSecciones)) {
            return false;
        }
        ForoSecciones other = (ForoSecciones) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.ForoSecciones[ id=" + id + " ]";
    }
    
}
