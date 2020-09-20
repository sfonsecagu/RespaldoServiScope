<?php 

include 'conexion.php';
$id=$_POST['id'];
$nombre=$_POST['nombre'];
$correo=$_POST['correo'];
$contrasena=$_POST['contrasena'];
//$fecha=$_POST['fecha'];

$consulta="INSERT INTO `pruebas` (`id`, `nombre`, `correo`, `contrasena`) VALUES ('".$id."', '".$nombre."', '".$correo."', '".$contrasena."')";



mysqli_query($conexion,$consulta) or die (mysqli_error());
mysqli_close($conexion);


 ?>