<?php 

include 'conexion.php';

$contrasena=$_POST['contrasena'];
$email=$_POST['email'];
$cod_contr=$_POST['cod_contr'];


$consulta="UPDATE usuario SET contrasena = MD5('".$contrasena."'), eliminado =0 where email = '".$email."' and cod_contr = '".$cod_contr."'";


mysqli_query($conexion,$consulta) or die (mysqli_error());
mysqli_close($conexion);


 ?>
 