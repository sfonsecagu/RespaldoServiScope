<?php 

include 'conexion.php';

$id_usuario=$_POST['id_usuario'];
$id_comuna=$_POST['id_comuna'];
$id_servicio_tecnico=$_POST['id_servicio_tecnico'];


//$id_usuario=119;
//$id_comuna=12;


$consulta=$conexion->prepare(" SELECT * FROM tecnicos WHERE id_usuario=? and id_comuna=? and id_servicio_tecnico=?");
$consulta->bind_param('iii',$id_usuario, $id_comuna, $id_servicio_tecnico);
$consulta->execute();

$resultado = $consulta->get_result();
if ($fila = $resultado-> fetch_assoc()){
	echo json_encode($fila,JSON_UNESCAPED_UNICODE);
}
$consulta->close();
$conexion->close();
 ?>