package negocio;

import entidad.Cuenta;
import java.util.List;

public interface CuentaNegocio {
    
    // Operaciones ABML con validaciones de negocio
    boolean crearCuenta(Cuenta cuenta);
    boolean modificarCuenta(Cuenta cuenta);
    boolean eliminarCuenta(String numeroCuenta);
    Cuenta buscarCuenta(String numeroCuenta);
    List<Cuenta> listarCuentas() throws Exception;
    
    // Operaciones específicas de negocio
    List<Cuenta> listarCuentasPorCliente(String clienteDNI);
    boolean transferirSaldo(String numeroCuentaOrigen, String numeroCuentaDestino, double monto);
    boolean depositarDinero(String numeroCuenta, double monto);
    boolean retirarDinero(String numeroCuenta, double monto);
    double consultarSaldo(String numeroCuenta);
    
    // Validaciones
    boolean validarDatosCuenta(Cuenta cuenta);
    String generarNumeroCuenta();
    String generarCBU();
    boolean puedeEliminarCuenta(String numeroCuenta);
}