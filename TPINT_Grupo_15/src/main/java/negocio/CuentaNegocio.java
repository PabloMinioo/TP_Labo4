package negocio;

import entidad.Cuenta;
import entidad.TipoCuenta;

import java.sql.SQLException;
import java.util.List;

public interface CuentaNegocio {

	// Operaciones ABML con validaciones de negocio
	boolean crearCuenta(Cuenta cuenta) throws SQLException;/// usio judi
	boolean modificarCuenta(Cuenta cuenta) throws Exception;
	boolean eliminarCuenta(int numeroCuenta) throws Exception;
	List<Cuenta> listarCuentas() throws Exception;/// uso celi
	
	List<Cuenta>CargarDDlCuentas(String dni) throws Exception;//solicitar prestamo -altaPrestamo
	List<TipoCuenta> listarTiposCuenta();
}