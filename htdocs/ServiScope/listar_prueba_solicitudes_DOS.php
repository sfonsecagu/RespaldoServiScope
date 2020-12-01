<?php 

include 'conexion.php';

$id_usuario=$_POST['id_usuario'];
//$id_usuario='4';

$sql = "SELECT id_solicitud, fecha, titulo, descripcion, id_region, id_comuna, id_servicio, estado_solicitud, descripcion_estado FROM solicitud INNER JOIN estado_solicitud ON solicitud.estado_solicitud = estado_solicitud.id_estado_solicitud where estado_solicitud = 3 and id_servicio = '".$id_usuario."' ORDER BY fecha desc";

$query = $conexion->query($sql);

$datos = array();


while($resultado = $query->fetch_assoc()){
	$datos[]= $resultado;
}

echo json_encode(array("Solicitudes" => $datos), JSON_UNESCAPED_UNICODE);

 ?>