<?php 

include 'conexion.php';

//$id_usuario=$_POST['id_usuario'];
$id_usuario='13';


$sql = "SELECT * FROM tecnico where id_tecnico = '".$id_usuario."' ";

$query = $conexion->query($sql);

$datos = array();

while($resultado = $query->fetch_assoc()){
	$datos[]= $resultado;
}

echo json_encode(array("Historial" => $datos));

 ?>