package negocioImpl;

import DAO.ClienteDAO;
import DAOimpl.ClienteDAOimpl;
import entidad.Cliente;
import negocio.ClienteNegocio;

import java.util.List;

public class ClienteNegocioImpl implements ClienteNegocio{

    private ClienteDAO clienteDao;

    public ClienteNegocioImpl() {
        this.clienteDao = new ClienteDAOimpl();
    }

    @Override
    public boolean agregarCliente(Cliente cliente) throws Exception {
        
        return clienteDao.agregarCliente(cliente);
    }

    @Override
    public boolean eliminarCliente(String dni) throws Exception {
        return clienteDao.eliminarCliente(dni);
    }

    @Override
    public boolean modificarCliente(Cliente cliente) throws Exception {
        return clienteDao.modificarCliente(cliente);
    }

    @Override
    public Cliente obtenerClientePorDni(String dni) throws Exception {
        return clienteDao.obtenerClientePorDni(dni);
    }

    @Override
    public List<Cliente> listarClientes() throws Exception {
        return clienteDao.listarClientes();
    }
}
