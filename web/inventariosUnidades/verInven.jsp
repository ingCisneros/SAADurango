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
        <h2>Inventarios de Unidades</h2>

        <hr/>
        <h3>Descargar Global</h3>
        <form action="descargarInvCruzado.jsp" method="post">
            <div class="row">
                <h4 class="col-sm-2">Seleccione Fecha:</h4>
                <div class="col-sm-2">
                    <input class="form-control" type="date" name="F_FecInv"/>
                </div>
                <div class="col-sm-2">
                    <button class="btn btn-success btn-block" type="submit"><span class="glyphicon glyphicon-download-alt"></span></button>
                </div>
            </div>
        </form>
        <hr/>
        <table class="table table-condensed table-bordered table-striped" id="tbInventarios">
            <thead>
                <tr>
                    <td>Clave Uni</td>
                    <td>Unidad</td>
                    <td>Fecha</td>
                    <td>Total Piezas</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td>INV vs IFIQ</td>
                </tr>
            </thead>
            <tbody>
                <%
                    try {
                        con.conectar();
                        ResultSet rset = con.consulta("select u.F_ClaCli, u.F_NomCli, sum(i.F_Cant) as F_Cant, DATE_FORMAT(i.F_FecInv, '%d/%m/%Y') as F_FecInvD, i.F_FecInv from tb_uniatn u, tb_invenuni i where u.F_ClaCli = i.F_ClaCli group by i.F_ClaCli, i.F_FecInv");
                        while (rset.next()) {
                %>
                <tr>
                    <td><%=rset.getString("F_ClaCli")%></td>
                    <td><%=rset.getString("F_NomCli")%></td>
                    <td><%=rset.getString("F_FecInvD")%></td>
                    <td class="text-right"><%=formatter.format(rset.getInt("F_Cant"))%></td>
                    <td>
                        <button class="btn btn-sm btn-primary btn-block" onclick="window.open('/SAADurango/inventariosUnidades/detalleInven.jsp?F_ClaCli=<%=rset.getString("F_ClaCli")%>&F_FecInv=<%=rset.getString("F_FecInv")%>', '', 'width=1200,height=800,left=50,top=50,toolbar=no')"><span class="glyphicon glyphicon-search"></span></button>
                    </td>
                    <td>
                        <a class="btn btn-sm btn-block btn-success" href="descargarInven.jsp?F_ClaCli=<%=rset.getString("F_ClaCli")%>&F_FecInv=<%=rset.getString("F_FecInv")%>"><span class="glyphicon glyphicon-download"></span></a>
                    </td>
                    <td>
                        <form action="ServletInventarios?F_ClaCli=<%=rset.getString("F_ClaCli")%>&F_FecInv=<%=rset.getString("F_FecInv")%>" method="post">
                            <button class="btn btn-sm btn-danger btn-block" name="accion" value="eliminar" onclick="return confirm('Seguro de Eliminar este Inventario?')" ><span class="glyphicon glyphicon-remove"></span></button>
                        </form>
                    </td>
                    <td>
                        <a class="btn btn-sm btn-block btn-info" href="descargarInvenVsIFIQ.jsp?F_ClaCli=<%=rset.getString("F_ClaCli")%>&F_FecInv=<%=rset.getString("F_FecInv")%>"><span class="glyphicon glyphicon-random"></span></a>
                    </td>
                </tr>
                <%
                        }
                        con.cierraConexion();
                    } catch (Exception e) {

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
                                $('#tbInventarios').dataTable();
                            });
        </script>
    </body>
</html>
