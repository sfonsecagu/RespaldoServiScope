<?php 

include 'conexion.php';

//$id_usuario=$_POST['id_usuario'];
$id_usuario='104';


$sql = "SELECT * FROM tecnico INNER JOIN usuario ON tecnico.id_usuario = usuario.id_usuario WHERE tecnico.id_usuario = '".$id_usuario."' order by id_comuna desc";

$query = $conexion->query($sql);

$datos = array();

while($resultado = $query->fetch_assoc()){
	$datos[]= $resultado;
}

echo json_encode(array("MiSservicios" => $datos));

 ?>