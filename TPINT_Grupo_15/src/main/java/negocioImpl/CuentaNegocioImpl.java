package negocioImpl;

import negocio.CuentaNegocio;
import DAO.CuentaDAO;
import 	DAOimpl.CuentaDAOImpl;
import entidad.Cliente;
import entidad.Cuenta;

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
	    	  //si si existe eguimos generando un cbu ramdomm ylo insertamos luego
	    	   cuenta.setCbu(generarCBU());
	            return cuentaDAO.insertar(cuenta);  
	     }
	    
	 
	      ///crear cbu cualquiera
	    ///
	    private String generarCBU() {
	        StringBuilder sb = new StringBuilder();
	        ThreadLocalRandom rnd = ThreadLocalRandom.current();
	        for (int i = 0; i < 22; i++) sb.append(rnd.nextInt(10));
	        return sb.toString();
	    }
	    
	    //c/celiii
	    public List<Cuenta> listarCuentas() throws Exception{
	    	return cuentaDAO.listarCuentas();
	    }
	    
	    
	    @Override
	    public boolean modificarCuenta(Cuenta cuenta) throws Exception {
	    	   return cuentaDAO.modificarCuenta(cuenta);
	    }

	  
    
   }    


//    @Override
//    public boolean actualizarCuenta(Cuenta cuenta) {
//        return cuentaDAO.actualizarCuenta(cuenta);
//    }








//    @Override
//    public boolean crearCuenta(Cuenta cuenta) {
//        try {
//            // Validaciones de negocio
//            if (!validarDatosCuenta(cuenta)) {
//                return false;
//            }
//            
//            // Generar número de cuenta único si no está asignado
//            if (cuenta.getNumeroCuenta() == 0) {
//                cuenta.setNumeroCuenta(generarNumeroCuenta());
//            }
//            
//            // Generar CBU único si no está asignado
//            if (cuenta.getCbu() == null || cuenta.getCbu().isEmpty()) {
//                cuenta.setCbu(generarCBU());
//            }
//           
//            // Establecer fecha de creación
//            if (cuenta.getFechaCreacion() == null) {
//                cuenta.setFechaCreacion(LocalDate.now());
//            }
//            
//            // Verificar que no exista el número de cuenta
//            if (cuentaDAO.existeNumeroCuenta(cuenta.getNumeroCuenta())) {
//                return false;
//            }
//            
//            // Verificar que no exista el CBU
//            if (cuentaDAO.existeCBU(cuenta.getCbu())) {
//                return false;
//            }
//            
//            // Validar que el cliente no tenga más de 3 cuentas
//            if (cuentaDAO.contarCuentasPorCliente(cuenta.getClienteDNI()) >= 3) {
//                return false;
//            }
//            
//            return cuentaDAO.insertar(cuenta);
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//    
//    @Override
//    public boolean modificarCuenta(Cuenta cuenta) {
//        try {
//            // Validaciones de negocio
//            if (!validarDatosCuenta(cuenta)) {
//                return false;
//            }
//            
//            // Verificar que la cuenta existe
//            Cuenta cuentaExistente = cuentaDAO.obtenerPorId(cuenta.getNumeroCuenta());
//            if (cuentaExistente == null) {
//                return false;
//            }
//            
//            // Verificar que el CBU no esté en uso por otra cuenta
//            if (!cuenta.getCbu().equals(cuentaExistente.getCbu()) && 
//                cuentaDAO.existeCBU(cuenta.getCbu())) {
//                return false;
//            }
//            
//            return cuentaDAO.actualizar(cuenta);
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//    
//    @Override
//    public boolean eliminarCuenta(int numeroCuenta) throws Exception {
//    	return cuentaDAO.eliminar(numeroCuenta);
//    }
//    
//    @Override
//    public Cuenta buscarCuenta(int numeroCuenta) {
//        try {
//            return cuentaDAO.obtenerPorId(numeroCuenta);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
    
   
    
