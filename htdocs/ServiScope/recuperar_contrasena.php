<?php 

include 'conexion.php';

$d=mt_rand(1000,9999);
$usu_rut=$_GET['rut'];
//$usu_rut=198562440;

$insertar=$conexion->prepare("UPDATE usuario SET cod_contr = '".$d."' where rut = ? ");
$insertar->bind_param('s', $usu_rut);
$insertar->execute();


$consulta=$conexion->prepare(" SELECT * FROM usuario WHERE rut=? ");
$consulta->bind_param('s', $usu_rut);
$consulta->execute();
$resultado = $consulta->get_result();

while($fila=$resultado->fetch_array()){
	$usuario[]=array_map('utf8_encode', $fila);
}

echo json_encode($usuario);
$resultado->close();

 ?>