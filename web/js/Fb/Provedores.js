$(function () {

   
    $("#folio_remi").focus();
    $("#btnProve").click(function(){
        
        prov();
    });
    $("#list_provee").select2();
    $("#list_provee").change(function(){
        var provedor= $("#list_provee").val();
        $("#provee").val(provedor);
        
    });
    
    

});

function prov()
{
    
    $.ajax({
        url: "EntradaManual",
        data: {ban: 1},
        type: 'POST',
        dataType: 'Json',
        success: function (data) {
            var $select = $("#list_provee");
            $select.find('option').remove();
            $('<option>').val("").text("---Seleccionar---").appendTo($select);
            $.each(data, function (key, value) {

                $('<option>').val(value).text(key).appendTo($select);
            });
        }, error: function (jqXHR, textStatus, errorThrown) {

            alert("Error contactar sistemas");

        }



    }); 
}