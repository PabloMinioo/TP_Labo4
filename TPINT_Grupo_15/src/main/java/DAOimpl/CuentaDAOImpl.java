package DAOimpl;


import DAO.CuentaDAO;
import entidad.Cuenta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CuentaDAOImpl implements CuentaDAO {
    
    private static final String INSERTAR = "INSERT INTO Cuentas (NumeroCuenta_Cu, ClienteDNI_Cu, FechaCreacion_Cu, TipoCuenta_Cu, CBU_Cu, SALDO_Cu) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String ACTUALIZAR = "UPDATE Cuentas SET ClienteDNI_Cu = ?, FechaCreacion_Cu = ?, TipoCuenta_Cu = ?, CBU_Cu = ?, SALDO_Cu = ? WHERE NumeroCuenta_Cu = ?";
    private static final String ELIMINAR = "DELETE FROM Cuentas WHERE NumeroCuenta_Cu = ?";
    private static final String OBTENER_POR_ID = "SELECT * FROM Cuentas WHERE NumeroCuenta_Cu = ?";
    private static final String OBTENER_TODAS = "SELECT * FROM Cuentas ORDER BY NumeroCuenta_Cu";
    private static final String OBTENER_POR_CLIENTE = "SELECT * FROM Cuentas WHERE ClienteDNI_Cu = ?";
    private static final String EXISTE_NUMERO_CUENTA = "SELECT COUNT(*) FROM Cuentas WHERE NumeroCuenta_Cu = ?";
    private static final String EXISTE_CBU = "SELECT COUNT(*) FROM Cuentas WHERE CBU_Cu = ?";
    private static final String OBTENER_POR_TIPO = "SELECT * FROM Cuentas WHERE TipoCuenta_Cu = ?";
    private static final String CONTAR_POR_CLIENTE = "SELECT COUNT(*) FROM Cuentas WHERE ClienteDNI_Cu = ?";
    
    private Connection conexion;
    
    public CuentaDAOImpl() {
        // Inicializar conexión
    }
    
    @Override
    public boolean insertar(Cuenta cuenta) {
        PreparedStatement statement;
        try {
            statement = conexion.prepareStatement(INSERTAR);
            statement.setString(1, cuenta.getNumeroCuenta());
            statement.setString(2, cuenta.getClienteDNI());
            statement.setDate(3, Date.valueOf(cuenta.getFechaCreacion()));
            statement.setInt(4, cuenta.getTipoCuenta());
            statement.setString(5, cuenta.getCbu());
            statement.setDouble(6, cuenta.getSaldo());
            
            if(statement.executeUpdate() > 0) {
                conexion.commit();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
    
    @Override
    public boolean actualizar(Cuenta cuenta) {
        PreparedStatement statement;
        try {
            statement = conexion.prepareStatement(ACTUALIZAR);
            statement.setString(1, cuenta.getClienteDNI());
            statement.setDate(2, Date.valueOf(cuenta.getFechaCreacion()));
            statement.setInt(3, cuenta.getTipoCuenta());
            statement.setString(4, cuenta.getCbu());
            statement.setDouble(5, cuenta.getSaldo());
            statement.setString(6, cuenta.getNumeroCuenta());
            
            if(statement.executeUpdate() > 0) {
                conexion.commit();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
    
    @Override
    public boolean eliminar(String numeroCuenta) {
        PreparedStatement statement;
        try {
            statement = conexion.prepareStatement(ELIMINAR);
            statement.setString(1, numeroCuenta);
            
            if(statement.executeUpdate() > 0) {
                conexion.commit();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
    
    @Override
    public Cuenta obtenerPorId(String numeroCuenta) {
        PreparedStatement statement;
        ResultSet resultSet;
        Cuenta cuenta = null;
        
        try {
            statement = conexion.prepareStatement(OBTENER_POR_ID);
            statement.setString(1, numeroCuenta);
            resultSet = statement.executeQuery();
            
            if(resultSet.next()) {
                cuenta = mapearCuenta(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cuenta;
    }
    
    @Override
    public List<Cuenta> obtenerTodas() {
        PreparedStatement statement;
        ResultSet resultSet;
        List<Cuenta> cuentas = new ArrayList<>();
        
        try {
            statement = conexion.prepareStatement(OBTENER_TODAS);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()) {
                cuentas.add(mapearCuenta(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cuentas;
    }
    
    @Override
    public List<Cuenta> obtenerPorClienteDNI(String clienteDNI) {
        PreparedStatement statement;
        ResultSet resultSet;
        List<Cuenta> cuentas = new ArrayList<>();
        
        try {
            statement = conexion.prepareStatement(OBTENER_POR_CLIENTE);
            statement.setString(1, clienteDNI);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()) {
                cuentas.add(mapearCuenta(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cuentas;
    }
    
    @Override
    public boolean existeNumeroCuenta(String numeroCuenta) {
        PreparedStatement statement;
        ResultSet resultSet;
        
        try {
            statement = conexion.prepareStatement(EXISTE_NUMERO_CUENTA);
            statement.setString(1, numeroCuenta);
            resultSet = statement.executeQuery();
            
            if(resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public boolean existeCBU(String cbu) {
        PreparedStatement statement;
        ResultSet resultSet;
        
        try {
            statement = conexion.prepareStatement(EXISTE_CBU);
            statement.setString(1, cbu);
            resultSet = statement.executeQuery();
            
            if(resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public List<Cuenta> obtenerPorTipoCuenta(int tipoCuenta) {
        PreparedStatement statement;
        ResultSet resultSet;
        List<Cuenta> cuentas = new ArrayList<>();
        
        try {
            statement = conexion.prepareStatement(OBTENER_POR_TIPO);
            statement.setInt(1, tipoCuenta);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()) {
                cuentas.add(mapearCuenta(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cuentas;
    }
    
    @Override
    public int contarCuentasPorCliente(String clienteDNI) {
        PreparedStatement statement;
        ResultSet resultSet;
        
        try {
            statement = conexion.prepareStatement(CONTAR_POR_CLIENTE);
            statement.setString(1, clienteDNI);
            resultSet = statement.executeQuery();
            
            if(resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    private Cuenta mapearCuenta(ResultSet resultSet) throws SQLException {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(resultSet.getString("NumeroCuenta_Cu"));
        cuenta.setClienteDNI(resultSet.getString("ClienteDNI_Cu"));
        cuenta.setFechaCreacion(resultSet.getDate("FechaCreacion_Cu").toLocalDate());
        cuenta.setTipoCuenta(resultSet.getInt("TipoCuenta_Cu"));
        cuenta.setCbu(resultSet.getString("CBU_Cu"));
        cuenta.setSaldo(resultSet.getDouble("SALDO_Cu"));
        return cuenta;
    }
}