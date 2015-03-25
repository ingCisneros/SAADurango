<%-- 
    Document   : detalleIFIQ
    Created on : 6/03/2015, 10:40:33 AM
    Author     : Americo
--%>
<%@page import="java.text.DecimalFormatSymbols"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="conn.*" %>
<!DOCTYPE html>
<%
    DecimalFormat formatter = new DecimalFormat("#,###,###");
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
        <hr/>

        <%
            try {
                con.conectar();
                ResultSet rset = con.consulta("select u.F_ClaCli, u.F_NomCli, sum(i.F_Cant) as F_Cant, DATE_FORMAT(i.F_FecInv, '%d/%m/%Y') as F_FecInvD, i.F_FecInv from tb_uniatn u, tb_invenuni i where u.F_ClaCli = i.F_ClaCli and i.F_ClaCli = '" + request.getParameter("F_ClaCli") + "' and i.F_FecInv = '" + request.getParameter("F_FecInv") + "' group by i.F_ClaCli");
                while (rset.next()) {
        %>
        <h3>Unidad: <%=rset.getString("F_ClaCli")%> <%=rset.getString("F_NomCli")%> <a class="btn btn-success" href="descargarInven.jsp?F_ClaCli=<%=rset.getString("F_ClaCli")%>&F_FecInv=<%=rset.getString("F_FecInv")%>"><span class="glyphicon glyphicon-download"></span></a></h3>
        <h4>Fecha:<%=(rset.getString("F_FecInvD"))%></h4>
        <h4>Total de Piezas:<%=formatter.format(rset.getInt("F_Cant"))%></h4>

        <%
                }
                con.cierraConexion();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        %>
        <table class="table table-condensed table-bordered table-striped" id="detalleIFIQ">
            <thead>
                <tr>
                    <td>Clave</td>
                    <td>Descripción</td>
                    <td>Cantidad</td>
                </tr>
            </thead>
            <tbody>
                <%
                    try {
                        con.conectar();
                        ResultSet rset = con.consulta("select m.F_ClaPro, m.F_DesPro, i.F_Cant from tb_medica m, tb_invenuni i where m.F_ClaPro = i.F_ClaPro and i.F_ClaCli = '" + request.getParameter("F_ClaCli") + "' and F_FecInv = '" + request.getParameter("F_FecInv") + "'");
                        while (rset.next()) {
                %>
                <tr>
                    <td><%=rset.getString("F_ClaPro")%></td>
                    <td><%=rset.getString("F_DesPro")%></td>
                    <td class="text-right"><%=formatter.format(rset.getInt("F_Cant"))%></td>
                </tr>
                <%
                        }
                        con.cierraConexion();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                %>
            </tbody>
        </table>

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
                $('#detalleIFIQ').dataTable();
            });
        </script>
    </body>
</html>
