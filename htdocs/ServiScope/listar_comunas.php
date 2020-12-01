<?php 

include 'conexion.php';

$id=$_GET['id_region'];

$consulta=$conexion->prepare("SELECT *  FROM `comunas` where id_region = ? ORDER by comuna_nombre ASC");
$consulta->bind_param('i', $id);
$consulta->execute();

$resultado= $consulta->get_result();

while($fila = $resultado->fetch_assoc()){
	$nombre[]=$fila;
}

echo json_encode($nombre, JSON_UNESCAPED_UNICODE);
$resultado->close();
 ?>