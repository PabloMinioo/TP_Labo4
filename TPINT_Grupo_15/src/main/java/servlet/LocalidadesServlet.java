package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.LocalidadDAO;
import entidad.Localidad;


@WebServlet("/CargarLocalidades")
public class LocalidadesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public LocalidadesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		 // System.out.println("=== CargarLocalidades servlet llamado ===");
	        
	        String provinciaId = request.getParameter("provinciaId");
	       // System.out.println("Provincia ID recibido: '" + provinciaId + "'");
	        
	        // Configurar response para JSON
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        
	        if (provinciaId == null || provinciaId.trim().isEmpty()) {
	          //  System.out.println("Error: Provincia ID vacío");
	            response.getWriter().write("[]");
	            return;
	        }
	        
	        try {
	            // Obtener localidades de la base de datos
	            List<Localidad> localidades = LocalidadDAO.obtenerPorProvincia(provinciaId.trim());
	           
	            
	            // Construir JSON manualmente
	            StringBuilder json = new StringBuilder();
	            json.append("[");
	            
	            for (int i = 0; i < localidades.size(); i++) {
	                Localidad loc = localidades.get(i);
	                json.append("{");
	                json.append("\"id\":\"").append(loc.getIdLocalidad_Loc()).append("\",");
	                json.append("\"descripcion\":\"").append(loc.getDescripcion_Loc()).append("\"");
	                json.append("}");
	                
	                if (i < localidades.size() - 1) {
	                    json.append(",");
	                }
	            }
	            json.append("]");
	            
	            String jsonResult = json.toString();
	         
	            
	            response.getWriter().write(jsonResult);
	            
	        } catch (Exception e) {
	          //  System.err.println("Error en CargarLocalidades: " + e.getMessage());
	            e.printStackTrace();
	            response.getWriter().write("[]");
	        }
	    }
    
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
