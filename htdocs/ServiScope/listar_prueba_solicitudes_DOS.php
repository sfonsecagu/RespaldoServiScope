<?php 

include 'conexion.php';

$id_usuario=$_POST['id_usuario'];
//$id_usuario='4';

$sql = "SELECT solicitud.id_solicitud, solicitud.fecha, solicitud.titulo, solicitud.descripcion, solicitud.id_region, solicitud.id_comuna, solicitud.id_servicio, solicitud.estado_solicitud, estado_solicitud.descripcion_estado, servicio.* 
FROM ((solicitud INNER JOIN estado_solicitud ON solicitud.estado_solicitud = estado_solicitud.id_estado_solicitud)
INNER JOIN servicio ON solicitud.id_servicio = servicio.id_servicio)
where estado_solicitud = 3 and solicitud.id_servicio = '".$id_usuario."' ORDER BY fecha desc";

$query = $conexion->query($sql);

$datos = array();


while($resultado = $query->fetch_assoc()){
	$datos[]= $resultado;
}

echo json_encode(array("Solicitudes" => $datos), JSON_UNESCAPED_UNICODE);

 ?>