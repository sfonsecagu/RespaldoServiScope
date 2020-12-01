<?php 

include 'conexion.php';

$id_solicitud=$_POST['id_solicitud'];
$id_usuario=$_POST['id_usuario'];
$titulo=$_POST['titulo'];
$titulo=utf8_decode($titulo);

//$id_solicitud=1083;
//$id_usuario=85;
//$titulo='Prueba';


$consulta="INSERT INTO `historial` (`id_solicitud`, `id_usuario`, `id_tecnico`, `titulo`, `fecha_modificacion`, `comentarios`) VALUES ('".$id_solicitud."', '".$id_usuario."','1', '".$titulo."', CURRENT_TIMESTAMP, 'Solicitud creada')";

mysqli_query($conexion,$consulta) or die (mysqli_error());
mysqli_close($conexion);


 ?>