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
import javax.persistence.Lob;
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
@Table(name = "SEDES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sedes.findAll", query = "SELECT s FROM Sedes s")
    , @NamedQuery(name = "Sedes.findById", query = "SELECT s FROM Sedes s WHERE s.id = :id")
    , @NamedQuery(name = "Sedes.findByNombreSede", query = "SELECT s FROM Sedes s WHERE s.nombreSede = :nombreSede")
    , @NamedQuery(name = "Sedes.findByDireccion", query = "SELECT s FROM Sedes s WHERE s.direccion = :direccion")
    , @NamedQuery(name = "Sedes.findByTelefono", query = "SELECT s FROM Sedes s WHERE s.telefono = :telefono")})
public class Sedes implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "NOMBRE_SEDE")
    private String nombreSede;
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "TELEFONO")
    private String telefono;
    @Lob
    @Column(name = "LATITUD")
    private Object latitud;
    @Lob
    @Column(name = "LONGITUD")
    private Object longitud;
    @OneToMany(mappedBy = "fkSede")
    private List<Secciones> seccionesList;
    @JoinColumn(name = "FK_ID_UNIVERSIDAD", referencedColumnName = "ID")
    @ManyToOne
    private InfoUniveridades fkIdUniversidad;

    public Sedes() {
    }

    public Sedes(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getNombreSede() {
        return nombreSede;
    }

    public void setNombreSede(String nombreSede) {
        this.nombreSede = nombreSede;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Object getLatitud() {
        return latitud;
    }

    public void setLatitud(Object latitud) {
        this.latitud = latitud;
    }

    public Object getLongitud() {
        return longitud;
    }

    public void setLongitud(Object longitud) {
        this.longitud = longitud;
    }

    @XmlTransient
    public List<Secciones> getSeccionesList() {
        return seccionesList;
    }

    public void setSeccionesList(List<Secciones> seccionesList) {
        this.seccionesList = seccionesList;
    }

    public InfoUniveridades getFkIdUniversidad() {
        return fkIdUniversidad;
    }

    public void setFkIdUniversidad(InfoUniveridades fkIdUniversidad) {
        this.fkIdUniversidad = fkIdUniversidad;
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
        if (!(object instanceof Sedes)) {
            return false;
        }
        Sedes other = (Sedes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.Sedes[ id=" + id + " ]";
    }
    
}
