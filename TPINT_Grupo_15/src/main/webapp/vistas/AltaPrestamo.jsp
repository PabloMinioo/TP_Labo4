<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Alta de Préstamo</title>
<style>
body {
	font-family: Arial;
	background-color: #f8f9fa;
	font-size: 14px;
}

.contenedor {
	padding: 20px;
	max-width: 600px;
	margin: auto;
	background-color: white;
	box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
	border-radius: 10px;
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

select, input[type="number"] {
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
		<form class="altaPrestamo" action="AltaPrestamoServlet" method="post">
			<h2>Solicitud de Préstamo</h2>
			<br>
			<label>Importe solicitado:</label> <input type="number" name="importe" min="1" required><br>
			<label>Cantidad	de cuotas:</label>
			<select name="cuotas" required>
				<option value="3">3 Cuotas</option>
				<option value="6">6 Cuotas</option>
				<option value="12">12 Cuotas</option>
				<option value="18">18 Cuotas</option>
				<option value="24">24 Cuotas</option>
			</select><br> 
			<label>Cuenta para depósito del préstamo:</label> <select name="cuentaDestino" required>
				<option value="">-- Seleccionar cuenta --</option>
				<option value="12345">Cuenta 12345 - Caja de ahorro</option>
				<option value="67890">Cuenta 67890 - Cuenta corriente</option>
			</select><br>
			<input type="submit" value="Solicitar Préstamo">
		</form>
	</div>
</body>
</html>
