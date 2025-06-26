package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entidad.Localidad;

public class LocalidadDAO {

	  public static List<Localidad> obtenerPorProvincia(String provinciaId) {
	        List<Localidad> localidades = new ArrayList<>();
	        Connection conn = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;

	        try {
	            conn = Conexion.getConexion();
	            
	            // Formatear el provinciaId para asegurar que tenga 2 dígitos
	            String provinciaIdFormateado = String.format("%02d", Integer.parseInt(provinciaId));
	            
	            // Debug: imprimir el valor recibido y el formateado
	            System.out.println("Provincia ID recibido: " + provinciaId);
	            System.out.println("Provincia ID formateado: " + provinciaIdFormateado);
	            
	            String sql = "SELECT IdLocalidad_Loc, IdProvincia_Loc, descripcion_Loc FROM Localidades WHERE IdProvincia_Loc = ?";
	            
	            ps = conn.prepareStatement(sql);
	            ps.setString(1, provinciaIdFormateado);
	            
	            // Debug: imprimir la consulta
	            System.out.println("Ejecutando consulta: " + sql + " con parámetro: " + provinciaIdFormateado);
	            
	            rs = ps.executeQuery();

	            while(rs.next()) {
	                Localidad loc = new Localidad();
	                loc.setIdLocalidad_Loc(rs.getString("IdLocalidad_Loc"));
	                loc.setIdProvincia_Loc(rs.getString("IdProvincia_Loc"));
	                loc.setDescripcion_Loc(rs.getString("descripcion_Loc"));
	                localidades.add(loc);
	                
	                // Debug: imprimir cada localidad encontrada
	                System.out.println("Localidad encontrada: " + loc.getDescripcion_Loc());
	            }
	            
	            System.out.println("Total localidades encontradas: " + localidades.size());
	            
	        } catch (SQLException e) {
	            System.err.println("Error SQL en obtenerPorProvincia: " + e.getMessage());
	            e.printStackTrace();
	        } catch (NumberFormatException e) {
	            System.err.println("Error al formatear provincia ID: " + provinciaId);
	            e.printStackTrace();
	        } finally {
	            // Cerrar rs, ps, conn
	            try { if(rs != null) rs.close(); } catch(Exception e) {}
	            try { if(ps != null) ps.close(); } catch(Exception e) {}
	            try { if(conn != null) conn.close(); } catch(Exception e) {}
	        }
	        return localidades;
	    }
}
