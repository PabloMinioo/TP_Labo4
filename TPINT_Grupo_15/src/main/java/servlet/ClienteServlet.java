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
import DAO.ProvinciaDAO;
import DAO.LocalidadDAO;
import DAOimpl.ClienteDAOimpl;
import DAOimpl.LocalidadDAOImpl;
import DAOimpl.ProvinciaDAOImpl;
import Excepciones.ContraseñaNoCoincideException;
import entidad.Cliente;
import entidad.Prestamo;
import entidad.Provincia;
import entidad.Usuario;	
import negocio.ClienteNegocio;
import negocio.UsuarioNegocio;
import negocio.LocalidadNegocio;
import negocio.ProvinciaNegocio;
import negocioImpl.LocalidadNegocioImpl;
import negocioImpl.ProvinciaNegocioImpl;
import negocioImpl.ClienteNegocioImpl;
import negocioImpl.UsuarioNegocioImpl;
import Excepciones.ClienteExistenteException;

@WebServlet("/ClienteServlet")
public class ClienteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // INTANCIAMOS LAS CAPAS
    private ClienteDAO clienteDAO = new ClienteDAOimpl();
    private ClienteNegocio clienteNegocio = new ClienteNegocioImpl();
    private UsuarioNegocio usuarioNegocio = new UsuarioNegocioImpl();
//    private PrestamoNegocio prestamoNegocio = new PrestamoNegocioImpl();

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
            // EDITAR UN CLIENTE MEDIANTE SU DNI
            case "modificar":
            	String dni = request.getParameter("dni");
                request.setAttribute("editarDni", dni);
                listarClientes(request, response);
            	break;
            	//Muestra listado los prestamos que estan con estado pendientes
    		case "listarPrestamoP":
    			request.setAttribute("editarPrestamos", request.getParameter("numeroCuenta"));
    			listarPrestamos(request, response);
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
            // MODIFICAR UN CLIENTE
            case "modificar":
                modificarCliente(request, response);
                break;
            case "autoriza":
                autorizarPrestamo(request, response);
                break;
            case "rechaza":
            	rechazaPrestamo(request, response);
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
            throw new ServletException("Error al listar clientes", e);
        }
    }
    
    
   // METODO PARA LISTAR pretamos P
    Prestamo Prestamo= new Prestamo();
    
    private void listarPrestamos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       /*  try {
            List<Prestamo> listaPrestamos = prestamoNegocio.listarPrestamos();
            request.setAttribute("listaPrestamos", listaPrestamos);                  
            RequestDispatcher rd = request.getRequestDispatcher("/vistas/ListarPrestamos.jsp");
            rd.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error al listar prestamos", e);
        }*/ }
   

    // METODO PARA DAR DE ALTA UN CLIENTE
    private void altaCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	// Inicializamos la lista de provincias (por si hay error)
        ProvinciaNegocio provinciaNegocio = new ProvinciaNegocioImpl();
        List<Provincia> listaProvincias = null;

        try {
            listaProvincias = provinciaNegocio.listarProvincias();
            request.setAttribute("provincias", listaProvincias);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
        	
        	// OBTENEMOS LAS CONTRASEÑAS DEL FORMULARIO
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");
            
            // VALIDAMOS QUE LAS CONTRASEÑAS COINCIDAN
            clienteNegocio.validarContraseniasIguales(password, confirmPassword);
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
            usuario.setContraseniaUsu(password);
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
        	}catch (ClienteExistenteException | ContraseñaNoCoincideException e) {
                request.setAttribute("mensajeError", e.getMessage());
                request.getRequestDispatcher("vistas/AltaCliente.jsp").forward(request, response);
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
    
    private void modificarCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Cliente cliente = new Cliente();
            cliente.setDni(request.getParameter("dni"));
            cliente.setCuil(request.getParameter("cuil"));
            cliente.setNombre(request.getParameter("nombre"));
            cliente.setApellido(request.getParameter("apellido"));
            cliente.setSexo(request.getParameter("sexo"));
            cliente.setNacionalidad(request.getParameter("nacionalidad"));
            cliente.setFechaNacimiento(LocalDate.parse(request.getParameter("fechaNacimiento")));
            cliente.setDireccion(request.getParameter("direccion"));
            cliente.setIdProvincia(request.getParameter("provincia"));
            cliente.setIdLocalidad(request.getParameter("localidad"));
            cliente.setCorreoElectronico(request.getParameter("email"));
            cliente.setTelefonos(request.getParameter("telefonos"));
            cliente.setEstado(true);

            boolean exitoCliente = clienteNegocio.modificarCliente(cliente);

            String nuevaPassword = request.getParameter("password");
            boolean exitoUsuario = true;
            if (nuevaPassword != null && !nuevaPassword.isEmpty()) {
                exitoUsuario = usuarioNegocio.actualizarPassword(cliente.getDni(), nuevaPassword);
            }

            if (exitoCliente && exitoUsuario) {
                response.sendRedirect("ClienteServlet?accion=listar&modificado=true");
            } else {
                response.sendRedirect("ClienteServlet?accion=listar&modificado=false");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("ClienteServlet?accion=listar&modificado=false");
        }
    }
    private void autorizarPrestamo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
    }
    private void rechazaPrestamo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
