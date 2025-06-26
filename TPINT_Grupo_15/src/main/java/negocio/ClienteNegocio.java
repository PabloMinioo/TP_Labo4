package negocio;

import entidad.Cliente;
import java.util.List;

public interface ClienteNegocio {
	  boolean agregarCliente(Cliente cliente) throws Exception;
	  boolean eliminarCliente(String dni) throws Exception;
	  boolean modificarCliente(Cliente cliente) throws Exception;
	  Cliente obtenerClientePorDni(String dni) throws Exception;
	  List<Cliente> listarClientes() throws Exception;
}
