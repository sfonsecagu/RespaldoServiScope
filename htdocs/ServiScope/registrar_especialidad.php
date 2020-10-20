<?php 

include 'conexion.php';

$id_usuario=$_POST['id_usuario'];
$id_comuna=$_POST['id_comuna'];
$direccion=$_POST['direccion'];
$id_servicio=$_POST['id_servicio'];
$descripcion=$_POST['descripcion'];

/*
$id_usuario='98';
$id_comuna='92';
$direccion='8043jkn';
$id_servicio='4';
$descripcion='2392039jnkn';
*/

$direccion=utf8_decode($direccion);
$descripcion=utf8_decode($descripcion);

$consulta="INSERT INTO `tecnico` (`id_tecnico`, `id_usuario`, `id_region`, `id_comuna`, `direccion`, `lugar_trabajo`, `id_servicio`, `descripcion`, `valoracion`, `certifiacion`, `eliminado`, `fecha_eliminado`) VALUES (NULL, '".$id_usuario."', '7', '".$id_comuna."',  '".$direccion."', NULL, '".$id_servicio."', '".$descripcion."', NULL, NULL, '0', '0000-00-00 00:00:00.000000')";




mysqli_query($conexion,$consulta) or die (mysqli_error());
mysqli_close($conexion);


 ?>
 