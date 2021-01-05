$(obtener_registros_solicitudes());

function obtener_registros_solicitudes(solicitudes)
{
	$.ajax({
		url : 'consulta_solicitudes.php',
		type : 'POST',
		dataType : 'html',
		data : { solicitudes: solicitudes },
		})

	.done(function(resultado){
		$("#tabla_resultado_solicitudes").html(resultado);
	})
}

$(document).on('keyup', '#busqueda_solicitudes', function()
{
	var valorBusqueda=$(this).val();
	if (valorBusqueda!="")
	{
		obtener_registros_solicitudes(valorBusqueda);
	}
	else
		{
			obtener_registros_solicitudes();
		}
});