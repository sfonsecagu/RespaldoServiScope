<?php 

include 'conexion.php';


$id_usuario=$_POST['id_usuario'];
$contrasena=$_POST['contrasena'];
$telefono=$_POST['telefono'];

//$id_usuario='107';
//$contrasena='123';
//$telefono='985656473';


 $consultaDOS=$conexion -> prepare("SELECT * from usuario where id_usuario =? and contrasena=MD5(?) AND telefono = ?");
$consultaDOS->bind_param('isi', $id_usuario, $contrasena, $telefono);
$consultaDOS->execute();

$resultado = $consultaDOS->get_result();
if ($fila = $resultado->fetch_assoc()) {
	echo json_encode($fila, JSON_UNESCAPED_UNICODE);
}
$consultaDOS->close();
$conexion ->close();


 ?>
