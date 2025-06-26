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
import DAOimpl.ClienteDAOimpl;
import entidad.Cliente;
import entidad.Usuario;
import negocio.ClienteNegocio;
import negocio.UsuarioNegocio;
import negocioImpl.ClienteNegocioImpl;
import negocioImpl.UsuarioNegocioImpl;

@WebServlet("/ClienteServlet")
public class ClienteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // INTANCIAMOS LAS CAPAS
    private ClienteDAO clienteDAO = new ClienteDAOimpl();
    private ClienteNegocio clienteNegocio = new ClienteNegocioImpl();
    private UsuarioNegocio usuarioNegocio = new UsuarioNegocioImpl();

    // CONSTRUCTOR VACIO
    public ClienteServlet() {
        super();
    }

    // PETICIONES GET: 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	// OBTENEMOS EL PARAMETRO DEL JSP
        String action = request.getParameter("accion");
        // POR DEFECTO, HACEMOS QUE LISTE LOS CLIENTES SIN FILTROS
        if (action == null) action = "listar";
        // SEGUN LA ACCION, REDIRIGIMOS AL METODO CORRESPONDIENTE
        switch (action) {
        	// MOSTRAR LISTADO DE CLIENTES
            case "listar":
                listarClientes(request, response);
                break;
            // REDIRIGIR A 'AltaCliente.jsp'
            default:
                response.sendRedirect("vistas/AltaCliente.jsp");
                break;
        }
    }
    // PETICIONES POST: 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	// OBTENEMOS EL PARAMETRO DEL JSP
        String action = request.getParameter("accion");
        // SEGUN LA ACCION, REDIRIGIMOS AL METODO CORRESPONDIENTE
        switch (action) {
        	// DAR DE ALTA UN CLIENTE
            case "alta":
                altaCliente(request, response);             
                break;
            // ELIMINAR UN CLIENTE
            case "eliminar":
                eliminarCliente(request, response);
                break;
            // POR DEFECTO, LISTAMOS TODOS LOS CLIENTES
            default:
                response.sendRedirect("ClienteServlet?accion=listar");
                break;
        }
    }

    // METODO PARA LISTAR CLIENTES
    private void listarClientes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Cliente> listaClientes = clienteNegocio.listarClientes();
            request.setAttribute("listaClientes", listaClientes);
            RequestDispatcher rd = request.getRequestDispatcher("/vistas/ListarClientes.jsp");
            rd.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // METODO PARA DAR DE ALTA UN CLIENTE
    private void altaCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
        	// CREAMOS UN OBJETO CLIENTE
        	Cliente cliente = new Cliente();
            // CARGAMOS AL OBJETO 'CLIENTE' LOS VALORES DEL JSP
            cliente.setDni(request.getParameter("dni"));
            cliente.setCuil(request.getParameter("cuil"));
            cliente.setNombre(request.getParameter("nombre"));
            cliente.setApellido(request.getParameter("apellido"));
            cliente.setSexo(request.getParameter("sexo"));
            cliente.setNacionalidad(request.getParameter("nacionalidad"));
            // CONVERTIMOS DE STRING A LOCALDATE
            cliente.setFechaNacimiento(LocalDate.parse(request.getParameter("fechaNacimiento")));
            cliente.setDireccion(request.getParameter("direccion"));
            cliente.setIdProvincia(request.getParameter("provincia"));
            cliente.setIdLocalidad(request.getParameter("localidad"));
            cliente.setCorreoElectronico(request.getParameter("email"));
            cliente.setTelefonos(request.getParameter("telefonos"));

            // CARGAMOS AL OBJETO 'USUARIO' LOS VALORES DEL JSP
            Usuario usuario = new Usuario();
            usuario.setDniUsu(cliente.getDni());
            usuario.setNombreUsu(request.getParameter("usuario"));
            usuario.setContraseniaUsu(request.getParameter("password"));
            usuario.setRolUsu('C');

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

    // METODO PARA HACER BAJA LOGICA DE UN CLIENTE
    private void eliminarCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
        	// OBTENEMOS EL DNI DEL CLIENTE
            String dni = request.getParameter("dni");
            // REALIZAMOS LA BAJA LOGICA DEL CLIENTE
            boolean eliminado = clienteNegocio.eliminarCliente(dni);
            if (eliminado) {
                response.sendRedirect("ClienteServlet?accion=listar");
            } else {
                response.sendRedirect("ClienteServlet?accion=listar&error=true");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("ClienteServlet?accion=listar&error=true");
        }
    }
}
