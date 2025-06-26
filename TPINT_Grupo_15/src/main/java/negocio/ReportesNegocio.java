package negocio;

import java.sql.SQLException;
import java.util.List;

import entidad.ClienteReportes;
public interface ReportesNegocio {

	 // Métodos de la capa de negocio 
    int obtenerTotalClientes() throws SQLException;
    
    int obtenerTotalCuentas() throws SQLException;
    
    double calcularPromedioCuentasPorCliente() throws SQLException;
    
    int obtenerPrestamosPorEstado(String estado) throws SQLException;
    
    int obtenerPrestamosPorEstadoYRangoFecha(String estado, String desde, String hasta) throws SQLException;
    
    int obtenerPrestamosPorEstadoYFechaExacta(String estado, String fecha) throws SQLException;
    
    List<ClienteReportes> generarReporteClientesCuentas() throws SQLException;
}
