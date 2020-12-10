<?php 

include 'conexion.php';

$id_usuario=$_POST['id_usuario'];
//$id_usuario='119';

//$sql = "SELECT * FROM tecnicos WHERE id_usuario = '".$id_usuario."'";

$sql = "SELECT t.id_tecnico, t.id_region, t.id_comuna, t.organizacion, t.id_servicio_tecnico, t.valoracion,t.eliminado_tecnico , s.servicio_nombre  , r.region_nombre, c.comuna_nombre
FROM (((tecnicos t inner join servicio s on t.id_servicio_tecnico = s.id_servicio )
INNER JOIN regiones r on t.id_region = r.id_region)
INNER JOIN comunas c on t.id_comuna = c.id_comuna)
WHERE t.id_usuario = '".$id_usuario."' and t.eliminado_tecnico =0";

$query = $conexion->query($sql);

$datos = array();

while($resultado = $query->fetch_array()){
	$datos[]= $resultado;
}
//var_dump($datos);

echo json_encode(array("MisServicios" => $datos), JSON_UNESCAPED_UNICODE);
 ?>



