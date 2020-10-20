<?php 

include 'conexion.php';


$usu_email=$_POST['email'];
$usu_contrasena=$_POST['contrasena'];

//$usu_email='email';
//$usu_contrasena='contrasena';

//$usu_email='s.fonsecagutierrez@gmail.com';
//$usu_rut=6713;
//$usu_telefono=4926;


$consulta=$conexion->prepare(" SELECT * FROM usuario WHERE email=? and contrasena = MD5(?) ");
$consulta->bind_param('ss', $usu_email, $usu_contrasena);
$consulta->execute();

$resultado = $consulta->get_result();
if ($fila = $resultado-> fetch_assoc()){
	echo json_encode($fila,JSON_UNESCAPED_UNICODE);
}
$consulta->close();
$conexion->close();
 ?>