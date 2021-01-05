<?php 

include 'conexion.php';

$id_usuario=$_POST['id_usuario'];
//$id_usuario='104';

$sql = "SELECT solicitud.*, estado_solicitud.*, servicio.servicio_nombre
FROM ((`solicitud` INNER JOIN tecnicos on solicitud.id_tecnico = tecnicos.id_tecnico)
INNER JOIN estado_solicitud on solicitud.estado_solicitud = estado_solicitud.id_estado_solicitud)
INNER JOIN servicio on solicitud.id_servicio = servicio.id_servicio
WHERE tecnicos.id_usuario = '".$id_usuario."' and solicitud.estado_solicitud = 5 order by fecha desc";


$query = $conexion->query($sql);

$datos = array();

while($resultado = $query->fetch_assoc()){
	$datos[]= $resultado;
}

echo json_encode(array("MisSolicitudes" => $datos));

 ?>