<?php 

include 'conexion.php';

$usu_rut=$_GET['id_solicitud'];
//$usu_rut="1151";

$consulta=$conexion->prepare("SELECT solicitud.*, usuario.*, estado_solicitud.*
FROM ((solicitud INNER JOIN usuario ON solicitud.id_usuario = usuario.id_usuario)
INNER JOIN estado_solicitud ON solicitud.estado_solicitud = estado_solicitud.id_estado_solicitud)
WHERE id_solicitud =?");


$consulta->bind_param('i', $usu_rut);
$consulta->execute();
$resultado = $consulta->get_result();

while($fila=$resultado->fetch_assoc()){
	$usuario[]= $fila;
}

echo json_encode($usuario);

$resultado->close();
 ?>