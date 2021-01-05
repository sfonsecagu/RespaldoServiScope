<?
/////// CONEXIÓN A LA BASE DE DATOS /////////
$host = 'localhost';
$basededatos = 'ServiscopeDB';
$usuario = 'root';
$contraseña = '';

$conexion = new mysqli($host, $usuario,$contraseña, $basededatos);
mysqli_set_charset($conexion,"utf8");
if ($conexion -> connect_errno)
{
	die("Fallo la conexion:(".$conexion -> mysqli_connect_errno().")".$conexion-> mysqli_connect_error());
}

//////////////// VALORES INICIALES ///////////////////////

$tabla="";
$query="select solicitud.id_solicitud, solicitud.fecha, solicitud.titulo, regiones.region_nombre,comunas.comuna_nombre , servicio.servicio_nombre, solicitud.titulo
FROM ((solicitud INNER JOIN regiones ON solicitud.id_region = regiones.id_region )
INNER JOIN comunas ON solicitud.id_comuna = comunas.id_comuna)
INNER JOIN servicio on solicitud.id_servicio = servicio.id_servicio
ORDER BY solicitud.id_solicitud desc";

///////// LO QUE OCURRE AL TECLEAR SOBRE EL INPUT DE BUSQUEDA ////////////
if(isset($_POST['solicitudes']))
{
	$q=$conexion->real_escape_string($_POST['solicitudes']);
	$query="SELECT solicitud.id_solicitud, solicitud.fecha, solicitud.titulo, regiones.region_nombre,comunas.comuna_nombre , servicio.servicio_nombre, solicitud.titulo
FROM ((solicitud INNER JOIN regiones ON solicitud.id_region = regiones.id_region )
INNER JOIN comunas ON solicitud.id_comuna = comunas.id_comuna)
INNER JOIN servicio on solicitud.id_servicio = servicio.id_servicio WHERE solicitud.id_solicitud LIKE '%".$q."%' OR solicitud.fecha LIKE '%".$q."%' OR regiones.region_nombre LIKE '%".$q."%' OR comunas.comuna_nombre LIKE '%".$q."%' OR servicio.servicio_nombre LIKE '%".$q."%' OR solicitud.titulo LIKE '%".$q."%'";
}

$buscaSol=$conexion->query($query);
if ($buscaSol->num_rows > 0)
{
	$tabla.= 
	'<table class="table">
		<tr class="bg-primary">
			<td>ID SOLICITUD</td>
			<td>FECHA</td>
			<td>REGIÓN</td>
			<td>COMUNA</td>
			<td>SERVICIO</td>
			<td>DESCRIPCIÓN</td>
		</tr>';

	while($filaAlumnos= $buscaSol->fetch_assoc())
	{
		$tabla.=
		'<tr>
			<td>'.$filaAlumnos['id_solicitud'].'</td>
			<td>'.$filaAlumnos['fecha'].'</td>
			<td>'.$filaAlumnos['region_nombre'].'</td>
			<td>'.$filaAlumnos['comuna_nombre'].'</td>
			<td>'.$filaAlumnos['servicio_nombre'].'</td>
			<td>'.$filaAlumnos['titulo'].'</td>
		 </tr>
		';
	}

	$tabla.='</table>';
} else
	{
		$tabla="No se encontraron coincidencias con sus criterios de búsqueda.";
	}


echo $tabla;
?>
