package DAOimpl;


import DAO.Conexion;
import DAO.CuentaDAO;
import entidad.Cliente;
import entidad.Cuenta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CuentaDAOImpl implements CuentaDAO {
    
//    private static final String INSERTAR = "INSERT INTO Cuentas (NumeroCuenta_Cu, ClienteDNI_Cu, FechaCreacion_Cu, TipoCuenta_Cu, CBU_Cu, SALDO_Cu) VALUES (?, ?, ?, ?, ?, ?)";
//    private static final String ACTUALIZAR = "UPDATE Cuentas SET ClienteDNI_Cu = ?, FechaCreacion_Cu = ?, TipoCuenta_Cu = ?, CBU_Cu = ?, SALDO_Cu = ? WHERE NumeroCuenta_Cu = ?";
//    private static final String OBTENER_POR_ID = "SELECT * FROM Cuentas WHERE NumeroCuenta_Cu = ?";
//    private static final String OBTENER_TODAS = "SELECT * FROM Cuentas ORDER BY NumeroCuenta_Cu";
//    private static final String OBTENER_POR_CLIENTE = "SELECT * FROM Cuentas WHERE ClienteDNI_Cu = ?";
//    private static final String EXISTE_NUMERO_CUENTA = "SELECT COUNT(*) FROM Cuentas WHERE NumeroCuenta_Cu = ?";
//    private static final String EXISTE_CBU = "SELECT COUNT(*) FROM Cuentas WHERE CBU_Cu = ?";
//    private static final String OBTENER_POR_TIPO = "SELECT * FROM Cuentas WHERE TipoCuenta_Cu = ?";
//    private static final String CONTAR_POR_CLIENTE = "SELECT COUNT(*) FROM Cuentas WHERE ClienteDNI_Cu = ?";
    
   // private Connection conexion;
    
   private PreparedStatement ps;


	// public CuentaDAOImpl() {
        // Inicializar conexión
 //   }
	 private Connection getConnection() throws SQLException {//helpers para abriri conexion
	        return Conexion.getConexion();   
	 }
	 
	 @Override
	    public boolean existeDNI(String dni) throws SQLException {
	        String consulta = "select  Nombre_Cl from clientes where DNI_Cl=?";
	        try (Connection con = Conexion.getConexion();
	                PreparedStatement ps = con.prepareStatement(consulta)) {

	        	
	            ps.setString(1, dni);
	            try (ResultSet rs = ps.executeQuery()) {
	                return rs.next();        // true si encontró almenos 1 fila
	            }
	        }
	    }
	 
	 
	 
	 
//		    public boolean actualizarCuenta(Cuenta cuenta) {
//		        try (Connection conn = Conexion.getConexion()) {
//		            String sql = "UPDATE Cuentas SET ClienteDNI_Cu=?, TipoCuenta_Cu=?, CBU_Cu=?, SALDO_Cu=?, Estado_Cu=? WHERE NumeroCuenta_Cu=?";
//		            PreparedStatement stmt = conn.prepareStatement(sql);
//		            stmt.setString(1, cuenta.getClienteDNI());
//		            stmt.setInt(2, cuenta.getTipoCuenta());
//		            stmt.setString(3, cuenta.getCbu());
//		            stmt.setDouble(4, cuenta.getSaldo());
//		            stmt.setBoolean(5, cuenta.getEstado());
//		            stmt.setInt(6, cuenta.getNumeroCuenta());
//		            return stmt.executeUpdate() > 0;
//		        } catch (SQLException e) {
//		            e.printStackTrace();
//		            return false;
//		        }
//		    }
//		


	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 @Override
  public int contarCuentasPorCliente(String clienteDNI) { //cuentas que tiene cada cliente hasta 3 puede terner y ademas este con estado 1
		 String consulta ="SELECT COUNT(*) FROM Cuentas WHERE ClienteDNI_Cu = ? and Estado_Cu=1";
		  
		 try (Connection con = Conexion.getConexion();
		         PreparedStatement ps = con.prepareStatement(consulta)) {
		        
		        ps.setString(1, clienteDNI); // Ponemos el parámetro
		        
		        try (ResultSet resultSet = ps.executeQuery()) {
		            if (resultSet.next()) {
		                return resultSet.getInt(1);  // Devuelve la cantidad (número de filas)
		            }
		        }
		        
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    
		    return 0;
   }
	 
	
	 @Override
	    public boolean insertar(Cuenta cuenta) throws SQLException {
	        String consulta ="INSERT INTO Cuentas ( ClienteDNI_Cu, FechaCreacion_Cu, TipoCuenta_Cu, CBU_Cu, SALDO_Cu) VALUES (?, ?, ?, ?, ?)";
	        		
	        try (Connection c = getConnection();
	             PreparedStatement ps = c.prepareStatement(consulta)) {

	            ps.setString(1, cuenta.getClienteDNI());
	            ps.setDate  (2, java.sql.Date.valueOf(cuenta.getFechaCreacion()));
	            ps.setInt   (3, cuenta.getTipoCuenta());
	            ps.setString(4, cuenta.getCbu());          // ya generado
	            ps.setDouble(5, cuenta.getSaldo());

	            return ps.executeUpdate() == 1;
	        }
	    }
	
	
	@Override
	//----------------------celii
	public List<Cuenta> listarCuentas() throws Exception {
		List<Cuenta> listaC = new ArrayList<>();
	    String sql = "SELECT c.NumeroCuenta_Cu, c.ClienteDNI_Cu, c.FechaCreacion_Cu, c.TipoCuenta_Cu,c.CBU_Cu, c.SALDO_Cu, t.NombreTipo_Tc AS TipoCuenta FROM Cuentas c " +
	                 "INNER JOIN TiposCuentas t ON c.TipoCuenta_Cu = t.IdTipoCuenta_Tc " +
	                 "WHERE c.Estado_Cu = TRUE";
	    try (Connection conn = Conexion.getConexion();
	         PreparedStatement ps = conn.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {
	        while (rs.next()) {
	        	Cuenta cuenta = new Cuenta();
	            cuenta.setNumeroCuenta(rs.getInt("NumeroCuenta_Cu"));
	            cuenta.setClienteDNI(rs.getString("ClienteDNI_Cu"));
	            cuenta.setFechaCreacion(rs.getDate("FechaCreacion_Cu").toLocalDate());
	           // cuenta.setTipoCuenta(rs.getInt("TipoCuenta_Cu"));No sirve porque guarda el numero
	               cuenta.setNombreTipoCuenta(rs.getString("TipoCuenta")); 
	            cuenta.setCbu(rs.getString("CBU_Cu"));
	            cuenta.setSaldo(rs.getDouble("SALDO_Cu"));
	        
	            
	            listaC.add(cuenta);
	        }
	    }
	    return listaC;
	    
	}
	
	
	 @Override
	    public boolean modificarCuenta(Cuenta cuenta) throws SQLException {
	        String consulta ="UPDATE Cuentas SET FechaCreacion_Cu=?, TipoCuenta_Cu=? WHERE NumeroCuenta_Cu=?";
	        try (Connection conn = Conexion.getConexion();
	             PreparedStatement ps = conn.prepareStatement(consulta)) {
	        	      	
	    
	        	  ps.setDate(1,  Date.valueOf(cuenta.getFechaCreacion())); // fecha nueva
	              ps.setInt (2,  cuenta.getTipoCuenta());                 // id tipo nuevo
	              ps.setInt (3,  cuenta.getNumeroCuenta());               // clave primaria

	              int filas = ps.executeUpdate();
	              System.out.println("Filas modificadas: " + filas);
	              return filas > 0;

	  
	 }
  } 
}	 ///agregge para cerar la clase   



//    @Override
//    public boolean insertar(Cuenta cuenta) {
//        PreparedStatement statement;
//        try {
//            statement = conexion.prepareStatement(INSERTAR);
//            statement.setInt(1, cuenta.getNumeroCuenta());
//            statement.setString(2, cuenta.getClienteDNI());
//            statement.setDate(3, Date.valueOf(cuenta.getFechaCreacion()));
//            statement.setInt(4, cuenta.getTipoCuenta());
//            statement.setString(5, cuenta.getCbu());
//            statement.setDouble(6, cuenta.getSaldo());
//            
//            if(statement.executeUpdate() > 0) {
//                conexion.commit();
//                return true;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//        return false;
//    }
//    
//    @Override
//    public boolean actualizar(Cuenta cuenta) {
//        PreparedStatement statement;
//        try {
//            statement = conexion.prepareStatement(ACTUALIZAR);
//            statement.setString(1, cuenta.getClienteDNI());
//            statement.setDate(2, Date.valueOf(cuenta.getFechaCreacion()));
//            statement.setInt(3, cuenta.getTipoCuenta());
//            statement.setString(4, cuenta.getCbu());
//            statement.setDouble(5, cuenta.getSaldo());
//            statement.setInt(6, cuenta.getNumeroCuenta());
//            
//            if(statement.executeUpdate() > 0) {
//                conexion.commit();
//                return true;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//        return false;
//    }
//    
//    @Override
//    public boolean eliminar(int numeroCuenta) throws Exception {
//        String sql = "UPDATE Cuentas SET Estado_Cu = FALSE WHERE NumeroCuenta_Cu = ?";
//        try (Connection conn = Conexion.getConexion();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setInt(1, numeroCuenta);
//            return ps.executeUpdate() > 0;
//        }
//    }
//    
//    @Override
//    public Cuenta obtenerPorId(int numeroCuenta) {
//        PreparedStatement statement;
//        ResultSet resultSet;
//        Cuenta cuenta = null;
//        
//        try {
//            statement = conexion.prepareStatement(OBTENER_POR_ID);
//            statement.setInt(1, numeroCuenta);
//            resultSet = statement.executeQuery();
//            
//            if(resultSet.next()) {
//                cuenta = mapearCuenta(resultSet);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return cuenta;
//    }
    
   
    
//    @Override
//    public List<Cuenta> obtenerPorClienteDNI(String clienteDNI) {
//        PreparedStatement statement;
//        ResultSet resultSet;
//        List<Cuenta> cuentas = new ArrayList<>();
//        
//        try {
//            statement = conexion.prepareStatement(OBTENER_POR_CLIENTE);
//            statement.setString(1, clienteDNI);
//            resultSet = statement.executeQuery();
//            
//            while(resultSet.next()) {
//                cuentas.add(mapearCuenta(resultSet));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return cuentas;
//    }
//    
//    @Override
//    public boolean existeNumeroCuenta(int numeroCuenta) {
//        PreparedStatement statement;
//        ResultSet resultSet;
//        
//        try {
//            statement = conexion.prepareStatement(EXISTE_NUMERO_CUENTA);
//            statement.setInt(1, numeroCuenta);
//            resultSet = statement.executeQuery();
//            
//            if(resultSet.next()) {
//                return resultSet.getInt(1) > 0;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//    
//    @Override
//    public boolean existeCBU(String cbu) {
//        PreparedStatement statement;
//        ResultSet resultSet;
//        
//        try {
//            statement = conexion.prepareStatement(EXISTE_CBU);
//            statement.setString(1, cbu);
//            resultSet = statement.executeQuery();
//            
//            if(resultSet.next()) {
//                return resultSet.getInt(1) > 0;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//    
//    @Override
//    public List<Cuenta> obtenerPorTipoCuenta(int tipoCuenta) {
//        PreparedStatement statement;
//        ResultSet resultSet;
//        List<Cuenta> cuentas = new ArrayList<>();
//        
//        try {
//            statement = conexion.prepareStatement(OBTENER_POR_TIPO);
//            statement.setInt(1, tipoCuenta);
//            resultSet = statement.executeQuery();
//            
//            while(resultSet.next()) {
//                cuentas.add(mapearCuenta(resultSet));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return cuentas;
//    }
//    
//    @Override
//    public int contarCuentasPorCliente(String clienteDNI) {
//        PreparedStatement statement;
//        ResultSet resultSet;
//        
//        try {
//            statement = conexion.prepareStatement(CONTAR_POR_CLIENTE);
//            statement.setString(1, clienteDNI);
//            resultSet = statement.executeQuery();
//            
//            if(resultSet.next()) {
//                return resultSet.getInt(1);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
//    
//    private Cuenta mapearCuenta(ResultSet resultSet) throws SQLException {
//        Cuenta cuenta = new Cuenta();
//        cuenta.setNumeroCuenta(resultSet.getInt("NumeroCuenta_Cu"));
//        cuenta.setClienteDNI(resultSet.getString("ClienteDNI_Cu"));
//        cuenta.setFechaCreacion(resultSet.getDate("FechaCreacion_Cu").toLocalDate());
//        cuenta.setTipoCuenta(resultSet.getInt("TipoCuenta_Cu"));
//        cuenta.setCbu(resultSet.getString("CBU_Cu"));
//        cuenta.setSaldo(resultSet.getDouble("SALDO_Cu"));
//        return cuenta;
//    }
