<?php 

include 'conexion.php';


$id_solicitud=$_POST['id_solicitud'];
$id_tecnico=$_POST['id_tecnico'];
$titulo=$_POST['titulo'];
$comentarios=$_POST['comentarios'];

//$id_solicitud=1196;
//$id_tecnico=1209;
//$titulo="Necesito una cada para mi perro";
//$comentarios="Prueba";


$consulta="INSERT INTO `historial` (`id_solicitud`,`id_usuario`,`id_tecnico`, `titulo`, `fecha_modificacion`, `comentarios`) VALUES ('".$id_solicitud."','1','".$id_tecnico."', '".$titulo."', CURRENT_TIMESTAMP, '".$comentarios."')";

mysqli_query($conexion,$consulta) or die ("Problemas al insertar".mysqli_error($conexion));

$consulta2="UPDATE `solicitud` SET `id_tecnico` = '".$id_tecnico."', `estado_solicitud` = '5' WHERE `solicitud`.`id_solicitud` = '".$id_solicitud."'";
mysqli_query($conexion,$consulta2) or die ("Problemas al insertar2".mysqli_error($conexion));

mysqli_close($conexion);


 ?>