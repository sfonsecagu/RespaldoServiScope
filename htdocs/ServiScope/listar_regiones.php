<?php 

include 'conexion.php';

$consulta=$conexion->prepare("SELECT * FROM regiones where id_region=7 or 0");
$consulta->execute();

$resultado= $consulta->get_result();

while($fila = $resultado->fetch_array()){
	$nombre[]=array_map('utf8_encode', $fila);
}

echo json_encode($nombre);
$resultado->close();
 ?>