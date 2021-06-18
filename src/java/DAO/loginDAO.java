/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import cnx.Dba;
import entidad.Usuarios;
import entidad.Maestros;
import entidad.PersonalRegisto;
import entidad.Alumnos;
import entidad.TiposDeUsuario;
import interfaces.ilogin;
import static java.lang.System.out;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author juanc
 */
public class loginDAO implements ilogin {

    Connection con;
    TiposDeUsuario tu = new TiposDeUsuario();
    PersonalRegisto pr = new PersonalRegisto();
    Usuarios user = new Usuarios();
    Dba cn = new Dba();
    PreparedStatement ps;
    ResultSet rs;
    int r = 0;

    @Override
    public int validar(Usuarios user) {
        String sql = "SELECT *\n"
                + "FROM USUARIOS\n"
                + "WHERE CORREO =?\n"
                + "AND\n"
                + "PASSWORD =?";
        try {
            con = cn.getCon();
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getCorreo());
            ps.setString(2, user.getPassword());
            rs = ps.executeQuery();
            if (rs.next() == false) {
                return 0;
            } else {
                BigDecimal tus;
                do {
                    r = r + 1;
                    user.setCorreo(rs.getString("CORREO"));
                    //user.setPassword(rs.getString("PASSWORD"));
                    tu.setTipoUsuario(rs.getBigDecimal("FK_TIPO_USUARIO"));
                    user.setFkTipoUsuario(tu);
                    user.setId(rs.getBigDecimal("ID"));
                    user.setEstado(rs.getString("ESTADO").charAt(0));
                } while (rs.next());

            }
            //user = this.validartipousuario(user);
            r = user.getFkTipoUsuario().getTipoUsuario().intValue();
            //rs.close();
            ps.close();
            con.close();
            rs.close();
            cn.desconectar();
        } catch (SQLException e) {
        }
        return r;
    }

    @Override
    public Usuarios validartipousuario(Usuarios us) {
        String sql = "SELECT\n"
                + "	TIPOS_DE_USUARIO.TIPO_USUARIO, \n"
                + "	TIPOS_DE_USUARIO.DESCRIPCION, \n"
                + "	USUARIOS.CORREO, \n"
                + "	USUARIOS.PASSWORD, \n"
                + "	USUARIOS.FK_TIPO_USUARIO, \n"
                + "	USUARIOS.ESTADO, \n"
                + "	USUARIOS.ID\n"
                + "FROM\n"
                + "	USUARIOS\n"
                + "	INNER JOIN\n"
                + "	TIPOS_DE_USUARIO\n"
                + "	ON \n"
                + "		USUARIOS.FK_TIPO_USUARIO = TIPOS_DE_USUARIO.TIPO_USUARIO\n"
                + "		WHERE 	USUARIOS.CORREO = ? AND\n"
                + "	USUARIOS.PASSWORD = ?,";
        try {
            con = cn.getCon();
            ps = con.prepareStatement(sql);
            ps.setString(1, us.getCorreo());
            ps.setString(2, us.getPassword());
            rs = ps.executeQuery();
            if (rs.next() == false) {
                return null;
            } else {
                do {
                    us.setCorreo(rs.getString(3));
                    us.setId(rs.getBigDecimal(7));
                    tu.setDescripcion(rs.getString(2));
                    tu.setTipoUsuario(rs.getBigDecimal(1));
                    us.setFkTipoUsuario(tu);
                } while (rs.next());

            }
            ps.close();
            rs.close();
            con.close();
        } catch (Exception e) {
        }
        user = us;
        return us;
    }

    @Override
    public Maestros recuperarDatoMaestro(BigDecimal id) {
        Maestros ma = new Maestros();
        String sql = "SELECT\n"
                + "	MAESTROS.ID, \n"
                + "	MAESTROS.IDENTIDAD, \n"
                + "	MAESTROS.NOMBRES, \n"
                + "	MAESTROS.APELLIDOS, \n"
                + "	MAESTROS.TELEFONO, \n"
                + "	MAESTROS.DIRECCION, \n"
                + "	MAESTROS.LATITUD, \n"
                + "	MAESTROS.LONGITUD, \n"
                + "	MAESTROS.FECHA_NACIMIENTO, \n"
                + "	MAESTROS.FK_USUARIO, \n"
                + "	MAESTROS.FOTO_URL\n"
                + "FROM\n"
                + "	MAESTROS\n"
                + "	WHERE\n"
                + "	MAESTROS.FK_USUARIO =?";
        try {
            con = cn.getCon();
            ps = con.prepareStatement(sql);
            ps.setBigDecimal(1, id);
            rs = ps.executeQuery();
            if (rs.next() == false) {
                return null;
            } else {
                do {
                    ma.setId(rs.getBigDecimal(1));
                    ma.setIdentidad(rs.getString(2));
                    ma.setNombres(rs.getString(3));
                    ma.setApellidos(rs.getString(4));
                    ma.setTelefono(rs.getString(5));
                    ma.setDireccion(rs.getString(6));
                    ma.setLatitud(rs.getObject(7));
                    ma.setLongitud(rs.getObject(8));
                    ma.setFechaNacimiento(rs.getDate(9));
                    user.setId(id);
                    ma.setFkUsuario(user);
                    ma.setFotoUrl(rs.getString(11));
                } while (rs.next());
            }
            ps.close();
            rs.close();
            con.close();
        } catch (Exception e) {
        }

        return ma;
    }

    @Override
    public PersonalRegisto recuperarDatoPersonalRegistro(BigDecimal id) {
        String sql = "SELECT PERSONAL_REGISTO.ID, PERSONAL_REGISTO.IDENTIDAD, PERSONAL_REGISTO.NOMBRES,PERSONAL_REGISTO.APELLIDOS, PERSONAL_REGISTO.TELEFONO, PERSONAL_REGISTO.DIRECCION, PERSONAL_REGISTO.LATITUD,PERSONAL_REGISTO.LONGITUD,PERSONAL_REGISTO.FECHA_NACIMIENTO ,PERSONAL_REGISTO.FK_USUARIO, PERSONAL_REGISTO.FOTO_URL FROM PERSONAL_REGISTO WHERE PERSONAL_REGISTO.FK_USUARIO =" + id.intValue();
        try {
            con = cn.getCon();
            ps = con.prepareStatement(sql);
            //ps.setBigDecimal(1, id);
            rs = ps.executeQuery();
            if (rs.next() == false) {
                return null;
            } else {
                do {
                    pr.setId(rs.getBigDecimal(1));
                    pr.setIdentidad(rs.getString(2));
                    pr.setNombres(rs.getString(3));
                    pr.setApellidos(rs.getString(4));
                    pr.setTelefono(rs.getString(5));
                    pr.setDireccion(rs.getString(6));
                    pr.setLatitud(rs.getObject(7));
                    pr.setLongitud(rs.getObject(8));
                    pr.setFechaNacimiento(rs.getDate(9));
                    user.setId(id);
                    pr.setFkUsuario(user);
                    pr.setFotoUrl(rs.getString(11));
                } while (rs.next());
            }
            ps.close();
            rs.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            out.print(e.getMessage());
        }
        return pr;
    }

    @Override
    public Alumnos recuperarDatoAlumno(BigDecimal id) {
        Alumnos al = new Alumnos();
        String sql = "SELECT\n"
                + "	ALUMNOS.ID, \n"
                + "	ALUMNOS.IDENTIDAD, \n"
                + "	ALUMNOS.NOMBRES, \n"
                + "	ALUMNOS.APELLIDOS, \n"
                + "	ALUMNOS.TELEFONO, \n"
                + "	ALUMNOS.DIRECCION, \n"
                + "	ALUMNOS.LATITUD, \n"
                + "	ALUMNOS.LONGITUD, \n"
                + "	ALUMNOS.FECHA_NACIMIENTO, \n"
                + "	ALUMNOS.FK_USUARIO, \n"
                + "	ALUMNOS.FOTO_URL\n"
                + "FROM\n"
                + "	ALUMNOS\n"
                + "	WHERE ALUMNOS.FK_USUARIO = ?";
        try {
            con = cn.getCon();
            ps = con.prepareStatement(sql);
            ps.setBigDecimal(1, id);
            rs = ps.executeQuery();
            if (rs.next() == false) {
                return null;
            } else {
                do {
                    al.setId(rs.getBigDecimal(1));
                    al.setIdentidad(rs.getString(2));
                    al.setNombres(rs.getString(3));
                    al.setApellidos(rs.getString(4));
                    al.setTelefono(rs.getString(5));
                    al.setDireccion(rs.getString(6));
                    al.setLatitud(rs.getObject(7));
                    al.setLongitud(rs.getObject(8));
                    al.setFechaNacimiento(rs.getDate(9));
                    user.setId(id);
                    al.setFkUsuario(user);
                    al.setFotoUrl(rs.getString(11));
                } while (rs.next());
            }
            ps.close();
            rs.close();
            con.close();
        } catch (Exception e) {
        }
        return al;
    }

}
