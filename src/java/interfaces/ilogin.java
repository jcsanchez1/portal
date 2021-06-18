/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entidad.Usuarios;
import entidad.TiposDeUsuario;
import entidad.Maestros;
import entidad.Alumnos;
import entidad.PersonalRegisto;
import java.math.BigDecimal;

/**
 *
 * @author juanc
 */
public interface ilogin {

    public int validar(Usuarios user);
    public Usuarios validartipousuario(Usuarios us);
    public Maestros recuperarDatoMaestro(BigDecimal id);
    public PersonalRegisto recuperarDatoPersonalRegistro(BigDecimal id);
    public Alumnos recuperarDatoAlumno(BigDecimal id);
}
