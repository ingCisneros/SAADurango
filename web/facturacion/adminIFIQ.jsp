<%-- 
    Document   : adminIFIQ
    Created on : 9/01/2015, 11:43:42 AM
    Author     : Americo
--%>

<%@page import="java.text.DecimalFormatSymbols"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="conn.*" %>
<!DOCTYPE html>
<%java.text.DateFormat df = new java.text.SimpleDateFormat("yyyyMMddhhmmss"); %>
<%java.text.DateFormat df2 = new java.text.SimpleDateFormat("yyyy-MM-dd"); %>
<%java.text.DateFormat df3 = new java.text.SimpleDateFormat("dd/MM/yyyy"); %>
<%
    DecimalFormat formatter = new DecimalFormat("#,###,###");
    DecimalFormatSymbols custom = new DecimalFormatSymbols();
    custom.setDecimalSeparator('.');
    custom.setGroupingSeparator(',');
    formatter.setDecimalFormatSymbols(custom);
    HttpSession sesion = request.getSession();
    String usua = "";
    String tipo = "";
    if (sesion.getAttribute("nombre") != null) {
        usua = (String) sesion.getAttribute("nombre");
        tipo = (String) sesion.getAttribute("Tipo");
    } else {
        response.sendRedirect("../index.jsp");
    }
    ConectionDB con = new ConectionDB();

%>
<html>
    <head>
        <!-- Estilos CSS -->
        <link href="../css/bootstrap.css" rel="stylesheet">
        <link rel="stylesheet" href="../css/cupertino/jquery-ui-1.10.3.custom.css" />
        <link href="../css/navbar-fixed-top.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="../css/dataTables.bootstrap.css">
        <!---->
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SIALSS</title>
    </head>
    <body class="container">
        <h1>SIALSS</h1>

        <%@include file="../jspf/menuPrincipal.jspf" %>
        <h3>Administración de IFIQ's</h3>
        <hr/>

        <div class="row">
            <form method="get" action="adminIFIQ.jsp">
                <h4 class="col-sm-2">Seleccione la U.A.</h4>
                <div class="col-sm-5">
                    <select class="form-control" name="F_ClaCli">
                        <option value="">Seleccione</option>
                        <%                        try {
                                con.conectar();
                                ResultSet rset = con.consulta("select F_ClaCli, F_NomCli from tb_uniatn");
                                while (rset.next()) {
                        %>
                        <option value="<%=rset.getString("F_ClaCli")%>"><%=rset.getString("F_Nomcli")%></option>
                        <%
                                }
                                con.cierraConexion();
                            } catch (Exception e) {
                                out.println(e.getMessage());
                            }
                        %>
                    </select>
                </div>
                <div class="col-sm-1">
                    <button class="btn btn-block btn-primary" name="accion" value="Buscar">Buscar</button>
                </div>
            </form>
        </div>
        <hr/>
        <%
            try {
                con.conectar();
                if (request.getParameter("accion").equals("Buscar")) {
        %>
        <div>
            <form method="get" action="../ActualizarIFIQ">
                <input value="<%=request.getParameter("F_ClaCli")%>" name="F_ClaCli" class="hidden" />
                <table class="table table-bordered table-condensed table-striped" id="tablaIFIQ">
                    <thead>
                        <tr>
                            <td>Clave</td>
                            <td>Descripción</td>
                            <td>Cantidad</td>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            ResultSet rset = con.consulta("select F_ClaPro, F_DesPro from tb_medica");
                            while (rset.next()) {
                                int cant = 0;
                                ResultSet rset2 = con.consulta("select F_Cant from tb_ifiq where F_ClaPro = '" + rset.getString("F_ClaPro") + "' and F_ClaCli = '" + request.getParameter("F_ClaCli") + "'");
                                while (rset2.next()) {
                                    cant = rset2.getInt("F_Cant");
                                }
                        %>
                        <tr>
                            <td><%=rset.getString("F_ClaPro")%></td>
                            <td><%=rset.getString("F_DesPro")%></td>
                            <td><input class="form-control" value="<%=cant%>" name="<%=rset.getString("F_ClaPro")%>" /></td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
                    <button name="accion" value="Actualizar" class="btn btn-block btn-primary">Actualizar</button>
            </form>
        </div>
        <%
                    con.cierraConexion();
                }
            } catch (Exception e) {
                //out.println(e.getMessage());
            }
        %>
    </body>
    <!-- 
        ================================================== -->
    <!-- Se coloca al final del documento para que cargue mas rapido -->
    <!-- Se debe de seguir ese orden al momento de llamar los JS -->
    <script src="../js/funcRutas.js"></script>
    <script src="../js/jquery-1.9.1.js"></script>
    <script src="../js/bootstrap.js"></script>
    <script src="../js/jquery-ui-1.10.3.custom.js"></script>
    <script src="../js/jquery.dataTables.js"></script>
    <script src="../js/dataTables.bootstrap.js"></script>
    <script>
        $(document).ready(function() {
            $('#tablaIFIQ').dataTable();
        });
    </script>
</html>
