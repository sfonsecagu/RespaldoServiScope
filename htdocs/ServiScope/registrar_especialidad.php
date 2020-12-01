<?php 

include 'conexion.php';


$id_usuario=$_POST['id_usuario'];
$id_region=$_POST['id_region'];
$id_comuna=$_POST['id_comuna'];
$organizacion=$_POST['organizacion'];
$direccion=$_POST['direccion'];
$id_servicio=$_POST['id_servicio'];
$descripcion=$_POST['descripcion'];


/*
$id_usuario='104';
$id_region='14';
$id_comuna='332';
$organizacion='Prueba';
$direccion='8043jkn';
$id_servicio='4';
$descripcion='2392039jnkn';
*/

//$direccion=utf8_decode($direccion);
//$descripcion=utf8_decode($descripcion);

$consulta="INSERT INTO `tecnicos` (`id_tecnico`, `id_usuario`, `id_region`, `id_comuna`, `organizacion`,`direccion`, `id_servicio_tecnico`, `descripcion_tecnico`, `valoracion`, `imagen_cert`, `eliminado_tecnico`, `fecha_eliminado_tecnico`) 
VALUES (NULL, '".$id_usuario."', '".$id_region."', '".$id_comuna."', '".$organizacion."', '".$direccion."', '".$id_servicio."', '".$descripcion."', '0', NULL, b'0', NULL)";

mysqli_query($conexion,$consulta) or die ("Problemas al insertar".mysqli_error($conexion));
mysqli_close($conexion);


 ?>
 