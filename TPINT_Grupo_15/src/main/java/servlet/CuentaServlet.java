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
			try {
				altaCuenta(request, response);
			} catch (ServletException | IOException | CampoInvalidoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			List<TipoCuenta> listaTiposCuenta = cuentaNegocio.listarTiposCuenta();
			System.out.println("Tipos de cuenta cargados: " + listaTiposCuenta.size());
			request.setAttribute("listaCuentas", listaCuentas);
			request.setAttribute("listaTiposCuenta", listaTiposCuenta);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/vistas/ListarCuentas.jsp");
			dispatcher.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// METODO PARA DAR DE ALTA UNA CUENTA
	private void altaCuenta(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, CampoInvalidoException {
		// OBTENEMOS LOS VALORES DEL JSP
		String dni = request.getParameter("cliente");
		String fechaStr = request.getParameter("fechaCreacion");
		String tipoCuentaStr = request.getParameter("tipoCuenta");
		int tipoCuenta = Integer.parseInt(tipoCuentaStr);
		double saldo = 10000.00;
		LocalDate fechaCreacion = LocalDate.parse(fechaStr);
        if (fechaCreacion.isAfter(LocalDate.now())) {
            throw new CampoInvalidoException("La fecha de creación no puede ser posterior a la actual.");
        }
		// CREAMOS UN OBJETO CUENTA
		Cuenta cuenta = new Cuenta();
		// CARGAMOS AL OBJETO 'CUENTA' LOS VALORES DEL JSP
		cuenta.setClienteDNI(dni);
		cuenta.setTipoCuenta(tipoCuenta);
		cuenta.setFechaCreacion(fechaCreacion);
		cuenta.setSaldo(saldo);
		CuentaNegocio negocio = new CuentaNegocioImpl();
		try {
			boolean ok = negocio.crearCuenta(cuenta);
			if (ok) {
				request.setAttribute("mensaje", "CUENTA CREADA  CON EXITO.");
				request.getRequestDispatcher("/vistas/AltaCuenta.jsp").forward(request, response);
			} else {
				request.setAttribute("mensaje", "El DNI NOO EXISTE COMO CLIENTE O EL CLIENTE YA CUENTA CON 3 CUENTAS.");
				request.getRequestDispatcher("/vistas/AltaCuenta.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendRedirect("error.jsp");
		}
	}

	private void modificarCuenta(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Cuenta cuenta = new Cuenta();
			String fechaStr = request.getParameter("fechaCreacion");
			String numeroCuentaStr = request.getParameter("numeroCuenta");
			LocalDate fechaCreacion = LocalDate.parse(fechaStr);
	        if (fechaCreacion.isAfter(LocalDate.now())) {
	            throw new CampoInvalidoException("La fecha de creación no puede ser posterior a la actual.");
	        }
			cuenta.setNumeroCuenta(Integer.parseInt(request.getParameter("numeroCuenta")));
			cuenta.setFechaCreacion(LocalDate.parse(request.getParameter("fechaCreacion")));
			cuenta.setTipoCuenta(Integer.parseInt(request.getParameter("tipoCuenta")));
			boolean ok = cuentaNegocio.modificarCuenta(cuenta);
			if (ok) {
				response.sendRedirect("CuentaServlet?accion=listar&modificado=true");
			} else {
				response.sendRedirect("CuentaServlet?accion=listar&modificado=false");
			}

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
