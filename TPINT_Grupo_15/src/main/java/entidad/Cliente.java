package entidad;

import java.time.LocalDate;

public class Cliente {
	private String dni;
    private String cuil;
    private String nombre;
    private String apellido;
    private String sexo;
    private String nacionalidad;
    private LocalDate fechaNacimiento;
    private String direccion;
    private String idLocalidad;
    private String descripcionLocalidad;
    private String idProvincia;
    private String descripcionProvincia;
    private String correoElectronico;
    private String telefonos;
    private String nombreUsuario;
    private String contraseniaUsuario;
    private boolean estado;
    
    // CONSTRUCTOR VACIO
    public Cliente() {
        this.estado = true;
        this.fechaNacimiento = LocalDate.now();
    }

    // CONSTRUCTOR CON PARAMETROS
    public Cliente(String dni, String cuil, String nombre, String apellido, String sexo,
                   String nacionalidad, LocalDate fechaNacimiento, String direccion,
                   String idLocalidad, String idProvincia, String correoElectronico,
                   String telefonos, boolean estado) {
        this.dni = dni;
        this.cuil = cuil;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
        this.nacionalidad = nacionalidad;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.idLocalidad = idLocalidad;
        this.idProvincia = idProvincia;
        this.correoElectronico = correoElectronico;
        this.telefonos = telefonos;
        this.estado = estado;
    }

    // GETTERS Y SETTERS
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getCuil() { return cuil; }
    public void setCuil(String cuil) { this.cuil = cuil; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public String getNacionalidad() { return nacionalidad; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getIdLocalidad() { return idLocalidad; }
    public void setIdLocalidad(String idLocalidad) { this.idLocalidad = idLocalidad; }

    public String getDescripcionLocalidad() { return descripcionLocalidad; }
    public void setDescripcionLocalidad(String descripcionLocalidad) { this.descripcionLocalidad = descripcionLocalidad; }
    
    public String getIdProvincia() { return idProvincia; }
    public void setIdProvincia(String idProvincia) { this.idProvincia = idProvincia; }
    
    public String getDescripcionProvincia() { return descripcionProvincia; }
    public void setDescripcionProvincia(String descripcionProvincia) { this.descripcionProvincia = descripcionProvincia; }

    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    public String getTelefonos() { return telefonos; }
    public void setTelefonos(String telefonos) { this.telefonos = telefonos; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getContraseniaUsuario() { return contraseniaUsuario; }
    public void setContraseniaUsuario(String contraseniaUsuario) { this.contraseniaUsuario = contraseniaUsuario; }
    
    public boolean getEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }

	@Override
	public String toString() {
		return "Cliente [dni=" + dni + ", cuil=" + cuil + ", nombre=" + nombre + ", apellido=" + apellido + ", sexo="
				+ sexo + ", nacionalidad=" + nacionalidad + ", fechaNacimiento=" + fechaNacimiento + ", direccion="
				+ direccion + ", idLocalidad=" + idLocalidad + ", idProvincia=" + idProvincia + ", correoElectronico="
				+ correoElectronico + ", telefonos=" + telefonos + ", nombreUsuario=" + nombreUsuario
				+ ", contraseniaUsuario=" + contraseniaUsuario + ", estado=" + estado + "]";
	}
    

}
