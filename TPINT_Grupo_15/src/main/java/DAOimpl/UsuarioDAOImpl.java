package DAOimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DAO.Conexion;
import DAO.UsuarioDAO;
import entidad.Usuario;

public class UsuarioDAOImpl implements UsuarioDAO {
	 @Override
	    public Usuario validarUsuarioLogin(String usuario, String contrasenia) {
		  System.out.println("=== INICIO validarUsuarioLogin ===");
		    System.out.println("Usuario recibido: " + usuario);
		    System.out.println("Contraseña recibida: " + contrasenia);
		    
		 
	        String consulta = "SELECT * FROM usuarios WHERE Nombre_Usu = ? AND Contrasenia_Usu = ? ";
	        try (Connection conn = Conexion.getConexion();
	                PreparedStatement st = conn.prepareStatement(consulta)) {
	               
	        	  System.out.println("Conexión obtenida: " + (conn != null ? "OK" : "NULL"));
	              System.out.println("PreparedStatement creado: " + (st != null ? "OK" : "NULL"));
	              
	               st.setString(1, usuario);
	               st.setString(2, contrasenia);
	               
	               try (ResultSet rs = st.executeQuery()) {
	            	   System.out.println("ResultSet obtenido: " + (rs != null ? "OK" : "NULL"));
	                   if (rs.next()) {
	                	   System.out.println("Usuario encontrado en BD!");
	                       Usuario user = new Usuario();
	                       user.setDniUsu(rs.getString("Dni_Usu"));
	                       user.setNombreUsu(rs.getString("Nombre_Usu"));
	                       user.setContraseniaUsu(rs.getString("Contrasenia_Usu"));
	                       user.setRolUsu(rs.getString("Rol_Usu").charAt(0));
	                       return user;
	                   }else {System.out.println("No se encontró usuario en BD");
	                   }
	               }
	           } catch (SQLException e) {
	               System.err.println("Error al validar usuario: " + e.getMessage());
	               e.printStackTrace();
	           }
	        System.out.println("=== FIN validarUsuarioLogin - Retornando NULL ===");
	           return null;
	       }
	 
	       
	 ///AGREGO UN USUARIO NUEVO ; PERO VIENE JUNTO CON EL ALTA DEL CLIENTE
	 @Override
	    public Boolean agregarUsuario(Usuario usuario) throws SQLException {
	        String sql = "INSERT INTO Usuarios (DNI_Usu, Nombre_Usu, Contrasenia_Usu, Rol_Usu) VALUES (?, ?, ?, ?)";
	        
	        try (Connection con = Conexion.getConexion();
	             PreparedStatement ps = con.prepareStatement(sql)) {
	             
	            ps.setString(1, usuario.getDniUsu());
	            ps.setString(2, usuario.getNombreUsu());
	            ps.setString(3, usuario.getContraseniaUsu());
	            ps.setString(4, String.valueOf(usuario.getRolUsu()));  // Asumiendo que rolUsu es char y C'
	            
	            int filas = ps.executeUpdate();
	            return filas > 0; // true si insertó
	        }
	    }
	     
}
