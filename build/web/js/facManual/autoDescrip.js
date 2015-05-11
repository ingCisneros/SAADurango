$(function(){
    
    
    $("#DesProAuto").autocomplete({
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
                        url: "/SAADurango/descriMed",
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
                    $.ajax({
                        url: "/SAADurango/descriMed",
                        data: {ban: 1, descri: des},
                        type: 'POST',
                        async: false,
                        success: function (data)
                        {
                            $("#ClaveSel").val(data.clave).text(data.clave);
                            $("#DesSel").val(data.descrip).text(data.descrip);
                            $("#Cantidad").focus();
                        }, error: function (jqXHR, textStatus, errorThrown) {

                            alert("Clave invalida");

                        }



                    });

                }

            });
    
    
});