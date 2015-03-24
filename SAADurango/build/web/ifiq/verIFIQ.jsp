<%-- 
    Document   : verIFIQ
    Created on : 6/03/2015, 09:42:49 AM
    Author     : Americo
--%>
<%@page import="java.text.DecimalFormatSymbols"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="conn.*" %>
<!DOCTYPE html>
<%    DecimalFormat formatter = new DecimalFormat("#,###,###");
    DecimalFormat formatterDecimal = new DecimalFormat("#,###,##0.00");
    DecimalFormatSymbols custom = new DecimalFormatSymbols();
    custom.setDecimalSeparator('.');
    custom.setGroupingSeparator(',');
    formatter.setDecimalFormatSymbols(custom);
    formatterDecimal.setDecimalFormatSymbols(custom);
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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Estilos CSS -->
        <link href="../css/bootstrap.css" rel="stylesheet">
        <link rel="stylesheet" href="../css/cupertino/jquery-ui-1.10.3.custom.css" />
        <link rel="stylesheet" type="text/css" href="../css/dataTables.bootstrap.css">
        <!---->
        <title>SIE Sistema de Ingreso de Entradas</title>
    </head>
    <body class="container">
        <h1>SIALSS</h1>
        <h4>SISTEMA INTEGRAL DE ADMINISTRACIÓN Y LOGÍSTICA PARA SERVICIOS DE SALUD</h4>
        <%@include file="../jspf/menuPrincipal.jspf" %>
        <h2>Inventarios de Fijos Quincenales Ideales</h2>

        <hr/>
        <table class="table table-condensed table-bordered table-striped" id="tbIFIQ">
            <thead>
                <tr>
                    <td>Clave Uni</td>
                    <td>Unidad</td>
                    <td>Total Piezas</td>
                    <td></td>
                    <td></td>
                </tr>
            </thead>
            <tbody>
                <%
                    try {
                        con.conectar();
                        ResultSet rset = con.consulta("select u.F_ClaCli, u.F_NomCli, sum(i.F_Cant) as F_Cant from tb_uniatn u, tb_ifiq i where u.F_ClaCli = i.F_ClaCli group by i.F_ClaCli");
                        while (rset.next()) {
                %>
                <tr>
                    <td><%=rset.getString("F_ClaCli")%></td>
                    <td><%=rset.getString("F_NomCli")%></td>
                    <td class="text-right"><%=formatter.format(rset.getInt("F_Cant"))%></td>
                    <td>
                        <button class="btn btn-sm btn-primary btn-block" onclick="window.open('/SAADurango/ifiq/detalleIFIQ.jsp?F_ClaCli=<%=rset.getString("F_ClaCli")%>', '', 'width=1200,height=800,left=50,top=50,toolbar=no')"><span class="glyphicon glyphicon-search"></span></button>
                    </td>
                    <td><a class="btn btn-sm btn-block btn-success" href="descargarIFIQ.jsp?F_ClaCli=<%=rset.getString("F_ClaCli")%>"><span class="glyphicon glyphicon-download"></span></a></td>
                </tr>
                <%
                        }
                        con.cierraConexion();
                    } catch (Exception e) {

                    }
                %>
            </tbody>
        </table>
        <%
        %>

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
                                $('#tbIFIQ').dataTable();
                            });
        </script>
    </body>
</html>
