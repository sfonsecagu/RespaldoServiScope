<?php 

include 'conexion.php';

$nombres=$_POST['nombres'];
$apellidos=$_POST['apellidos'];
$rut=$_POST['rut'];
$email=$_POST['email'];
$region=$_POST['region'];
$comunca=$_POST['comuna'];
$direccion=$_POST['direccion'];
$telefono=$_POST['telefono'];
$contrasena=$_POST['contrasena'];
$tipo_usuario=$_POST['tipo_usuario'];
$eliminado=$_POST['eliminado'];
$imagen=$_POST['imagen'];

$path = "imagenes/$nombre.jpg";

$url = "http://$_hostname_localhost/ServiScope/$path";

file_put_contents($path,base64_decode($imagen));
$bytesArchivo=file_get_contents($path);

$nombres=utf8_decode($nombres);
$apellidos=utf8_decode($apellidos);
$direccion=utf8_decode($direccion);


$consulta="INSERT INTO `usuario` (`nombres`, `apellidos`, `rut`, `email`, `region`, `comuna`, `direccion`, `telefono`, `contrasena`, `cod_contr`, `fecha_registro`, `tipo_usuario`, `eliminado`, `fecha_eliminado`,`imagen`) VALUES ('".$nombres."', '".$apellidos."', '".$rut."', '".$email."', '7', '109', '123321123321', '".$telefono."', MD5('".$contrasena."'), NULL , CURRENT_TIMESTAMP, '4', '0', NULL, '".$imagen."')";


mysqli_query($conexion,$consulta) or die (mysqli_error());
mysqli_close($conexion);


 ?>