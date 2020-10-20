<?php 

include 'conexion.php';

$consulta=$conexion->prepare("SELECT id_solicitud, titulo, descripcion  FROM `solicitud` WHERE `id_region` = 7 ORDER by fecha DESC");
$consulta->execute();

$resultado= $consulta->get_result();

while($fila = $resultado->fetch_array()){
	$nombre[]=array_map('utf8_encode', $fila);
}

echo json_encode($nombre);
$resultado->close();
 ?>