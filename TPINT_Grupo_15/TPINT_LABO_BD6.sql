/*
En cuanto a los datos, mínimamente cada tabla tiene los siguientes datos:
• Por cada movimiento se tiene una fecha, detalle o concepto, importe y tipo de movimiento.
• Un cliente tiene un DNI, CUIL, nombre, apellido, sexo, nacionalidad, fecha de nacimiento, 
  dirección, localidad, provincia, correo electrónico, teléfonos, un usuario y contraseña para acceder a la página.
• Una cuenta tiene un cliente asignado, fecha de creación, tipo de cuenta, un número de cuenta, CBU y un saldo.
• Un préstamo tiene un cliente asignado, una fecha, el importe que deberá pagar el cliente (con intereses), 
  el importe pedido por el cliente, un plazo de pago en meses, el monto que deberá pagar por mes y cuotas.
*/


-- 1. CREAR BASE DE DATOS
CREATE DATABASE TP_INT;
USE TP_INT;



-- 2. TABLAS BÁSICAS SIN DEPENDENCIAS
CREATE TABLE Provincias (
    IdProvincias_Pr VARCHAR(2) NOT NULL,
    descripcion_Pr VARCHAR(50) NOT NULL,
    CONSTRAINT PK_Provincia PRIMARY KEY (IdProvincias_Pr)
);


CREATE TABLE Localidades (
    IdLocalidad_Loc VARCHAR(2) NOT NULL,
    IdProvincia_Loc VARCHAR(2) NOT NULL,
    descripcion_Loc VARCHAR(30) NOT NULL,
    CONSTRAINT PK_Localidad PRIMARY KEY (IdLocalidad_Loc),
    CONSTRAINT FK_LocalidadProvincia FOREIGN KEY (IdProvincia_Loc) REFERENCES Provincias (IdProvincias_Pr)
);

CREATE TABLE TiposCuentas (
    IdTipoCuenta_Tc INT PRIMARY KEY ,
    NombreTipo_Tc VARCHAR(30) NOT NULL
);

CREATE TABLE TiposMovimientos (
    IdTipoMovimiento_Tm INT NOT NULL PRIMARY KEY,
      Descripcion_Tm VARCHAR(50)
);


-- 3. TABLA CLIENTES: Datos del Cliente
CREATE TABLE Clientes (
    DNI_Cl VARCHAR(8) PRIMARY KEY,
    CUIL_Cl VARCHAR(11) UNIQUE NOT NULL,
    Nombre_Cl VARCHAR(30) NOT NULL,
    Apellido_Cl VARCHAR(30) NOT NULL,
    Sexo_Cl VARCHAR(1) NOT NULL,
    Nacionalidad_Cl VARCHAR(30),
    FechaNacimiento_Cl DATE NOT NULL,
    Direccion_Cl VARCHAR(50),
    IdLocalidad_Cl VARCHAR(2),
    IdProvincia_Cl VARCHAR(2),
    CorreoElectronico_Cl VARCHAR(50) UNIQUE NOT NULL,
    Telefonos_Cl VARCHAR(12) NOT NULL,
    Estado_Cl BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT FK_ClienteLocalidad FOREIGN KEY (IdLocalidad_Cl) REFERENCES Localidades(IdLocalidad_Loc),
    CONSTRAINT FK_ClienteProvincia FOREIGN KEY (IdProvincia_Cl) REFERENCES Provincias(IdProvincias_Pr)
);

-- 4. TABLA USUARIOS:
CREATE TABLE Usuarios (
CodUsuario_Usu INT AUTO_INCREMENT NOT NULL,
    DNI_Usu CHAR(8),
    Nombre_Usu VARCHAR(8),
    Contrasenia_Usu VARCHAR(8),
    Rol_Usu CHAR(1),
    CONSTRAINT check_Rol CHECK (Rol_Usu IN ('A', 'C')),
    CONSTRAINT PK_CodUsuario_Usu PRIMARY KEY (CodUsuario_Usu)
    
);
-- 5. TABLA CUENTA depende de prestamo y cuenta
CREATE TABLE Cuentas (

NumeroCuenta_Cu INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    ClienteDNI_Cu VARCHAR(8) NOT NULL,
    FechaCreacion_Cu DATE NOT NULL,
    TipoCuenta_Cu INT NOT NULL,
    CBU_Cu VARCHAR(22) UNIQUE NOT NULL,
    SALDO_Cu DECIMAL(15,2) NOT NULL DEFAULT 0,
    Estado_Cu BOOLEAN  DEFAULT TRUE,
    CONSTRAINT FK_TipoCuenta FOREIGN KEY (TipoCuenta_Cu) REFERENCES TiposCuentas(IdTipoCuenta_Tc),
    CONSTRAINT FK_CuentaCliente FOREIGN KEY (ClienteDNI_Cu) REFERENCES Clientes(DNI_Cl)
);




