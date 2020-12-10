<?php 

include 'conexion.php';

$id_solicitud=$_POST['id_solicitud'];



$consulta="UPDATE solicitud SET estado_solicitud = 4 where id_solicitud = '".$id_solicitud."'";


mysqli_query($conexion,$consulta) or die (mysqli_error());
mysqli_close($conexion);


 ?>
 