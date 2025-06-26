package servlet;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import DAO.ReportesDao;

import java.io.IOException;
import java.util.List;
import DAO.ReportesDao;
import DAOimpl.ReportesDAOimpl;
/**
 * Servlet implementation class ReportesServlet
 */
@WebServlet("/ReportesServlet")
public class ReportesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
            ReportesDao dao = new ReportesDAOimpl();

            String desde = request.getParameter("desde");
            String hasta = request.getParameter("hasta");
            String fechaExacta = request.getParameter("fechaExacta");

            request.setAttribute("totalClientes", dao.contarClientes());
            request.setAttribute("totalCuentas", dao.contarCuentas());
            request.setAttribute("promedioCuentas", dao.promedioCuentasPorCliente());
            request.setAttribute("estadisticasClientes", dao.obtenerEstadisticasCuentas());

            if (fechaExacta != null && !fechaExacta.isEmpty()) {
                request.setAttribute("prestamosAprobados", dao.contarPrestamosPorEstadoYFechaExacta("Aprobado", fechaExacta));
                request.setAttribute("prestamosRechazados", dao.contarPrestamosPorEstadoYFechaExacta("Rechazado", fechaExacta));
                request.setAttribute("modo", "exacta");
                request.setAttribute("fechaExacta", fechaExacta);
            } else if (desde != null && hasta != null && !desde.isEmpty() && !hasta.isEmpty()) {
                request.setAttribute("prestamosAprobados", dao.contarPrestamosPorEstadoYFecha("Aprobado", desde, hasta));
                request.setAttribute("prestamosRechazados", dao.contarPrestamosPorEstadoYFecha("Rechazado", desde, hasta));
                request.setAttribute("modo", "rango");
                request.setAttribute("desde", desde);
                request.setAttribute("hasta", hasta);
            } else {
                request.setAttribute("prestamosAprobados", dao.contarPrestamosPorEstado("Aprobado"));
                request.setAttribute("prestamosRechazados", dao.contarPrestamosPorEstado("Rechazado"));
                request.setAttribute("modo", "todos");
            }

            request.getRequestDispatcher("estadisticas.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error al obtener estadísticas", e);
        }
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
