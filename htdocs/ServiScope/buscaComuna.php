<?php 

include 'conexion.php';

$usu_rut=$_GET['nombre'];
$usu_rut=utf8_decode($usu_rut);
//$usu_rut="Puente Alto";

$consulta=$conexion->prepare(" SELECT * FROM comunas WHERE nombre=? ");
$consulta->bind_param('s', $usu_rut);
$consulta->execute();
$resultado = $consulta->get_result();

while($fila=$resultado->fetch_array()){
	$usuario[]=array_map('utf8_encode', $fila);
}

echo json_encode($usuario);

$resultado->close();
 ?>