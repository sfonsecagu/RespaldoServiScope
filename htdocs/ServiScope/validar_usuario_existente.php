<?php 

include 'conexion.php';


$usu_email=$_POST['email'];
$usu_rut=$_POST['rut'];
$usu_telefono=$_POST['telefono'];

//$usu_email='s.fonsecagutierrez@gmail.com';
//$usu_rut=6713;
//$usu_telefono=4926;


$consulta=$conexion->prepare(" SELECT * FROM usuario WHERE email=? or rut=? or telefono=?");
$consulta->bind_param('ssi', $usu_email, $usu_rut, $usu_telefono);
$consulta->execute();

$resultado = $consulta->get_result();
if ($fila = $resultado-> fetch_assoc()){
	echo json_encode($fila,JSON_UNESCAPED_UNICODE);
}
$consulta->close();
$conexion->close();
 ?>