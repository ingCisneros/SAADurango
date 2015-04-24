<%-- 
    Document   : RutaDiaria
    Created on : 10/04/2015, 04:29:51 PM
    Author     : anonimus
--%>
<%@page import="Modelos.cons"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<%
    HttpSession sesion = request.getSession(true);
    String usua = "";
    String tipo = "";
    if (sesion.getAttribute("nombre") != null) {
        usua = (String) sesion.getAttribute("nombre");
        tipo = (String) sesion.getAttribute("Tipo");
    } else {
        response.sendRedirect("index.jsp");
    }


%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Estilos CSS -->
        <link href="../css/bootstrap.css" rel="stylesheet" media="screen">
        <link href="../css/ajax/jquery.dataTables.css" rel="stylesheet" media="screen">
        <link href="../css/ajax/dataTables.tableTools.css" rel="stylesheet" media="screen">
        <link href="../css/ajax/jquery-ui.css" rel="stylesheet" media="screen">
        <link href="../css/ajax/jquery-ui.theme.css" rel="stylesheet" media="screen">
        <link href="../css/ajax/jquery-ui.structure.css" rel="stylesheet" media="screen">
        <link href="../css/select2.css" rel="stylesheet">
        <title>SIE Sistema de Ingreso de Entradas</title>
        
           <script type="text/javascript" src="../js/ajax/jquery.js"></script>
        <script type="text/javascript" src="../js/ajax/bootstrap.min.js"></script>
        <script type="text/javascript" src="../js/ajax/jquery.dataTables.js"></script>
        <script type="text/javascript" src="../js/ajax/dataTables.tableTools.js"></script>
        <script type="text/javascript" src="../js/ajax/jquery-ui.js"></script>
        <script src="../js/concentrados/concen.js"></script>
        <script src="../js/select2.js"></script>
    </head>
    <body>
        <div class="container" >
            <h1>SIALSS</h1>
            <h4>SISTEMA INTEGRAL DE ADMINISTRACIÓN Y LOGÍSTICA PARA SERVICIOS DE SALUD</h4>
            <input name="usuName" id="usuName" type="hidden" value="<%=usua%>" >
            <%@include file="../jspf/menuPrincipal.jspf" %>
            <div>
                <h3>Impresión de folios de ruta diaria</h3>
                <!--div class="row">

                    <h4 class="col-sm-2" >Seleccionar fecha:</h4>

                    <div class="col-sm-3" >
                       
                        <input id="fec" class="form-control" name="fec" placeholder="Fecha"  >
                    </div>

                    <div class="col-sm-4" >
                        <button id="imprTodosNormal" onclick="reload();" name="cons" type="button" value="normalFec" class="btn btn-info" >Mostrar todas</button>
                    </div>

                    <div class="col-sm-3" >
                        <button id="imprTodos" onclick="allImpr();" name="cons" type="button"  class="btn btn-danger" >Imrpimir toda la lista</button>
                    </div>

                </div-->

                <h4>Seleccione cricterio de búsqueda:</h4>
                <br/>
                <div class="row" >

                    <label  class="col-lg-2 co2-md-2 col-sm-2 control-label " for="radioUni" >Por unidad: </label>
                    <div class="col-lg-1 col-md-1 col-sm-1">
                        <input class="radio" id="radioUni" name="search" type="radio" value="radioUni" >
                    </div>
                    <label  class="col-lg-2 col-md-2 col-sm-2 control-label " for="radioFecUni" >Por fecha y unidad: </label>
                    <div class="col-lg-1 col-md-1 col-sm-1">
                        <input class="radio" id="radioFecUni" name="search" type="radio" value="radioFecUni" >
                    </div>
                    <label  class="col-lg-2 col-md-2 col-sm-2 control-label " for="radioFecUni" >Por fecha: </label>
                    <div class="col-lg-1 col-md-1 col-sm-1">
                        <input class="radio" id="radioFec" name="search" type="radio" value="radioFec" >
                    </div>


                </div>

                <br/>
                <br/>
                <div class="row"  id="byFec" >

                    <h4 class="col-sm-2" >Seleccionar fecha:</h4>

                    <div class="col-sm-3 " >

                        <input id="fec" class="form-control" name="fec" placeholder="Fecha"  >
                    </div>

                    <div class="col-sm-4" >
                        <button id="imprTodosNormal" onclick="reload();" name="cons" type="button" value="normalFec" class="btn btn-info" >Mostrar todas</button>
                    </div>

                    <div class="col-sm-3" >
                        <button id="imprTodos"  name="cons" type="button"  class="btn btn-danger" >Imprimir toda la lista</button>
                    </div>
                </div> 
                
                  <div class="row  " id="byUni" >

                    <h4 class="col-sm-2" >Seleccionar Unidad:</h4>

                    <div class="col-sm-5 col-md-5 col-lg-5 " >

                        <input class="form-control" id="txtUni" >
                        
                    </div>
                    <div class="col-sm-2" >
                        <button id="showAllUni"  name="cons" type="button"  class="btn btn-success" >Ver todas las unidades</button>
                    </div>
                    <div class="col-sm-2" >
                        <button id="imprUni"  name="cons" type="button"  class="btn btn-danger" >Imprimir toda la lista</button>
                    </div>
                </div>  
               
                <br/>
                <br/>
                <div class="panel panel-primary"  >
                    <br/>
                    <div class="panel-body" id="tableTot" >

                        <div id="container">

                            <div id="dynamic"></div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
     

    </body>
</html>
