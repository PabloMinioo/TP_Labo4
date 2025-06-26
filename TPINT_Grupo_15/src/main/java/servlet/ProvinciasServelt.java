package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.ProvinciaDAO;
import entidad.Provincia;


@WebServlet("/CargarProvincias")
public class ProvinciasServelt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public ProvinciasServelt() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  List<Provincia> listaProvincias = ProvinciaDAO.obtenerTodas();  // Tu método DAO
	        request.setAttribute("provincias", listaProvincias);
	        
	        request.getRequestDispatcher("/vistas/AltaCliente.jsp").forward(request, response);
	    }
	    
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
