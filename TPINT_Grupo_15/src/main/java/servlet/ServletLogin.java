package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.UsuarioDAO;
import DAOimpl.UsuarioDAOImpl;
import entidad.Usuario;

@WebServlet("/ServletLogin")
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UsuarioDAO usuarioDAO; // Declaración correcta

	public ServletLogin() {
		super();
		this.usuarioDAO = new UsuarioDAOImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Mostrar la página de login
		request.getRequestDispatcher("/vistas/Login.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// String ConsultaAdmin= "select * from usuarios where Nombre_Usu= ? and
		// Contrasenia_Usu= ? and Rol_Usu='A'";
		// String ConsultaCliente= "select * from usuarios where Nombre_Usu= ? and
		// Contrasenia_Usu= ? and Rol_Usu='C'";

		String Usuario = request.getParameter("usuario");
		String Contrasenia = request.getParameter("contrasenia");

		/*
		 * if (Usuario == null || Usuario.trim().isEmpty() || Contrasenia == null ||
		 * Contrasenia.trim().isEmpty()) { request.setAttribute("error",
		 * "Por favor complete todos los campos.");
		 * request.getRequestDispatcher("/vistas/Login.jsp").forward(request, response);
		 * return; }
		 */

		// Validar usuario
		Usuario usuarioValidado = usuarioDAO.validarUsuarioLogin(Usuario, Contrasenia);
		if (usuarioValidado == null) {
			request.setAttribute("mensaje", "Usuario o contraseña no existen"); // ESTO ESTÁ MAL en un DAO
		}
		if (usuarioValidado != null) {
			// Login exitoso
			HttpSession session = request.getSession();
			session.setAttribute("usuario", usuarioValidado);
			session.setAttribute("nombreUsuario", usuarioValidado.getNombreUsu());
			if (usuarioValidado.getRolUsu() == 'A') {
				response.sendRedirect(request.getContextPath() + "/vistas/HomeAdministrador.jsp");
			} else if (usuarioValidado.getRolUsu() == 'C') {
				response.sendRedirect(request.getContextPath() + "/vistas/HomeCliente.jsp");
			} else {
				request.setAttribute("mensaje", "Usuario o contraseña no existen"); // ESTO ESTÁ MAL en un DAO
				request.setAttribute("error", "Rol de usuario no válido.");
				request.getRequestDispatcher("/vistas/Login.jsp").forward(request, response);
			}
		} else {
			// Login fallido
			request.setAttribute("mensaje", "Usuario o contraseña no existen"); // ESTO ESTÁ MAL en un DAO
			request.setAttribute("error", "Usuario o contraseña incorrectos.");
			request.setAttribute("usuario", Usuario);
			request.getRequestDispatcher("/vistas/Login.jsp").forward(request, response);
		}
	}
}
