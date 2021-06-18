package cnx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class Dba {

    private Connection conexion;
    public Statement query;
    public static PreparedStatement prest = null;
    public static CallableStatement cllst = null;
    public static Statement st = null;
    public static ResultSet rt = null;
    public static Connection con;

    public Connection getCon() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "JC", "1234");
        } catch (Exception e) {

        }
        return con;
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }
public DriverManagerDataSource conecta()
{
    DriverManagerDataSource ds = new DriverManagerDataSource();
    ds.setDriverClassName("oracle.jdbc.OracleDriver");
    ds.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
    ds.setUsername("JC");
    ds.setPassword("1234");
    return ds;
}
    public Dba Conectar() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            String BaseDeDatos = "jdbc:oracle:thin:@localhost:1521:XE";

            //poner usuario y contraseña
            conexion = DriverManager.getConnection(BaseDeDatos, "JC", "1234");
            query = conexion.createStatement();
            if (conexion != null) {
                System.out.println("Conexion exitosa ");
            } else {
                System.out.println("Conexion fallida");
            }
        } catch (ClassNotFoundException | SQLException e) {
        }
        return this;
    }

    public void desconectar() {
        try {
            query.close();
            conexion.close();
        } catch (Exception e) {
        }
    }

    public void commit() {
        try {
            conexion.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
