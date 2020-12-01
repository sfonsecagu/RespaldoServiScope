<?php 

include 'conexion.php';

$consulta=$conexion->prepare(" SELECT * FROM comunas WHERE id_comuna =1");
$consulta->execute();

$resultado = $consulta->get_result();
if ($fila = $resultado-> fetch_assoc()){
	echo json_encode($fila,JSON_UNESCAPED_UNICODE);
}
$consulta->close();
$conexion->close();
 ?>