-- 6. TABLA PRESTAMO
CREATE TABLE Prestamos (
    IdPrestamo_Pr INT AUTO_INCREMENT PRIMARY KEY,
    DNICliente_Pr VARCHAR(8) NOT NULL,
	NumeroCuenta_Pr int UNSIGNED  NOT NULL,
    Fecha_Pr DATE NOT NULL,
    ImporteTotal_Pr DECIMAL(15,2) NOT NULL,
    ImporteSolicitado_Pr DECIMAL(15,2) NOT NULL,
    PlazoPagoMeses_Pr INT NOT NULL,
    MontoMensual_Pr DECIMAL(15,2) NOT NULL,
    Cuotas_Pr VARCHAR(50),
    Estado_Pr CHAR(1) NOT NULL DEFAULT 'P',
	CONSTRAINT chk_EstadoPrestamo CHECK (Estado_Pr IN ('P', 'A', 'R')),
    CONSTRAINT FK_PrestamoCliente FOREIGN KEY (DNICliente_Pr) REFERENCES Clientes(DNI_Cl),
	CONSTRAINT FK_PrestamoCuenta FOREIGN KEY (NumeroCuenta_Pr) REFERENCES Cuentas(NumeroCuenta_Cu)
);

-- 7.tabla cuotas

CREATE TABLE Cuotas (
    IdCuota_Cuo INT AUTO_INCREMENT PRIMARY KEY,
    IDPrestamo_Cuo INT NOT NULL,
    NroCuenta_Cuo INT UNSIGNED NOT NULL,
    NroCuota_Cuo INT  NOT NULL,
    FechaVencimiento_Cuo DATE NOT NULL,
    MontoCuota_Cuo DECIMAL(15,2) NOT NULL,
    Estado_Cuo CHAR(1) NOT NULL DEFAULT 'D',
    CONSTRAINT chk_EstadoCuota CHECK (Estado_Cuo IN ('P', 'D')),
    CONSTRAINT FK_CuotaPrestamo FOREIGN KEY (IDPrestamo_Cuo) REFERENCES Prestamos(IdPrestamo_Pr),
    CONSTRAINT FK_CuotaCuenta FOREIGN KEY (NroCuenta_Cuo) REFERENCES Cuentas(NumeroCuenta_Cu)
);


-- 8. TABLA MOVIMIENTO (depende de CUENTA, TIPOS_MOVIMIENTO y PRESTAMO)
CREATE TABLE Movimientos (
    IDMovimiento_Mv INT AUTO_INCREMENT PRIMARY KEY,
    Fecha_Mv DATE NOT NULL,
    Detalle_Mv VARCHAR(100) NOT NULL,
    Importe_Mv DECIMAL(10,2) NOT NULL,
    NumeroCuenta_Mv INT UNSIGNED NULL,
    IdTipoMovimiento_Mv INT NOT NULL,
     CONSTRAINT FK_MovimientoCuenta FOREIGN KEY (NumeroCuenta_Mv) REFERENCES Cuentas(NumeroCuenta_Cu),
    CONSTRAINT FK_MovimientoTipo FOREIGN KEY (IdTipoMovimiento_Mv) REFERENCES TiposMovimientos(IdTipoMovimiento_Tm)
   
  
);



-- insertar datos
INSERT INTO Provincias (IdProvincias_Pr, descripcion_Pr) VALUES
('01', 'Buenos Aires'),
('02', 'Misiones');

INSERT INTO Localidades (IdLocalidad_Loc, IdProvincia_Loc, descripcion_Loc) VALUES
-- Buenos Aires
('01', '01', 'La Plata'),
('03', '01', 'Mar del Plata'),
('04', '01', 'Bahía Blanca'),
('05', '01', 'Tigre'),

-- Misiones
('02', '02', 'Posadas'),
('06', '02', 'Oberá'),
('07', '02', 'Eldorado'),
('08', '02', 'Puerto Iguazú');

 INSERT INTO Clientes (
    DNI_Cl, CUIL_Cl, Nombre_Cl, Apellido_Cl, Sexo_Cl,
    Nacionalidad_Cl, FechaNacimiento_Cl, Direccion_Cl,
    IdLocalidad_Cl, IdProvincia_Cl, CorreoElectronico_Cl, Telefonos_Cl
) VALUES
('12345678', '20123456780', 'MARIA', 'PEREZ', 'F',
 'Argentina', '1990-05-10', 'Calle Falsa 123',
 '01', '01', 'maria.perez@email.com', '1134567890'),

('23456789', '20234567891', 'JUAN', 'GOMEZ', 'M',
 'Argentina', '1985-03-20', 'Av. Siempre Viva 742',
 '03', '01', 'juan.gomez@email.com', '1145678901'),

('34567890', '20345678902', 'LAURA', 'SUAREZ', 'F',
 'Argentina', '1992-07-15', 'San Martín 1234',
 '03', '02', 'laura.suarez@email.com', '1156789012');
 
INSERT INTO Usuarios (DNI_Usu, Nombre_Usu, Contrasenia_Usu, Rol_Usu) VALUES
('12345678', 'MARIA',    'clave123', 'A'),
('34955063', 'JUDITH',    'clave456', 'A'),
('23456789', 'JUAN',     'segura99', 'C'),
('34567890', 'LAURA',    'pass4567', 'C');


