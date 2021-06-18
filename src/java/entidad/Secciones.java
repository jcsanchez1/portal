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
@Table(name = "SECCIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Secciones.findAll", query = "SELECT s FROM Secciones s")
    , @NamedQuery(name = "Secciones.findById", query = "SELECT s FROM Secciones s WHERE s.id = :id")
    , @NamedQuery(name = "Secciones.findBySeccion", query = "SELECT s FROM Secciones s WHERE s.seccion = :seccion")
    , @NamedQuery(name = "Secciones.findByHora", query = "SELECT s FROM Secciones s WHERE s.hora = :hora")
    , @NamedQuery(name = "Secciones.findByAula", query = "SELECT s FROM Secciones s WHERE s.aula = :aula")
    , @NamedQuery(name = "Secciones.findByDias", query = "SELECT s FROM Secciones s WHERE s.dias = :dias")
    , @NamedQuery(name = "Secciones.findByCupos", query = "SELECT s FROM Secciones s WHERE s.cupos = :cupos")
    , @NamedQuery(name = "Secciones.findByAcumulativos", query = "SELECT s FROM Secciones s WHERE s.acumulativos = :acumulativos")
    , @NamedQuery(name = "Secciones.findByExamen", query = "SELECT s FROM Secciones s WHERE s.examen = :examen")})
public class Secciones implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "SECCION")
    private String seccion;
    @Column(name = "HORA")
    private String hora;
    @Column(name = "AULA")
    private String aula;
    @Column(name = "DIAS")
    private String dias;
    @Column(name = "CUPOS")
    private BigDecimal cupos;
    @Column(name = "ACUMULATIVOS")
    private BigDecimal acumulativos;
    @Column(name = "EXAMEN")
    private BigDecimal examen;
    @JoinColumn(name = "FK_CODIGO_CLASE", referencedColumnName = "ID")
    @ManyToOne
    private Clases fkCodigoClase;
    @JoinColumn(name = "FK_DOCENTE", referencedColumnName = "ID")
    @ManyToOne
    private Maestros fkDocente;
    @JoinColumn(name = "FK_MODALIDAD", referencedColumnName = "ID")
    @ManyToOne
    private Modalidades fkModalidad;
    @JoinColumn(name = "FK_PERIODO_ACTUAL", referencedColumnName = "ID")
    @ManyToOne
    private PeriodoActual fkPeriodoActual;
    @JoinColumn(name = "FK_SEDE", referencedColumnName = "ID")
    @ManyToOne
    private Sedes fkSede;
    @OneToMany(mappedBy = "fkSeccion")
    private List<ForoSecciones> foroSeccionesList;
    @OneToMany(mappedBy = "fkSeccion")
    private List<PruebasSecciones> pruebasSeccionesList;
    @OneToMany(mappedBy = "fkSeccion")
    private List<TareasSecciones> tareasSeccionesList;
    @OneToMany(mappedBy = "fkSeccion")
    private List<ActividadesClases> actividadesClasesList;
    @OneToMany(mappedBy = "fkSeecion")
    private List<AlumnosSecciones> alumnosSeccionesList;

    public Secciones() {
    }

    public Secciones(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public BigDecimal getCupos() {
        return cupos;
    }

    public void setCupos(BigDecimal cupos) {
        this.cupos = cupos;
    }

    public BigDecimal getAcumulativos() {
        return acumulativos;
    }

    public void setAcumulativos(BigDecimal acumulativos) {
        this.acumulativos = acumulativos;
    }

    public BigDecimal getExamen() {
        return examen;
    }

    public void setExamen(BigDecimal examen) {
        this.examen = examen;
    }

    public Clases getFkCodigoClase() {
        return fkCodigoClase;
    }

    public void setFkCodigoClase(Clases fkCodigoClase) {
        this.fkCodigoClase = fkCodigoClase;
    }

    public Maestros getFkDocente() {
        return fkDocente;
    }

    public void setFkDocente(Maestros fkDocente) {
        this.fkDocente = fkDocente;
    }

    public Modalidades getFkModalidad() {
        return fkModalidad;
    }

    public void setFkModalidad(Modalidades fkModalidad) {
        this.fkModalidad = fkModalidad;
    }

    public PeriodoActual getFkPeriodoActual() {
        return fkPeriodoActual;
    }

    public void setFkPeriodoActual(PeriodoActual fkPeriodoActual) {
        this.fkPeriodoActual = fkPeriodoActual;
    }

    public Sedes getFkSede() {
        return fkSede;
    }

    public void setFkSede(Sedes fkSede) {
        this.fkSede = fkSede;
    }

    @XmlTransient
    public List<ForoSecciones> getForoSeccionesList() {
        return foroSeccionesList;
    }

    public void setForoSeccionesList(List<ForoSecciones> foroSeccionesList) {
        this.foroSeccionesList = foroSeccionesList;
    }

    @XmlTransient
    public List<PruebasSecciones> getPruebasSeccionesList() {
        return pruebasSeccionesList;
    }

    public void setPruebasSeccionesList(List<PruebasSecciones> pruebasSeccionesList) {
        this.pruebasSeccionesList = pruebasSeccionesList;
    }

    @XmlTransient
    public List<TareasSecciones> getTareasSeccionesList() {
        return tareasSeccionesList;
    }

    public void setTareasSeccionesList(List<TareasSecciones> tareasSeccionesList) {
        this.tareasSeccionesList = tareasSeccionesList;
    }

    @XmlTransient
    public List<ActividadesClases> getActividadesClasesList() {
        return actividadesClasesList;
    }

    public void setActividadesClasesList(List<ActividadesClases> actividadesClasesList) {
        this.actividadesClasesList = actividadesClasesList;
    }

    @XmlTransient
    public List<AlumnosSecciones> getAlumnosSeccionesList() {
        return alumnosSeccionesList;
    }

    public void setAlumnosSeccionesList(List<AlumnosSecciones> alumnosSeccionesList) {
        this.alumnosSeccionesList = alumnosSeccionesList;
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
        if (!(object instanceof Secciones)) {
            return false;
        }
        Secciones other = (Secciones) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.Secciones[ id=" + id + " ]";
    }
    
}
