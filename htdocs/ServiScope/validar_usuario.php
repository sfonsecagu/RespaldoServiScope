<?php 

include 'conexion.php';


$usu_email=$_POST['email'];
$usu_contrasena=$_POST['contrasena'];

//$usu_email='encriptado.com';
//$usu_contrasena='Contrasena';



$consulta=$conexion->prepare(" SELECT * FROM usuario WHERE email=? AND contrasena=MD5(?) ");
$consulta->bind_param('ss', $usu_email, $usu_contrasena);
$consulta->execute();

$resultado = $consulta->get_result();
if ($fila = $resultado-> fetch_assoc()){
	echo json_encode($fila,JSON_UNESCAPED_UNICODE);
}
$consulta->close();
$conexion->close();
 ?>