<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Listar Prestamos</title>
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
		<table>
			<thead>
				<tr>
					
				<th>Numero Cuenta</th>
                <th>DNI Cliente</th>
                <th>Fecha alta </th>
                <th>Tipo de Cuenta</th>
                <th>CBU</th>
                <th>Prestamo Total</th>
                <th>cuotas pendientes</th>
                <th>cuota</th>
                <th>Acciones</th>
                
				</tr>
		</thead>
			<tbody>
		        <tr>
		            <td>12345</td>
		            <td>30123456</td>
		            <td>2024-06-01</td>
		            <td>Cuenta Corriente</td>
		            <td>1234567890123456789012</td>
		            <td>$100,000</td>
		            <td>10</td>
		            <td>$10,000</td>
		            <td>
		                <div class="acciones-btn">
		                    <button type="submit" class="btn-accion btn-modificar">Autorizar</button>
		                    <button type="submit" class="btn-accion btn-eliminar">Rechazar</button>
		                </div>
		            </td>
		        </tr>
    	</tbody>
	</table>
	</div>
</body>
</html>