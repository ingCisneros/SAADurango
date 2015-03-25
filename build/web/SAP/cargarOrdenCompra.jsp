<%-- 
    Document   : index
    Created on : 17/02/2014, 03:34:46 PM
    Author     : Americo
--%>

<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="conn.*" %>
<!DOCTYPE html>
<%java.text.DateFormat df = new java.text.SimpleDateFormat("yyyyMMddhhmmss"); %>
<%java.text.DateFormat df2 = new java.text.SimpleDateFormat("yyyy-MM-dd"); %>
<%java.text.DateFormat df3 = new java.text.SimpleDateFormat("dd/MM/yyyy"); %>
<%

    HttpSession sesion = request.getSession();
    String usua = "";
    String tipo = "";
    if (sesion.getAttribute("nombre") != null) {
        usua = (String) sesion.getAttribute("nombre");
        tipo = (String) sesion.getAttribute("Tipo");
    } else {
        response.sendRedirect("index.jsp");
    }
    ConectionDB con = new ConectionDB();
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Estilos CSS -->
        <link href="../css/bootstrap.css" rel="stylesheet">
        <link rel="stylesheet" href="../css/cupertino/jquery-ui-1.10.3.custom.css" />
        <link href="../css/navbar-fixed-top.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="../css/dataTables.bootstrap.css">
        <!---->
        <title>SIE Sistema de Ingreso de Entradas</title>
    </head>
    <body>
        <div class="container">
            <h1>SIALSS</h1>
            <h4>SISTEMA INTEGRAL DE ADMINISTRACIÓN Y LOGÍSTICA PARA SERVICIOS DE SALUD</h4>
            <%@include file="../jspf/menuPrincipal.jspf" %>
        </div>
        <div class="container">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">Carga de Ordenes de Compra</h3>
                </div>
                <div class="panel-body ">
                    <form method="post" class="jumbotron"  action="../SubeOrdenSapServlet" enctype="multipart/form-data" name="form1" onsubmit="subeOrdenCompra()">
                        <div class="form-group">
                            <div class="row">
                                <h4 class="col-lg-4">Seleccione el Excel a Cargar</h4>
                            </div>
                            <!--label for="Clave" class="col-xs-2 control-label">Clave*</label>
                            <div class="col-xs-2">
                                <input type="text" class="form-control" id="Clave" name="Clave" placeholder="Clave" onKeyPress="return tabular(event, this)" autofocus >
                            </div-->
                            <div class="row">
                                <h4 class="col-lg-4">Fecha Estimada de Recepción</h4>
                                <div class="col-sm-3"> 
                                    <input type="date" class="form-control" required name="F_Fecha" />
                                </div>
                            </div>
                            <div class="row">
                                <h4 class="col-xs-2">Nombre Archivo*</h4>
                                <div class="col-sm-5">
                                    <input class="form-control" type="file" name="file1" id="file1" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" required/>                                    
                                </div>
                            </div>
                        </div>
                        <button class="btn btn-block btn-primary" type="submit" name="accion" value="guardar" id="btnOrdnCompra" onclick="return valida_alta();"> Cargar Archivo</button>
                    </form>
                    <div class="text-center" id="Loader">
                        <img src="../imagenes/ajax-loader-1.gif" height="150" />
                    </div>
                    <div>
                        <h6>Los campos marcados con * son obligatorios</h6>
                    </div>
                </div>
            </div>
        </div>


        <!-- 
        ================================================== -->
        <!-- Se coloca al final del documento para que cargue mas rapido -->
        <!-- Se debe de seguir ese orden al momento de llamar los JS -->
        <script src="../js/jquery-1.9.1.js"></script>
        <script src="../js/bootstrap.js"></script>
        <script src="../js/jquery-ui-1.10.3.custom.js"></script>
        <script src="../js/jquery.dataTables.js"></script>
        <script src="../js/dataTables.bootstrap.js"></script>
        <script>
                            $(document).ready(function() {
                                $('#Loader').toggle();
                            });
                            function subeOrdenCompra() {
                                $('#btnOrdnCompra').attr('disabled', true);
                                $('#Loader').toggle();
                            }
        </script>

    </body>
</html>
