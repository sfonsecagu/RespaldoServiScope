<?php 

include 'conexion.php';

$id_usuario=$_POST['id_usuario'];
//$id_usuario='104';

$sql = "SELECT solicitud.* , servicio.servicio_nombre, estado_solicitud.*
FROM ((solicitud INNER JOIN estado_solicitud ON solicitud.estado_solicitud = estado_solicitud.id_estado_solicitud )
INNER JOIN servicio on solicitud.id_servicio = servicio.id_servicio)
WHERE id_usuario = '".$id_usuario."' ORDER BY fecha desc";

$query = $conexion->query($sql);

$datos = array();

while($resultado = $query->fetch_array()){
	$datos[]= $resultado;
}

echo json_encode(array("MisSolicitudes" => $datos), JSON_UNESCAPED_UNICODE);

 ?>