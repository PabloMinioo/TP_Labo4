package negocioImpl;

import java.math.BigDecimal;
import java.util.List;

import DAO.PrestamoDAO;
import DAOimpl.PrestamoDAOimpl;
import Excepciones.SaldoInsuficienteException;
import DAO.CuotaDAO;
import DAO.CuentaDAO;
import DAOimpl.CuentaDAOImpl;
import DAOimpl.CuotaDAOImpl;
import entidad.Cliente;
import entidad.Cuota;
import entidad.Prestamo;
import negocio.PrestamoNegocio;
import negocio.CuentaNegocio;

public class PrestamoNegocioImpl implements PrestamoNegocio {

	private PrestamoDAO prestamoDAO = new PrestamoDAOimpl();
	private CuentaDAOImpl cuentaDAO = new CuentaDAOImpl();
	private CuotaDAOImpl cuotaDAO = new CuotaDAOImpl();

	//
	@Override
	public boolean SolicitarPrestamo(Prestamo prestamo) throws Exception {
		return prestamoDAO.CargarSolicitud(prestamo);
	}

	// prestamos listar los que tienene estado P
	@Override
	public List<Prestamo> listarPrestamos() throws Exception {
		return prestamoDAO.listarPrestamos();
	}

	// prestamos pendienetes de pago, con D
	@Override
	public List<Cuota> listarPagarPrestamos() throws Exception {
		return prestamoDAO.listarPagarPrestamos();
	}

	// cambi el estado del prestamo de P a A aprobado

	@Override
	public boolean cambiarEstadoP(int idPrestamo) throws Exception {
		return prestamoDAO.estadoP(idPrestamo);
	}

	// cambi el estado del prestamo de P a reprobado
	@Override
	public boolean cambiarEstadoR(int idPrestamo) throws Exception {
		return prestamoDAO.estadoR(idPrestamo);
	}

	public boolean pagarCuota(int idPrestamo, int nroCuota, int nroCuenta) throws Exception, SaldoInsuficienteException {
		BigDecimal saldo = cuentaDAO.obtenerSaldo(nroCuenta);

		// Necesitamos la lista de cuotas para saber cuánto es el monto de esta cuota
		List<Cuota> cuotas = listarPagarPrestamos();
		Cuota cuotaPagar = null;
		for (Cuota c : cuotas) {
			if (c.getIdPrestamo() == idPrestamo && c.getNroCuota() == nroCuota) {
				cuotaPagar = c;
				break;
			}
		}
		if (cuotaPagar == null) {
			return false; // no se encontró la cuota
		}

		BigDecimal monto = cuotaPagar.getMontoCuota();
		if (saldo.compareTo(monto) < 0) {
			 throw new SaldoInsuficienteException("No se puede pagar la cuota: saldo insuficiente.");
		}

		boolean debitoOK = cuentaDAO.debitarMonto(nroCuenta, monto);
		boolean cuotaPagadaOK = cuotaDAO.marcarCuotaPagada(idPrestamo, nroCuota);

		return debitoOK && cuotaPagadaOK;
	}
}
