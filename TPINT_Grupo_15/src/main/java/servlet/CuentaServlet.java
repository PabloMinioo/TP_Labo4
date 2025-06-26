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

import DAO.CuentaDAO;
import DAOimpl.CuentaDAOImpl;
import entidad.Cuenta;
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
			RequestDispatcher dispatcher = request.getRequestDispatcher("/vistas/ListarCuentas.jsp");
			dispatcher.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    // METODO PARA DAR DE ALTA UNA CUENTA
	private void altaCuenta(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// OBTENEMOS LOS VALORES DEL JSP
			String clienteDNI = request.getParameter("clienteDNI");
			int tipoCuenta = Integer.parseInt(request.getParameter("tipoCuenta"));
			double saldoInicial = Double.parseDouble(request.getParameter("saldoInicial"));
			
			// CREAMOS UN OBJETO CUENTA
			Cuenta cuenta = new Cuenta();
			
			// CARGAMOS AL OBJETO 'CUENTA' LOS VALORES DEL JSP
			cuenta.setClienteDNI(clienteDNI);
			cuenta.setTipoCuenta(tipoCuenta);
			cuenta.setSaldo(saldoInicial);
			cuenta.setFechaCreacion(LocalDate.now());

			// GUARDAMOS LOS REGISTROS EN LA BD
			boolean exito = cuentaNegocio.crearCuenta(cuenta);
			if (exito) {
				request.setAttribute("mensaje", "Cuenta creada exitosamente");
				request.setAttribute("tipoMensaje", "success");
			} else {
				request.setAttribute("mensaje", "Error al crear la cuenta");
				request.setAttribute("tipoMensaje", "error");
			}

		} catch (Exception e) {
			request.setAttribute("mensaje", "Error: " + e.getMessage());
			request.setAttribute("tipoMensaje", "error");
		}
	}

	// METODO PARA ELIMINAR UNA CUENTA
	private void eliminarCuenta(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// OBTENEMOS EL NUMERO DE CUENTA
			int numeroCuenta = Integer.parseInt(request.getParameter("numeroCuenta"));
			// REALIZAMOS LA BAJA LOGICA DE LA CUENTA
			boolean exito = cuentaNegocio.eliminarCuenta(numeroCuenta);
			if (exito) {
				response.sendRedirect("CuentaServlet?accion=listar");
			} else {
				response.sendRedirect("CuentaServlet?accion=listar&error=true");
			}

		} catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("CuentaServlet?accion=listar&error=true");
		}

		listarCuentas(request, response);
	}
}
