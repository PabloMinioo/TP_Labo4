<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<style>
	body{
		font-family: Arial, sans-serif;
	}
	.form-wrapper {
	display: flex;
	justify-content: center;
	align-items: center;
	/*min-height: calc(100vh - 80px); /* Resta el alto estimado del header */
	padding: 20px;
	}

	.form {
		background-color: #fff;
		padding: 20px;
		border-radius: 10px;
		box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
		width: 100%;
		max-width: 500px;
	}
</style>
</head>
<body>

<div class="form-wrapper">

	<form class="form" action="/TPINT_GRUPO_15_LAB4/ServletLogin" method="post">
	
	     <h2>Iniciar sesión</h2><br>
	        <label for="usuario">Usuario:</label>
	        <input type="text" id="usuario" name="usuario" required>
	        <br><br>
	
	        <label for="contrasena">Contraseña:</label>
	        <input type="password" id="contrasenia" name="contrasenia" required >
	        <br><br>
			<input type="submit"  name="btnIngresar" value="Ingresar" >
	      
	    </form>
</div>
    
     <%-- Mostrar mensaje si existe --%>
    <% if (request.getAttribute("mensaje") != null) { %>
        <div style="background-color:  #f8d7da; color: #721c24; padding: 10px; border: 1px solid #f5c6cb; border-radius: 5px; margin: 10px; text-align: center;">
            <%= request.getAttribute("mensaje") %>
        </div>
    <% } %>
</body>
</html>