package entidad;

import java.math.BigDecimal;
import java.sql.Date;

import Excepciones.MontoCuotaInvalido;


public class Cuota {
private int IdCuota;
private int IdPrestamo;
private int NroCuenta;
private int NroCuota;
private Date FechaVencimiento;

private BigDecimal MontoCuota;
private String Estado;


public Cuota(){};

public Cuota(int IdCuota,int IdPrestamo,int NroCuenta ,int NroCuota,Date FechaVencimiento,BigDecimal MontoCuota,String Estado) throws MontoCuotaInvalido{
if(MontoCuota.compareTo(BigDecimal.ZERO)<=0){
	throw new MontoCuotaInvalido("El monto de la cuota debe ser mayor a 0 ");
}

this.IdCuota=IdCuota;
this.IdPrestamo=IdPrestamo;
this.NroCuota=NroCuota;
this.NroCuota=NroCuenta;
this.FechaVencimiento=FechaVencimiento;

this.MontoCuota=MontoCuota;
this.Estado=Estado;
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

public String getEstado() {
	return Estado;
}

public void setEstado(String estado) {
	Estado = estado;
}

public BigDecimal getMontoCuota() {
	return MontoCuota;
}

public void setMontoCuota(BigDecimal montoCuota) {
	MontoCuota = montoCuota;
}

public int getNroCuenta() {
	return NroCuenta;
}

public void setNroCuenta(int nrocuenta) {
	NroCuenta = nrocuenta;
}

@Override
public String toString() {
	return "Cuota [IdCuota=" + IdCuota + ", IdPrestamo=" + IdPrestamo + ", NroCuenta=" + NroCuenta + ", NroCuota="
			+ NroCuota + ", FechaVencimiento=" + FechaVencimiento + ", MontoCuota=" + MontoCuota + ", Estado=" + Estado
			+ "]";
}









}
