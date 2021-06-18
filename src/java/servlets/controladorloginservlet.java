/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import DAO.loginDAO;
import entidad.Usuarios;
import entidad.TiposDeUsuario;
import entidad.Alumnos;
import entidad.Maestros;
import entidad.PersonalRegisto;
import cnx.Dba;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.math.BigDecimal;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author juanc
 */
public class controladorloginservlet extends HttpServlet {

    Dba con = new Dba();
    JdbcTemplate jdbcTemplate = new JdbcTemplate(con.conecta());
    ModelAndView mav = new ModelAndView();
    loginDAO ldao = new loginDAO();
    Usuarios us = new Usuarios();
    Maestros ma = new Maestros();
    Alumnos alu = new Alumnos();
    PersonalRegisto pr = new PersonalRegisto();
    TiposDeUsuario tus = new TiposDeUsuario();
    int r = 0;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String accion = request.getParameter("accion");
        if (accion.equals("ingresar")) {
            String nom = request.getParameter("txtnomb");
            String pass = request.getParameter("txtpass");
            us.setCorreo(nom);
            us.setPassword(pass);
            r = ldao.validar(us);
            if (r == 0) {
                out.print("error");
                request.getRequestDispatcher("index.htm").forward(request, response);
            } else {

                int idu = us.getId().intValue();
                String correo = us.getCorreo();
                int idtu = us.getFkTipoUsuario().getTipoUsuario().intValue();
                char estado = us.getEstado();
                int idpersona;
                String identidad;
                String nombre;
                String telefono;
                String direccion;
                float latitud;
                float longitud;
                Date fehcanacimiento;
                String foto;
                //String rol = us.getFkTipoUsuario().getDescripcion();
                request.getSession().setAttribute("iduser", idu);
                request.getSession().setAttribute("correo", correo);
                request.getSession().setAttribute("idtu", idtu);
                request.getSession().setAttribute("estado", estado);
                //request.getSession().setAttribute("rol", rol);

                if (estado == 0) {
                    request.getRequestDispatcher("administradoresindex.htm").forward(request, response);
                } else {
                    try {
                        if (r == 1) {
//                            pr = ldao.recuperarDatoPersonalRegistro(BigDecimal.valueOf(idu));
//                            idpersona = pr.getId().intValue();
//                            identidad = pr.getIdentidad();
//                            nombre = pr.getNombres() + " " + pr.getApellidos();
//                            telefono = pr.getTelefono();
//                            direccion = pr.getDireccion();
//                            latitud = ((Double) pr.getLatitud()).floatValue();
//                            longitud = ((Double) pr.getLongitud()).floatValue();
//                            fehcanacimiento = pr.getFechaNacimiento();
//                            foto = pr.getFotoUrl();
//                            request.getSession().setAttribute("idpersona", idpersona);
//                            request.getSession().setAttribute("identidad", identidad);
//                            request.getSession().setAttribute("nombre", nombre);
//                            request.getSession().setAttribute("telefono", telefono);
//                            request.getSession().setAttribute("direccion", direccion);
//                            request.getSession().setAttribute("latitud", latitud);
//                            request.getSession().setAttribute("longitud", longitud);
//                            request.getSession().setAttribute("fehcanacimiento", fehcanacimiento);
//                            request.getSession().setAttribute("foto", foto);
                            request.getRequestDispatcher("reg-index.htm").forward(request, response);
                        } else if (r == 2) {
//                        ma = ldao.recuperarDatoMaestro(BigDecimal.valueOf(idu));
//                            idpersona = ma.getId().intValue();
//                            identidad = ma.getIdentidad();
//                            nombre = ma.getNombres() + " " + ma.getApellidos();
//                            telefono = ma.getTelefono();
//                            direccion = ma.getDireccion();
//                            latitud = ((Double) ma.getLatitud()).floatValue();
//                            longitud = ((Double) ma.getLongitud()).floatValue();
//                            fehcanacimiento = ma.getFechaNacimiento();
//                            foto = ma.getFotoUrl();
//                            request.getSession().setAttribute("idpersona", idpersona);
//                            request.getSession().setAttribute("identidad", identidad);
//                            request.getSession().setAttribute("nombre", nombre);
//                            request.getSession().setAttribute("telefono", telefono);
//                            request.getSession().setAttribute("direccion", direccion);
//                            request.getSession().setAttribute("latitud", latitud);
//                            request.getSession().setAttribute("longitud", longitud);
//                            request.getSession().setAttribute("fehcanacimiento", fehcanacimiento);
//                            request.getSession().setAttribute("foto", foto);
                            request.getRequestDispatcher("mas-index.htm").forward(request, response);
                        } else if (r == 3) {
                            alu = ldao.recuperarDatoAlumno(BigDecimal.valueOf(idu));
                            idpersona = alu.getId().intValue();
                            identidad = alu.getIdentidad();
                            nombre = alu.getNombres() + " " + alu.getApellidos();
                            telefono = alu.getTelefono();
                            direccion = alu.getDireccion();
                            latitud = ((Double) alu.getLatitud()).floatValue();
                            longitud = ((Double) alu.getLongitud()).floatValue();
                            fehcanacimiento = alu.getFechaNacimiento();
                            foto = alu.getFotoUrl();
                            request.getSession().setAttribute("idpersona", idpersona);
                            request.getSession().setAttribute("identidad", identidad);
                            request.getSession().setAttribute("nombre", nombre);
                            request.getSession().setAttribute("telefono", telefono);
                            request.getSession().setAttribute("direccion", direccion);
                            request.getSession().setAttribute("latitud", latitud);
                            request.getSession().setAttribute("longitud", longitud);
                            request.getSession().setAttribute("fehcanacimiento", fehcanacimiento);
                            request.getSession().setAttribute("foto", foto);
                            request.getRequestDispatcher("alu-index.htm").forward(request, response);
                        }
                    } catch (Exception e) {

                    }
                }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
