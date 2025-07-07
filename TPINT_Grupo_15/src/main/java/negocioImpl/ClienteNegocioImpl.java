package negocioImpl;

import DAO.ClienteDAO;
import DAOimpl.ClienteDAOimpl;
import Excepciones.ContraseñaNoCoincideException;
import entidad.Cliente;
import negocio.ClienteNegocio;
import Excepciones.ClienteExistenteException;


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
    //
    @Override
    public boolean existeClientePorDniOCuil(String dni, String cuil) throws Exception {
        return clienteDao.existeClientePorDniOCuil(dni,cuil);
    }
    
    @Override
    public boolean existeClientePorDni(String dni) throws Exception {
    	return clienteDao.existeClientePorDni(dni);
    }
    @Override
    public boolean existeNombreApellidoEnOtroCliente(String nombre, String apellido, String dniActual) throws Exception{
    	return clienteDao.existeNombreApellidoEnOtroCliente(nombre, apellido, dniActual);
    }
    
    @Override
    public boolean validarContraseniasIguales(String contrasenia1, String contrasenia2) 
            throws ContraseñaNoCoincideException {
        if (contrasenia1 == null || contrasenia2 == null) {
            throw new ContraseñaNoCoincideException("Las contraseñas no pueden ser nulas");
        }
        if (!contrasenia1.equals(contrasenia2)) {
            throw new ContraseñaNoCoincideException("Las contraseñas no coinciden");
        }
        return true;
    }
    
    
      
}
