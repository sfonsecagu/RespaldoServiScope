<?php 

include 'conexion.php';

$usu_rut=$_GET['id_tecnico'];
//$usu_rut=1201;



$consulta=$conexion->prepare(" SELECT * FROM tecnicos WHERE organizacion=? ");
$consulta->bind_param('s', $usu_rut);
$consulta->execute();
$resultado = $consulta->get_result();

while($fila=$resultado->fetch_assoc()){
	$usuario[]=($fila);
}

echo json_encode($usuario, JSON_UNESCAPED_UNICODE);
$resultado->close();

 ?>