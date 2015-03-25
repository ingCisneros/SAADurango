<%-- 
    Document   : descargarIFIQ
    Created on : 6/03/2015, 10:53:23 AM
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

    response.setContentType("application/vnd.ms-excel");
    response.setHeader("Content-Disposition", "attachment;filename=\"Inven_" + request.getParameter("F_ClaCli") + "_" + request.getParameter("F_FecInv") + ".xls\"");
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

            }
        %>
    </tbody>
</table>
