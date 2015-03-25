<%-- 
    Document   : gnrSolvsSur
    Created on : 9/03/2015, 08:02:32 AM
    Author     : Americo
--%>


<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="conn.*" %>
<!DOCTYPE html>
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

    response.setContentType("application/vnd.ms-excel");
    response.setHeader("Content-Disposition", "attachment;filename=\"SolvsSur_" + request.getParameter("fecha1") + "_" + request.getParameter("fecha2") + ".xls\"");
%>

<h2>Solicitado vs Surtido</h2>
<h3>Del: <%=request.getParameter("fecha1")%> al <%=request.getParameter("fecha2")%></h3>

<%
    try {
        con.conectar();
        int ori1 = 0, ori2 = 0;
        ResultSet rset = con.consulta("select SUM(F_CantSur) as F_CantSur from facturas where F_Origen = '1' and F_FecApl between '" + request.getParameter("fecha1") + "' and '" + request.getParameter("fecha2") + "' group by F_Origen ");
        while (rset.next()) {
            ori1 = rset.getInt("F_CantSur");
        }

        rset = con.consulta("select SUM(F_CantSur) as F_CantSur from facturas where F_Origen = '2' and F_FecApl between '" + request.getParameter("fecha1") + "' and '" + request.getParameter("fecha2") + "' group by F_Origen ");
        while (rset.next()) {
            ori2 = rset.getInt("F_CantSur");
        }
%>
<h4>Origen 1: <%=ori1%></h4>
<h4>Origen 2: <%=ori2%></h4>
<%

        con.cierraConexion();
    } catch (Exception e) {
        out.println(e.getMessage());
    }
%>
<table>
    <tr>
        <td>Clave</td>
        <td>Descripcion</td>
        <td>Solicitado</td>
        <td>Surtido</td>
    </tr>
    <%
        try {
            con.conectar();
            ResultSet rset = con.consulta("select F_ClaPro, F_DesPro, SUM(F_CantReq) as F_CantReq, SUM(F_CantSur) as F_CantSur from facturas where F_FecApl between '" + request.getParameter("fecha1") + "' and '" + request.getParameter("fecha2") + "' group by F_ClaPro ");
            while (rset.next()) {
    %>
    <tr>
        <td><%=rset.getString("F_ClaPro")%></td>
        <td><%=rset.getString("F_DesPro")%></td>
        <td><%=rset.getString("F_CantReq")%></td>
        <td><%=rset.getString("F_CantSur")%></td>
    </tr>
    <%
            }
            con.cierraConexion();
        } catch (Exception e) {
            out.println(e.getMessage());
        }
    %>
</table>