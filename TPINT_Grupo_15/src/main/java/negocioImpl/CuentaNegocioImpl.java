package negocioImpl;

import negocio.CuentaNegocio;
import DAO.CuentaDAO;
import DAOimpl.CuentaDAOImpl;
import entidad.Cliente;
import entidad.Cuenta;
import entidad.TipoCuenta;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CuentaNegocioImpl implements CuentaNegocio {

	private CuentaDAO cuentaDAO;

	public CuentaNegocioImpl() {
		this.cuentaDAO = new CuentaDAOImpl();
	}

	@Override
	public boolean crearCuenta(Cuenta cuenta) throws SQLException {
		// try {
		// Validaciones de negocio, existe el dni en la bd como cliente?
		if (!cuentaDAO.existeDNI(cuenta.getClienteDNI())) {
			return false;
		}
		// Validar que el cliente no tenga más de 3 cuentas
		if (cuentaDAO.contarCuentasPorCliente(cuenta.getClienteDNI()) >= 3) {
			return false;
		}
		// si si existe eguimos generando un cbu ramdomm ylo insertamos luego
		cuenta.setCbu(generarCBU());
		return cuentaDAO.insertar(cuenta);
	}

	/// crear cbu cualquiera
	///
	private String generarCBU() {
		StringBuilder sb = new StringBuilder();
		ThreadLocalRandom rnd = ThreadLocalRandom.current();
		for (int i = 0; i < 22; i++)
			sb.append(rnd.nextInt(10));
		return sb.toString();
	}

	// c/celiii
	public List<Cuenta> listarCuentas() throws Exception {
		return cuentaDAO.listarCuentas();
	}

	@Override
	public boolean modificarCuenta(Cuenta cuenta) throws Exception {
		return cuentaDAO.modificarCuenta(cuenta);
	}

	@Override
	public boolean eliminarCuenta(int numeroCuenta) throws Exception {
		return cuentaDAO.eliminar(numeroCuenta);
	}

	// solicitud de prestamo cargar la ddl cuentas
	@Override
	public List<Cuenta> CargarDDlCuentas(String dni) throws Exception {
		return cuentaDAO.CargarDDlCuentas(dni);
	}

	public List<TipoCuenta> listarTiposCuenta() {
		CuentaDAO dao = new CuentaDAOImpl();
		return dao.obtenerTiposCuenta();
	}

}
