package DAO;

import java.util.List;
import entidad.Movimiento;

public interface MovimientoDAO {
	List<Movimiento> obtenerMovimientosPorCuenta(int numeroCuenta) throws Exception;
}
