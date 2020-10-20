<?php 

//include 'conexion.php';
 
 //Getting the page number which is to be displayed  
 $page = $_GET['page']; 
 
 //Initially we show the data from 1st row that means the 0th row 
 $start = 0; 
 
 //Limit is 3 that means we will show 3 items at once
 $limit = 3; 
 
 //Importing the database connection 
 require_once('dbConnect.php');
 
 //Counting the total item available in the database 
 $total = mysqli_num_rows(mysqli_query($con, "SELECT id_solicitud from solicitud where id_usuario=98"));
 
 //We can go atmost to page number total/limit
 $page_limit = $total/$limit; 
 
 //If the page number is more than the limit we cannot show anything 
 if($limit=3){
 
 //Calculating start for every given page number 
 $start = ($page-1) * $limit; 
 
 //SQL query to fetch data of a range 
 $sql = "SELECT * from solicitud limit $start, $limit";
 
 //Getting result 
 $result = mysqli_query($con,$sql); 
 
 //Adding results to an array 
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
 //Displaying the array in json format 
 echo json_encode($res);
 }else{
            echo "over";
    }

 ?>