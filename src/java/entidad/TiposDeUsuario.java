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
import javax.persistence.CascadeType;
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
@Table(name = "TIPOS_DE_USUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TiposDeUsuario.findAll", query = "SELECT t FROM TiposDeUsuario t")
    , @NamedQuery(name = "TiposDeUsuario.findByTipoUsuario", query = "SELECT t FROM TiposDeUsuario t WHERE t.tipoUsuario = :tipoUsuario")
    , @NamedQuery(name = "TiposDeUsuario.findByDescripcion", query = "SELECT t FROM TiposDeUsuario t WHERE t.descripcion = :descripcion")})
public class TiposDeUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "TIPO_USUARIO")
    private BigDecimal tipoUsuario;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkTipoUsuario")
    private List<Usuarios> usuariosList;

    public TiposDeUsuario() {
    }

    public TiposDeUsuario(BigDecimal tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public TiposDeUsuario(BigDecimal tipoUsuario, String descripcion) {
        this.tipoUsuario = tipoUsuario;
        this.descripcion = descripcion;
    }

    public BigDecimal getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(BigDecimal tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Usuarios> getUsuariosList() {
        return usuariosList;
    }

    public void setUsuariosList(List<Usuarios> usuariosList) {
        this.usuariosList = usuariosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipoUsuario != null ? tipoUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TiposDeUsuario)) {
            return false;
        }
        TiposDeUsuario other = (TiposDeUsuario) object;
        if ((this.tipoUsuario == null && other.tipoUsuario != null) || (this.tipoUsuario != null && !this.tipoUsuario.equals(other.tipoUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.TiposDeUsuario[ tipoUsuario=" + tipoUsuario + " ]";
    }
    
}
