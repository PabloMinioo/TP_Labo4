package negocioImpl;

import negocio.CuentaNegocio;
import DAO.CuentaDAO;
import 	DAOimpl.CuentaDAOImpl;
import entidad.Cuenta;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class CuentaNegocioImpl implements CuentaNegocio {
    
    private CuentaDAO cuentaDAO;
    
    public CuentaNegocioImpl() {
        this.cuentaDAO = new CuentaDAOImpl();
    }
    
    @Override
    public boolean crearCuenta(Cuenta cuenta) {
        try {
            // Validaciones de negocio
            if (!validarDatosCuenta(cuenta)) {
                return false;
            }
            
            // Generar número de cuenta único si no está asignado
            if (cuenta.getNumeroCuenta() == null || cuenta.getNumeroCuenta().isEmpty()) {
                cuenta.setNumeroCuenta(generarNumeroCuenta());
            }
            
            // Generar CBU único si no está asignado
            if (cuenta.getCbu() == null || cuenta.getCbu().isEmpty()) {
                cuenta.setCbu(generarCBU());
            }
           
            // Establecer fecha de creación
            if (cuenta.getFechaCreacion() == null) {
                cuenta.setFechaCreacion(LocalDate.now());
            }
            
            // Verificar que no exista el número de cuenta
            if (cuentaDAO.existeNumeroCuenta(cuenta.getNumeroCuenta())) {
                return false;
            }
            
            // Verificar que no exista el CBU
            if (cuentaDAO.existeCBU(cuenta.getCbu())) {
                return false;
            }
            
            // Validar que el cliente no tenga más de 3 cuentas
            if (cuentaDAO.contarCuentasPorCliente(cuenta.getClienteDNI()) >= 3) {
                return false;
            }
            
            return cuentaDAO.insertar(cuenta);
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean modificarCuenta(Cuenta cuenta) {
        try {
            // Validaciones de negocio
            if (!validarDatosCuenta(cuenta)) {
                return false;
            }
            
            // Verificar que la cuenta existe
            Cuenta cuentaExistente = cuentaDAO.obtenerPorId(cuenta.getNumeroCuenta());
            if (cuentaExistente == null) {
                return false;
            }
            
            // Verificar que el CBU no esté en uso por otra cuenta
            if (!cuenta.getCbu().equals(cuentaExistente.getCbu()) && 
                cuentaDAO.existeCBU(cuenta.getCbu())) {
                return false;
            }
            
            return cuentaDAO.actualizar(cuenta);
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean eliminarCuenta(String numeroCuenta) {
        try {
            if (!puedeEliminarCuenta(numeroCuenta)) {
                return false;
            }
            
            return cuentaDAO.eliminar(numeroCuenta);
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public Cuenta buscarCuenta(String numeroCuenta) {
        try {
            return cuentaDAO.obtenerPorId(numeroCuenta);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public List<Cuenta> listarCuentas() {
        try {
            return cuentaDAO.obtenerTodas();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public List<Cuenta> listarCuentasPorCliente(String clienteDNI) {
        try {
            if (clienteDNI == null || clienteDNI.trim().isEmpty()) {
                return null;
            }
            return cuentaDAO.obtenerPorClienteDNI(clienteDNI);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public boolean transferirSaldo(String numeroCuentaOrigen, String numeroCuentaDestino, double monto) {
        try {
            // Validar monto
            if (monto <= 0) {
                return false;
            }
            
            // Obtener cuentas
            Cuenta cuentaOrigen = cuentaDAO.obtenerPorId(numeroCuentaOrigen);
            Cuenta cuentaDestino = cuentaDAO.obtenerPorId(numeroCuentaDestino);
            
            if (cuentaOrigen == null || cuentaDestino == null) {
                return false;
            }
            
            // Verificar saldo suficiente
            if (cuentaOrigen.getSaldo() < monto) {
                return false;
            }
            
            // Realizar transferencia
            cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - monto);
            cuentaDestino.setSaldo(cuentaDestino.getSaldo() + monto);
            
            // Actualizar ambas cuentas
            return cuentaDAO.actualizar(cuentaOrigen) && cuentaDAO.actualizar(cuentaDestino);
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean depositarDinero(String numeroCuenta, double monto) {
        try {
            if (monto <= 0) {
                return false;
            }
            
            Cuenta cuenta = cuentaDAO.obtenerPorId(numeroCuenta);
            if (cuenta == null) {
                return false;
            }
            
            cuenta.setSaldo(cuenta.getSaldo() + monto);
            return cuentaDAO.actualizar(cuenta);
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean retirarDinero(String numeroCuenta, double monto) {
        try {
            if (monto <= 0) {
                return false;
            }
            
            Cuenta cuenta = cuentaDAO.obtenerPorId(numeroCuenta);
            if (cuenta == null) {
                return false;
            }
            
            // Verificar saldo suficiente
            if (cuenta.getSaldo() < monto) {
                return false;
            }
            
            cuenta.setSaldo(cuenta.getSaldo() - monto);
            return cuentaDAO.actualizar(cuenta);
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public double consultarSaldo(String numeroCuenta) {
        try {
            Cuenta cuenta = cuentaDAO.obtenerPorId(numeroCuenta);
            return cuenta != null ? cuenta.getSaldo() : -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    @Override
    public boolean validarDatosCuenta(Cuenta cuenta) {
        if (cuenta == null) {
            return false;
        }
        
        // Validar DNI del cliente
        if (cuenta.getClienteDNI() == null || cuenta.getClienteDNI().trim().length() != 8) {
            return false;
        }
        
        // Validar tipo de cuenta
        if (cuenta.getTipoCuenta() <= 0) {
            return false;
        }
        
        // Validar CBU
        if (cuenta.getCbu() != null && cuenta.getCbu().length() != 22) {
            return false;
        }
        
        // Validar saldo (no puede ser negativo)
        if (cuenta.getSaldo() < 0) {
            return false;
        }
        
        return true;
    }
    
    @Override
    public String generarNumeroCuenta() {
        String numeroCuenta;
        Random random = new Random();
        
        do {
            // Generar número de cuenta de 12 dígitos
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 12; i++) {
                sb.append(random.nextInt(10));
            }
            numeroCuenta = sb.toString();
        } while (cuentaDAO.existeNumeroCuenta(numeroCuenta));
        
        return numeroCuenta;
    }
    
    @Override
    public String generarCBU() {
        String cbu;
        Random random = new Random();
        
        do {
            // Generar CBU de 22 dígitos
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 22; i++) {
                sb.append(random.nextInt(10));
            }
            cbu = sb.toString();
        } while (cuentaDAO.existeCBU(cbu));
        
        return cbu;
    }
    
    @Override
    public boolean puedeEliminarCuenta(String numeroCuenta) {
        try {
            Cuenta cuenta = cuentaDAO.obtenerPorId(numeroCuenta);
            if (cuenta == null) {
                return false;
            }
            
            // Solo se puede eliminar si el saldo es 0
            return cuenta.getSaldo() == 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}