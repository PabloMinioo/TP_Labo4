<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Reportes</title>
    <style>
        body {
            font-family: Arial;
            background-color: #f8f9fa;
            font-size: 14px;
        }

        .contenedor-principal {
            max-width: 1000px;
            margin: auto;
            padding: 20px;
        }

        .botones-reporte {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
            justify-content: center;
            margin-bottom: 20px;
        }

        .botones-reporte button {
            background-color: #3498db;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .botones-reporte button:hover {
            background-color: #2980b9;
        }

        .contenedor {
            display: none;
            padding: 20px;
            margin-bottom: 20px;
            background-color: white;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
        }

        .contenedor.activo {
            display: block;
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

        input[type="date"],
        input[type="submit"],
        select {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        input[type="submit"] {
            background-color: #3498db;
            color: white;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #2980b9;
        }

        .resultados {
            margin-top: 20px;
            background-color: #ecf0f1;
            padding: 15px;
            border-radius: 5px;
        }
    </style>

    <script>
        function mostrarReporte(id) {
            let secciones = document.querySelectorAll('.contenedor');
            secciones.forEach(sec => sec.classList.remove('activo'));
            document.getElementById(id).classList.add('activo');
        }
    </script>
</head>
<body>
     <jsp:include page="/WEB-INF/MasterAdmin.jsp" />

    <div class="contenedor-principal">
        <div class="botones-reporte">
            <button onclick="mostrarReporte('reporteMovimientos')">Movimientos por Fecha</button>
            <button onclick="mostrarReporte('reporteCuentas')">Cantidad de Cuentas por Tipo</button>
            <button onclick="mostrarReporte('reportePrestamos')">Cantidad de Préstamos</button>
            <button onclick="mostrarReporte('reporteClientes')">Cantidad de Clientes con Préstamos</button>
        </div>

        <!-- CANTIDAD DE PLATA POR MOVIMIENTOS -->
        <div class="contenedor" id="reporteMovimientos">
            <h2>Reporte de Movimientos</h2>
            <form action="ReporteMovimientosServlet" method="post">
                <label>Fecha desde:</label>
                <input type="date" name="fechaInicio" required>

                <label>Fecha hasta:</label>
                <input type="date" name="fechaFin" required>

                <input type="submit" value="Generar Reporte">
            </form>
            <div class="resultados">
                <p><strong>Total Ingresos:</strong> $</p>
                <p><strong>Total Egresos:</strong> $</p>
                <p><strong>Monto Neto:</strong> $</p>
                <p><strong>Cantidad de Movimientos:</strong> </p>
            </div>
        </div>

        <!-- CANTIDAD CUENTAS POR TIPO -->
        <div class="contenedor" id="reporteCuentas">
            <h2>Cantidad de Cuentas por Tipo</h2>
            <form action="ReporteCuentasTipoServlet" method="post">
                <input type="submit" value="Mostrar Reporte">
            </form>
            <div class="resultados">
                <p><strong>Caja de Ahorro: </strong></p>
                <p><strong>Cuenta Corriente: </strong></p>
            </div>
        </div>

        <!-- CANTIDAD DE PRESTAMOS  -->
        <div class="contenedor" id="reportePrestamos">
            <h2>Préstamos Solicitados, Aprobados y Rechazados</h2>
            <form action="ReportePrestamosServlet" method="post">
                <label>Fecha desde:</label>
                <input type="date" name="fechaInicio" required>

                <label>Fecha hasta:</label>
                <input type="date" name="fechaFin" required>

                <input type="submit" value="Generar Reporte">
            </form>
            <div class="resultados">
                <p><strong>Total Préstamos Solicitados:</strong> $</p>
                <p><strong>Total Préstamos Aprobados:</strong> $</p>
                <p><strong>Total Préstamos Rechazados:</strong> $</p>            
                <p><strong>Porcentaje de Aprobados:</strong> %</p>
                <p><strong>Porcentaje de Rechazados:</strong> %</p>
            </div>
        </div>

        <!-- CANTIDAD DE CLIENTES CON PRESTAMOS -->
        <div class="contenedor" id="reporteClientes">
            <h2>Clientes que Solicitaron Préstamos</h2>
            <form action="ReporteClientesPrestamoServlet" method="post">
                <label>Fecha desde:</label>
                <input type="date" name="fechaInicio" required>

                <label>Fecha hasta:</label>
                <input type="date" name="fechaFin" required>

                <input type="submit" value="Mostrar Clientes">
            </form>
            <div class="resultados">
                <p><strong>Total de Clientes que solicitaron préstamos: </strong></p>
            </div>
        </div>
    </div>
</body>
</html>
