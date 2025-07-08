package DAOimpl;

import DAO.ClienteDAO;
import DAOimpl.ClienteDAOimpl;
import entidad.Cliente;
import java.sql.PreparedStatement;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import DAO.Conexion;

public class ClienteDAOimpl implements ClienteDAO {

	@Override
	
	//se valido para que no se agreguen clientes con el mismo dni y/o cuil
	
	public boolean agregarCliente(Cliente cliente) throws Exception {
	    // // Verificamos si existe un cliente con el mismo dni y/o cuil
	    if (existeClientePorDniOCuil(cliente.getDni(), cliente.getCuil())) {
	        throw new Exception("Ya existe un cliente con el DNI o CUIL proporcionado");
	    }
	    
	    String sql = "INSERT INTO Clientes (DNI_Cl, CUIL_Cl, Nombre_Cl, Apellido_Cl, Sexo_Cl, Nacionalidad_Cl, "
	    		+ "FechaNacimiento_Cl, Direccion_Cl, IdLocalidad_Cl, IdProvincia_Cl, CorreoElectronico_Cl,"
	    		+ " Telefonos_Cl, Estado_Cl) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	    
	    try (Connection conn = Conexion.getConexion(); 
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        
	        ps.setString(1, cliente.getDni());
	        ps.setString(2, cliente.getCuil());
	        ps.setString(3, cliente.getNombre());
	        ps.setString(4, cliente.getApellido());
	        ps.setString(5, cliente.getSexo());
	        ps.setString(6, cliente.getNacionalidad());
	        ps.setDate(7, Date.valueOf(cliente.getFechaNacimiento()));
	        ps.setString(8, cliente.getDireccion());
	        ps.setString(9, cliente.getIdLocalidad());
	        ps.setString(10, cliente.getIdProvincia());
	        ps.setString(11, cliente.getCorreoElectronico());
	        ps.setString(12, cliente.getTelefonos());
	        ps.setBoolean(13, cliente.getEstado());

	        return ps.executeUpdate() > 0;
	    }
	}

