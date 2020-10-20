<?php 

include 'conexion.php';

$d=mt_rand(1000,9999);
$usu_email=$_GET['id_usuario'];
//$usu_rut=198562440;

$consulta=$conexion->prepare(" SELECT * FROM tecnico WHERE id_usuario=? ");
$consulta->bind_param('s', $usu_email);
$consulta->execute();
$resultado = $consulta->get_result();

while($fila=$resultado->fetch_array()){
	$usuario[]=array_map('utf8_encode', $fila);
}

echo json_encode($usuario);
$resultado->close();

 ?>