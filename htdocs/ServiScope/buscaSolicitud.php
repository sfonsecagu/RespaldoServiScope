<?php 

include 'conexion.php';

$usu_rut=$_GET['id_solicitud'];

$consulta=$conexion->prepare("SELECT solicitud.id_usuario, solicitud.id_tecnico, solicitud.fecha, solicitud.titulo,solicitud.descripcion,solicitud.id_region, solicitud.id_comuna,solicitud.direccion,solicitud.id_servicio, solicitud.estado_solicitud, solicitud.valoracion, solicitud.imagen, usuario.nombres, usuario.apellidos, estado_solicitud.descripcion_estado
FROM ((solicitud INNER JOIN usuario ON solicitud.id_usuario = usuario.id_usuario)
INNER JOIN estado_solicitud ON solicitud.estado_solicitud = estado_solicitud.id_estado_solicitud)
WHERE id_solicitud = ?");

$consulta->bind_param('i', $usu_rut);
$consulta->execute();
$resultado = $consulta->get_result();

while($fila=$resultado->fetch_assoc()){
	$usuario[]= $fila;
}

echo json_encode($usuario);


$resultado->close();
 ?>


 