<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="entidad.Cliente"%>
<%@ page import="entidad.Provincia"%>
<%@ page import="entidad.Localidad"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Listado de Clientes</title>
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" charset="utf8"
	src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#listadoClientes').DataTable();
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
				<% String error = (String) request.getAttribute("errorMensaje"); %>
		<% if (error != null) { %>
		    <div style="color: red; font-weight: bold; margin-bottom: 10px;"><%= error %></div>
		<% } %>
		<table id="listadoClientes" class="display">
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
					<th>Provincia</th>
					<th>Localidad</th>
					<th>Correo Electrónico</th>
					<th>Teléfonos</th>
					<th>Usuario</th>
					<th>Contraseña</th>
					<th>Acciones</th>
				</tr>
			</thead>
			<tbody>
				<!-- OBTENEMOS LA LISTA DE LOS CLIENTES Y LA RECORREMOS -->
				<!-- GUARDAMOS EL DNI DEL CLIENTE QUE SE VA A EDITAR -->
				<%
				String editarDni = (String) request.getAttribute("editarDni");
				List<Cliente> listaClientes = (List<Cliente>) request.getAttribute("listaClientes");
				List<Provincia> provincias = (List<Provincia>) request.getAttribute("provincias");
				List<Localidad> localidades = (List<Localidad>) request.getAttribute("localidades");
				if (listaClientes != null) {
					for (Cliente cli : listaClientes) {
						boolean enEdicion = editarDni != null && editarDni.equals(cli.getDni());
				%>
				<tr>
					<%
					if (enEdicion) {
					%>
					<!-- FILA EN MODO EDICIÓN -->
					<form action="ClienteServlet" method="post">
						<input type="hidden" name="accion" value="modificar"> <input
							type="hidden" name="dni" value="<%=cli.getDni()%>">
						<td><%=cli.getDni()%></td>
						<td><input type="text" name="cuil" value="<%=cli.getCuil()%>"></td>
						<td><input type="text" name="nombre"
							value="<%=cli.getNombre()%>"></td>
						<td><input type="text" name="apellido"
							value="<%=cli.getApellido()%>"></td>
						<td><input type="text" name="sexo" value="<%=cli.getSexo()%>"></td>
						<td><input type="text" name="nacionalidad"
							value="<%=cli.getNacionalidad()%>"></td>
						<td><input type="date" name="fechaNacimiento"
							value="<%=cli.getFechaNacimiento()%>"></td>
						<td><input type="text" name="direccion"
							value="<%=cli.getDireccion()%>"></td>
						<td><select name="provincia" id="provincia_<%=cli.getDni()%>"
							onchange="actualizarLocalidades('<%=cli.getDni()%>')">
								<%
								for (Provincia prov : provincias) {
									String selected = cli.getIdProvincia().equals(prov.getId()) ? "selected" : "";
								%>
								<option value="<%=prov.getId()%>" <%=selected%>><%=prov.getDescripcion()%></option>
								<%
								}
								%>
						</select></td>
						<td><select name="localidad" id="localidad_<%=cli.getDni()%>">
								<%
								for (Localidad loc : localidades) {
									if (loc.getIdProvincia_Loc().equals(cli.getIdProvincia())) {
										String selected = cli.getIdLocalidad().equals(loc.getIdLocalidad_Loc()) ? "selected" : "";
								%>
								<option value="<%=loc.getIdLocalidad_Loc()%>" <%=selected%>><%=loc.getDescripcion_Loc()%></option>
								<%
								}
								}
								%>
						</select></td>
						<td><input type="email" name="email"
							value="<%=cli.getCorreoElectronico()%>"></td>
						<td><input type="text" name="telefonos"
							value="<%=cli.getTelefonos()%>"></td>
						<td><%=cli.getNombreUsuario()%></td>
						<td><input type="text" name="password"
							value="<%=cli.getContraseniaUsuario()%>"></td>
						<td>
							<button type="submit" class="btn-accion btn-modificar">Guardar</button>
							<a href="ClienteServlet?accion=listar"
							class="btn-accion btn-eliminar">Cancelar</a>
						</td>
					</form>
					<%
					} else {
					%>
					<!-- FILA NORMAL -->
					<td><%=cli.getDni()%></td>
					<td><%=cli.getCuil()%></td>
					<td><%=cli.getNombre()%></td>
					<td><%=cli.getApellido()%></td>
					<td><%=cli.getSexo()%></td>
					<td><%=cli.getNacionalidad()%></td>
					<td><%=cli.getFechaNacimiento()%></td>
					<td><%=cli.getDireccion()%></td>
					<td><%=cli.getDescripcionProvincia()%></td>
					<td><%=cli.getDescripcionLocalidad()%></td>
					<td><%=cli.getCorreoElectronico()%></td>
					<td><%=cli.getTelefonos()%></td>
					<td><%=cli.getNombreUsuario()%></td>
					<td><%=cli.getContraseniaUsuario()%></td>
					<td>
						<div class="acciones-btn">
							<form method="get" action="ClienteServlet">
								<input type="hidden" name="accion" value="modificar"> <input
									type="hidden" name="dni" value="<%=cli.getDni()%>">
								<button type="submit" class="btn-accion btn-modificar">Modificar</button>
							</form>
							<form method="post" action="ClienteServlet"
								onsubmit="return confirm('¿Está seguro que desea eliminar este cliente?');">
								<input type="hidden" name="accion" value="eliminar"> <input
									type="hidden" name="dni" value="<%=cli.getDni()%>">
								<button type="submit" class="btn-accion btn-eliminar">Eliminar</button>
							</form>
						</div>
					</td>
					<%
					}
					%>
				</tr>
				<%
				}
				}
				%>
			</tbody>
		</table>
	</div>
<script>
    // Estructura: { idProvincia: [ { id, nombre }, ... ] }
    var localidadesPorProvincia = {};

    <% 
    List<entidad.Localidad> todasLasLocs = (List<entidad.Localidad>) request.getAttribute("localidades");
    for (entidad.Localidad loc : todasLasLocs) {
    %>
        if (!localidadesPorProvincia['<%=loc.getIdProvincia_Loc()%>']) {
            localidadesPorProvincia['<%=loc.getIdProvincia_Loc()%>'] = [];
        }
        localidadesPorProvincia['<%=loc.getIdProvincia_Loc()%>'].push({
            id: '<%=loc.getIdLocalidad_Loc()%>',
            nombre: '<%=loc.getDescripcion_Loc()%>'
        });
    <% } %>
</script>
	<script>
		function actualizarLocalidades(dni) {
			var provinciaSelect = document.getElementById("provincia_" + dni);
			var localidadSelect = document.getElementById("localidad_" + dni);
			var idProvincia = provinciaSelect.value;

			var localidades = localidadesPorProvincia[idProvincia] || [];

			// Limpiar el combo de localidades
			localidadSelect.innerHTML = "";

			localidades.forEach(function(loc) {
				var option = document.createElement("option");
				option.value = loc.id;
				option.text = loc.nombre;
				localidadSelect.appendChild(option);
			});
		}
	</script>
</body>
</html>