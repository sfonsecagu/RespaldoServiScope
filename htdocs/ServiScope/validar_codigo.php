<?php 

include 'conexion.php';


$usu_email=$_POST['email'];
$usu_cod_contr=$_POST['cod_contr'];

//$usu_email='s.fonsecagutierrez@gmail.com';
//$usu_cod_contr=4422;


$consulta=$conexion->prepare(" SELECT * FROM usuario WHERE email=?  and cod_contr=?");
$consulta->bind_param('si', $usu_email, $usu_cod_contr);
$consulta->execute();

$resultado = $consulta->get_result();
if ($fila = $resultado-> fetch_assoc()){
	echo json_encode($fila,JSON_UNESCAPED_UNICODE);
}
$consulta->close();
$conexion->close();
 ?>