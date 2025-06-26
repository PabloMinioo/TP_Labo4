package negocioImpl;
import java.sql.SQLException;
import java.util.List;
import DAO.ReportesDao;
import DAOimpl.ReportesDAOimpl;

import entidad.ClienteReportes;
import negocio.ReportesNegocio;

public class ReportesNegocioImpl implements ReportesNegocio {
    
    
    private ReportesDao reportesDAO;
    
    
    public ReportesNegocioImpl() {
        this.reportesDAO = new ReportesDAOimpl();
    }
    
    @Override
    public int obtenerTotalClientes() throws SQLException {
        
        return reportesDAO.contarClientes();
    }
    
    @Override
    public int obtenerTotalCuentas() throws SQLException {
        return reportesDAO.contarCuentas();
    }
    
    @Override
    public double calcularPromedioCuentasPorCliente() throws SQLException {
        double promedio = reportesDAO.promedioCuentasPorCliente();
       
        return Math.round(promedio * 100.0) / 100.0;
    }
    
    @Override
    public int obtenerPrestamosPorEstado(String estado) throws SQLException {
       
        if (estado == null || estado.trim().isEmpty()) {
            throw new IllegalArgumentException("El estado no puede estar vacío");
        }
        return reportesDAO.contarPrestamosPorEstado(estado);
    }
    
    @Override
    public int obtenerPrestamosPorEstadoYRangoFecha(String estado, String desde, String hasta) throws SQLException {
      
        if (estado == null || estado.trim().isEmpty()) {
            throw new IllegalArgumentException("El estado no puede estar vacío");
        }
        if (desde == null || hasta == null) {
            throw new IllegalArgumentException("Las fechas no pueden ser nulas");
        }
        return reportesDAO.contarPrestamosPorEstadoYFecha(estado, desde, hasta);
    }
    
    @Override
    public int obtenerPrestamosPorEstadoYFechaExacta(String estado, String fecha) throws SQLException {
        
        if (estado == null || estado.trim().isEmpty()) {
            throw new IllegalArgumentException("El estado no puede estar vacío");
        }
        if (fecha == null || fecha.trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha no puede estar vacía");
        }
        return reportesDAO.contarPrestamosPorEstadoYFechaExacta(estado, fecha);
    }
    
    @Override
    public List<ClienteReportes> generarReporteClientesCuentas() throws SQLException {
        List<ClienteReportes> reportes = reportesDAO.obtenerEstadisticasCuentas();
        
        
        reportes.sort((r1, r2) -> Integer.compare(r2.getCantidadCuentas(), r1.getCantidadCuentas()));
        
        return reportes;
    }
}