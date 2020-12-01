<?php 

include 'conexion.php';

$id_usuario=$_POST['id_usuario'];
//$id_usuario='104';

$sql = "SELECT * FROM solicitud INNER JOIN estado_solicitud ON solicitud.estado_solicitud = estado_solicitud.id_estado_solicitud WHERE id_usuario = '".$id_usuario."' ORDER BY fecha desc";

$query = $conexion->query($sql);

$datos = array();

while($resultado = $query->fetch_assoc()){
	$datos[]= $resultado;
}

echo json_encode(array("MisSolicitudes" => $datos));

 ?>