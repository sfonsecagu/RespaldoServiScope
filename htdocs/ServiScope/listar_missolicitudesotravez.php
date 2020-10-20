<?php 

//include 'conexion.php';
 

 $page = $_GET['page']; 
 

 $start = 0; 
 

 $limit = 3; 
 

 require_once('dbConnect.php');
 

 $total = mysqli_num_rows(mysqli_query($con, "SELECT id_solicitud from solicitud"));
 

 $page_limit = $total/$limit; 
 

 if($limit=3){
 

 $start = ($page-1) * $limit; 
 

 $sql = "SELECT * from solicitud limit $start, $limit";
 

 $result = mysqli_query($con,$sql); 
 

 $res = array(); 
 
 while($row = mysqli_fetch_array($result)){
 array_push($res, array(
 	"id_solicitud"=>$row['id_solicitud'],
 	"id_usuario"=>$row['id_usuario'],
 	"id_tecnico"=>$row['id_tecnico'],
 	"fecha"=>$row['fecha'],
 	"titulo"=>$row['titulo'],
 	"descripcion"=>$row['descripcion'],
 	"id_region"=>$row['id_region'],
 	"id_comuna"=>$row['id_comuna'],
 	"direccion"=>$row['direccion'],
 	"valor"=>$row['valor'],
 	"id_servicio"=>$row['id_servicio'],
 	"estado_solicitud"=>$row['estado_solicitud'],
 	"valoracion"=>$row['valoracion']));
 }

 echo json_encode($res);
 }else{
            echo "over";
    }

 ?>