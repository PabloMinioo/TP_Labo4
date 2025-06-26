<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="entidad.Provincia"%>
<!DOCTYPE html>
<html>
<head>
<title>Alta de Cliente</title>
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

select, input[type="text"], input[type="number"], input[type="date"],
	input[type="password"], input[type="email"] {
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
	<jsp:include page="/WEB-INF/MasterAdmin.jsp" />


	<div class="contenedor">
		<form class="altaCliente" action="ClienteServlet" method="post">
			<input type="hidden" name="accion" value="alta" />
			<h2>Alta de Cliente</h2>
			<br> <label>DNI: </label><input type="text" name="dni" required><br>
			<label>CUIL: </label><input type="text" name="cuil" required><br>
			<label>Nombre: </label><input type="text" name="nombre" required><br>
			<label>Apellido: </label><input type="text" name="apellido" required><br>
			<label>Sexo: </label> <select name="sexo">
				<option value="M">Masculino</option>
				<option value="F">Femenino</option>
			</select><br> <label>Nacionalidad: </label><input type="text"
				name="nacionalidad"><br> <label>Fecha de
				Nacimiento: </label><input type="date" name="fechaNacimiento"><br>
			<label>Dirección: </label><input type="text" name="direccion"><br>


			<!-- cargo la ddl de provincias, apenas entro a al jsp, yaque primero pasa por el servlet cargga provincias y despues redirrecciona al ALtaCliente.jsp, con la ddl cargada-->
			<%
    List<Provincia> provincias = (List<Provincia>) request.getAttribute("provincias");
    if (provincias == null) {
        provincias = new ArrayList<>();
    }
%>
			<label>Provincia:</label> <select name="provincia" id="ddlProvincia"
				onchange="cargarLocalidades()" required>
				<option value="">Seleccione una provincia</option>
				<%
        for (Provincia prov : provincias) {
    %>
				<option value="<%= prov.getId() %>"><%= prov.getDescripcion() %></option>
				<%
        }
    %>
			</select><br>

			<!--CARGO la DDL LOCALIDADes ; uso javaScript para que el servlet me devulva las localiades de la porvinvia que seleccione , como? : hace la llamada AJAX al servlet CargarLocalidades enviándole el ID de la provincia seleccionada.Ese servlet responde con las localidades de esa provincia en formato JSON.

El JavaScript recibe ese JSON y llena el <select> de localidades (ddlLocalidad) dinámicamente.-->

			<label>Localidad: </label><select name="localidad" id="ddlLocalidad"
				required>
				<option value="">Seleccione una localidad</option>
			</select><br>

			<script>// aca empieza el scip conel ajax
              function cargarLocalidades() {
            	    var provinciaId = document.getElementById("ddlProvincia").value;
            	    var ddlLocalidad = document.getElementById("ddlLocalidad");

            	    // Limpiar localidades
            	    ddlLocalidad.innerHTML = '<option value="">Seleccione una localidad</option>';

            	    if(provinciaId === "") return;

            	    // Mostrar loading
            	    ddlLocalidad.innerHTML = '<option value="">Cargando...</option>';
            	    ddlLocalidad.disabled = true;

            	    console.log("Cargando localidades para provincia ID:", provinciaId);//---------lo puedo borar

            	    var xhr = new XMLHttpRequest();
            	    xhr.open('GET', '<%= request.getContextPath() %>/CargarLocalidades?provinciaId=' + encodeURIComponent(provinciaId), true);

            	    xhr.onload = function() {
            	        ddlLocalidad.disabled = false;
            	        
            	        if(xhr.status === 200) {
            	            try {
            	                console.log("Respuesta del servidor:", xhr.responseText);//---------lo puedo borar
            	                
            	                var localidades = JSON.parse(xhr.responseText);
            	                
            	                ddlLocalidad.innerHTML = '<option value="">Seleccione una localidad</option>';
            	                
            	                if(localidades.length === 0) {
            	                    ddlLocalidad.innerHTML = '<option value="">No hay localidades disponibles</option>';
            	                    console.log("No se encontraron localidades para la provincia:", provinciaId);//---------lo puedo borar
            	                } else {
            	                    localidades.forEach(function(loc) {
            	                        var option = document.createElement('option');
            	                        option.value = loc.id;
            	                        option.text = loc.descripcion;
            	                        ddlLocalidad.appendChild(option);
            	                    });
            	                    console.log("Se cargaron", localidades.length, "localidades");//---------lo puedo borar
            	                }
            	            } catch(e) {
            	                console.error("Error parseando JSON:", e);//---------lo puedo borar
            	                console.error("Respuesta recibida:", xhr.responseText);//---------lo puedo borar
            	                ddlLocalidad.innerHTML = '<option value="">Error cargando localidades</option>';
            	                alert('Error procesando respuesta del servidor');//---------lo puedo borar
            	            }
            	        } else {
            	            console.error("Error HTTP:", xhr.status, xhr.statusText);//---------lo puedo borar
            	            ddlLocalidad.innerHTML = '<option value="">Error cargando localidades</option>';
            	            alert('Error cargando localidades: ' + xhr.status);//---------lo puedo borar
            	        }
            	    };

            	    xhr.onerror = function() {
            	        ddlLocalidad.disabled = false;
            	        ddlLocalidad.innerHTML = '<option value="">Error de conexión</option>';
            	        console.error("Error de conexión");//---------lo puedo borar
            	        alert('Error de conexión al servidor');//---------lo puedo borar
            	    };

            	    xhr.send();
}
</script>

			<label>Correo Electrónico: </label><input type="email" name="email"><br>
			<label>Teléfonos: </label><input type="text" name="telefonos"><br>
			<label>Usuario: </label><input type="text" name="usuario" required><br>
			<label>Contraseña: </label><input type="password" name="password"
				required><br> <input type="submit" name="btnAgregar"
				value="Registrar">
		</form>
		<!-- Alertas de CLiente agreggado corecctamente o Error al argegar cliente -->

	</div>
	<script>
window.onload = function() {
    const params = new URLSearchParams(window.location.search);
    if (params.get("exito") === "true") {
        mostrarModal("Cliente agregado con éxito", "success");
    } else if (params.get("exito") === "false") {
        mostrarModal("Error al agregar cliente", "error");
    }
};

function mostrarModal(texto, tipo) {
    // Capa de fondo
    const overlay = document.createElement("div");
    overlay.style.position = "fixed";
    overlay.style.top = 0;
    overlay.style.left = 0;
    overlay.style.width = "100%";
    overlay.style.height = "100%";
    overlay.style.backgroundColor = "rgba(0,0,0,0.5)";
    overlay.style.display = "flex";
    overlay.style.alignItems = "center";
    overlay.style.justifyContent = "center";
    overlay.style.zIndex = 10000;

    // Caja del mensaje
    const modal = document.createElement("div");
    modal.style.backgroundColor = "white";
    modal.style.padding = "20px";
    modal.style.borderRadius = "8px";
    modal.style.maxWidth = "300px";
    modal.style.textAlign = "center";
    modal.style.boxShadow = "0 4px 10px rgba(0,0,0,0.3)";

    // Texto
    const msg = document.createElement("p");
    msg.textContent = texto;
    msg.style.marginBottom = "20px";
    msg.style.color = tipo === "success" ? "#28a745" : "#c0392b";
    msg.style.fontSize = "16px";
    modal.appendChild(msg);

    // Botón Aceptar
    const btn = document.createElement("button");
    btn.textContent = "Aceptar";
    btn.style.padding = "8px 16px";
    btn.style.border = "none";
    btn.style.borderRadius = "4px";
    btn.style.cursor = "pointer";
    btn.style.backgroundColor = tipo === "success" ? "#28a745" : "#c0392b";
    btn.style.color = "white";
    btn.onclick = () => {
        document.body.removeChild(overlay);
        // opcional: limpiar parámetros de la URL
        history.replaceState(null, "", window.location.pathname);
    };
    modal.appendChild(btn);

    overlay.appendChild(modal);
    document.body.appendChild(overlay);
}
</script>
</body>
</html>