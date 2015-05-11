$(function () {

    reload();
    $("#fec1").datepicker({
        dateFormat: 'yy-mm-dd',
        changeYear: true,
        changeMonth: true
    });
    $("#fecCons").datepicker({
        dateFormat: 'yy-mm-dd',
        changeYear: true,
        changeMonth: true
    });

    $("#fec").datepicker({
        dateFormat: 'yy-mm-dd',
        changeYear: true,
        changeMonth: true,
        onSelect: function (date) {
            $.ajax({
                url: "/SAADurango/consImpre",
                data: {ban: 10, fecha: date},
                type: 'POST',
                async: false,
                success: function (data)
                {
                    if (data === '[]')
                    {
                        alert("Fecha sin concentrados registrados");

                    } else {
                        limpiarTabla();
                        MostrarTabla(data);
                    }
                }, error: function (jqXHR, textStatus, errorThrown) {

                    alert("Clave invalida");

                }



            });
        }


    });


    $("#byFec").hide();
    $("#byUniAndFec").hide();
    $("#byUni").hide();
    $("#fecUniOnly").hide();
    $("#imprUniAndFecAll").hide();
    $("#uniSlct").select2();
    $("#FecUNiCheck").hide();
    $("#labelAll").hide();

    $('input[name=search]').click(function () {

        $("input[name='todasImpr']").prop('checked', false);
        $('.checkbox').prop('checked', false);
        $("#txtUni1").val("").text("");
        $("#txtUni").val("").text("");
        $("#FecUNiCheck").hide();
        $("#labelAll").hide();
        reload();
        var option = $('input[name=search]:checked').val();


        if (option === 'radioFec')
        {
            reload();
            $("#byFec").show();
            $("#fecUniOnly").hide();
            $("#byUni").hide();
            $("#byUniAndFec").hide();
            $("#imprTodos").click(function () {

                allImpr();

            });


        }
        else if (option === 'radioFecUni')
        {
            $("#imprUniAndFecAll").hide();
            reload();
            $("#byFec").hide();
            $("#byUni").hide();
            $("#byUniAndFec").show();
            $("#fecUniOnly").show();
            $("#txtUni1").autocomplete({
                width: 300,
                max: 10,
                delay: 100,
                minLength: 1,
                autoFocus: true,
                cacheLength: 1,
                scroll: true,
                highlight: false,
                source: function (request, response) {
                    var descrip = request.term;
                    $.ajax({
                        url: "/SAADurango/UniServlet",
                        dataType: "json",
                        data: {descrip: descrip, ban: 3},
                        type: 'GET',
                        success: function (data, textStatus, jqXHR) {
                            console.log(data);
                            var items = data;
                            response(items);

                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            console.log(textStatus);

                        }
                    });
                }, select: function (a, b) {
                    var des = b.item.value;
                    $.ajax({
                        url: "/SAADurango/UniServlet",
                        data: {ban: 2, descri: des},
                        type: 'POST',
                        async: false,
                        success: function (data)
                        {
                            $("#clUni").val(data.clave);

                        }, error: function (jqXHR, textStatus, errorThrown) {

                            alert("Clave invalida");

                        }



                    });

                }

            });
            $("#imprUniAndFec").click(function () {


                uniFecSearch();

            });



        }
        else if (option === 'radioUni')
        {
            $("#byFec").hide();
            $("#clUni").val("");
            $("#fec1").val("");
            $("#byUni").show();
            $("#fecUniOnly").hide();
            $("#byUniAndFec").hide();


            $("#txtUni").autocomplete({
                width: 300,
                max: 10,
                delay: 100,
                minLength: 1,
                autoFocus: true,
                cacheLength: 1,
                scroll: true,
                highlight: false,
                source: function (request, response) {

                    var descrip = request.term;

                    $.ajax({
                        url: "/SAADurango/UniServlet",
                        dataType: "json",
                        data: {descrip: descrip, ban: 3},
                        type: 'GET',
                        success: function (data) {


                            console.log(data);
                            var items = data;
                            response(items);

                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            console.log(textStatus);

                        }
                    });
                }, select: function (a, b) {
                    var des = b.item.value;

                    $.ajax({
                        url: "/SAADurango/UniServlet",
                        data: {ban: 2, descri: des},
                        type: 'POST',
                        async: false,
                        success: function (data)
                        {

                            $.ajax({
                                url: "/SAADurango/consImpre",
                                data: {ban: 4, uni: data.clave},
                                type: 'POST',
                                async: false,
                                success: function (data)
                                {
                                    limpiarTabla();
                                    MostrarTabla(data);

                                }, error: function (jqXHR, textStatus, errorThrown) {

                                    alert("Clave invalida");

                                }



                            });

                        }, error: function (jqXHR, textStatus, errorThrown) {

                            alert("Clave invalida");

                        }



                    });


                }

            });



        }





    });

    $("input[name='todasImpr']").change(function () {

        if ($(this).is(':checked')) {

            $('.checkbox').prop('checked', true);
        }
        else
        {
            $('.checkbox').prop('checked', false);

        }

    });
    $("#imprUniAll").click(function () {

        allImpr();

    });
    $("#imprUniAndFecAll").click(function () {

        allImpr();

    });
    
    $("#ConsNormal").click(function () {
        var fechaCons = $("#fecCons").val();

        if (fechaCons === '')
        {
            alert("Seleccionar una fecha porfavor");
            return false;
        }
        else
        {

            window.open('/SAADurango/reimpRutaSubUrb.jsp?F_FecSur=' + fechaCons + '');

        }


    });
});

