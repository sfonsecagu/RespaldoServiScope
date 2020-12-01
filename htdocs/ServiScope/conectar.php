 <?php


$mysqli = new mysqli("localhost","root","","ServiscopeDB")


if($mysqli -> connect_errno) {
        echo "Fallo la conexion";
    } else {
        echo "Conexion exitosa";
    }
 
?>