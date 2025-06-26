<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Transferencias</title>
<style>
body {
	font-family: Arial;
	background-color: #f8f9fa;
	font-size: 14px;
}

.contenedor {
	padding: 20px;
	max-width: 800px;
	margin: auto;
	background-color: white;
	box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
	border-radius: 10px;
}

h2 {
	text-align: center;
	color: #2c3e50;
}

.form-bloque {
	margin-bottom: 25px;
}

label {
	display: block;
	margin-bottom: 8px;
	font-weight: bold;
}

select, input[type="text"], input[type="number"] {
	width: 100%;
	padding: 10px;
	margin-bottom: 15px;
	border: 1px solid #ccc;
	border-radius: 5px;
}

input[type="submit"] {
	background-color: #3498db;
	color: white;
	padding: 10px 20px;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	font-size: 14px;
}

input[type="submit"]:hover {
	background-color: #2980b9;
}
</style>
</head>
<body>

<jsp:include page="/WEB-INF/MasterUsuario.jsp" />

<div class="contenedor">
	<h2>Transferencias</h2>

	<form>
		<div class="form-bloque">
			<label for="cuentaOrigen">Cuenta Origen (propia):</label>
			<select name="cuentaOrigen" id="cuentaOrigen">
				<option value="314-629309/1">314-629309/1 - Caja de Ahorro</option>
				<option value="214-789654/2">214-789654/2 - Cuenta Corriente</option>
			</select>
		</div>

		<div class="form-bloque">
			<label for="cuentaDestinoPropia">Cuenta Destino (cuenta propia):</label>
			<select name="cuentaDestinoPropia" id="cuentaDestinoPropia">
				<option value="314-629309/1">314-629309/1 - Caja de Ahorro</option>
				<option value="214-789654/2">214-789654/2 - Cuenta Corriente</option>
			</select>
		</div>

		<div class="form-bloque">
			<label for="cbuDestino">Cuenta Destino con CBU (otro cliente):</label>
			<input type="text" name="cbuDestino" id="cbuDestino" placeholder="Ingrese CBU (22 dígitos)">
		</div>

		<div class="form-bloque">
			<label for="monto">Monto a transferir:</label>
			<input type="number" name="monto" id="monto" step="0.01"  min="0.01" placeholder="Ej: 5000.00">
		</div>

		<input type="submit" value="Realizar Transferencia">
	</form>
</div>

</body>
</html>