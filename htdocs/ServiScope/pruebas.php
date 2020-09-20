<?php 

include 'conexion.php';

$consulta = ("SELECT id FROM pruebas");

mysqli_query($conexion,$consulta) or die (mysqli_error());
mysqli_close($conexion);
 ?>