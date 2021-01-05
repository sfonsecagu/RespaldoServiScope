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
$query="SELECT usuario.id_usuario, usuario.nombres, regiones.region_nombre, comunas.comuna_nombre, usuario.fecha_registro
FROM ((usuario INNER JOIN regiones ON usuario.region = regiones.id_region)
INNER JOIN comunas ON usuario.comuna = comunas.id_comuna) ORDER BY id_usuario desc";

///////// LO QUE OCURRE AL TECLEAR SOBRE EL INPUT DE BUSQUEDA ////////////
if(isset($_POST['usuarios']))
{
	$q=$conexion->real_escape_string($_POST['usuarios']);
	$query="SELECT usuario.id_usuario, usuario.nombres, regiones.region_nombre, comunas.comuna_nombre, usuario.fecha_registro 
	FROM ((usuario INNER JOIN regiones ON usuario.region = regiones.id_region)
	INNER JOIN comunas ON usuario.comuna = comunas.id_comuna) WHERE 
		usuario.id_usuario LIKE '%".$q."%' OR
		usuario.nombres LIKE '%".$q."%' OR
		regiones.region_nombre LIKE '%".$q."%' OR
		comunas.comuna_nombre LIKE '%".$q."%' OR
		usuario.fecha_registro LIKE '%".$q."%'";
}


$buscaUsu=$conexion->query($query);
if ($buscaUsu->num_rows > 0)
{
	$tabla.= 
	'<table class="table">
		<tr class="bg-primary">
			<td>ID USUARIO</td>
			<td>FECHA REGISTRO</td>
			<td>REGIÓN</td>
			<td>COMUNA</td>
			<td>Nombre</td>
		</tr>';

	while($filaAlumnos= $buscaUsu->fetch_assoc())
	{
		$tabla.=
		'<tr>
			<td>'.$filaAlumnos['id_usuario'].'</td>
			<td>'.$filaAlumnos['fecha_registro'].'</td>
			<td>'.$filaAlumnos['region_nombre'].'</td>
			<td>'.$filaAlumnos['comuna_nombre'].'</td>
			<td>'.$filaAlumnos['nombres'].'</td>
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
