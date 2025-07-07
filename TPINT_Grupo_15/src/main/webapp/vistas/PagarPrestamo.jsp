<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.List, entidad.Cuenta, entidad.Cuota" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Préstamo Cliente</title>

<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$('#listadoPrestamos').DataTable();
		language:{
	           emptyTable:"¡No hay préstamos pendientes!"
	       }
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

h2 {
	text-align: center;
	color: #2c3e50;
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

.btn-pagar {
	background-color: #48ab31;
	color: #fff;
}

.btn-pagar:hover {
	background-color: #31811e;
}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/MasterUsuario.jsp" />

	<div class="listado-wrapper">
		<h2>Prestamos pendientes de Pago</h2>
		<br>
		<br>
		
		<table  id="listadoPrestamos" class="display">
			<thead>
				<tr>

				
					<th>Fecha vencimiento</th>
					<th>Num Cuenta</th>
					<th>ID prestamo</th>
					<th>Cuota</th>
					<th>Monto Cuota</th>
						<th>Cuenta a debitar</th>
					<th>Acciones</th>

				</tr>
			</thead>
			<tbody>
			<!-- EJEMPLO DE COMO SE VERIA EL LISTADO de prestamos pendientes -->
				
				<% 
 				
				
				List<Cuota> listaPagarPrestamos = (List<Cuota>) request.getAttribute("listaPagarPrestamos");
				
				if (listaPagarPrestamos != null ) {
					
					for (Cuota cu : listaPagarPrestamos) {
							
				%> 
			
			
			
			
				<tr>
				<td><%= cu.getFechaVencimiento() %></td>
				 <td><%= cu.getNroCuenta() %></td>
            <td><%= cu.getIdPrestamo() %></td>
           <td><%= cu.getNroCuota() %></td>
            <td><%= cu.getMontoCuota() %></td>
            
            <!-- select con las cuentas del usuario donde vy adebitar para pagar el prestamo-->
				<td>	<select name="cuentaDestino" required>
				<option value="">-- cuenta a debitar--</option>
				<%
					//cargo la DDL .hice la consulta a la bd y me traje todas las cuentas que tenia el dni que me afané del session 
					List<Cuenta> listaCuentas = (List<Cuenta>) session.getAttribute("listaCuentas");
					if (listaCuentas != null) {
						for (Cuenta c : listaCuentas) {
				%>
				<option value="<%= c.getNumeroCuenta() %>">
					ceunta:<%= c.getNumeroCuenta() %> - saldo:$<%= c.getSaldo() %>
				</option>
				<%
						}
					}
				%>
			</select>
			</td>
			
					<td>
						<div class="acciones-btn">
							<button type="submit" class="btn-accion btn-pagar">Pagar</button>
						</div>
					</td>
				</tr>
	<%
	        }   // ← cierra for
	    }       // ← cierra if
  %>
			</tbody>
		</table>
	</div>
</body>
</html>