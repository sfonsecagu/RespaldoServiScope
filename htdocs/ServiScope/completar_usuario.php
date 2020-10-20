<?php 

include 'conexion.php';

$comuna=$_POST['comuna'];
$direccion=$_POST['direccion'];
$email=$_POST['email'];


$consulta="UPDATE usuario SET region = 7, comuna = '".$comuna."', direccion = '".$direccion."', tipo_usuario = 1 where email = '".$email."'";


mysqli_query($conexion,$consulta) or die (mysqli_error());
mysqli_close($conexion);


 ?>
 