	// Método  para verificar si existe un cliente con el  mismo DNI o CUIL
	public boolean existeClientePorDniOCuil(String dni, String cuil) throws Exception {
	    String sql = "SELECT COUNT(*) FROM Clientes WHERE DNI_Cl = ? OR CUIL_Cl = ?";
	    
	    try (Connection conn = Conexion.getConexion(); 
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        
	        ps.setString(1, dni);
	        ps.setString(2, cuil);
	        
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt(1) > 0;
	            }
	        }
	    }
	    return false;
	}

	@Override
	public boolean eliminarCliente(String dni) throws Exception {
		String sql = "UPDATE Clientes SET Estado_Cl = FALSE WHERE DNI_Cl = ?";
		try (Connection conn = Conexion.getConexion(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, dni);
			return ps.executeUpdate() > 0;
		}
	}
	
	//validamos que el cuil no este siendo usado por otro cliente de la base de datos
	// Validamos que el cuil no este siendo usado por otro cliente de la base de datos
	// Y que no exista otro cliente con el mismo nombre y apellido
	@Override
	public boolean modificarCliente(Cliente cliente) throws Exception {
	    // Verificar que el cliente a modificar existe
	    if (!existeClientePorDni(cliente.getDni())) {
	        throw new Exception("No existe un cliente con el DNI proporcionado");
	    }

	    // Verificar que el CUIL no esté siendo usado por otro cliente
	    if (existeCuilEnOtroCliente(cliente.getCuil(), cliente.getDni())) {
	        throw new Exception("El CUIL ya está siendo utilizado por otro cliente");
	    }
	    
	    // Verificar que no exista otro cliente con el mismo nombre y apellido
	    if (existeNombreApellidoEnOtroCliente(cliente.getNombre(), cliente.getApellido(), cliente.getDni())) {
	        throw new Exception("Ya existe otro cliente con el mismo nombre y apellido");
	    }

	    String sql = "UPDATE Clientes SET CUIL_Cl=?, Nombre_Cl=?, Apellido_Cl=?, Sexo_Cl=?, Nacionalidad_Cl=?, FechaNacimiento_Cl=?, Direccion_Cl=?, IdLocalidad_Cl=?, IdProvincia_Cl=?, CorreoElectronico_Cl=?, Telefonos_Cl=?, Estado_Cl=? WHERE DNI_Cl=?";

	    try (Connection conn = Conexion.getConexion();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setString(1, cliente.getCuil());
	        ps.setString(2, cliente.getNombre());
	        ps.setString(3, cliente.getApellido());
	        ps.setString(4, cliente.getSexo());
	        ps.setString(5, cliente.getNacionalidad());
	        ps.setDate(6, Date.valueOf(cliente.getFechaNacimiento()));
	        ps.setString(7, cliente.getDireccion());
	        ps.setString(8, cliente.getIdLocalidad());
	        ps.setString(9, cliente.getIdProvincia());
	        ps.setString(10, cliente.getCorreoElectronico());
	        ps.setString(11, cliente.getTelefonos());
	        ps.setBoolean(12, cliente.getEstado());
	        ps.setString(13, cliente.getDni());

	        return ps.executeUpdate() > 0;
	    }
	}

	// Método auxiliar para verificar si existe otro cliente con el mismo nombre y apellido
	public boolean existeNombreApellidoEnOtroCliente(String nombre, String apellido, String dniActual) throws Exception {
	    String sql = "SELECT COUNT(*) FROM Clientes WHERE UPPER(TRIM(Nombre_Cl)) = UPPER(TRIM(?)) AND UPPER(TRIM(Apellido_Cl)) = UPPER(TRIM(?)) AND DNI_Cl != ?";
	    
	    try (Connection conn = Conexion.getConexion(); 
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        
	        ps.setString(1, nombre);
	        ps.setString(2, apellido);
	        ps.setString(3, dniActual);
	        
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt(1) > 0;
	            }
	        }
	    }
	    return false;
	}
	
	
	public boolean existeClientePorDni(String dni) throws Exception {
	    String sql = "SELECT COUNT(*) FROM Clientes WHERE DNI_Cl = ?";
	    
	    try (Connection conn = Conexion.getConexion(); 
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        
	        ps.setString(1, dni);
	        
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt(1) > 0;
	            }
	        }
	    }
	    return false;
	}

	// Método auxiliar para verificar si el CUIL está siendo usado por otro cliente
	public boolean existeCuilEnOtroCliente(String cuil, String dniActual) throws Exception {
	    String sql = "SELECT COUNT(*) FROM Clientes WHERE CUIL_Cl = ? AND DNI_Cl != ?";
	    
	    try (Connection conn = Conexion.getConexion(); 
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        
	        ps.setString(1, cuil);
	        ps.setString(2, dniActual);
	        
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt(1) > 0;
	            }
	        }
	    }
	    return false;
	}
	
	@Override
	public Cliente obtenerClientePorDni(String dni) throws Exception {
		String sql = "SELECT c.*, u.Nombre_Usu, u.Contrasenia_Usu, " + " p.descripcion_Pr  AS DescripcionProvincia, "
				+ "l.descripcion_Loc AS DescripcionLocalidad " + "FROM Clientes c "
				+ "INNER JOIN Usuarios u ON c.DNI_Cl = u.DNI_Usu "
				+ "INNER JOIN Provincias p ON c.IdProvincia_Cl = p.IdProvincias_Pr "
				+ "INNER JOIN  Localidades l ON c.IdLocalidad_Cl = l.IdLocalidad_Loc " + "WHERE DNI_Cl = ?";
		try (Connection conn = Conexion.getConexion(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, dni);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Cliente cliente = new Cliente();
					cliente.setDni(rs.getString("DNI_Cl"));
					cliente.setCuil(rs.getString("CUIL_Cl"));
					cliente.setNombre(rs.getString("Nombre_Cl"));
					cliente.setApellido(rs.getString("Apellido_Cl"));
					cliente.setSexo(rs.getString("Sexo_Cl"));
					cliente.setNacionalidad(rs.getString("Nacionalidad_Cl"));
					cliente.setFechaNacimiento(rs.getDate("FechaNacimiento_Cl").toLocalDate());
					cliente.setDireccion(rs.getString("Direccion_Cl"));
					cliente.setIdProvincia(rs.getString("IdProvincia_Cl"));
					cliente.setDescripcionProvincia(rs.getString("DescripcionProvincia"));
					cliente.setIdLocalidad(rs.getString("IdLocalidad_Cl"));
					cliente.setDescripcionLocalidad(rs.getString("DescripcionLocalidad"));
					cliente.setCorreoElectronico(rs.getString("CorreoElectronico_Cl"));
					cliente.setTelefonos(rs.getString("Telefonos_Cl"));
					cliente.setEstado(rs.getBoolean("Estado_Cl"));
					cliente.setNombreUsuario(rs.getString("Nombre_Usu"));
					return cliente;
				} else {
					return null;
				}
			}
		}
	}

	@Override
	public List<Cliente> listarClientes() throws Exception {
		List<Cliente> lista = new ArrayList<>();
		String sql = "SELECT c.*, u.Nombre_Usu, u.Contrasenia_Usu, " + " p.descripcion_Pr  AS DescripcionProvincia, "
				+ "l.descripcion_Loc AS DescripcionLocalidad " + "FROM Clientes c "
				+ "INNER JOIN Usuarios u ON c.DNI_Cl = u.DNI_Usu "
				+ "INNER JOIN Provincias p ON c.IdProvincia_Cl = p.IdProvincias_Pr "
				+ "INNER JOIN  Localidades l ON c.IdLocalidad_Cl = l.IdLocalidad_Loc " + "WHERE c.Estado_Cl = TRUE";
		try (Connection conn = Conexion.getConexion();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				Cliente cliente = new Cliente();
				cliente.setDni(rs.getString("DNI_Cl"));
				cliente.setCuil(rs.getString("CUIL_Cl"));
				cliente.setNombre(rs.getString("Nombre_Cl"));
				cliente.setApellido(rs.getString("Apellido_Cl"));
				cliente.setSexo(rs.getString("Sexo_Cl"));
				cliente.setNacionalidad(rs.getString("Nacionalidad_Cl"));
				cliente.setFechaNacimiento(rs.getDate("FechaNacimiento_Cl").toLocalDate());
				cliente.setDireccion(rs.getString("Direccion_Cl"));
				cliente.setIdProvincia(rs.getString("IdProvincia_Cl"));
				cliente.setDescripcionProvincia(rs.getString("DescripcionProvincia"));
				cliente.setIdLocalidad(rs.getString("IdLocalidad_Cl"));
				cliente.setDescripcionLocalidad(rs.getString("DescripcionLocalidad"));
				cliente.setCorreoElectronico(rs.getString("CorreoElectronico_Cl"));
				cliente.setTelefonos(rs.getString("Telefonos_Cl"));
				cliente.setEstado(rs.getBoolean("Estado_Cl"));
				// Agregá los campos del usuario como extra
				cliente.setNombreUsuario(rs.getString("Nombre_Usu")); // este campo lo agregás temporalmente
				cliente.setContraseniaUsuario(rs.getString("Contrasenia_Usu")); // idem
				lista.add(cliente);
			}
		}
		return lista;
	}
}
