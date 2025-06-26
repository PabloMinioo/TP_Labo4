package entidad;

import java.time.LocalDate;
import java.util.List;

public class Cuenta {
	private int numeroCuenta;
    private String clienteDNI;
    private LocalDate fechaCreacion;
    private int tipoCuenta;
    private String cbu;
    private double saldo;
    
    // Constructor vacío
    public Cuenta() {
        this.saldo = 0.0;
        this.fechaCreacion = LocalDate.now();
    }
    
    // Constructor con parametros
    public Cuenta(int numeroCuenta, String clienteDNI, LocalDate fechaCreacion, 
                  int tipoCuenta, String cbu, double saldo) {
        this.numeroCuenta = numeroCuenta;
        this.clienteDNI = clienteDNI;
        this.fechaCreacion = fechaCreacion;
        this.tipoCuenta = tipoCuenta;
        this.cbu = cbu;
        this.saldo = saldo;
    }
    
    // Getters y Setters
    public int getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(int numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }
    
    public String getClienteDNI() {
        return clienteDNI;
    }
    
    public void setClienteDNI(String clienteDNI) {
        this.clienteDNI = clienteDNI;
    }
    
    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public int getTipoCuenta() {
        return tipoCuenta;
    }
    
    public void setTipoCuenta(int tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }
    
    public String getCbu() {
        return cbu;
    }
    
    public void setCbu(String cbu) {
        this.cbu = cbu;
    }
    
    public double getSaldo() {
        return saldo;
    }
    
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    
    //metodo to string 
    @Override
    public String toString() {
        return "Cuenta{" +
                "numeroCuenta='" + numeroCuenta + '\'' +
                ", clienteDNI='" + clienteDNI + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", tipoCuenta=" + tipoCuenta +
                ", cbu='" + cbu + '\'' +
                ", saldo=" + saldo +
                '}';
    }

	


}