//    @Override
//    public List<Cuenta> listarCuentasPorCliente(String clienteDNI) {
//        try {
//            if (clienteDNI == null || clienteDNI.trim().isEmpty()) {
//                return null;
//            }
//            return cuentaDAO.obtenerPorClienteDNI(clienteDNI);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//    
//    @Override
//    public boolean transferirSaldo(int numeroCuentaOrigen, int numeroCuentaDestino, double monto) {
//        try {
//            // Validar monto
//            if (monto <= 0) {
//                return false;
//            }
//            
//            // Obtener cuentas
//            Cuenta cuentaOrigen = cuentaDAO.obtenerPorId(numeroCuentaOrigen);
//            Cuenta cuentaDestino = cuentaDAO.obtenerPorId(numeroCuentaDestino);
//            
//            if (cuentaOrigen == null || cuentaDestino == null) {
//                return false;
//            }
//            
//            // Verificar saldo suficiente
//            if (cuentaOrigen.getSaldo() < monto) {
//                return false;
//            }
//            
//            // Realizar transferencia
//            cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - monto);
//            cuentaDestino.setSaldo(cuentaDestino.getSaldo() + monto);
//            
//            // Actualizar ambas cuentas
//            return cuentaDAO.actualizar(cuentaOrigen) && cuentaDAO.actualizar(cuentaDestino);
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//    
//    @Override
//    public boolean depositarDinero(int numeroCuenta, double monto) {
//        try {
//            if (monto <= 0) {
//                return false;
//            }
//            
//            Cuenta cuenta = cuentaDAO.obtenerPorId(numeroCuenta);
//            if (cuenta == null) {
//                return false;
//            }
//            
//            cuenta.setSaldo(cuenta.getSaldo() + monto);
//            return cuentaDAO.actualizar(cuenta);
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//    
//    @Override
//    public boolean retirarDinero(int numeroCuenta, double monto) {
//        try {
//            if (monto <= 0) {
//                return false;
//            }
//            
//            Cuenta cuenta = cuentaDAO.obtenerPorId(numeroCuenta);
//            if (cuenta == null) {
//                return false;
//            }
//            
//            // Verificar saldo suficiente
//            if (cuenta.getSaldo() < monto) {
//                return false;
//            }
//            
//            cuenta.setSaldo(cuenta.getSaldo() - monto);
//            return cuentaDAO.actualizar(cuenta);
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//    
//    @Override
//    public double consultarSaldo(int numeroCuenta) {
//        try {
//            Cuenta cuenta = cuentaDAO.obtenerPorId(numeroCuenta);
//            return cuenta != null ? cuenta.getSaldo() : -1;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return -1;
//        }
//    }
//    
//    @Override
//    public boolean validarDatosCuenta(Cuenta cuenta) {
//        if (cuenta == null) {
//            return false;
//        }
//        
//        // Validar DNI del cliente
//        if (cuenta.getClienteDNI() == null || cuenta.getClienteDNI().trim().length() != 8) {
//            return false;
//        }
//        
//        // Validar tipo de cuenta
//        if (cuenta.getTipoCuenta() <= 0) {
//            return false;
//        }
//        
//        // Validar CBU
//        if (cuenta.getCbu() != null && cuenta.getCbu().length() != 22) {
//            return false;
//        }
//        
//        // Validar saldo (no puede ser negativo)
//        if (cuenta.getSaldo() < 0) {
//            return false;
//        }
//        
//        return true;
//    }
//    
//    @Override
//    public int generarNumeroCuenta() {
//        int numeroCuenta;
//        Random random = new Random();
//
//        do {
//            // Generar número de cuenta de 6 dígitos (podés cambiar la cantidad)
//            numeroCuenta = 100000 + random.nextInt(900000);
//        } while (cuentaDAO.existeNumeroCuenta(numeroCuenta));
//
//        return numeroCuenta;
//    }
//
//    @Override
//    public String generarCBU() {
//        String cbu;
//        Random random = new Random();
//
//        do {
//            StringBuilder sb = new StringBuilder();
//            for (int i = 0; i < 22; i++) {
//                sb.append(random.nextInt(10));
//            }
//            cbu = sb.toString();
//        } while (cuentaDAO.existeCBU(cbu));
//
//        return cbu;
//    }
//    
//    @Override
//    public boolean puedeEliminarCuenta(int numeroCuenta) {
//        try {
//            Cuenta cuenta = cuentaDAO.obtenerPorId(numeroCuenta);
//            if (cuenta == null) {
//                return false;
//            }
//            
//            // Solo se puede eliminar si el saldo es mayor 0
//            return cuenta.getSaldo() > 0;
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
