<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home Cliente</title>
</head>
<style>
/* ---------- Reset / básicos ---------- */
body {
	font-family: Arial;
	background-color: #f8f9fa;
	font-size: 14px;
	padding: 0;
}

/* ---------- Layout principal ---------- */
.listado-wrapper {
	background-image:
		url('<%=request.getContextPath()%>/imagenes/principal.jpg');
	background-size: cover;
	background-position: center;
	background-repeat: no-repeat;
	padding: 20px;
	max-width: 100%;
	margin: auto;
}

.contenedor-flex {
	display: flex;
	gap: 20px;
	max-width: 1400px;
	margin: auto;
	align-items: stretch;
}

.columna-izquierda, .columna-derecha {
	background-color: rgba(200, 230, 255, 0.7);
	padding: 20px;
	border: 1px solid #ccc;
	border-radius: 8px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, .2);
	box-sizing: border-box;
	display: flex;
	flex-direction: column;
}

.columna-izquierda {
	flex: 3;
}

.columna-derecha {
	flex: 1;
	align-items: center;
	gap: 15px;
}

.acciones {
	margin-bottom: 20px;
	text-align: center;
	margin: 20px auto;
}

select {
	width: 300px;
	padding: 8px;
	font-size: 14px;
	border: 1px solid #ccc;
	border-radius: 5px;
	margin: 8px 0;
}

/* ---------- Botones genéricos ---------- */
input[type="submit"] {
	width: 180px; /* ancho fijo, no se estira */
	padding: 10px 15px;
	font-size: 14px;
	border: none;
	border-radius: 5px;
	background: #3498db;
	color: #fff;
	cursor: pointer;
}

input[type="submit"]:hover {
	background: #2980b9;
}

/* ---------- Botones Ver movimientos / Transferencias ---------- */
.botones-mov {
	display: flex;
	justify-content: center; /* centrados en la fila */
	gap: 15px;
	margin: 20px 0;
}

/* ---------- Títulos ---------- */
h2 {
	margin-bottom: 30px; /* espacio debajo del título */
	text-align: center;
}

h3 {
	margin: 30px 0 20px;
	text-align: center;
}

/* ---------- Tabla ---------- */
.tabla-contenedor {
	overflow-x: auto;
}

table {
	width: 100%;
	border-collapse: collapse;
	margin-top: 10px;
	background: #fff;
}

th, td {
	padding: 12px;
	border: 1px solid #ccc;
	white-space: nowrap;
	text-align: center;
}

th {
	background: #2c3e50;
	color: #fff;
}

tr:hover {
	background: #f2f2f2;
}

.saldo-box {
	margin: 10px auto 0;
	/* arriba 10px, auto para centrar horizontalmente, 0 abajo */
	padding: 6px 10px;
	width: fit-content;
	font-size: 15px;
	color: #333;
	text-align: center; 3;
	font-weight: bold;
}

fieldset {
	border: 5px solid #2c3e50;
	border-radius: 8px;
	width: 300px;
}

label {
	display: inline-block;
	width: 60px;
}

input[type="date"] {
	width: 200px;
}

.fechas-rango {
	display: flex;
	flex-direction: column;
	align-items: center;
	margin-top: 20px;
	gap: 10px;
}

.acciones label, .acciones select {
	display: inline-block; /* para que text-align centre ambos */
}
</style>
</head>

<body>

	<jsp:include page="/WEB-INF/MasterUsuario.jsp" />

	<div class="listado-wrapper">
		<div class="contenedor-flex">


			<div class="columna-izquierda">

<!-- 				<h2>Bienvenido Cliente</h2> -->

				<div class="acciones">
					<form>
						<fieldset style="width: 300px; padding: 10px;">
							<legend>
								<strong>Seleccione Número de Cuenta:</strong>
							</legend>
							<select name="cbu" id="cbu" required>
								<option value="">-- Seleccione una cuenta --</option>
								<option value="42623741521">42623741521</option>
								<option value="120584923">120584923</option>
								<option value="82719302">82719302</option>
							</select>
						</fieldset>
						<div class="saldo-box">
							<fieldset style="width: 300px; padding: 10px;">

								<legend>Saldo</legend>
								<label><strong>$</strong></label> <label><strong>100000 ponele</strong></label>
							</fieldset>
						</div>
					</form>
				</div>


				<div class="botones-mov">
					<input type="submit" value="Todos los movimientos">


				</div>
				<div class="fechas-rango">
					<div>
						<label for="fecha_desde">Desde:</label> <input type="date"
							id="fecha_desde" name="fecha_desde">
					</div>

					<div>
						<label for="fecha_hasta">Hasta:</label> <input type="date"
							id="fecha_hasta" name="fecha_hasta">
					</div>

					<input type="submit" value="Ver">
				</div>


				<h3>Historial de movimientos realizados en cuenta seleccionada:</h3>

				<div class="tabla-contenedor">
					<table>
						<thead>
							<tr>
								<th>N° Movimiento</th>
								<th>Cuenta Origen</th>
								<th>Cuenta Destino</th>
								<th>Monto</th>
								<th>Fecha</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>001</td>
								<td>315‑629344/1</td>
								<td>001‑000001/0</td>
								<td>$5 000</td>
								<td>2025‑06‑01</td>
							</tr>
							<tr>
								<td>002</td>
								<td>315‑629344/1</td>
								<td>214‑789654/2</td>
								<td>$7 200</td>
								<td>2025‑06‑03</td>
							</tr>
							<tr>
								<td>003</td>
								<td>214‑789654/2</td>
								<td>314‑629309/1</td>
								<td>$2 500</td>
								<td>2025‑06‑05</td>
							</tr>
						</tbody>
					</table>
				</div>

			</div>


			<div class="columna-derecha">


				<input type="submit" value="Transferencias"> 
				
				<form action="${pageContext.request.contextPath}/UsuarioClienteServlet" method="get">
    			<input type="hidden" name="accion" value="solicitarPrestamo">
				<input	type="submit" value="Solicitud Prestamo"> 
				</form>
				
				<input type="submit" value="Pagar Préstamo"> 
				
				<form action="${pageContext.request.contextPath}/UsuarioClienteServlet" method="post">
    			<input type="hidden" name="accion" value="verInformacionPersonal" />
    			<input type="submit" value="Datos Personales" />
				</form>
			</div>

		</div>
	</div>


</body>
</html>

