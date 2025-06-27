package servlet;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entidad.Cuenta;

/**
 * Servlet implementation class ModificarCuentaServlet
 */
@WebServlet("/ModificarCuentaServlet")
public class ModificarCuentaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModificarCuentaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	    int numeroCuenta = Integer.parseInt(request.getParameter("numeroCuenta"));
//	    String dni = request.getParameter("dni");
//	    int tipoCuenta = Integer.parseInt(request.getParameter("tipoCuenta"));
//	    String cbu = request.getParameter("cbu");
//	    BigDecimal saldo = new BigDecimal(request.getParameter("saldo"));
//	    boolean estado = Boolean.parseBoolean(request.getParameter("estado"));
//
//	    Cuenta cuenta = new Cuenta(numeroCuenta, dni, tipoCuenta, cbu, saldo, estado);
//	    CuentaNegocio cuentaNegocio = new CuentaNegocioImpl();
//	    cuentaNegocio.actualizarCuenta(cuenta);
//
//	    response.sendRedirect("listarCuentas.jsp");
	}


}
