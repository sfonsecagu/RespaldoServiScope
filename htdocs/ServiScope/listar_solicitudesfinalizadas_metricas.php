<?php 

include 'conexion.php';

$id_usuario=$_POST['id_usuario'];
//$id_usuario='104';

$sql = "SELECT tecnicos.id_tecnico, solicitud.id_solicitud, solicitud.id_usuario, solicitud.fecha, solicitud.titulo, solicitud.descripcion, solicitud.id_region, solicitud.id_comuna, solicitud.direccion,solicitud.valor, solicitud.id_servicio,solicitud.estado_solicitud, solicitud.valoracion, estado_solicitud.id_estado_solicitud,estado_solicitud.descripcion_estado, servicio.servicio_nombre
FROM (((usuario INNER JOIN tecnicos ON usuario.id_usuario = tecnicos.id_usuario)
INNER JOIN solicitud ON tecnicos.id_tecnico = solicitud.id_tecnico )
INNER JOIN estado_solicitud on solicitud.estado_solicitud = estado_solicitud.id_estado_solicitud)
INNER JOIN servicio on solicitud.id_servicio = servicio.id_servicio
WHERE usuario.id_usuario = '".$id_usuario."' and solicitud.estado_solicitud=4";

$query = $conexion->query($sql);

$datos = array();

while($resultado = $query->fetch_assoc()){
	$datos[]= $resultado;
}

echo json_encode(array("MisSolicitudes" => $datos));

 ?>