package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.CuentaDAO;
import DAOimpl.CuentaDAOImpl;
import Excepciones.CampoInvalidoException;
import entidad.Cliente;
import entidad.Cuenta;
import entidad.TipoCuenta;
import negocio.CuentaNegocio;
import negocioImpl.CuentaNegocioImpl;

@WebServlet("/CuentaServlet")
public class CuentaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// INTANCIAMOS LAS CAPAS
	private CuentaNegocio cuentaNegocio = new CuentaNegocioImpl();

	// CONSTRUCTOR VACIO
	public CuentaServlet() {
		super();
		this.cuentaNegocio = new CuentaNegocioImpl();
	}

	// PETICIONES GET:
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// OBTENEMOS EL PARAMETRO DEL JSP
		String action = request.getParameter("accion");
		// POR DEFECTO, HACEMOS QUE LISTE LAS CUENTAS SIN FILTROS
		if (action == null) {
			action = "listar";
		}
		// SEGUN LA ACCION, REDIRIGIMOS AL METODO CORRESPONDIENTE
		switch (action) {
		// MOSTRAR LISTADO DE CUENTAS
		case "listar":
			listarCuentas(request, response);
			break;			
		// EDITAR UN CLIENTE MEDIANTE SU DNI
		case "modificar":
			request.setAttribute("editarCuenta", request.getParameter("numeroCuenta"));
			listarCuentas(request, response);
			break;
		// REDIRIGIR A 'AltaCuenta.jsp'
		default:
			response.sendRedirect("vistas/AltaCuenta.jsp");
			break;
		}
	}

	// PETICIONES POST:
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// OBTENEMOS EL PARAMETRO DEL JSP
		String action = request.getParameter("accion");
		// SEGUN LA ACCION, REDIRIGIMOS AL METODO CORRESPONDIENTE
		switch (action) {
		// DAR DE ALTA UNA CUENTA
		case "alta":
			altaCuenta(request, response);
			break;
		// ELIMINAR UNA CUENTA
		case "eliminar":
			eliminarCuenta(request, response);
			break;
		// MODIFICAR UN CLIENTE
		case "modificar":
			modificarCuenta(request, response);
			break;
		// POR DEFECTO, LISTAMOS TODAS LAS CUENTAS
		default:
			response.sendRedirect("CuentaServlet?accion=listar");
			break;
		}
	}

	// METODO PARA LISTAR CUENTAS
	private void listarCuentas(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	    try {
	        List<Cuenta> listaCuentas = cuentaNegocio.listarCuentas();
	        request.setAttribute("listaCuentas", listaCuentas);
	        cargarTiposCuenta(request);
	        request.getRequestDispatcher("/vistas/ListarCuentas.jsp").forward(request, response);
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new ServletException("Error al listar cuentas", e);
	    }
	}
	
	// METODO PARA DAR DE ALTA UNA CUENTA
	private void altaCuenta(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    try {
	        // OBTENEMOS LOS DATOS DE LA CUENTA DESDE EL JSP PARA VALIDAD DATOS
	        Cuenta cuenta = obtenerCuentaDesdeJSP(request);
	        // SETEAMOS EL SALDO EN 10000.00
	        cuenta.setSaldo(10000.00);
	        // CREAMOS LA CUENTA
	        boolean exitoCuenta = cuentaNegocio.crearCuenta(cuenta);
			if (exitoCuenta) {
				request.setAttribute("mensaje", "CUENTA CREADA  CON EXITO.");
				request.getRequestDispatcher("/vistas/AltaCuenta.jsp").forward(request, response);
			} else {
				request.setAttribute("mensaje", "EL DNI NO EXISTE COMO CLIENTE O EL CLIENTE YA CUENTA CON 3 CUENTAS.");
				request.getRequestDispatcher("/vistas/AltaCuenta.jsp").forward(request, response);
			}
	    // EXCEPCIONES
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.sendRedirect("vistas/AltaCuenta.jsp?exito=false");
	    }
	}

	// METODO PARA MODIFICAR UNA CUENTA
	private void modificarCuenta(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    try {
	        // OBTENEMOS LOS DATOS DE LA CUENTA DESDE EL JSP PARA VALIDAD DATOS
	        Cuenta cuenta = obtenerCuentaDesdeJSP(request);
	        // MODIFICAMOS LA CUENTA
	        boolean exitoCuenta = cuentaNegocio.modificarCuenta(cuenta);
			if (exitoCuenta) {
				response.sendRedirect("CuentaServlet?accion=listar&modificado=true");
			} else {
				response.sendRedirect("CuentaServlet?accion=listar&modificado=false");
			}
		// EXCEPCIONES
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.sendRedirect("CuentaServlet?accion=listar&modificado=false");
	    }
	}

	// METODO PARA ELIMINAR UNA CUENTA
	private void eliminarCuenta(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    try {
	    	// OBTENEMOS EL NUMERO DE CUENTA
	        int numeroCuenta = Integer.parseInt(request.getParameter("numeroCuenta"));
	        // ELIMINAMOS LA CUENTA
	        boolean exito = cuentaNegocio.eliminarCuenta(numeroCuenta);
	        if (exito) {
	            response.sendRedirect("CuentaServlet?accion=listar");
	        } else {
	            response.sendRedirect("CuentaServlet?accion=listar&error=true");
	        }
	    // EXCEPCIONES
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.sendRedirect("CuentaServlet?accion=listar&error=true");
	    }
	}

	// METODO PARA OBTENER LOS DATOS DE LAS CUENTAS DEL JSP
	private Cuenta obtenerCuentaDesdeJSP(HttpServletRequest request) {
		// CREAMOS UNA CUENTA Y LE SETEAMOS LOS VALORES
	    Cuenta cuenta = new Cuenta();
	    cuenta.setNumeroCuenta(Integer.parseInt(request.getParameter("numeroCuenta")));
	    cuenta.setTipoCuenta(Integer.parseInt(request.getParameter("tipoCuenta")));
	    cuenta.setFechaCreacion(LocalDate.parse(request.getParameter("fechaCreacion")));
	    cuenta.setSaldo(Double.parseDouble(request.getParameter("saldo")));
	    return cuenta;
	}
	
	// METODO PARA CARGAR LOS TIPOS DE CUENTA DESDE LA BD
	private void cargarTiposCuenta(HttpServletRequest request) {
	    try {
	        List<TipoCuenta> listaTiposCuenta = cuentaNegocio.listarTiposCuenta();
	        request.setAttribute("listaTiposCuenta", listaTiposCuenta);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
