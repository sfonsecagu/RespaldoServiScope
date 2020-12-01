<?php 

include 'conexion.php';

$consulta=$conexion->prepare("SELECT *  FROM `servicio` ORDER by servicio_nombre ASC");
$consulta->execute();

$resultado= $consulta->get_result();

while($fila = $resultado->fetch_assoc()){
	$nombre[]= $fila;
}

echo json_encode($nombre, JSON_UNESCAPED_UNICODE);
$resultado->close();
 ?>