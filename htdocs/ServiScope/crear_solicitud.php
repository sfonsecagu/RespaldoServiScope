<?php 

include 'conexion.php';

$id_usuario=$_POST['id_usuario'];
$titulo=$_POST['titulo'];
$descripcion=$_POST['descripcion'];
$id_comuna=$_POST['id_comuna'];
$direccion=$_POST['direccion'];
$id_servicio=$_POST['id_servicio'];

//$id_usuario=85;
//$titulo='Prueba';
//$descripcion='DePrueba';
//$id_comuna=88;
//$direccion='miksas';
//$id_servicio='8';


$consulta="INSERT INTO `solicitud` (`id_usuario`, `fecha`, `titulo`, `descripcion`, `id_region`, `id_comuna`, `direccion`, `valor`, `id_servicio`, `estado_solicitud`, `valoracion`) 
VALUES ('".$id_usuario."', CURRENT_TIMESTAMP, '".$titulo."', '".$descripcion."', '7', '".$id_comuna."', '".$direccion."', 0, '".$id_servicio."', 3,0)";

mysqli_query($conexion,$consulta) or die (mysqli_error());
mysqli_close($conexion);


 ?>
 