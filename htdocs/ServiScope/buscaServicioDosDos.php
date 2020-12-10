<?php 

include 'conexion.php';

$usu_rut=$_GET['id_servicio'];
//$usu_rut="1200";

$consulta=$conexion->prepare(" SELECT so.id_solicitud, s.id_servicio, s.servicio_nombre, r.region_nombre, c.comuna_nombre
FROM (((solicitud so inner join servicio s on so.id_servicio = s.id_servicio )
INNER JOIN regiones r on so.id_region = r.id_region))
INNER JOIN comunas c on so.id_comuna = c.id_comuna
WHERE so.id_solicitud = ?");
$consulta->bind_param('i', $usu_rut);
$consulta->execute();
$resultado = $consulta->get_result();

while($fila=$resultado->fetch_assoc()){
	$usuario[]=$fila;
}

echo json_encode($usuario, JSON_UNESCAPED_UNICODE);

$resultado->close();
 ?>