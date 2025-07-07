package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entidad.Prestamo;
import negocio.PrestamoNegocio;
import negocioImpl.PrestamoNegocioImpl;

/**
 * Servlet implementation class PrestamoServlet
 */
@WebServlet("/PrestamoServlet")
public class PrestamoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private PrestamoNegocio prestamoNegocio = new PrestamoNegocioImpl();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrestamoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		  String action = request.getParameter("accion");
	      
	        // SEGUN LA ACCION, REDIRIGIMOS AL METODO CORRESPONDIENTE
	        switch (action) {
	        	// MOSTRAR LISTADO DE CLIENTES
	         
	            	//Muestra listado los prestamos que estan con estado pendientes
	    		case "listarPrestamoP":
	    			request.setAttribute("editarPrestamos", request.getParameter("numeroCuenta"));
	    			listarPrestamos(request, response);
	    			break;
	            // REDIRIGIR A 'ListarPrestamo.jsp'
	            default:
	                response.sendRedirect("vistas/ListarPrestamo.jsp");
	                break;
	        }
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// OBTENEMOS EL PARAMETRO DEL JSP
        String action = request.getParameter("accion");
        // SEGUN LA ACCION, REDIRIGIMOS AL METODO CORRESPONDIENTE
        switch (action) {
      
            case "autorizar":
                autorizarPrestamo(request, response);
                break;
            case "rechazar":
            	rechazaPrestamo(request, response);
                break;
            // POR DEFECTO, LISTAMOS TODOS LOS CLIENTES
            default:
                response.sendRedirect("PrestamoServlet?accion=listarPrestamoP");
                break;
        }
	}

	// METODO PARA LISTAR pretamos P
    Prestamo Prestamo= new Prestamo();
    
    private void listarPrestamos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
        	 List<Prestamo> listaPrestamos = prestamoNegocio.listarPrestamos();
            request.setAttribute("listaPrestamos", listaPrestamos);                  
            RequestDispatcher rd = request.getRequestDispatcher("/vistas/ListarPrestamos.jsp");
            rd.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error al listar prestamos", e);
        }
    }
    
    private void autorizarPrestamo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	
    	int idPrestamo = Integer.parseInt(request.getParameter("idPrestamo"));
    
    	try {
    		
    		boolean cambioEstadoOK = prestamoNegocio.cambiarEstadoP(idPrestamo);
    	    
    		if (cambioEstadoOK) {
    			  List<Prestamo> lista = prestamoNegocio.listarPrestamos();
    	            request.setAttribute("listaPrestamos", lista);
    	       // request.setAttribute("error", "Préstamo aprobado");
    	    } else {
    	    	List<Prestamo> lista = prestamoNegocio.listarPrestamos();
	            request.setAttribute("listaPrestamos", lista);
    	        request.setAttribute("error", "No se pudo aprobar el préstamo");
    	    }
    		
    	} catch (Exception e) {
    	    e.printStackTrace();
    	    request.setAttribute("error", "Error interno: " + e.getMessage());
    	}
    	request.getRequestDispatcher("/vistas/ListarPrestamos.jsp").forward(request, response);
    	
    
    }
    
    
    private void rechazaPrestamo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	int idPrestamo = Integer.parseInt(request.getParameter("idPrestamo"));
        
    	try {
    		
    		boolean cambioEstadoOK = prestamoNegocio.cambiarEstadoR(idPrestamo);
    	    
    		if (cambioEstadoOK) {
    			  List<Prestamo> lista = prestamoNegocio.listarPrestamos();
    	            request.setAttribute("listaPrestamos", lista);
    	       // request.setAttribute("error", "Préstamo aprobado");
    	    } else {
    	    	List<Prestamo> lista = prestamoNegocio.listarPrestamos();
	            request.setAttribute("listaPrestamos", lista);
    	        request.setAttribute("error", "devuelve falso la consulta");
    	    }
    		
    	} catch (Exception e) {
    	    e.printStackTrace();
    	    request.setAttribute("error", "Error interno: " + e.getMessage());
    	}
    	request.getRequestDispatcher("/vistas/ListarPrestamos.jsp").forward(request, response);
    	
    
    }
    
	
}
