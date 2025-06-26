package DAO;

import java.sql.SQLException;
import java.util.List;
import entidad.ClienteReportes;

public interface  ReportesDao {
	   // Solo las declaraciones de métodos (sin implementación)
    int contarClientes() throws SQLException;
    
    int contarCuentas() throws SQLException;
    
    double promedioCuentasPorCliente() throws SQLException;
    
    int contarPrestamosPorEstado(String estado) throws SQLException;
    
    int contarPrestamosPorEstadoYFecha(String estado, String desde, String hasta) throws SQLException;
    
    int contarPrestamosPorEstadoYFechaExacta(String estado, String fecha) throws SQLException;
    
    List<ClienteReportes> obtenerEstadisticasCuentas() throws SQLException;
}
