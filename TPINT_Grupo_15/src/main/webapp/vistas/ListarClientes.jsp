<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="entidad.Cliente"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Listado de Clientes</title>
<style>
body {
	font-family: Arial;
	background-color: #f8f9fa;
	font-size: 14px;
}

.listado-wrapper {
	padding: 20px;
	max-width: 100%;
	margin: auto;
	text-align: center;
}

.acciones {
	margin-bottom: 20px;
	text-align: center;
}

.acciones a {
	background-color: #3498db;
	color: white;
	padding: 10px 15px;
	border-radius: 5px;
	text-decoration: none;
	transition: background-color 0.3s ease;
}

.acciones a:hover {
	background-color: #2980b9;
}

table {
	width: 100%;
	max-width: 1400px;
	border-collapse: collapse;
	background-color: white;
	box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
	border: 1px solid #ccc;
	margin: auto;
}

th, td {
	padding: 12px;
	border: 1px solid #ccc;
	text-align: center;
	white-space: nowrap;
}

th {
	background-color: #2c3e50;
	color: white;
	text-align: center;
}

tr:hover {
	background-color: #f2f2f2;
}

.acciones-btn {
	display: flex;
	gap: 10px;
}

.btn-accion {
	border: none;
	padding: 7px 12px;
	border-radius: 5px;
	color: white;
	cursor: pointer;
	font-size: 14px;
	transition: background-color 0.3s ease;
}

.btn-modificar {
	background-color: #f1c40f;
	color: #2c3e50;
}

.btn-modificar:hover {
	background-color: #d4ac0d;
}

.btn-eliminar {
	background-color: #e74c3c;
}

.btn-eliminar:hover {
	background-color: #c0392b;
}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/MasterAdmin.jsp" />

	<div class="listado-wrapper">
		<div class="acciones">
			<a href="<%=request.getContextPath()%>/CargarProvincias">Nuevo
				Cliente</a>
		</div>

		<table>
			<thead>
				<tr>
					<th>DNI</th>
					<th>CUIL</th>
					<th>Nombre</th>
					<th>Apellido</th>
					<th>Sexo</th>
					<th>Nacionalidad</th>
					<th>Fecha de Nacimiento</th>
					<th>Dirección</th>
					<th>Id Localidad</th>
					<th>Id Provincia</th>
					<th>Correo Electrónico</th>
					<th>Teléfonos</th>
					<th>Usuario</th>
					<th>Contraseña</th>
					<th>Acciones</th>
				</tr>
			</thead>
			<tbody>
				<!-- OBTENEMOS LA LISTA DE LOS CLIENTES Y LA RECORREMOS -->
				
				<%
				List<Cliente> listaClientes = (List<Cliente>) request.getAttribute("listaClientes");
				if (listaClientes != null && !listaClientes.isEmpty()) {
					for (Cliente cli : listaClientes) {
				%>
				<tr>
					<td><%=cli.getDni()%></td>
					<td><%=cli.getCuil()%></td>
					<td><%=cli.getNombre()%></td>
					<td><%=cli.getApellido()%></td>
					<td><%=cli.getSexo()%></td>
					<td><%=cli.getNacionalidad()%></td>
					<td><%=cli.getFechaNacimiento()%></td>
					<td><%=cli.getDireccion()%></td>
					<td><%=cli.getIdLocalidad()%></td>
					<td><%=cli.getIdProvincia()%></td>
					<td><%=cli.getCorreoElectronico()%></td>
					<td><%=cli.getTelefonos()%></td>
					<td>FALTA DESARROLLAS JOIN</td>
					<!-- Placeholder, ver nota abajo -->
					<td>FALTA DESARROLLAS JOIN</td>
					<!-- Contraseña no se muestra -->
					<td>
						<div class="acciones-btn">
							<button type="submit" class="btn-accion btn-modificar">Modificar</button>
							<form method="post" action="ClienteServlet" onsubmit="return confirm('¿Está seguro que desea eliminar este cliente?');">
    							<input type="hidden" name="accion" value="eliminar">
    							<input type="hidden" name="dni" value="<%=cli.getDni()%>">
    						<button type="submit" class="btn-accion btn-eliminar">Eliminar</button>
							</form>
						</div>
					</td>
				</tr>
				<%
				}
				} else {
				%>
				<tr>
					<td colspan="5">No hay clientes disponibles.</td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>
	</div>
</body>
</html>