package negocio;

import java.util.List;
import entidad.Movimiento;

public interface MovimientoNegocio {
	List<Movimiento> obtenerMovimientosPorCuenta(int numeroCuenta) throws Exception;
}
