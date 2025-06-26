<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Información Personal</title>

<style>
	body {
		font-family: Arial;
		background-color: #f8f9fa;
		font-size: 14px;
	}
	.form-wrapper {
		display: flex;
		justify-content: center;
		align-items: center;
		/*min-height: calc(100vh - 80px); /* Resta el alto estimado del header */
		padding: 20px;
	}

	.info {
		background-color: #fff;
		padding: 20px;
		border-radius: 10px;
		box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
		width: 100%;
		max-width: 500px;
	}
	h2 {
		text-align: center;
		color: #2c3e50;
	}
	label {
		display: block;
		margin-bottom: 8px;
		font-weight: bold;
	}
</style>

</head>
<body>
<jsp:include page="/WEB-INF/MasterUsuario.jsp" />
	<div class="form-wrapper">
		<form class="info" action="InformacionPersonalServlet" method="post">
		<h2>Información Personal</h2><br>
		<label for="dni">DNI: </label>
        <label for="cuil">CUIL: </label>
        <label for="nombre">Nombre: </label>
        <label for="apellido">Apellido: </label>
        <label for="sexo">Sexo: </label>
        <label for="nacionalidad">Nacionalidad: </label>
        <label for="fecha-de-nacimiento">Fecha de Nacimiento: </label>
        <label for="direccion">Dirección: </label>
        <label for="localidad">Localidad: </label>
        <label for="provincia">Provincia: </label>
        <label for="correo-electronico">Correo Electrónico: </label>
        <label for="telefono">Teléfonos: </label>
        <label for="usuario">Usuario: </label>
		</form>
	</div>
</body>
</html>