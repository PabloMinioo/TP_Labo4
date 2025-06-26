package DAO;

import java.sql.SQLException;

import entidad.Usuario;

public interface UsuarioDAO {
	    Usuario validarUsuarioLogin(String usuario, String contrasenia);
	    Boolean agregarUsuario(Usuario objCargado) throws SQLException;; 
	   // boolean existeUsuario(String usuario);

	}

