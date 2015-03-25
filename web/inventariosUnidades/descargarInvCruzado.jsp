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
    response.setHeader("Content-Disposition", "attachment;filename=\"InvenCruzado_" + request.getParameter("F_FecInv") + ".xls\"");

%>
<table border="1">
    <tr>
        <td>ClaPro</td>
        <%            try {
                con.conectar();
                ResultSet rset = con.consulta("select F_ClaCli from tb_uniatn order by F_ClaCli+0 asc");
                while (rset.next()) {
        %>
        <td><%=rset.getString("F_ClaCli")%></td>
        <%
                }
                con.cierraConexion();
            } catch (Exception e) {

            }
        %>
    </tr>
    <%
        try {
            con.conectar();
            ResultSet rset = con.consulta("select F_ClaPro from tb_medica ");
            while (rset.next()) {
    %>
    <tr>
        <td><%=rset.getString("F_ClaPro")%></td>
        <%            try {
                con.conectar();
                ResultSet rset2 = con.consulta("select F_ClaCli from tb_uniatn order by F_ClaCli+0 asc");
                while (rset2.next()) {
                    int cant = 0;
                    ResultSet rset3 = con.consulta("select SUM(F_Cant) as F_Cant from tb_invenuni where F_ClaCli = '" + rset2.getString("F_ClaCli") + "' and F_ClaPro = '" + rset.getString("F_ClaPro") + "' and F_FecInv = '" + request.getParameter("F_FecInv") + "'");
                    while (rset3.next()) {
                        cant = rset3.getInt("F_Cant");
                    }
        %>
        <td><%=cant%></td>
        <%
                }
                con.cierraConexion();
            } catch (Exception e) {

            }
        %>
    </tr>
    <%
            }
            con.cierraConexion();
        } catch (Exception e) {

        }
    %>
</table>
