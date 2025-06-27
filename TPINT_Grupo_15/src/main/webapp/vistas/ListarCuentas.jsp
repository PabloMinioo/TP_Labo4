<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page import="java.util.List"%>
	<%@ page import="entidad.Cuenta"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Listado de Cuentas</title>
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
			<a href="<%= request.getContextPath() %>/vistas/AltaCuenta.jsp">Nueva Cuenta</a>
		</div>

		<table>
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
				<!-- EJEMPLO DE COMO SE VERIA EL LISTADO -->
				<!-- OBTENEMOS LAS CUENTAS Y LAS LISTAMOS -->

				<%
				List<Cuenta> listaCuentas = (List<Cuenta>) request.getAttribute("listaCuentas");
				if (listaCuentas != null && !listaCuentas.isEmpty()) {
					for (Cuenta cu : listaCuentas) {
				%>
				<tr>
					<td><%=cu.getNumeroCuenta() %></td>
					<td><%=cu.getClienteDNI() %></td>
					<td><%=cu.getFechaCreacion() %></td>
					<td><%=cu.getTipoCuenta() %></td>
					<td><%=cu.getCbu() %></td>
					<td><%=cu.getSaldo() %></td>
					<td>
						<div class="acciones-btn">
								<form action="CuentaServlet" method="post" style="display:inline;" 
          						onsubmit="return confirm('¿Está seguro que desea eliminar esta cuenta?');">
      							<input type="hidden" name="accion" value="eliminar" />
     							 <input type="hidden" name="numeroCuenta" value="<%= cu.getNumeroCuenta() %>" />
      							<button type="submit" class="btn-accion btn-eliminar">Eliminar</button>
    							</form>
								<button type="submit" class="btn-accion btn-modificar">Modificar</button>
						</div>
					</td>
				</tr>
				<%
				}
				} else {
				%>
				<tr>
					<td colspan="5">No hay cuentas para mostrar.</td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>
	</div>
</body>
</html>