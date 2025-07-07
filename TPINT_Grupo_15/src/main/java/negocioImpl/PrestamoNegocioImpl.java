package negocioImpl;

import java.util.List;

import DAO.PrestamoDAO;
import DAOimpl.PrestamoDAOimpl;
import entidad.Cliente;
import entidad.Prestamo;
import negocio.PrestamoNegocio;

public class PrestamoNegocioImpl implements PrestamoNegocio {

	 private final PrestamoDAO prestamoDAO = new PrestamoDAOimpl();
	 //
	 @Override
	    public boolean SolicitarPrestamo(Prestamo prestamo) throws Exception {
	        return prestamoDAO.CargarSolicitud(prestamo);
	    }
	 
	 //prestamos listar los que tienene estado  P
	 @Override
	    public List<Prestamo> listarPrestamos() throws Exception {
	        return prestamoDAO.listarPrestamos();
	    }
	 
	 // cambi el estado del prestamo de P a A aprobado
	 
	 @Override
	 public boolean  cambiarEstadoP(int idPrestamo)  throws Exception{
			 return prestamoDAO.estadoP(idPrestamo);
	 }
	 
	 
	 // cambi el estado del prestamo de P a  reprobado
	 @Override
	 public boolean  cambiarEstadoR(int idPrestamo)  throws Exception{
			 return prestamoDAO.estadoR(idPrestamo);
	 }
}
