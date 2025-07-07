<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.List,entidad.Prestamo"
    %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Listar Prestamos</title>
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

.btn-aprobar {
	background-color: #49c82b;
	color: #fff;
}

.btn-aprobar:hover {
	background-color: #209e03;
}

.btn-eliminar {
	background-color: #e74c3c;
	color: #fff;
}

.btn-eliminar:hover {
	background-color: #c0392b;
}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/MasterAdmin.jsp" />

	<div class="listado-wrapper">
			<br>
				<br>
	<h2>Prestamos Pendientes de Autorización</h2>
	<br>
		<br>
		<table id="listadoPrestamos" class="display">
			<thead>
				<tr>
					<th>ID</th>
				<th>Numero Cuenta</th>
                <th>DNI Cliente</th>
                <th>Fecha alta </th>
             
                <th>Prestamo Total</th>
                <th>cuotas</th>
                <th>precio cuota</th>
                <th>Acciones</th>
                
				</tr>
		</thead>
			<tbody>
			<!-- EJEMPLO DE COMO SE VERIA EL LISTADO de prestamos pendientes -->
				
				<% 
 				String editarPrestamos=(String) request.getAttribute("editarPrestamos");
				
				List<Prestamo> listaPrestamos = (List<Prestamo>) request.getAttribute("listaPrestamos");
				
				if (listaPrestamos != null ) {
					
					for (Prestamo p : listaPrestamos) {
							boolean enEdicion = editarPrestamos != null && Integer.parseInt(editarPrestamos) == p.getIdPrestamo();
				%> 
 
 				<tr>
 				
 			<td><%= p.getIdPrestamo() %></td>
            <td><%= p.getNumeroCuenta() %></td>
            <td><%= p.getDniCliente() %></td>
            <td><%= p.getFecha() %></td>
            <td><%= p.getImporteSolicitado() %></td>
            <td><%= p.getCuotas() %></td>
            <td><%= p.getMontoMensual() %></td>
					 <td>
							<!--Formulario  para apobar  -->
						<form action="PrestamoServlet" method="post" style="display: inline;">
 							<input type="hidden" name="idPrestamo"value="<%= p.getIdPrestamo() %>"> 
							 <input type="hidden"name="accion" value="autorizar"> 
							 <button type="submit" class="btn-accion btn-aprobar">Aprobar</button> 
							 
						</form> 
						
 						<!-- Formulario  para rechazar  -->
 						<form action="PrestamoServlet" method="post" style="display: inline;"> 
							<input type="hidden" name="idPrestamo" value="<%= p.getIdPrestamo() %>">
							<input type="hidden" name="accion" value="rechazar">
 							<button type="submit"  class="btn-accion btn-eliminar">Rechazar</button> 
 						</form>
					</td> 
				</tr> 
				
 				<%			
                   } // fin for
	          } // fin if
				 %> 
    </tbody>
  </table>
</div>
<!-- 		mensaje de erro -->

 <% if (request.getAttribute("error") != null) { %>
   				 <label style="color: red;"><%= request.getAttribute("error") %></label>
				<% } %>


</body>
</html>			
			 
