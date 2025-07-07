package entidad;

import java.math.BigDecimal;
import java.sql.Date;

import Excepciones.MontoCuotaInvalido;


public class Cuota {
private int IdCuota;
private int IdPrestamo;
private int NroCuota;
private Date FechaVencimiento;
private Date FechaPago;
private BigDecimal MontoCuota;
private boolean Pagada;
private int CuentaPago;

public Cuota(){};

public Cuota(int IdCuota,int IdPrestamo,int NroCuota,Date FechaVencimiento,Date FechaPago,BigDecimal MontoCuota,boolean Pagada,int CuentaPago ) throws MontoCuotaInvalido{
if(MontoCuota.compareTo(BigDecimal.ZERO)<=0){
	throw new MontoCuotaInvalido("El monto de la cuota debe ser mayor a 0 ");
}

this.IdCuota=IdCuota;
this.IdPrestamo=IdPrestamo;
this.NroCuota=NroCuota;
this.FechaVencimiento=FechaVencimiento;
this.FechaPago=FechaPago;
this.MontoCuota=MontoCuota;
this.Pagada=Pagada;
this.CuentaPago=CuentaPago;
}

public int getIdCuota() {
	return IdCuota;
}

public void setIdCuota(int idCuota) {
	IdCuota = idCuota;
}

public int getIdPrestamo() {
	return IdPrestamo;
}

public void setIdPrestamo(int idPrestamo) {
	IdPrestamo = idPrestamo;
}

public int getNroCuota() {
	return NroCuota;
}

public void setNroCuota(int nroCuota) {
	NroCuota = nroCuota;
}

public Date getFechaVencimiento() {
	return FechaVencimiento;
}

public void setFechaVencimiento(Date fechaVencimiento) {
	FechaVencimiento = fechaVencimiento;
}

public Date getFechaPago() {
	return FechaPago;
}

public void setFechaPago(Date fechaPago) {
	FechaPago = fechaPago;
}

public BigDecimal getMontoCuota() {
	return MontoCuota;
}

public void setMontoCuota(BigDecimal montoCuota) {
	MontoCuota = montoCuota;
}

public boolean getPagada() {
	return Pagada;
}

public void setPagada(boolean pagada) {
	Pagada = pagada;
}

public int getCuentaPago() {
	return CuentaPago;
}

public void setCuentaPago(int cuentaPago) {
	CuentaPago = cuentaPago;
}

@Override
public String toString() {
	return "Cuota [IdCuota=" + IdCuota + ", IdPrestamo=" + IdPrestamo + ", NroCuota=" + NroCuota + ", FechaVencimiento="
			+ FechaVencimiento + ", FechaPago=" + FechaPago + ", MontoCuota=" + MontoCuota + ", Pagada=" + Pagada
			+ ", CuentaPago=" + CuentaPago + "]";
}





}
