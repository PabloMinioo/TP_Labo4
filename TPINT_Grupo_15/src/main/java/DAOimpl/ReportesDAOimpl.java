package DAOimpl;

import java.sql.*;
import java.util.*;
import DAO.ReportesDao;
import entidad.ClienteReportes;
import DAO.Conexion;

public class ReportesDAOimpl implements ReportesDao{

	 @Override
	    public int contarClientes() throws SQLException {
	        String sql = "SELECT COUNT(*) AS totalClientes FROM clientes";
	        try (Connection con = Conexion.getConexion();
	             PreparedStatement ps = con.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {
	            rs.next();
	            return rs.getInt("totalClientes");
	        }
	    }

	    @Override
	    public int contarCuentas() throws SQLException {
	        String sql = "SELECT COUNT(*) AS totalCuentas FROM cuentas";
	        try (Connection con = Conexion.getConexion();
	             PreparedStatement ps = con.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {
	            rs.next();
	            return rs.getInt("totalCuentas");
	        }
	    }

	    @Override
	    public double promedioCuentasPorCliente() throws SQLException {
	        String sql = "SELECT AVG(cantidad) AS promedio FROM (SELECT COUNT(*) AS cantidad FROM cuentas GROUP BY cliente_id) AS sub";
	        try (Connection con = Conexion.getConexion();
	             PreparedStatement ps = con.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {
	            rs.next();
	            return rs.getDouble("promedio");
	        }
	    }

	    @Override
	    public int contarPrestamosPorEstado(String estado) throws SQLException {
	        String sql = "SELECT COUNT(*) AS total FROM prestamos WHERE estado = ?";
	        try (Connection con = Conexion.getConexion();
	             PreparedStatement ps = con.prepareStatement(sql)) {
	            ps.setString(1, estado);
	            try (ResultSet rs = ps.executeQuery()) {
	                rs.next();
	                return rs.getInt("total");
	            }
	        }
	    }

	    @Override
	    public int contarPrestamosPorEstadoYFecha(String estado, String desde, String hasta) throws SQLException {
	        String sql = "SELECT COUNT(*) AS total FROM prestamos WHERE estado = ? AND fecha BETWEEN ? AND ?";
	        try (Connection con = Conexion.getConexion();
	             PreparedStatement ps = con.prepareStatement(sql)) {
	            ps.setString(1, estado);
	            ps.setString(2, desde);
	            ps.setString(3, hasta);
	            try (ResultSet rs = ps.executeQuery()) {
	                rs.next();
	                return rs.getInt("total");
	            }
	        }
	    }

	    @Override
	    public int contarPrestamosPorEstadoYFechaExacta(String estado, String fecha) throws SQLException {
	        String sql = "SELECT COUNT(*) AS total FROM prestamos WHERE estado = ? AND fecha = ?";
	        try (Connection con = Conexion.getConexion();
	             PreparedStatement ps = con.prepareStatement(sql)) {
	            ps.setString(1, estado);
	            ps.setString(2, fecha);
	            try (ResultSet rs = ps.executeQuery()) {
	                rs.next();
	                return rs.getInt("total");
	            }
	        }
	    }

	    @Override
	    public List<ClienteReportes> obtenerEstadisticasCuentas() throws SQLException {
	        String sql = "SELECT c.nombre AS nombre, COUNT(a.id) AS cantidadCuentas " +
	                     "FROM clientes c LEFT JOIN cuentas a ON c.id = a.cliente_id " +
	                     "GROUP BY c.nombre";
	        List<ClienteReportes> lista = new ArrayList<>();
	        try (Connection con = Conexion.getConexion();
	             PreparedStatement ps = con.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                lista.add(new ClienteReportes(
	                    rs.getString("nombre"),
	                    rs.getInt("cantidadCuentas")
	                ));
	            }
	        }
	        return lista;
	    }
}
