package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.CuentaDAO;
import DAOimpl.CuentaDAOImpl;
import entidad.Cuenta;
import negocio.CuentaNegocio;
import negocioImpl.CuentaNegocioImpl;

@WebServlet("/CuentaServlet")
public class CuentaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    
    private CuentaDAO cuentaDAOImpl = new CuentaDAOImpl();
    private CuentaNegocio cuentaNegocio = new CuentaNegocioImpl();
    
    public CuentaServlet() {
        super();
        this.cuentaNegocio = new CuentaNegocioImpl();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
    	
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "listar";
        }
        
        switch (action) {
            case "listar":
                listarCuentas(request, response);
                break;
            case "nuevo":
                mostrarFormularioNuevo(request, response);
                break;
            case "editar":
                mostrarFormularioEditar(request, response);
                break;
            case "eliminar":
                eliminarCuenta(request, response);
                break;
            case "ver":
                verCuenta(request, response);
                break;
            case "buscarPorCliente":
                buscarPorCliente(request, response);
                break;
            default:
                listarCuentas(request, response);
                break;
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        switch (action) {
            case "insertar":
                insertarCuenta(request, response);
                break;
            case "actualizar":
                actualizarCuenta(request, response);
                break;
            case "depositar":
                depositarDinero(request, response);
                break;
            case "retirar":
                retirarDinero(request, response);
                break;
            case "transferir":
                transferirDinero(request, response);
                break;
            default:
                listarCuentas(request, response);
                break;
        }
    }
    
   private void listarCuentas(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
    	try
        {
        	List<Cuenta> listaCuentas = cuentaNegocio.listarCuentas();
        	
            request.setAttribute("listaCuentas", listaCuentas);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/vistas/ListarCuentas.jsp");
            dispatcher.forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/cuentas/nuevo.jsp");
        dispatcher.forward(request, response);
    }
    
    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String numeroCuenta = request.getParameter("numeroCuenta");
        Cuenta cuenta = cuentaNegocio.buscarCuenta(numeroCuenta);
        
        request.setAttribute("cuenta", cuenta);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/cuentas/editar.jsp");
        dispatcher.forward(request, response);
    }
    
    private void insertarCuenta(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String clienteDNI = request.getParameter("clienteDNI");
            int tipoCuenta = Integer.parseInt(request.getParameter("tipoCuenta"));
            double saldoInicial = Double.parseDouble(request.getParameter("saldoInicial"));
            
            Cuenta cuenta = new Cuenta();
            cuenta.setClienteDNI(clienteDNI);
            cuenta.setTipoCuenta(tipoCuenta);
            cuenta.setSaldo(saldoInicial);
            cuenta.setFechaCreacion(LocalDate.now());
            
            boolean exito = cuentaNegocio.crearCuenta(cuenta);
            
            if (exito) {
                request.setAttribute("mensaje", "Cuenta creada exitosamente");
                request.setAttribute("tipoMensaje", "success");
            } else {
                request.setAttribute("mensaje", "Error al crear la cuenta");
                request.setAttribute("tipoMensaje", "error");
            }
            
        } catch (Exception e) {
            request.setAttribute("mensaje", "Error: " + e.getMessage());
            request.setAttribute("tipoMensaje", "error");
        }
        
        listarCuentas(request, response);
    }
    
    private void actualizarCuenta(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String numeroCuenta = request.getParameter("numeroCuenta");
            String clienteDNI = request.getParameter("clienteDNI");
            int tipoCuenta = Integer.parseInt(request.getParameter("tipoCuenta"));
            String cbu = request.getParameter("cbu");
            double saldo = Double.parseDouble(request.getParameter("saldo"));
            
            Cuenta cuenta = new Cuenta();
            cuenta.setNumeroCuenta(numeroCuenta);
            cuenta.setClienteDNI(clienteDNI);
            cuenta.setTipoCuenta(tipoCuenta);
            cuenta.setCbu(cbu);
            cuenta.setSaldo(saldo);
            
            boolean exito = cuentaNegocio.modificarCuenta(cuenta);
            
            if (exito) {
                request.setAttribute("mensaje", "Cuenta actualizada exitosamente");
                request.setAttribute("tipoMensaje", "success");
            } else {
                request.setAttribute("mensaje", "Error al actualizar la cuenta");
                request.setAttribute("tipoMensaje", "error");
            }
            
        } catch (Exception e) {
            request.setAttribute("mensaje", "Error: " + e.getMessage());
            request.setAttribute("tipoMensaje", "error");
        }
        
        listarCuentas(request, response);
    }
    
    private void eliminarCuenta(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String numeroCuenta = request.getParameter("numeroCuenta");
            boolean exito = cuentaNegocio.eliminarCuenta(numeroCuenta);
            
            if (exito) {
                request.setAttribute("mensaje", "Cuenta eliminada exitosamente");
                request.setAttribute("tipoMensaje", "success");
            } else {
                request.setAttribute("mensaje", "No se puede eliminar la cuenta. Verifique que el saldo sea $0");
                request.setAttribute("tipoMensaje", "error");
            }
            
        } catch (Exception e) {
            request.setAttribute("mensaje", "Error: " + e.getMessage());
            request.setAttribute("tipoMensaje", "error");
        }
        
        listarCuentas(request, response);
    }
    
    private void verCuenta(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String numeroCuenta = request.getParameter("numeroCuenta");
        Cuenta cuenta = cuentaNegocio.buscarCuenta(numeroCuenta);
        
        request.setAttribute("cuenta", cuenta);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/cuentas/ver.jsp");
        dispatcher.forward(request, response);
    }
    
    private void buscarPorCliente(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String clienteDNI = request.getParameter("clienteDNI");
        List<Cuenta> listaCuentas = cuentaNegocio.listarCuentasPorCliente(clienteDNI);
        
        request.setAttribute("listaCuentas", listaCuentas);
        request.setAttribute("clienteDNI", clienteDNI);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/vistas/ListarCuentas.jsp");
        dispatcher.forward(request, response);
    }
    
    private void depositarDinero(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String numeroCuenta = request.getParameter("numeroCuenta");
            double monto = Double.parseDouble(request.getParameter("monto"));
            
            boolean exito = cuentaNegocio.depositarDinero(numeroCuenta, monto);
            
            if (exito) {
                request.setAttribute("mensaje", "Depósito realizado exitosamente");
                request.setAttribute("tipoMensaje", "success");
            } else {
                request.setAttribute("mensaje", "Error al realizar el depósito");
                request.setAttribute("tipoMensaje", "error");
            }
            
        } catch (Exception e) {
            request.setAttribute("mensaje", "Error: " + e.getMessage());
            request.setAttribute("tipoMensaje", "error");
        }
        
        listarCuentas(request, response);
    }
    
    private void retirarDinero(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String numeroCuenta = request.getParameter("numeroCuenta");
            double monto = Double.parseDouble(request.getParameter("monto"));
            
            boolean exito = cuentaNegocio.retirarDinero(numeroCuenta, monto);
            
            if (exito) {
                request.setAttribute("mensaje", "Retiro realizado exitosamente");
                request.setAttribute("tipoMensaje", "success");
            } else {
                request.setAttribute("mensaje", "Error al realizar el retiro. Verifique el saldo disponible");
                request.setAttribute("tipoMensaje", "error");
            }
            
        } catch (Exception e) {
            request.setAttribute("mensaje", "Error: " + e.getMessage());
            request.setAttribute("tipoMensaje", "error");
        }
        
        listarCuentas(request, response);
    }
    
    private void transferirDinero(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String numeroCuentaOrigen = request.getParameter("numeroCuentaOrigen");
            String numeroCuentaDestino = request.getParameter("numeroCuentaDestino");
            double monto = Double.parseDouble(request.getParameter("monto"));
            
            boolean exito = cuentaNegocio.transferirSaldo(numeroCuentaOrigen, numeroCuentaDestino, monto);
            
            if (exito) {
                request.setAttribute("mensaje", "Transferencia realizada exitosamente");
                request.setAttribute("tipoMensaje", "success");
            } else {
                request.setAttribute("mensaje", "Error al realizar la transferencia. Verifique los datos y saldo disponible");
                request.setAttribute("tipoMensaje", "error");
            }
            
        } catch (Exception e) {
            request.setAttribute("mensaje", "Error: " + e.getMessage());
            request.setAttribute("tipoMensaje", "error");
        }
        
        listarCuentas(request, response);
    }

	public CuentaDAOImpl getCuentaDAOImpl() {
		return (CuentaDAOImpl) cuentaDAOImpl;
	}

	public void setCuentaDAOImpl(CuentaDAOImpl cuentaDAOImpl) {
		this.cuentaDAOImpl = cuentaDAOImpl;
	}
}
