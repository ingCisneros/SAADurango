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

    String fol_gnkl = "", fol_remi = "", orden_compra = "", fecha = "";
    try {
        if (request.getParameter("accion").equals("buscar")) {
            fol_gnkl = request.getParameter("fol_gnkl");
            fol_remi = request.getParameter("fol_remi");
            orden_compra = request.getParameter("orden_compra");
            fecha = request.getParameter("fecha");
        }
    } catch (Exception e) {

    }
    if (fol_gnkl == null) {
        fol_gnkl = "";
        fol_remi = "";
        orden_compra = "";
        fecha = "";
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Estilos CSS -->
        <link href="css/bootstrap.css" rel="stylesheet">
        <link rel="stylesheet" href="css/cupertino/jquery-ui-1.10.3.custom.css" />
        <link href="css/navbar-fixed-top.css" rel="stylesheet">
        <link href="css/datepicker3.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="css/dataTables.bootstrap.css">
        <!---->
        <title>SIE Sistema de Ingreso de Entradas</title>
    </head>
    <body>
        <div class="container">
            <h1>SIALSS</h1>
            <h4>SISTEMA INTEGRAL DE ADMINISTRACIÓN Y LOGÍSTICA PARA SERVICIOS DE SALUD</h4>

            <%@include file="../jspf/menuPrincipal.jspf" %>

            <div>
                <h3>Revisión de Concentrados por Proveedor</h3>

                <br />
                <div class="panel panel-primary">
                    <form id="frmCons" action="concentrado" method="post" >
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-lg-2">
                                <button class="btn btn-danger btn-block "  type="submit" name="cons" value="RURALES" id="btnrual">RURALES</button>
                                <br/>
                                <br/>
                               
                            </div>
                            <div class="col-lg-2">
                                <button class="btn btn-info btn-block "   type="submit" name="cons" value="SUB-URBANOS" id="btnsuburbanos">SUB-URBANOS</button>
                                <br/>
                                <br/>
                                
                            </div>
                            <div class="col-lg-2">
                                <button class="btn btn-warning btn-block "  type="submit" name="cons"  value="CARAVANAS" id="btncaravanas">CARAVANAS</button>
                                <br/>
                                <br/>
                                
                            </div>
                            <div class="col-lg-2">
                                <button class="btn btn-success btn-block "  type="submit" name="cons"   type="button" value="ALM. JURIS" id="btnjuris">ALM. JURIS</button>
                                <br/>
                                <br/>
                                
                            </div>
                            <div class="col-lg-2">
                                <button class="btn btn-primary btn-block "  type="submit" name="cons" value="RUTA DIARIA" id="btnnormal">RUTA DIARIA</button>
                                <br/>
                                <br/>
                                
                                <input name="tipUni" type="hidden" value="normal" >
                            </div>
                        </div>
                    </div>
                    </form>
                </div>

                <h4>Folios Cancelados</h4>
                <div class="panel panel-primary">
                    <div class="panel-body">
                        <table class="table table-bordered table-striped" id="datosCompras">
                            <thead>
                                <tr>
                                    <td>No. Folio</td>
                                    <td>Punto de entrega</td>
                                    <td>Orden de Compra</td>
                                    <td>Excel</td>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    try {
                                        con.conectar();
                                        try {
                                            ResultSet rset = con.consulta("SELECT u.F_NomCli, DATE_FORMAT(f.F_FecEnt, '%d/%m/%Y') as FecEnt, l.F_ClaPro,	l.F_ClaLot,	DATE_FORMAT(l.F_FecCad, '%d/%m/%Y'),	(f.F_Cant+0) as F_Cant,	l.F_Ubica,	f.F_IdFact,	l.F_Cb,	p.F_Pzs,	(f.F_Cant DIV p.F_Pzs),	(f.F_Cant MOD p.F_Pzs) FROM	tb_facttemp_elim f,	tb_lote l,	tb_uniatn u,	tb_pzxcaja p WHERE	f.F_IdLot = l.F_IdLote AND f.F_ClaCli = u.F_ClaCli AND p.F_ClaPro = l.F_ClaPro GROUP BY f.F_IdFact;");
                                            while (rset.next()) {
                                %>
                                <tr>

                                    <td><%=rset.getString("F_IdFact")%></td>
                                    <td><%=rset.getString("F_NomCli")%></td>
                                    <td><%=rset.getString("FecEnt")%></td>
                                    <td>
                                        <a class="btn btn-block btn-primary" href="gnrConcentradoElim.jsp?fol_gnkl=<%=rset.getString("F_IdFact")%>" target="_blank">Descargar</a>
                                    </td>
                                </tr>
                                <%
                                            }
                                        } catch (Exception e) {

                                        }
                                        con.cierraConexion();
                                    } catch (Exception e) {

                                    }
                                %>
                            </tbody>
                        </table>
                    </div>
                </div>


            </div>
        </div>
        <%@include file="../jspf/piePagina.jspf" %>

        <!-- 
        ================================================== -->
        <!-- Se coloca al final del documento para que cargue mas rapido -->
        <!-- Se debe de seguir ese orden al momento de llamar los JS -->
        <script src="js/jquery-1.9.1.js"></script>
        <script src="js/bootstrap.js"></script>
        <script src="js/jquery-ui-1.10.3.custom.js"></script>
        <script src="js/bootstrap-datepicker.js"></script>
        <script src="js/jquery.dataTables.js"></script>
        <script src="js/dataTables.bootstrap.js"></script>
       
        <script>
            $(document).ready(function () {
                $('#datosCompras').dataTable();
            });
        </script>
        <script>
            $(function () {

                $("#normal").datepicker({
                    format:'yyyy-mm-dd'
                });
                 $("#rural").datepicker({
                    format:'yyyy-mm-dd'
                });
                 $("#suburbano").datepicker({
                   format:'yyyy-mm-dd'
                });
                 $("#caravanas").datepicker({
                    format:'yyyy-mm-dd'
                });
                $("#almjuris").datepicker({
                    format:'yyyy-mm-dd'
                });
            });
        </script>
    </body>
</html>

