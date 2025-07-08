<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="entidad.Cuenta"%>
<%@ page import="entidad.TipoCuenta"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Listado de Cuentas</title>
	<link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
	<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>
	<script>
		$(document).ready(function(){
			$('#listadoCuentas').DataTable();
		});
	</script>
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
			width: 95%;
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
		}

		tr:hover {
			background-color: #f2f2f2;
		}

		.acciones-btn {
			display: flex;
			gap: 10px;
			justify-content: center;
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
			<a href="<%= request.getContextPath() %>/vistas/AltaCuenta.jsp">Nueva Cuenta</a>
		</div>
		
		<%
		    String modificado = request.getParameter("modificado");
		%>
		
		<% if ("true".equals(modificado)) { %>
		    <div style="color: green; font-weight: bold; margin-bottom: 10px;">
		        ✔ El tipo de cuenta fue modificado correctamente.
		    </div>
		<% } else if ("false".equals(modificado)) { %>
		    <div style="color: red; font-weight: bold; margin-bottom: 10px;">
		        ✖ Error al modificar el tipo de cuenta.
		    </div>
		<% } %>

		<table id="listadoCuentas" class="display">
			<thead>
				<tr>
					<th>Número Cuenta</th>
					<th>DNI Cliente</th>
					<th>Fecha de Creación</th>
					<th>Tipo de Cuenta</th>
					<th>CBU</th>
					<th>Saldo</th>
					<th>Acciones</th>
				</tr>
			</thead>
			<tbody>
				<%
				String editarCuenta = (String) request.getAttribute("editarCuenta");
				List<TipoCuenta> listaTiposCuenta = (List<TipoCuenta>) request.getAttribute("listaTiposCuenta");
				List<Cuenta> listaCuentas = (List<Cuenta>) request.getAttribute("listaCuentas");

				if (listaCuentas != null) {
					for (Cuenta cu : listaCuentas) {
						boolean enEdicion = editarCuenta != null && Integer.parseInt(editarCuenta) == cu.getNumeroCuenta();
				%>
				<tr>
					<% if (enEdicion) { %>
					<!-- FILA EN MODO EDICIÓN -->
					<form action="CuentaServlet" method="post" onsubmit="return confirm('¿Está seguro que desea guardar los cambios de la cuenta?');">>
						<input type="hidden" name="accion" value="modificar">
						<input type="hidden" name="numeroCuenta" value="<%= cu.getNumeroCuenta() %>">
						<input type="hidden" name="saldo" value="<%= cu.getSaldo() %>">

						<td><%= cu.getNumeroCuenta() %></td>
						<td><%= cu.getClienteDNI() %></td>
						<td><input type="date" name="fechaCreacion" value="<%= cu.getFechaCreacion() %>"></td>
						<td>
							<select name="tipoCuenta">
								<%
								for (TipoCuenta tipo : listaTiposCuenta) {
									boolean selected = cu.getTipoCuenta() == tipo.getIdTipoCuenta();
								%>
								<option value="<%= tipo.getIdTipoCuenta() %>" <%= selected ? "selected" : "" %>>
									<%= tipo.getNombreTipo() %>
								</option>
								<% } %>
							</select>
						</td>
						<td><%= cu.getCbu() %></td>
						<td><%= cu.getSaldo() %></td>
						<td>
							<button type="submit" class="btn-accion btn-modificar">Guardar</button>
							<a href="CuentaServlet?accion=listar" class="btn-accion btn-eliminar">Cancelar</a>
						</td>
					</form>
					<% } else { %>
					<!-- FILA NORMAL -->
					<td><%= cu.getNumeroCuenta() %></td>
					<td><%= cu.getClienteDNI() %></td>
					<td><%= cu.getFechaCreacion() %></td>
					<td><%= cu.getNombreTipoCuenta() %></td>
					<td><%= cu.getCbu() %></td>
					<td><%= cu.getSaldo() %></td>
					<td>
						<div class="acciones-btn">
							<form action="CuentaServlet" method="get">
								<input type="hidden" name="accion" value="modificar">
								<input type="hidden" name="numeroCuenta" value="<%= cu.getNumeroCuenta() %>">
								<button type="submit" class="btn-accion btn-modificar">Modificar</button>
							</form>
							<form method="post" action="CuentaServlet" onsubmit="return confirm('¿Está seguro que desea eliminar esta cuenta?');">
								<input type="hidden" name="accion" value="eliminar">
								<input type="hidden" name="numeroCuenta" value="<%= cu.getNumeroCuenta() %>">
								<button type="submit" class="btn-accion btn-eliminar">Eliminar</button>
							</form>
						</div>
					</td>
					<% } %>
				</tr>
				<% } } %>
			</tbody>
		</table>
	</div>
</body>
</html>
