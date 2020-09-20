<?php 

$hostname='localhost';
$database='ServiscopeDB';
$username='root';
$password='';

$conexion=new mysqli($hostname,$username,$password,$database);
if($conexion -> connect_errno){
	echo "El sitio está presentando intermitencia, lo sentimos";
}


 ?>