package DAOimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import DAO.Conexion;
import DAO.MovimientoDAO;
import entidad.Movimiento;
import entidad.TipoMovimiento;
import java.sql.ResultSet;

public class MovimientoDAOimpl implements MovimientoDAO{
	 @Override
	    public List<Movimiento> obtenerMovimientosPorCuenta(int numeroCuenta) throws Exception {
	        List<Movimiento> lista = new ArrayList<>();

	        String sql = "SELECT m.*, tm.IdTipoMovimiento_Tm, tm.Descripcion_Tm " +
	                     "FROM Movimientos m " +
	                     "JOIN TiposMovimientos tm ON m.IdTipoMovimiento_Mv = tm.IdTipoMovimiento_Tm " +
	                     "WHERE m.NumeroCuenta_Mv = ?";

	        try (Connection conn = Conexion.getConexion();
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            ps.setInt(1, numeroCuenta);
	            try (ResultSet rs = ps.executeQuery()) {
	                while (rs.next()) {
	                    Movimiento mov = new Movimiento();
	                    mov.setIDMovimiento(rs.getInt("IDMovimiento_Mv"));
	                    mov.setFecha(rs.getDate("Fecha_Mv"));
	                    mov.setDetalle(rs.getString("Detalle_Mv"));
	                    mov.setImporte(rs.getBigDecimal("Importe_Mv"));
	                    mov.setNumeroCuenta(rs.getInt("NumeroCuenta_Mv"));
	                    mov.setCuentaDestino(rs.getInt("CuentaDestino_Mv")); 
	                    mov.setSaldoAnterior(null);  
	                    mov.setSaldoPosterior(null);

	                    TipoMovimiento tipo = new TipoMovimiento();
	                    tipo.setIdTipoMovimiento(rs.getInt("IdTipoMovimiento_Mv"));
	                    tipo.setDescripcion(rs.getString("Descripcion_Tm"));
	                    

	                    mov.setTipoMovimiento(tipo); 

	                    lista.add(mov);
	                }
	            }
	        }
	        return lista;
	    }
}
