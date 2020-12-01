<?php 

include 'conexion.php';

$id_usuario=$_POST['id_usuario'];
$titulo=$_POST['titulo'];
$descripcion=$_POST['descripcion'];
$id_region=$_POST['id_region'];
$id_comuna=$_POST['id_comuna'];
$direccion=$_POST['direccion'];
$id_servicio=$_POST['id_servicio'];
$imagen=$_POST['imagen'];

$path = "imagenes/$nombre.jpg";

$url = "http://$_hostname_localhost/ServiScope/$path";

file_put_contents($path,base64_decode($imagen));
$bytesArchivo=file_get_contents($path);

//$id_usuario=85;
//$titulo='Prueba';
//$descripcion='DePrueba';
//$id_comuna=88;
//$direccion='miksas';
//$id_servicio='8';


$consulta="INSERT INTO `solicitud` (`id_usuario`, `id_tecnico`, `fecha`, `titulo`, `descripcion`, `id_region`, `id_comuna`, `direccion`, `valor`, `id_servicio`, `estado_solicitud`, `valoracion`, `imagen`) 
VALUES ('".$id_usuario."','1', CURRENT_TIMESTAMP, '".$titulo."', '".$descripcion."', '".$id_region."', '".$id_comuna."', '".$direccion."', 0, '".$id_servicio."', 3,0, '".$imagen."')";

mysqli_query($conexion,$consulta) or die (mysqli_error());
mysqli_close($conexion);


 ?>
 