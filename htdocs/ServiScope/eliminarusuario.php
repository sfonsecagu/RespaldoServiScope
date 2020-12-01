<?php 

include 'conexion.php';

//$contrasena=$_POST['contrasena'];
$id_usuario=$_POST['id_usuario'];

//$id_usuario='107';


$consulta="UPDATE usuario SET eliminado = '1', fecha_eliminado = CURRENT_TIMESTAMP where id_usuario = '".$id_usuario."'";


mysqli_query($conexion,$consulta) or die (mysqli_error());
mysqli_close($conexion);


 ?>
