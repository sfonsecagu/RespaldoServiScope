<?php 

include 'conexion.php';

$d=mt_rand(1000,9999);
$usu_email=$_GET['email'];
//$usu_email="s.fonsecagutierrez@gmail.com";

$consulta=$conexion->prepare(" SELECT email, nombres, apellidos, telefono,fecha_registro,tipo_usuario,id_usuario,imagen FROM usuario WHERE email=? ");
$consulta->bind_param('s', $usu_email);
$consulta->execute();
$resultado = $consulta->get_result();

while($fila=$resultado->fetch_array()){
	$usuario[]=array_map('utf8_encode', $fila);
}

echo json_encode($usuario);
$resultado->close();

 ?>