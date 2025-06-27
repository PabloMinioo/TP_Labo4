package negocio;

import entidad.Cuenta;


import java.sql.SQLException;
import java.util.List;

public interface CuentaNegocio {
    
    // Operaciones ABML con validaciones de negocio
  boolean crearCuenta(Cuenta cuenta) throws SQLException;///usio judi
  boolean modificarCuenta(Cuenta cuenta) throws Exception;
//    boolean eliminarCuenta(int numeroCuenta) throws Exception;
//    Cuenta buscarCuenta(int numeroCuenta);
    List<Cuenta> listarCuentas() throws Exception;///uso celi
	//boolean actualizarCuenta(Cuenta cuenta);
    
    // Operaciones específicas de negocio
//    List<Cuenta> listarCuentasPorCliente(String clienteDNI);
//    boolean transferirSaldo(int numeroCuentaOrigen, int numeroCuentaDestino, double monto);
//    boolean depositarDinero(int numeroCuenta, double monto);
//    boolean retirarDinero(int numeroCuenta, double monto);
//    double consultarSaldo(int numeroCuenta);
    
    // Validaciones
//    boolean validarDatosCuenta(Cuenta cuenta);
//    int generarNumeroCuenta();
//    String generarCBU();
//    boolean puedeEliminarCuenta(int numeroCuenta);
}