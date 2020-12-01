<?php 

include 'conexion.php';

$id_solicitud=$_POST['id_solicitud'];
//$id_solicitud='1148';


$sql = "SELECT * FROM historial INNER JOIN usuario ON historial.id_usuario = usuario.id_usuario WHERE id_solicitud = '".$id_solicitud."' order by fecha_modificacion desc";

$query = $conexion->query($sql);

$datos = array();

while($resultado = $query->fetch_assoc()){
	$datos[]= $resultado;
}

echo json_encode(array("Historial" => $datos));

 ?>