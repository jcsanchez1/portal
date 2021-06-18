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
@Table(name = "TAREAS_ALUMNOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TareasAlumnos.findAll", query = "SELECT t FROM TareasAlumnos t")
    , @NamedQuery(name = "TareasAlumnos.findById", query = "SELECT t FROM TareasAlumnos t WHERE t.id = :id")
    , @NamedQuery(name = "TareasAlumnos.findByUrl", query = "SELECT t FROM TareasAlumnos t WHERE t.url = :url")
    , @NamedQuery(name = "TareasAlumnos.findByNota", query = "SELECT t FROM TareasAlumnos t WHERE t.nota = :nota")
    , @NamedQuery(name = "TareasAlumnos.findByComentarioEntrega", query = "SELECT t FROM TareasAlumnos t WHERE t.comentarioEntrega = :comentarioEntrega")})
public class TareasAlumnos implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "URL")
    private String url;
    @Column(name = "NOTA")
    private BigDecimal nota;
    @Column(name = "COMENTARIO_ENTREGA")
    private String comentarioEntrega;
    @JoinColumn(name = "FK_ALUMNO", referencedColumnName = "ID")
    @ManyToOne
    private Alumnos fkAlumno;
    @JoinColumn(name = "FK_TAREA", referencedColumnName = "ID")
    @ManyToOne
    private TareasSecciones fkTarea;

    public TareasAlumnos() {
    }

    public TareasAlumnos(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public BigDecimal getNota() {
        return nota;
    }

    public void setNota(BigDecimal nota) {
        this.nota = nota;
    }

    public String getComentarioEntrega() {
        return comentarioEntrega;
    }

    public void setComentarioEntrega(String comentarioEntrega) {
        this.comentarioEntrega = comentarioEntrega;
    }

    public Alumnos getFkAlumno() {
        return fkAlumno;
    }

    public void setFkAlumno(Alumnos fkAlumno) {
        this.fkAlumno = fkAlumno;
    }

    public TareasSecciones getFkTarea() {
        return fkTarea;
    }

    public void setFkTarea(TareasSecciones fkTarea) {
        this.fkTarea = fkTarea;
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
        if (!(object instanceof TareasAlumnos)) {
            return false;
        }
        TareasAlumnos other = (TareasAlumnos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.TareasAlumnos[ id=" + id + " ]";
    }
    
}
