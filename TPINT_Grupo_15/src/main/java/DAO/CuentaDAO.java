package DAO;

import entidad.Cuenta;
import java.util.List;

public interface CuentaDAO {
    
    // Operaciones basicas
    boolean insertar(Cuenta cuenta);
    boolean actualizar(Cuenta cuenta);
    boolean eliminar(String numeroCuenta);
    Cuenta obtenerPorId(String numeroCuenta);
    List<Cuenta> obtenerTodas();
    
    //  Operaciones de consultas 
    List<Cuenta> obtenerPorClienteDNI(String clienteDNI);
    boolean existeNumeroCuenta(String numeroCuenta);
    boolean existeCBU(String cbu);
    List<Cuenta> obtenerPorTipoCuenta(int tipoCuenta);
    int contarCuentasPorCliente(String clienteDNI);
}