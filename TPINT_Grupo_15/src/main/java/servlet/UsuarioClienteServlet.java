package servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidad.Cliente;
import entidad.Cuenta;
import entidad.Cuota;
import entidad.Prestamo;
import entidad.Provincia;
import negocio.CuentaNegocio;
import negocio.PrestamoNegocio;
import negocioImpl.CuentaNegocioImpl;
import negocioImpl.PrestamoNegocioImpl;
import negocio.PrestamoNegocio;
import negocio.ClienteNegocio;
import negocioImpl.ClienteNegocioImpl;

/**
 * Servlet implementation class UsuarioClienteServlet
 */
@WebServlet("/UsuarioClienteServlet")
public class UsuarioClienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	  private final CuentaNegocio cuentaNegocio = new CuentaNegocioImpl();
	  private final PrestamoNegocio  prestamoNegocio =new PrestamoNegocioImpl();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsuarioClienteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//cargo las ddl con el dni de lasession
		  HttpSession session = request.getSession(false);
		  String  dni = (session != null) ? (String ) session.getAttribute("dniUsuario") : null;

	        if (dni == null) {
	            response.sendRedirect(request.getContextPath() + "/vistas/Login.jsp");
	            return;
	        }
	       
	        ///esto hast aca
	 
	        
	        try {
	        	  List<Cuenta> cuentas = cuentaNegocio.CargarDDlCuentas(dni);
	        	  session.setAttribute("listaCuentas", cuentas);//le pongo session y no request porque sino cada vez que se actulaiza lapagina se pierden nlos DDL precargados
	  	        request.getRequestDispatcher("/vistas/AltaPrestamo.jsp").forward(request, response);
	  	    
		    } catch (Exception e) {
		        e.printStackTrace();
		        throw new ServletException("Error cargando cuentas", e);
		    }
		
	     
	        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//cargo las ddl con el dni de lasession
		  
			
	        //empiezan las acciones de los values

	        String accion = request.getParameter("accion");
	       
	      
	        // SEGUN LA ACCION, REDIRIGIMOS AL METODO CORRESPONDIENTE
	        switch (accion) {
	        	// Vemos como estaria conformado el prestamo
	            case "SolicitarPrestamo":
	            	solicitarPrestamo(request, response);
	            break;    
	            // pedir prestamo
	            case "VerPrestamo":
	            	  verPrestamo(request, response);
	             break;
	            case "listarPagarPrestamo":
	            	  ListarPagarPrestamo(request, response);
	             break;
	             
	             
	             
	            //info personal  
	            case "verInformacionPersonal":
	                verInformacionPersonal(request, response);  
             	break;
	            // REDIRIGIR A 'HomeCliente.jsp'
	            default:
	                response.sendRedirect("vistas/HomeCliente.jsp");
	                break;
	        }
	        
	}
	
	
	 private void ListarPagarPrestamo(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
		 
		//cargo las ddl con el dni de lasession
		  HttpSession session = request.getSession(false);
		  String  dni = (session != null) ? (String ) session.getAttribute("dniUsuario") : null;

	        if (dni == null) {
	            response.sendRedirect(request.getContextPath() + "/vistas/Login.jsp");
	            return;
	        }
	       
	        ///esto hast aca
	 //esto carga la ddl con las cuenta - saldo a debitar
	        
	        try {
	        	  List<Cuenta> cuentas = cuentaNegocio.CargarDDlCuentas(dni);
	        	  session.setAttribute("listaCuentas", cuentas);//le pongo session y no request porque sino cada vez que se actulaiza lapagina se pierden nlos DDL precargados
	  	       
	        	// METODO PARA LISTAR pretamos Para pagar con la D debe
	        	   
	        	        	 List<Cuota> listaPagarPrestamos = prestamoNegocio.listarPagarPrestamos();
	        	            request.setAttribute("listaPagarPrestamos", listaPagarPrestamos);                  
	        	           request.getRequestDispatcher("/vistas/PagarPrestamo.jsp").forward(request, response);
	        	        } catch (Exception e) {
	        	            e.printStackTrace();
	        	            throw new ServletException("Error al listar prestamos", e);
	        	        }
	        	    
	        	    }
	      
		 
		 
	 
	 
		 
	
	 private void verPrestamo(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
		 
		
		 
		 System.out.println("------------entra  ver pretamo");
      //agarro los valores de mi jsp
			String importeStr = request.getParameter("importe");
			String  cuotasStr = request.getParameter("cuotas");
			String cuentaDestino = request.getParameter("cuentaDestino");
			
			
//parseo
			int cuotas = Integer.parseInt(cuotasStr);
		float importe = Float.parseFloat(importeStr); /// El problema es quemum eustra con 1 decimal, ver is estan de acuerdo!!!
		

			float interes;
			switch (cuotas) {

			case 3:
				interes = 0.20f;
				break;
			case 6:
				interes = 0.50f;
				break;
			case 12:
				interes = 0.75f;
				break;
			case 24:
				interes = 1.00f;
				break;
			default:
				interes = 0.0f;
			}
			 System.out.println("importe----------------- = " +(importe*interes+importe));
			float total = importe * interes + importe; // 1000.5 * 1.5 = 1500.75
		 System.out.println(total);
			float cuotaMens = total / cuotas; // 250.125

				request.setAttribute("cuentaDestino", cuentaDestino);
				request.setAttribute("importe", importe);
				request.setAttribute("cuotas", cuotas);
				request.setAttribute("cuotaMensual", cuotaMens);
				request.setAttribute("total", total);
	
			  
			  request.getRequestDispatcher("/vistas/AltaPrestamo.jsp").forward(request, response);
		
	 }
	 
	 
	 private void solicitarPrestamo(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {

		 System.out.println("Entro Solicitar prestam!!!-------------");
		 //me afano el dni de la session 
		 HttpSession session = request.getSession(false);
		  String  dni = (session != null) ? (String ) session.getAttribute("dniUsuario") : null;
//agarro los valores de mi jsp
		 
		  String cuentaDestinoStr = request.getParameter("cuentaDestino-VerPrestamo");
		    String importeStr = request.getParameter("importe-VerPrestamo");
		    String cuotas = request.getParameter("cuotas-VerPrestamo");
		    String totalStr =request.getParameter("total-VerPrestamo");
		    String cuotaMensualStr = request.getParameter("cuotaMensual-VerPrestamo");
		    // Parseo
		     
		     System.out.println(dni);
		     System.out.println(cuentaDestinoStr);
		     System.out.println(importeStr);
		     System.out.println(cuotas);
		     System.out.println(totalStr);
		     
		     if (cuentaDestinoStr == null || cuentaDestinoStr.isEmpty() ||
		    		    importeStr       == null || importeStr.isEmpty()       ||
		    		    cuotas        == null || cuotas.isEmpty()        ||
		    		    totalStr         == null || totalStr.isEmpty()) {

		    		    request.setAttribute("error", 
		    		        "Faltan datos para procesar la solicitud. Asegúrese de haber hecho primero 'Ver Préstamo'.");
		    		    request.getRequestDispatcher("/vistas/AltaPrestamo.jsp")
		    		           .forward(request, response);
		    		    return;
		    		}
		     
		    int cuentaDestino= Integer.parseInt(cuentaDestinoStr);//esta como int jeje
		
		 BigDecimal importe = new BigDecimal(importeStr) .setScale(2, RoundingMode.HALF_UP);///no uso float por las dudas, yaque usea arriba y hay problemas e redondeo
		 BigDecimal total = new BigDecimal(totalStr).setScale(2, RoundingMode.HALF_UP);
		 BigDecimal cuotaMensual = new BigDecimal(cuotaMensualStr).setScale(2, RoundingMode.HALF_UP);
		     
		     
		     //seteooo
		    
		    Prestamo prestamo = new Prestamo();
		    prestamo.setNumeroCuenta(cuentaDestino);
		  prestamo.setImporteSolicitado(importe);
		    prestamo.setCuotas(cuotas);//aca esto esta como  varchar en la , se podrai cambiaren bd a int?? no se 
		    prestamo.setEstado("P");
		    prestamo.setDniCliente(dni);
		    prestamo.setFecha(LocalDate.now());
		  prestamo.setImporteTotal(total);
		  //inventado masomenos no se uqe poner ene sos campos sin o me deja cargar la bd
		  prestamo.setPlazoMeses(Integer.parseInt(cuotas));
		  prestamo.setMontoMensual(cuotaMensual);
		  
		  
		    System.out.println(prestamo.getNumeroCuenta());
		    System.out.println(prestamo.getDniCliente());
		    System.out.println(prestamo.getImporteSolicitado());
		    System.out.println(prestamo.getEstado());
		    System.out.println(prestamo.getCuotas());
		    System.out.println(prestamo.getFecha());
		    System.out.println(prestamo.getImporteTotal());
		    		
		
		    try {
		    	PrestamoNegocio negocio = new PrestamoNegocioImpl();
		    	boolean solicitud= negocio.SolicitarPrestamo(prestamo);
		    	if(solicitud ){
		    		// response.sendRedirect(request.getContextPath() + "/vistas/ConfirmacionPrestamo.jsp");
		    		
		    		 request.setAttribute("error", "¡Tu préstamo se solicitó con éxito!En breve recibirás la aprobación");
		    		request.getRequestDispatcher( "/vistas/AltaPrestamo.jsp").forward(request, response);
		    		
		    		
		       
		    	} else {
		            request.setAttribute("error", "No se pudo guardar el préstamo.");
		        }
		      
		    } catch (Exception e) {
		        request.setAttribute("error", "Error al solicitar préstamo: " + e.getMessage());
		    }
		    
		    // Volver a la vista con mensaje
		    request.getRequestDispatcher("/vistas/AltaPrestamo.jsp").forward(request, response);
		    
		    
		    
	 }
	 
	 private void verInformacionPersonal(HttpServletRequest request, HttpServletResponse response)
		        throws ServletException, IOException {

		    HttpSession session = request.getSession(false);
		    String dni = (session != null) ? (String) session.getAttribute("dniUsuario") : null;

		    if (dni == null) {
		        response.sendRedirect(request.getContextPath() + "/vistas/Login.jsp");
		        return;
		    }

		    try {
		        ClienteNegocio clienteNeg = new ClienteNegocioImpl();
		        Cliente cliente = clienteNeg.obtenerClientePorDni(dni); 
		        request.setAttribute("cliente", cliente);
		        request.getRequestDispatcher("/vistas/InformacionPersonal.jsp").forward(request, response);

		    } catch (Exception e) {
		        e.printStackTrace();
		        response.sendRedirect(request.getContextPath() + "/vistas/Error.jsp");
		    }
		}
	 
	 
}
