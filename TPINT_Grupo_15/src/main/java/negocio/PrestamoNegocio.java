package negocio;

import java.util.List;

import entidad.Cliente;
import entidad.Prestamo;

public interface PrestamoNegocio {
	 boolean SolicitarPrestamo(Prestamo prestamo) throws Exception;//me carga la tabla de prestamos 
	 List<Prestamo> listarPrestamos() throws Exception;//me lista los pretsamos ocn estado P
	 boolean  cambiarEstadoR(int idPrestamo) throws Exception;
	 boolean cambiarEstadoP(int idPrestamo) throws Exception;
}
