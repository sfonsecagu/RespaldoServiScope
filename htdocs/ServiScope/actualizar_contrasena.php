<?php 

include 'conexion.php';

$contrasena=$_POST['contrasena'];
$email=$_POST['email'];



$consulta="UPDATE usuario SET contrasena = MD5('".$contrasena."') where email = '".$email."'";


mysqli_query($conexion,$consulta) or die (mysqli_error());
mysqli_close($conexion);


 ?>
 