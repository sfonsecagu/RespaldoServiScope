<?php 

include 'conexion.php';

$usu_rut=$_GET['id_estado_solicitud'];
//$usu_rut="1151";

$consulta=$conexion->prepare(" SELECT * FROM estado_solicitud WHERE id_estado_solicitud=? ");
$consulta->bind_param('i', $usu_rut);
$consulta->execute();
$resultado = $consulta->get_result();

if ($fila = $resultado-> fetch_assoc()) {
	echo json_encode($fila,JSON_UNESCAPED_UNICODE);
}


$resultado->close();
 ?>