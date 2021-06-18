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
import javax.persistence.Lob;
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
@Table(name = "INFO_UNIVERIDADES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InfoUniveridades.findAll", query = "SELECT i FROM InfoUniveridades i")
    , @NamedQuery(name = "InfoUniveridades.findById", query = "SELECT i FROM InfoUniveridades i WHERE i.id = :id")
    , @NamedQuery(name = "InfoUniveridades.findByNomre", query = "SELECT i FROM InfoUniveridades i WHERE i.nomre = :nomre")
    , @NamedQuery(name = "InfoUniveridades.findByMision", query = "SELECT i FROM InfoUniveridades i WHERE i.mision = :mision")
    , @NamedQuery(name = "InfoUniveridades.findByVision", query = "SELECT i FROM InfoUniveridades i WHERE i.vision = :vision")
    , @NamedQuery(name = "InfoUniveridades.findByObjetivos", query = "SELECT i FROM InfoUniveridades i WHERE i.objetivos = :objetivos")
    , @NamedQuery(name = "InfoUniveridades.findByPoliticas", query = "SELECT i FROM InfoUniveridades i WHERE i.politicas = :politicas")
    , @NamedQuery(name = "InfoUniveridades.findByLogo", query = "SELECT i FROM InfoUniveridades i WHERE i.logo = :logo")
    , @NamedQuery(name = "InfoUniveridades.findByOrganigrama", query = "SELECT i FROM InfoUniveridades i WHERE i.organigrama = :organigrama")
    , @NamedQuery(name = "InfoUniveridades.findByDireccionPostal", query = "SELECT i FROM InfoUniveridades i WHERE i.direccionPostal = :direccionPostal")
    , @NamedQuery(name = "InfoUniveridades.findByEmail", query = "SELECT i FROM InfoUniveridades i WHERE i.email = :email")
    , @NamedQuery(name = "InfoUniveridades.findByTelefono1", query = "SELECT i FROM InfoUniveridades i WHERE i.telefono1 = :telefono1")
    , @NamedQuery(name = "InfoUniveridades.findByTelefono2", query = "SELECT i FROM InfoUniveridades i WHERE i.telefono2 = :telefono2")})
public class InfoUniveridades implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @Column(name = "NOMRE")
    private String nomre;
    @Basic(optional = false)
    @Column(name = "MISION")
    private String mision;
    @Basic(optional = false)
    @Column(name = "VISION")
    private String vision;
    @Basic(optional = false)
    @Column(name = "OBJETIVOS")
    private String objetivos;
    @Basic(optional = false)
    @Column(name = "POLITICAS")
    private String politicas;
    @Basic(optional = false)
    @Column(name = "LOGO")
    private String logo;
    @Basic(optional = false)
    @Column(name = "ORGANIGRAMA")
    private String organigrama;
    @Basic(optional = false)
    @Lob
    @Column(name = "LATITUD")
    private Object latitud;
    @Basic(optional = false)
    @Lob
    @Column(name = "LONJITUD")
    private Object lonjitud;
    @Basic(optional = false)
    @Column(name = "DIRECCION_POSTAL")
    private String direccionPostal;
    @Basic(optional = false)
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "TELEFONO_1")
    private String telefono1;
    @Column(name = "TELEFONO_2")
    private String telefono2;
    @OneToMany(mappedBy = "fkIdUniversidad")
    private List<Sedes> sedesList;

    public InfoUniveridades() {
    }

    public InfoUniveridades(BigDecimal id) {
        this.id = id;
    }

    public InfoUniveridades(BigDecimal id, String nomre, String mision, String vision, String objetivos, String politicas, String logo, String organigrama, Object latitud, Object lonjitud, String direccionPostal, String email) {
        this.id = id;
        this.nomre = nomre;
        this.mision = mision;
        this.vision = vision;
        this.objetivos = objetivos;
        this.politicas = politicas;
        this.logo = logo;
        this.organigrama = organigrama;
        this.latitud = latitud;
        this.lonjitud = lonjitud;
        this.direccionPostal = direccionPostal;
        this.email = email;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getNomre() {
        return nomre;
    }

    public void setNomre(String nomre) {
        this.nomre = nomre;
    }

    public String getMision() {
        return mision;
    }

    public void setMision(String mision) {
        this.mision = mision;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public String getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(String objetivos) {
        this.objetivos = objetivos;
    }

    public String getPoliticas() {
        return politicas;
    }

    public void setPoliticas(String politicas) {
        this.politicas = politicas;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getOrganigrama() {
        return organigrama;
    }

    public void setOrganigrama(String organigrama) {
        this.organigrama = organigrama;
    }

    public Object getLatitud() {
        return latitud;
    }

    public void setLatitud(Object latitud) {
        this.latitud = latitud;
    }

    public Object getLonjitud() {
        return lonjitud;
    }

    public void setLonjitud(Object lonjitud) {
        this.lonjitud = lonjitud;
    }

    public String getDireccionPostal() {
        return direccionPostal;
    }

    public void setDireccionPostal(String direccionPostal) {
        this.direccionPostal = direccionPostal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    @XmlTransient
    public List<Sedes> getSedesList() {
        return sedesList;
    }

    public void setSedesList(List<Sedes> sedesList) {
        this.sedesList = sedesList;
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
        if (!(object instanceof InfoUniveridades)) {
            return false;
        }
        InfoUniveridades other = (InfoUniveridades) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.InfoUniveridades[ id=" + id + " ]";
    }
    
}
