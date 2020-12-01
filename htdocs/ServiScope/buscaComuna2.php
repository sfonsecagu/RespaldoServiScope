<?php 

include 'conexion.php';

$usu_rut=$_GET['nombre'];
//$usu_rut=utf8_decode($usu_rut);
//$usu_rut="Puente Alto";

$consulta=$conexion->prepare(" SELECT * FROM comunas WHERE id_comuna=? ");
$consulta->bind_param('i', $usu_rut);
$consulta->execute();
$resultado = $consulta->get_result();

while($fila=$resultado->fetch_array()){
	$usuario[]=$fila;
}

echo json_encode(($usuario), JSON_UNESCAPED_UNICODE);

$resultado->close();
 ?>