<?php 

include 'conexion.php';

$usu_idusuario=$_GET['id_usuario'];
//$usu_idusuario=85;


$consulta=$conexion->prepare("SELECT * from solicitud WHERE id_usuario= ? order by fecha desc ");
$consulta->bind_param('i', $usu_idusuario);
$consulta->execute();
$resultado = $consulta->get_result();

if($fila=$resultado->fetch_array()){
	$otro[]=array_map('utf8_encode',$fila);
}

echo json_encode($otro);
$resultado->close();
 ?>