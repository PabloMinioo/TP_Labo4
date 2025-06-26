package DAO;

import java.util.List;
import entidad.Cliente;

public interface ClienteDAO {
	 boolean agregarCliente(Cliente cliente) throws Exception;
	 boolean eliminarCliente(String dni) throws Exception;
	 boolean modificarCliente(Cliente cliente) throws Exception;
	 Cliente obtenerClientePorDni(String dni) throws Exception;
	 List<Cliente> listarClientes() throws Exception;
}
