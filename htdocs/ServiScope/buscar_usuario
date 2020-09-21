<?php 

include 'conexion.php';

$usu_rut=$_GET['email'];
//$usu_rut=100;



$consulta=$conexion->prepare(" SELECT * FROM usuario WHERE email=? ");
$consulta->bind_param('s', $usu_rut);
$consulta->execute();
$resultado = $consulta->get_result();

while($fila=$resultado->fetch_array()){
	$usuario[]=array_map('utf8_encode', $fila);
}

echo json_encode($usuario);
$resultado->close();

 ?>