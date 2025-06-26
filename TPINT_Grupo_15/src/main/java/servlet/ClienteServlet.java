package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.ClienteDAO;
import DAO.UsuarioDAO;
import DAOimpl.ClienteDAOimpl;
import DAOimpl.UsuarioDAOImpl;
import entidad.Cliente;
import entidad.Usuario;
import negocio.ClienteNegocio;
import negocio.UsuarioNegocio;
import negocioImpl.ClienteNegocioImpl;
import negocioImpl.UsuarioNegocioImpl;

@WebServlet("/ClienteServlet")
public class ClienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// LLAMAMOS LAS DEMAS CAPAS
	private ClienteDAO clienteDAO = new ClienteDAOimpl();
	private ClienteNegocio clienteNegocio = new ClienteNegocioImpl();
	private UsuarioNegocio usuarioNegocio = new UsuarioNegocioImpl();

	public ClienteServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        try {
            // LISTAMOS LOS CLIENTES
            List<Cliente> listaClientes = clienteNegocio.listarClientes();
            request.setAttribute("listaClientes", listaClientes);

            String destino = request.getParameter("destino");

            if ("listar".equalsIgnoreCase(destino)) {
            	RequestDispatcher rd = request.getRequestDispatcher("/vistas/ListarClientes.jsp");
                rd.forward(request, response);
            } else {
            	RequestDispatcher rd = request.getRequestDispatcher("/vistas/AltaCliente.jsp");
                rd.forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Alta Cliente
		if (request.getParameter("btnAgregar") != null) {
			int filas = 0;
			try {
				Cliente cliente = new Cliente();
				// CARGAMOS AL OBJETO 'CLIENTE' LOS VALORES DEL JSP
				cliente.setDni(request.getParameter("dni"));
				cliente.setCuil(request.getParameter("cuil"));
				cliente.setNombre(request.getParameter("nombre"));
				cliente.setApellido(request.getParameter("apellido"));
				cliente.setSexo(request.getParameter("sexo"));
				cliente.setNacionalidad(request.getParameter("nacionalidad"));
				// CONVERTIMOS DE STRING A LOCALDATE
				String fechaNacStr = request.getParameter("fechaNacimiento");
				LocalDate fechaNac = LocalDate.parse(fechaNacStr);
				cliente.setFechaNacimiento(fechaNac);
				cliente.setDireccion(request.getParameter("direccion"));
				cliente.setIdProvincia(request.getParameter("provincia"));
				cliente.setIdLocalidad(request.getParameter("localidad"));
				cliente.setCorreoElectronico(request.getParameter("email"));
				cliente.setTelefonos(request.getParameter("telefonos"));

				// CARGAMOS AL OBJETO 'USUARIO' LOS VALORES DEL JSP
				Usuario usuario = new Usuario();
				usuario.setDniUsu(request.getParameter("dni"));
				usuario.setNombreUsu(request.getParameter("usuario"));
				usuario.setContraseniaUsu(request.getParameter("password"));
				usuario.setRolUsu('C'); // PARA INDICAR QUE ES CLIENTE

				// GUARDAMOS LOS REGISTROS EN LA BD
				boolean exitoCliente = clienteDAO.agregarCliente(cliente);
				boolean exitoUsuario = false;
				if (exitoCliente) {
					exitoUsuario = usuarioNegocio.agregarUsuario(usuario);
				}
				if (exitoCliente && exitoUsuario) {
					response.sendRedirect("vistas/AltaCliente.jsp?exito=true");
				} else {
					response.sendRedirect("vistas/AltaCliente.jsp?exito=false");
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.sendRedirect("vistas/AltaCliente.jsp?exito=false");
			}
		}
		
		// Baja lógica de cliente
		if ("eliminar".equalsIgnoreCase(request.getParameter("accion"))) {
		    try {
		        String dni = request.getParameter("dni");
		        boolean eliminado = clienteNegocio.eliminarCliente(dni);
		        if (eliminado) {
		            response.sendRedirect("ClienteServlet?destino=listar");
		        } else {
		            response.sendRedirect("ClienteServlet?destino=listar&error=true");
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		        response.sendRedirect("ClienteServlet?destino=listar&error=true");
		    }
		}
		
	}
}
