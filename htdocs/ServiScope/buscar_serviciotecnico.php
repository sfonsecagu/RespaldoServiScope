<?php 

include 'conexion.php';

$usu_rut=$_GET['id_tecnico'];
//$usu_rut=1201;



$consulta=$conexion->prepare(" SELECT tecnicos.id_tecnico, regiones.region_nombre,comunas.comuna_nombre, tecnicos.organizacion, tecnicos.direccion, tecnicos.descripcion_tecnico, tecnicos.valoracion, tecnicos.imagen_cert, servicio.servicio_nombre
FROM (((tecnicos INNER JOIN regiones ON tecnicos.id_region = regiones.id_region)
INNER JOIN comunas ON tecnicos.id_comuna = comunas.id_comuna)
INNER JOIN servicio ON tecnicos.id_servicio_tecnico = servicio.id_servicio)
WHERE organizacion=?");
$consulta->bind_param('s', $usu_rut);
$consulta->execute();
$resultado = $consulta->get_result();

while($fila=$resultado->fetch_assoc()){
	$usuario[]=($fila);
}

echo json_encode($usuario, JSON_UNESCAPED_UNICODE);
$resultado->close();

 ?>