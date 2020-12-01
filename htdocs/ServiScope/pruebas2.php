<?php 

include 'conexion.php';

//$contrasena=$_POST['contrasena'];
//$email=$_POST['email'];
$email='1';


$consulta="UPDATE usuario SET eliminado = '0' where id_usuario = '".$email."'";


mysqli_query($conexion,$consulta) or die (mysqli_error());
mysqli_close($conexion);


 ?>


 <?php 





 