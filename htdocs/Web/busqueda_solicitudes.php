

<!DOCTYPE html>
<html lang="es">
	<head>
		<title>ServiScope</title>
		<link rel="icon" type="image/png" sizes="16x16" href="img/android-icon-192x192.png">
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
		<!-- ESTILOS -->
		<link href="css/estilos.css" rel="stylesheet">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
		<!-- SCRIPTS JS-->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
		<script src="peticion_solicitudes.js"></script>
	</head>
	<body>
		<header>
			<img src="img/logo.jpg" width="88" height="88.4">
			<div class="alert alert-info">
			<h3>ServiScope</h3>
			</div>
		</header>

	<div id="menu">
        <ul>
        	<input type="text" name="busqueda_solicitudes" id="busqueda_solicitudes" placeholder="Buscar...">
            <li class="btn btn-default"><a href="busqueda.php">Usuarios</a></li>
            <li class="btn btn-default"><a href="busqueda_solicitudes.php">Solicitudes</a></li>
            <span id="titulo_Tabla">Solicitudes</span>
        </ul>
    </div>

		<section id="tabla_resultado_solicitudes">
		<!-- AQUI SE DESPLEGARA NUESTRA TABLA DE CONSULTA -->
		</section>

	</body>
</html>


