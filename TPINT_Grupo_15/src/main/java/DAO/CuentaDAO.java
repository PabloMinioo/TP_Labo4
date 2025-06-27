package DAO;

import entidad.Cuenta;
import java.util.List;

public interface CuentaDAO {
    
    // Operaciones basicas
    boolean insertar(Cuenta cuenta);
    boolean actualizar(Cuenta cuenta);
    boolean eliminar(int numeroCuenta) throws Exception;
    Cuenta obtenerPorId(int numeroCuenta);
    List<Cuenta> listarCuentas() throws Exception;
    
    //  Operaciones de consultas 
    List<Cuenta> obtenerPorClienteDNI(String clienteDNI);
    boolean existeNumeroCuenta(int numeroCuenta);
    boolean existeCBU(String cbu);
    List<Cuenta> obtenerPorTipoCuenta(int tipoCuenta);
    int contarCuentasPorCliente(String clienteDNI);
}