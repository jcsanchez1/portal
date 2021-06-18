<%-- 
    Document   : reg-index
    Created on : Jun 12, 2021, 1:02:14 AM
    Author     : juanc
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html>
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">


        <title>Sistema de registro- universidad</title>
        <!-- Font Awesome Icons -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="css/adminlte.min.css">
        <!-- Google Font: Source Sans Pro -->
        <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">
    </head>
    <body class="hold-transition sidebar-mini">
        <div class="wrapper">

            <!-- Navbar -->
            <nav class="main-header navbar navbar-expand navbar-white navbar-light">
                <!-- Left navbar links -->
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" data-widget="pushmenu" href="#" role="button"><i class="fas fa-bars"></i></a>
                    </li>
                </ul>



                <!-- Right navbar links -->
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <a class="nav-link" data-widget="control-sidebar" data-slide="true" href="#" role="button"><i
                                class="fas fa-th-large"></i></a>
                    </li>
                </ul>
            </nav>
            <!-- /.navbar -->

            <!-- Main Sidebar Container -->
            <aside class="main-sidebar sidebar-dark-primary elevation-4">
                <!-- Brand Logo -->
                <a href="index3.html" class="brand-link text-center">
                    <img src="img/logo.png" alt="Logo" class="brand-image img-circle elevation-3"
                         style="opacity: .8"><br>
                    <span class="brand-text font-weight-light">${tipo}</span>
                </a>

                <!-- Sidebar -->
                <div class="sidebar">
                    <!-- Sidebar user panel (optional) -->
                    <div class="user-panel mt-3 pb-3 mb-3 d-flex">
                        <div class="image">
                            <img src="${foto}" class="img-circle elevation-2" alt="User Image">
                        </div>
                        <div class="info">
                            <a href="#" class="d-block">${nom}</a>
                        </div>
                    </div>

                    <!-- Sidebar Menu -->
                    <nav class="mt-2">
                        <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
                            <!-- Add icons to the links using the .nav-icon class
                                 with font-awesome or any other icon font library -->
                            <li class="nav-item has-treeview menu-open">
                                <a href="#" class="nav-link active">
                                    <i class="nav-icon fas fa-tachometer-alt"></i>
                                    <p>
                                        Institucion
                                        <i class="right fas fa-angle-left"></i>
                                    </p>
                                </a>
                                <ul class="nav nav-treeview">
                                    <li class="nav-item">
                                        <a href="adminuseradmin.htm" class="nav-link active">
                                            <i class="far fa-circle nav-icon"></i>
                                            <p>Datos Universidad</p>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a href="adminusermmesa.htm" class="nav-link">
                                            <i class="far fa-circle nav-icon"></i>
                                            <p>Periodo lectivo</p>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a href="adminuservotante.htm" class="nav-link">
                                            <i class="far fa-circle nav-icon"></i>
                                            <p>Usuarios</p>
                                        </a>
                                    </li>                                   
                                    <li class="nav-item">
                                        <a href="adminuservotante.htm" class="nav-link">
                                            <i class="far fa-circle nav-icon"></i>
                                            <p>Personal Registro</p>
                                        </a>
                                    </li>                                                 
                                </ul>
                            </li>
                            <li class="nav-item has-treeview menu-open">
                                <a href="#" class="nav-link active">
                                    <i class="nav-icon fas fa-tachometer-alt"></i>
                                    <p>
                                        Carreras y clases
                                        <i class="right fas fa-angle-left"></i>
                                    </p>
                                </a>
                                <ul class="nav nav-treeview">
                                    <li class="nav-item">
                                        <a href="adminpartpindex.htm" class="nav-link">
                                            <i class="far fa-circle nav-icon"></i>
                                            <p>Carreras</p>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a href="adminmesaindex.htm" class="nav-link">
                                            <i class="far fa-circle nav-icon"></i>
                                            <p>Clases</p>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a href="#" class="nav-link">
                                            <i class="far fa-circle nav-icon"></i>
                                            <p>Asignar clases docente</p>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a href="#" class="nav-link">
                                            <i class="far fa-circle nav-icon"></i>
                                            <p>Matricular clases alumno</p>
                                        </a>
                                    </li>                                    
                                </ul>
                            </li> 
                            <li class="nav-item has-treeview menu-open">
                                <a href="#" class="nav-link active">
                                    <i class="nav-icon fas fa-tachometer-alt"></i>
                                    <p>
                                        Maestros y alumnos
                                        <i class="right fas fa-angle-left"></i>
                                    </p>
                                </a>
                                <ul class="nav nav-treeview">
                                    <li class="nav-item">
                                        <a href="administradorpapeletapresidenciable.htm" class="nav-link">
                                            <i class="far fa-circle nav-icon"></i>
                                            <p>Maestros</p>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a href="#" class="nav-link">
                                            <i class="far fa-circle nav-icon"></i>
                                            <p>Alumnos</p>
                                        </a>
                                    </li>                                                                      
                                </ul>
                            </li>                             
                            <li class="nav-item has-treeview menu-open">
                                <a href="#" class="nav-link active">
                                    <i class="nav-icon fas fa-tachometer-alt"></i>
                                    <p>
                                        Reportes
                                        <i class="right fas fa-angle-left"></i>
                                    </p>
                                </a>
                                <ul class="nav nav-treeview">
                                    <li class="nav-item">
                                        <a href="adminreportexmesa.htm" class="nav-link">
                                            <i class="far fa-circle nav-icon"></i>
                                            <p>Reporte de matricula</p>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a href="#" class="nav-link">
                                            <i class="far fa-circle nav-icon"></i>
                                            <p>Reporte generales</p>
                                        </a>
                                    </li>                                 
                                </ul>
                            </li>                            
                        </ul>
                    </nav>
                    <!-- /.sidebar-menu -->
                </div>
                <!-- /.sidebar -->
            </aside>

            <!-- Main content -->
            <div class="content">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-lg-12">                        


                            <!-- Content Wrapper. Contains page content -->
                            <div class="content-wrapper">
                                <!-- Content Header (Page header) -->
                                <div class="content-header">
                                    <div class="container-fluid">
                                        <div class="row mb-2">
                                            <div class="col-sm-6">
                                                <h1 class="m-0 text-dark">Usuarios Administradores</h1>
                                            </div><!-- /.col -->
                                            <div class="col-sm-6">
                                                <ol class="breadcrumb float-sm-right">
                                                    <li class="breadcrumb-item"><a href="#">Inicio</a></li>
                                                    <li class="breadcrumb-item active">Todos</li>
                                                </ol>
                                            </div><!-- /.col -->
                                        </div><!-- /.row -->
                                    </div><!-- /.container-fluid -->
                                </div>
                                <!-- /.content-header -->
                                <!-- Main content -->
                                <div class="content">
                                    <div class="container-fluid">
                                        <div class="row">

                                            <div class="container mt-4">
                                                <div class="card border-info">
                                                    <div class="card-header bg-info text-dark">
                                                        <a class="btn btn-primary" style="color:black" href="administradorespersonaadmin.htm">Nuevo Registro</a>

                                                    </div>
                                                    <div class="card-body">

                                                    </div>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                            </div>


                            <!-- Control Sidebar -->
                            <aside class="control-sidebar control-sidebar-dark">
                                <!-- Control sidebar content goes here -->
                                <div class="p-3">
                                    <a><img src="${foto}" alt="" height="80" width="80" /></a><br>
                                    <a>${nom}</a>
                                    <br>
                                    <a>${iden}</a>
                                    <br>
                                    <a>${muni}</a>
                                    <br>                    
                                    <a>${tipo}</a>
                                    <div class="dropdown-divider"></div>
                                    <a href="login.htm" class="dropdown-item text-white">Salir</a>

                                </div>
                            </aside>
                            <!-- /.control-sidebar -->

                            <!-- Main Footer -->
                            <footer class="main-footer">
                                <!-- To the right -->
                                <div class="copyright py-4 text-center">
                                    <div class="container"><small>Copyright © CEUTEC -  2021</small></div>
                                </div>
                            </footer>
                        </div>
                    </div>
                </div>
            </div>
            <!-- ./wrapper -->

            <!-- REQUIRED SCRIPTS -->

            <!-- jQuery -->
            <script src="js/jquery.min.js"></script>
            <!-- Bootstrap 4 -->
            <script src="js/bootstrap.bundle.min.js"></script>
            <!-- AdminLTE App -->
            <script src="js/adminlte.min.js"></script>
    </body>
</html>
