package negocio;

import java.sql.SQLException;
import entidad.Usuario;

public interface UsuarioNegocio {
    Usuario validarUsuarioLogin(String usuario, String contrasenia);
    boolean agregarUsuario(Usuario usuario) throws SQLException;
}
