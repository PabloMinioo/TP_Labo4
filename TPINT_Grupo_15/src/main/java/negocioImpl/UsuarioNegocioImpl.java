package negocioImpl;

import DAO.UsuarioDAO;
import DAOimpl.UsuarioDAOImpl;
import entidad.Usuario;
import negocio.UsuarioNegocio;

import java.sql.SQLException;

public class UsuarioNegocioImpl implements UsuarioNegocio {

	 private UsuarioDAO usuarioDAO;

	    public UsuarioNegocioImpl() {
	        this.usuarioDAO = new UsuarioDAOImpl();
	    }
	
   @Override
   public Usuario validarUsuarioLogin(String usuario, String contrasenia) {
	        return usuarioDAO.validarUsuarioLogin(usuario, contrasenia);
	    }
   @Override
   public boolean agregarUsuario(Usuario usuario) throws SQLException {
       return usuarioDAO.agregarUsuario(usuario);
   }
    
}
