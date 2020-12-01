<?php 

include 'conexion.php';

$usu_rut=$_GET['id_servicio'];
//$usu_rut="1151";

$consulta=$conexion->prepare(" SELECT * FROM servicio WHERE id_servicio=? ");
$consulta->bind_param('i', $usu_rut);
$consulta->execute();
$resultado = $consulta->get_result();

while($fila=$resultado->fetch_assoc()){
	$usuario[]=$fila;
}

echo json_encode($usuario, JSON_UNESCAPED_UNICODE);

$resultado->close();
 ?>