INSERT INTO TiposCuentas ( IdTipoCuenta_Tc, NombreTipo_Tc) VALUES
(1,'Cuenta Corriente'),
(2,'Caja de Ahorro');


-- acordarcequepara insertar ceuntas , siempre tiene que existir el cliente
INSERT INTO Cuentas (ClienteDNI_Cu, FechaCreacion_Cu, TipoCuenta_Cu, CBU_Cu, SALDO_Cu) VALUES
('12345678', '2025-06-01', 1, '0001000000000000000001', 15000.00),
('23456789', '2025-06-05', 2, '0001000000000000000002', 8000.50),
('34567890', '2025-06-10', 1, '0001000000000000000003', 12000.75);


INSERT INTO TiposMovimientos (IdTipoMovimiento_Tm, Descripcion_Tm)
VALUES 
    (1, 'Alta de cuenta'),
    (2, 'Alta de préstamo'),
    (3, 'Pago de préstamo'),
    (4, 'Transferencia');



    
--TRIGGER PARA CARGAR TABLA MOVIMIENTOS 

DELIMITER $$

CREATE TRIGGER tr_cuentas_despues_insert
AFTER INSERT ON Cuentas
FOR EACH ROW
BEGIN
  INSERT INTO Movimientos (
    Fecha_Mv,
    Detalle_Mv,
    Importe_Mv,
    NumeroCuenta_Mv,
    IdTipoMovimiento_Mv
  )
  VALUES (
    CURDATE(),
    'positivo',
    NEW.Saldo_Cu,
    NEW.NumeroCuenta_Cu,
    1
  );
END$$

DELIMITER ;

-- trigger qeuse aaciona cuando aprobanos un prestamo, guarda el moviemeinto y ademas en la tabla cuentas le suma el importe que solicitaron

DELIMITER $$

CREATE TRIGGER trg_AprobacionPrestamo AFTER UPDATE ON Prestamos
FOR EACH ROW
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE totalCuotas INT;
    DECLARE fechaBase DATE;

    -- solo se activa si cambiamos a ´'A', si cambiamos a R rechazado no entra el trigger no cambia nada
    IF OLD.Estado_Pr <> 'A' AND NEW.Estado_Pr = 'A' THEN

        -- Insertar movimiento en Movimientos
        INSERT INTO Movimientos (
            Fecha_Mv,
            Detalle_Mv,
            Importe_Mv,
            NumeroCuenta_Mv,
            IdTipoMovimiento_Mv
        ) VALUES (
            CURDATE(),
            'positivo',
            NEW.ImporteSolicitado_Pr,
            NEW.NumeroCuenta_Pr,
            2
        );

        -- Actualizar saldo en Cuentas
        UPDATE Cuentas
        SET SALDO_Cu = SALDO_Cu + NEW.ImporteSolicitado_Pr
        WHERE NumeroCuenta_Cu = NEW.NumeroCuenta_Pr;

        -- Preparar variables para insertar cuotas
        SET totalCuotas = CAST(NEW.Cuotas_Pr AS UNSIGNED);
        SET fechaBase = NEW.Fecha_Pr;

        -- Insertar cuotas en bucle
        WHILE i <= totalCuotas DO
            INSERT INTO Cuotas (
                IDPrestamo_Cuo,
                NroCuenta_Cuo,
                NroCuota_Cuo,
                FechaVencimiento_Cuo,
                MontoCuota_Cuo,
                Estado_Cuo
            ) VALUES (
                NEW.IdPrestamo_Pr,
                NEW.NumeroCuenta_Pr,
                i,
                DATE_ADD(fechaBase, INTERVAL i MONTH),
                NEW.MontoMensual_Pr,
                'D'
            );
            SET i = i + 1;
        END WHILE;

    END IF;
END$$

DELIMITER ;

-- VALIDACIONES TABLA CUENTAS

ALTER TABLE CUENTAS
ADD constraint chk_saldo_valido
check (SALDO_Cu >0);




-- VALIDACIONES TABLA CUOTAS
alter table Cuotas
add constraint chk_IDPrestamo_valido
check(IDPrestamo_Cuo>0);

alter table Cuotas
add constraint chk_MontoCuota_valido
check(MontoCuota_Cuo>0);

alter table Cuotas
add constraint chk_CuentaPago_valido
check(CuentaPago_Cuo>0);


-- VALIDACIONES TABLA PRESTAMOS
alter table  Prestamos
add constraint chk_importeTotal_valido
check(ImporteTotal_Pr >0);

alter table  Prestamos
add constraint chk_importeSolicitado_valido
check(ImporteSolicitado_Pr >0);

alter table Prestamos 
add constraint chk_PlazoPagoMeses_valido
check(PlazoPagoMeses_Pr>0);

alter table Prestamos 
add constraint chk_MontoMensual_valido
check(MontoMensual_Pr>0);

alter table Prestamos 
add constraint chk_Cuotas_valido
check(Cuotas_Pr>0);


select * from  Prestamos;
select* from movimientos;
select * from cuotas;
select * from cuentas;

select * from Usuarios;

select * from clientes;

select * from localidades;

select* from provincias;
