<?php 

include 'conexion.php';

$usu_telefono=$_POST['organizacion'];


//$usu_telefono="Prueba";


$consulta=$conexion->prepare(" SELECT * FROM tecnicos WHERE organizacion=?");
$consulta->bind_param('s',$usu_telefono);
$consulta->execute();

$resultado = $consulta->get_result();
if ($fila = $resultado-> fetch_assoc()){
	echo json_encode($fila,JSON_UNESCAPED_UNICODE);
}
$consulta->close();
$conexion->close();
 ?>