<%@page import="java.sql.ResultSet"%>
<%@page import="cnx.Dba"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">

        <title>Portal</title>
    </head>
    <body>
        <div class="sidenav">
            <%
                Dba db = new Dba(); //en la clase dba poner el user y pass
                db.Conectar();

                try {
                    db.query.execute("SELECT NOMRE, LOGO FROM  INFO_UNIVERIDADES ");
                    ResultSet rs = db.query.getResultSet();
                    while (rs.next()) {
                        String campo1, campo2;
                        campo1 = rs.getString(1);
                        campo2 = rs.getString(2);

            %>


            <div class="login-main-text">
                <div class="izquierda-logo">
                    <img src="uni/<%=campo2%>" height="100px" width="300px" alt="logo">
                    <h5 class="text-white bg-dark"> <%=campo1%> </h5>
                </div>


            </div>
        </div>
        <div class="main">
            <div class="col-md-6 col-sm-12">
                <div class="login-form">
                    <div class="derecha-logo">
                        <img src="uni/<%=campo2%>" height="200px" width="600px" alt="logo">
                        <h6> <%=campo1%> </h6>   
                    </div>
                        <%
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            db.desconectar();
                        %>                    
                        <form name="frm-login" id="frm-login" action="controladorloginservlet" method="POST">
                            <div class="form-group">
                                <label>Correo institucional</label>
                                <input name="txtnomb" id="txtnomb" type="text" class="form-control" placeholder="usuario@unitec.edu">
                            </div>
                            <div class="form-group">
                                <label>Cotraseña</label>
                                <input name="txtpass" id="txtpass" type="password" class="form-control" placeholder="Cotraseña">
                            </div>
                            <button type="submit" class="btn btn-lg btn-primary" name="accion" id="accion" value="ingresar">Ingresar</button>
                            <!--<button type="submit" class="btn btn-secondary">Register</button>-->
                            <p>Si tienes problemas con el ingreso al portal da clic en este <a href="#">enlace.</a></p>
                        </form>
                    </div>
                </div>
            </div>
            <style>
                body {
                    font-family: "Lato", sans-serif;
                }



                .main-head{
                    height: 150px;
                    background: #FFF;

                }

                .sidenav {
                    height: 100%;
                    /*background-color: #000;*/
                    background-size:100% 100%;
                    background-image: url('img/back2.jpg');
                    overflow-x: hidden;
                    padding-top: 20px;
                }
                .izquierda-logo{
                    display: block;
                }
                .derecha-logo{
                    display: none;
                }

                .main {
                    padding: 0px 10px;
                }

                @media screen and (max-height: 450px) {
                    .sidenav {padding-top: 15px;}
                }

                @media screen and (max-width: 450px) {
                    .login-form{
                        margin-top: 10%;
                    }

                    .register-form{
                        margin-top: 10%;
                    }
                }

                @media screen and (min-width: 768px){
                    .main{
                        margin-left: 40%; 
                    }

                    .sidenav{
                        background-image: url('img/back5.jpg');
                        width: 40%;
                        position: fixed;
                        z-index: 1;
                        top: 0;
                        left: 0;
                    }
                .izquierda-logo{
                    display: none;
                }
                .derecha-logo{
                    display: block;
                }
                    .login-form{
                        margin-top: 20%;
                    }

                    .register-form{
                        margin-top: 20%;
                    }
                }


                .login-main-text{
                    margin-top: 20%;
                    padding: 60px;
                    color: #fff;
                }

                .login-main-text h2{
                    font-weight: 300;
                }

                .btn-black{
                    background-color: #000 !important;
                    color: #fff;
                }      
            </style>
            <!-- Optional JavaScript; choose one of the two! -->

            <!-- Option 1: jQuery and Bootstrap Bundle (includes Popper) -->
            <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>

            <!-- Option 2: Separate Popper and Bootstrap JS -->

            <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
            <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.min.js" integrity="sha384-+YQ4JLhjyBLPDQt//I+STsc9iw4uQqACwlvpslubQzn4u2UU2UFM80nGisd026JF" crossorigin="anonymous"></script>

    </body>
</html>