function reload()
{

    $.ajax({
        url: "/SAADurango/consImpre",
        data: {ban: 9},
        type: 'POST',
        async: false,
        success: function (data)
        {
            limpiarTabla();
            MostrarTabla(data);

        }, error: function (jqXHR, textStatus, errorThrown) {

            alert("Clave invalida");

        }



    });

}
function limpiarTabla() {
    $("#example").remove();
}

function MostrarTabla(data) {
    var json = JSON.parse(data);
    var aDataSet = [];
    for (var i = 0; i < json.length; i++) {
        var fol = json[i].fol;
        var uni = json[i].unidad;
        var entrega = json[i].entrega;

        var cadena = '<button onclick="impr(' + fol + ')"  name="btn" value=' + fol + ' type="button" class="btn btn-success btn-block" ><span class=" glyphicon glyphicon-print" ></span></button>';

        var del = '<button onclick="del(' + fol + ')" name="btn" value=' + fol + ' type="button" class="btn btn-danger btn-block" ><span class=" glyphicon glyphicon-remove" ></span></button>';
        var impr = '<input  type="checkbox" class="checkbox" name="checkbox" value=' + fol + '  >';

        aDataSet.push([fol, uni, entrega, cadena, del, impr]);
    }
    $(document).ready(function () {
        $('#dynamic').html('<table class="cell-border" width="100%" cellspacing="0"  id="example"></table>');
        $('#example').dataTable({
            "aaData": aDataSet, "button": 'aceptar',
            "bScrollInfinite": true,
            "bScrollCollapse": true,
            "sScrollY": "500px",
            "bProcessing": true,
            "sPaginationType": "full_numbers",
            //"sDom": 'T<"clear">lfrtip',            
            "aoColumns": [
                {"sTitle": "Folio", "sClass": "center"},
                {"sTitle": "Unidad de atención", "sClass": "center"},
                {"sTitle": "Fecha de entrega", "sClass": "center"},
                {"sTitle": "Imprimir", "sClass": "center"},
                {"sTitle": "Eliminar", "sClass": "center"},
                {"sTitle": "Imprimir", "sClass": "center"}



            ]
        });
    }
    );
}
function impr(fol)
{
    window.open("../reimpGlobalReq.jsp?fol_gnkl=" + fol + "");

}
function del(id)
{
    var nombre = $("#usuName").val();

    if (confirm("¿Seguro de eliminar este concentrado?") === true) {


        $.ajax({
            url: "/SAADurango/consImpre",
            data: {fol: id, ban: 3, name: nombre},
            type: 'POST',
            success: function (data)
            {

                alert("Concentrado eliminado con éxito.");
                reload();

            }, error: function (jqXHR, textStatus, errorThrown) {

                alert("Clave invalida");

            }



        });


    }
    else
    {
        return false;

    }
}
function allImpr()
{
    var date = $("#fec").val();

    var checkboxValues = new Array();
    $('input[name=checkbox]:checked').each(
            function () {

                checkboxValues.push($(this).val());
            }
    );


    if (jQuery.isEmptyObject(checkboxValues))
    {

        alert("Seleccionar folios a imprimir porfavor.")
        return false;
    }
    else
    {

        if (confirm("¿Esta seguro de imprimir los folios seleccionados ?") === true)
        {


            var json = JSON.stringify(checkboxValues);


            $.ajax({
                url: "/SAADurango/consReport",
                data: {ban: 1, folios: checkboxValues},
                type: 'POST',
                async: false,
                success: function (data)
                {

                    window.open('/SAADurango/consReport?ban=1');

                }, error: function (jqXHR, textStatus, errorThrown) {

                    alert("Clave invalida");

                }



            });


        }




    }



}
function uniFecSearch()
{
    var uni = $("#clUni").val();
    var fec = $("#fec1").val();

    if (uni === '')
    {
        alert("Seleccione una unidad porfavor.");
        return false;
    }
    else if (fec === '')
    {

        alert("Seleccione una fecha porfavor.");
        return false;
    }
    else
    {

        $.ajax({
            url: "/SAADurango/consImpre",
            data: {ban: 11, fecha: fec, uni: uni},
            type: 'POST',
            async: false,
            success: function (data)
            {
                if (data === '[]')
                {
                    $("#imprUniAndFecAll").hide();
                    $("#FecUNiCheck").hide();
                    $("#labelAll").hide();
                    alert("Búsqueda sin coincidencias");

                } else {
                    $("#imprUniAndFecAll").show();
                    $("#FecUNiCheck").show();
                    $("#labelAll").show();
                    limpiarTabla();
                    MostrarTabla(data);
                }

            }, error: function (jqXHR, textStatus, errorThrown) {

                alert("Clave invalida");

            }



        });

    }

}