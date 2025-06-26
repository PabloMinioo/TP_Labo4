package negocio;

import entidad.Cuenta;
import java.util.List;

public interface CuentaNegocio {
    
    // Operaciones ABML con validaciones de negocio
    boolean crearCuenta(Cuenta cuenta);
    boolean modificarCuenta(Cuenta cuenta);
    boolean eliminarCuenta(int numeroCuenta);
    Cuenta buscarCuenta(int numeroCuenta);
    List<Cuenta> listarCuentas() throws Exception;
    
    // Operaciones específicas de negocio
    List<Cuenta> listarCuentasPorCliente(String clienteDNI);
    boolean transferirSaldo(int numeroCuentaOrigen, int numeroCuentaDestino, double monto);
    boolean depositarDinero(int numeroCuenta, double monto);
    boolean retirarDinero(int numeroCuenta, double monto);
    double consultarSaldo(int numeroCuenta);
    
    // Validaciones
    boolean validarDatosCuenta(Cuenta cuenta);
    int generarNumeroCuenta();
    String generarCBU();
    boolean puedeEliminarCuenta(int numeroCuenta);
}