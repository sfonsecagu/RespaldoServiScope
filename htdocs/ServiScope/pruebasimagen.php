<?php 

include 'conexion.php';

//$usu_rut=$_POST['imagen'];
$usu_rut="1203";

$consulta=$conexion->prepare("SELECT imagen from solicitud
WHERE id_solicitud =?");


$consulta->bind_param('i', $usu_rut);
$consulta->execute();
$resultado = $consulta->get_result();

$fila=$resultado->fetch_assoc();
 //echo json_decode($fila);


//echo $img;

$str = "Hola";
echo base64_decode($consulta->get_result());

$resultado->close();
 ?>