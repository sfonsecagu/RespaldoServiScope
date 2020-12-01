<?php 

include 'conexion.php';

$sql = "SELECT * FROM solicitud INNER JOIN estado_solicitud ON solicitud.estado_solicitud = estado_solicitud.id_estado_solicitud where estado_solicitud = 3 ORDER BY fecha desc";
$query = $conexion->query($sql);

$datos = array();


while($resultado = $query->fetch_assoc()){
	$datos[]= $resultado;
}

echo json_encode(array("Solicitudes" => $datos));

 ?>