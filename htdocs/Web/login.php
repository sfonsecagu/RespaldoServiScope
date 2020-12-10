<?php
$hostname='localhost';
$database='ServiscopeDB';
$username='root';
$password='';

$conn=new mysqli($hostname,$username,$password,$database);
if($conn -> connect_errno){
	echo "El sitio está presentando intermitencia, lo sentimos";
}
mysqli_set_charset($conn,"utf8");



$nombre = $_POST["edtUsuario"];
$pass = $_POST["edtContrasena"];

$query = mysqli_query($conn,"SELECT * FROM usuario WHERE email='".$nombre."' AND contrasena=MD5('".$pass."')");
$nr = mysqli_num_rows($query);

if ($nr == 1) {
	echo "Bienvenido: ".$nombre;
}else if ($nr == 0) {
	echo "no ingreso";
}

?>