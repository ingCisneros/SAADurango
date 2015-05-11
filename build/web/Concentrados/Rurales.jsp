<%-- 
    Document   : Rurales
    Created on : 10/04/2015, 04:16:10 PM
    Author     : anonimus
--%>
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
        <script src="../js/concentrados/rural.js"></script>
        <script src="../js/select2.js"></script>
    </head>
    <body>
        <div class="container" >
            <h1>SIALSS</h1>
            <h4>SISTEMA INTEGRAL DE ADMINISTRACIÓN Y LOGÍSTICA PARA SERVICIOS DE SALUD</h4>
            <input name="usuName" id="usuName" type="hidden" value="<%=usua%>" >
            <%@include file="../jspf/menuPrincipal.jspf" %>
            <div>
                <h3>Impresión de folios rurales</h3>
                <br/>
                <h3>Concentrado diario</h3>
                <br/>
                
                <div class="row">
                    
                    <h4 class="col-sm-2" >Seleccionar fecha:</h4>

                    <div class="col-sm-3" >
                       
                        <input id="fecCons" class="form-control" name="fecCons" placeholder="Fecha"  >
                    </div>

                    <div class="col-sm-4" >
                        <button id="ConsNormal"  name="ConsNormal" type="button" value="ConsNormal" class="btn btn-info" >Imprimir concentrado</button>
                    </div>


                </div>
                <br/>
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

                    <div class="col-sm-2 " >

                        <input id="fec" class="form-control" name="fec" placeholder="Fecha"  >
                    </div>

                    <div class="col-sm-2" >
                        <button id="imprTodosNormal" onclick="reload();" name="cons" type="button" value="normalFec" class="btn btn-info" >Mostrar todas</button>
                    </div>

                    <div class="col-sm-2" >
                        <button id="imprTodos"  name="cons" type="button"  class="btn btn-danger" >Imprimir Selección</button>
                    </div>
                     <h4 class="col-sm-2" >Seleccionar todos:</h4>
                    <div class="col-sm-1" >
                        <input  type="checkbox" class="checkbox" name="todasImpr" id="todasImpr">
                    </div>
                </div> 
                
                  <div class="row  " id="byUni" >

                    <h4 class="col-sm-1" >Seleccionar Unidad:</h4>

                    <div class="col-sm-4 col-md-4 col-lg-4 " >

                        <input class="form-control" id="txtUni"  >
                        
                    </div>
                    <div class="col-sm-2" >
                        <button id="showAllUni"  name="cons" type="button"  class="btn btn-success" >Ver todas las unidades</button>
                    </div>
                    <h4 class="col-sm-2" >Seleccionar todos:</h4>
                    <div class="col-sm-1" >
                        <input  type="checkbox" class="checkbox" name="todasImpr" id="todasImpr">
                    </div>
                    <div class="col-sm-2" >
                        <button id="imprUniAll"  name="cons" type="button"  class="btn btn-danger" >Imprimir Selección</button>
                    </div>
                    
                </div>  
                 <div class="row  " id="byUniAndFec" >
                   <input id="clUni" type="hidden" value="">
                    <h4 class="col-sm-2" >Seleccionar Unidad:</h4>

                    <div class="col-sm-4 col-md-4 col-lg-4 " >

                        <input class="form-control" id="txtUni1" >
                        
                    </div>
                    <h4 class="col-sm-2" >Seleccionar fecha:</h4>
                    <div class="col-sm-2 " >

                        <input id="fec1" class="form-control" name="fec" placeholder="Fecha"  >
                    </div>
                    <div class="col-sm-1" >
                        <button id="imprUniAndFec"  name="cons" type="button"  class="btn btn-info" >Buscar</button>
                    </div>
                    <div class="col-sm-1" >
                        <button id="imprUniAndFecAll"  name="cons" type="button"  class="btn btn-danger" >Impr. Sel</button>
                    </div>
                    <br/>
                    <br/>
                  
                    <h4 class="col-sm-2" id="labelAll" >Seleccionar todos:</h4>
                    <div class="col-sm-1" >
                        <input  type="checkbox" class="checkbox" name="todasImpr" id="FecUNiCheck">
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
