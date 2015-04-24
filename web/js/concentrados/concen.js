$(function () {


    $("#fec").datepicker({
        dateFormat: 'yy-mm-dd',
        onSelect: function (date) {
            $.ajax({
                url: "/SAADurango/consImpre",
                data: {ban: 2, fecha: date},
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
    $("#byUni").hide();
    $("#uniSlct").select2();
    $('input[name=search]').click(function () {

        var option = $('input[name=search]:checked').val();


        if (option === 'radioFec')
        {
            reload();
            $("#byFec").show();
            $("#byUni").hide();
            $("#imprTodos").click(function () {

                allImpr();

            });


        }
        else if (option === 'radioFecUni')
        {
            reload();
            $("#byFec").hide();
            $("#byUni").hide();
        }
        else if (option === 'radioUni')
        {
            $("#byFec").hide();
            $("#byUni").show();
            
            
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
                    $.ajax({
                        url: "/SAADurango/UniServlet",
                        dataType: "json",
                        data: request,
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
                    alert(des);
                   

                }

            });

        }



    });
    $("#showAllUni").click(function () {


        reload();


    });

    $.ajax({
        url: "/SAADurango/consImpre",
        data: {ban: 1},
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



});

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

        aDataSet.push([fol, uni, entrega, cadena, del]);
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
                {"sTitle": "Clave", "sClass": "center"},
                {"sTitle": "Descripción", "sClass": "center"},
                {"sTitle": "Lote", "sClass": "center"},
                {"sTitle": "Imprimir", "sClass": "center"},
                {"sTitle": "Eliminar", "sClass": "center"}



            ]
        });
    }
    );
}

function reload()
{
    $.ajax({
        url: "/SAADurango/consImpre",
        data: {ban: 1},
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

function impr(fol)
{
    window.open("../reimpGlobalReq.jsp?fol_gnkl=" + fol + "");

}

function allImpr()
{
    var date = $("#fec").val();


    if (confirm("¿Esta seguro de imprimir todos los folios de la lista?") === true)
    {
        if (date === '')
        {


            $.ajax({
                url: "/SAADurango/consReport",
                data: {ban: 1},
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
        else {

            alert("chido");
            $.ajax({
                url: "/SAADurango/consReport",
                data: {ban: 2, date: date},
                type: 'POST',
                async: false,
                success: function (data)
                {
                    window.open('/SAADurango/consReport?ban=2');
                }, error: function (jqXHR, textStatus, errorThrown) {

                    alert("Clave invalida");

                }



            });
        }

    }








}

function uniUmpr()
{
    var uni = $("#uniSlct").val();

    if (confirm("¿Esta seguro de imprimir todos los folios de la lista?") === true)
    {
        if (uni !== '') {

            $.ajax({
                url: "/SAADurango/consReport",
                data: {ban: 3, uni: uni},
                type: 'POST',
                async: false,
                success: function (data)
                {
                    window.open('/SAADurango/consReport?ban=3');

                }, error: function (jqXHR, textStatus, errorThrown) {

                    alert("Clave invalida");

                }



            });

        } else
        {
            $.ajax({
                url: "/SAADurango/consReport",
                data: {ban: 1},
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
