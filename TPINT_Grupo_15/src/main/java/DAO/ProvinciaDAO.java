package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entidad.Provincia;

public class ProvinciaDAO {
	 public static List<Provincia> obtenerTodas() {
	        List<Provincia> lista = new ArrayList<>();
	        try (Connection conn = Conexion.getConexion()) {
	            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Provincias");
	            ResultSet rs = stmt.executeQuery();
	            while (rs.next()) {
	                Provincia p = new Provincia();
	                p.setId(rs.getString("IdProvincias_Pr"));
	                p.setDescripcion(rs.getString("descripcion_Pr"));
	                lista.add(p);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return lista;
	    }

}
