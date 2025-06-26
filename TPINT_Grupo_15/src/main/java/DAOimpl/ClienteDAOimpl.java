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
    public boolean agregarCliente(Cliente cliente) throws Exception {
        String sql = "INSERT INTO Clientes (DNI_Cl, CUIL_Cl, Nombre_Cl, Apellido_Cl, Sexo_Cl, Nacionalidad_Cl, FechaNacimiento_Cl, Direccion_Cl, IdLocalidad_Cl, IdProvincia_Cl, CorreoElectronico_Cl, Telefonos_Cl, Estado_Cl) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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

    @Override
    public boolean eliminarCliente(String dni) throws Exception {
        String sql = "DELETE FROM Clientes WHERE DNI_Cl = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dni);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean modificarCliente(Cliente cliente) throws Exception {
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

    @Override
    public Cliente obtenerClientePorDni(String dni) throws Exception {
        String sql = "SELECT * FROM Clientes WHERE DNI_Cl = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
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
                    cliente.setIdLocalidad(rs.getString("IdLocalidad_Cl"));
                    cliente.setIdProvincia(rs.getString("IdProvincia_Cl"));
                    cliente.setCorreoElectronico(rs.getString("CorreoElectronico_Cl"));
                    cliente.setTelefonos(rs.getString("Telefonos_Cl"));
                    cliente.setEstado(rs.getBoolean("Estado_Cl"));
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
        String sql = "SELECT * FROM Clientes";
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
                cliente.setIdLocalidad(rs.getString("IdLocalidad_Cl"));
                cliente.setIdProvincia(rs.getString("IdProvincia_Cl"));
                cliente.setCorreoElectronico(rs.getString("CorreoElectronico_Cl"));
                cliente.setTelefonos(rs.getString("Telefonos_Cl"));
                cliente.setEstado(rs.getBoolean("Estado_Cl"));
                lista.add(cliente);
            }
        }
        return lista;
    }
}
