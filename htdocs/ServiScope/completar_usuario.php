<?php 

include 'conexion.php';

$region=$_POST['region'];
$comuna=$_POST['comuna'];
$direccion=$_POST['direccion'];
$email=$_POST['email'];


$consulta="UPDATE usuario SET region = '".$region."', comuna = '".$comuna."', direccion = '".$direccion."', tipo_usuario = 1 where email = '".$email."'";


mysqli_query($conexion,$consulta) or die (mysqli_error());
mysqli_close($conexion);


 ?>
 