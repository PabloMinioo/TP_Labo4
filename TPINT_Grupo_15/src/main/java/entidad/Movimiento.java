package entidad;

import java.math.BigDecimal;
import java.sql.Date;

import Excepciones.ImporteInvalidoException;
import Excepciones.SaldoInvalidoException;



public class Movimiento {
private int IDMovimiento;
private Date Fecha;
private String Detalle;
private BigDecimal Importe;
private int NumeroCuenta;
private int IdTipoMovimiento;
private BigDecimal SaldoAnterior;
private BigDecimal SaldoPosterior;
private int CuentaDestino;

public Movimiento() {};

public Movimiento(int IDMovimiento,Date Fecha,String Detalle,BigDecimal Importe,int NumeroCuenta,int IdTipoMovimiento,BigDecimal SaldoAnterior,BigDecimal SaldoPosterior,int CuentaDestino) throws ImporteInvalidoException, SaldoInvalidoException {
if(Importe.compareTo(BigDecimal.ZERO)<=0) {
	throw new ImporteInvalidoException("El Importe debe ser mayor a 0 ");
}
if(SaldoAnterior.compareTo(BigDecimal.ZERO)<=0) {
	throw new SaldoInvalidoException("El saldo debe ser mayor a 0 ");
}
if(SaldoPosterior.compareTo(BigDecimal.ZERO)<=0) {
	throw new SaldoInvalidoException("El saldo debe ser mayor a 0 ");	
}

this.IDMovimiento=IDMovimiento;
this.Fecha=Fecha;
this.Detalle=Detalle;
this.Importe=Importe;
this.NumeroCuenta=NumeroCuenta;
this.IdTipoMovimiento=IdTipoMovimiento;
this.SaldoAnterior=SaldoAnterior;
this.SaldoPosterior=SaldoPosterior;
this.CuentaDestino=CuentaDestino;

}

public int getIDMovimiento() {
	return IDMovimiento;
}

public void setIDMovimiento(int iDMovimiento) {
	IDMovimiento = iDMovimiento;
}

public Date getFecha() {
	return Fecha;
}

public void setFecha(Date fecha) {
	Fecha = fecha;
}

public String getDetalle() {
	return Detalle;
}

public void setDetalle(String detalle) {
	Detalle = detalle;
}

public BigDecimal getImporte() {
	return Importe;
}

public void setImporte(BigDecimal importe) {
	Importe = importe;
}

public int getNumeroCuenta() {
	return NumeroCuenta;
}

public void setNumeroCuenta(int numeroCuenta) {
	NumeroCuenta = numeroCuenta;
}

public int getIdTipoMovimiento() {
	return IdTipoMovimiento;
}

public void setIdTipoMovimiento(int idTipoMovimiento) {
	IdTipoMovimiento = idTipoMovimiento;
}

public BigDecimal getSaldoAnterior() {
	return SaldoAnterior;
}

public void setSaldoAnterior(BigDecimal saldoAnterior) {
	SaldoAnterior = saldoAnterior;
}

public BigDecimal getSaldoPosterior() {
	return SaldoPosterior;
}

public void setSaldoPosterior(BigDecimal saldoPosterior) {
	SaldoPosterior = saldoPosterior;
}

public int getCuentaDestino() {
	return CuentaDestino;
}

public void setCuentaDestino(int cuentaDestino) {
	CuentaDestino = cuentaDestino;
}

@Override
public String toString() {
	return "Movimiento [IDMovimiento=" + IDMovimiento + ", Fecha=" + Fecha + ", Detalle=" + Detalle + ", Importe="
			+ Importe + ", NumeroCuenta=" + NumeroCuenta + ", IdTipoMovimiento=" + IdTipoMovimiento + ", SaldoAnterior="
			+ SaldoAnterior + ", SaldoPosterior=" + SaldoPosterior + ", CuentaDestino=" + CuentaDestino + "]";
}


}
