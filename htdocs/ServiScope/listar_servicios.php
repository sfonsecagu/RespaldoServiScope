<?php 

include 'conexion.php';

$consulta=$conexion->prepare("SELECT *  FROM `servicio` ORDER by nombre ASC");
$consulta->execute();

$resultado= $consulta->get_result();

while($fila = $resultado->fetch_array()){
	$nombre[]=array_map('utf8_encode', $fila);
}

echo json_encode($nombre);
$resultado->close();
 ?>