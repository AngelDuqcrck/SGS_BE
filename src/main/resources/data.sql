-- Crear la tabla roles si no existe
CREATE TABLE IF NOT EXISTS roles (
    id INT NOT NULL AUTO_INCREMENT,
    descripcion VARCHAR(40) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY (descripcion)
);
-- Insertar roles por defecto en la tabla roles si aún no existen
INSERT IGNORE INTO roles (id,descripcion) VALUES
    (1,"ROLE_ADMIN"),
    (2,"ROLE_EMPLEADO"),
    (3,"ROLE_JEFE"),
    (4,"ROLE_EMPLEADO_SERVICE");

-- Crear la tabla dependencias si no existe
CREATE TABLE IF NOT EXISTS dependencias (
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY (nombre)
);

-- Insertar dependencias por defecto en la tabla dependencias si aún no existen
INSERT IGNORE INTO dependencias (id, nombre) VALUES
(1,"MARKETING"),
(2,"PRODUCCION"),
(3,"FINANCIERO"),
(4, "RRHH"),
(5, "SERVICIOS");

-- Crear la tabla estadoSolicitudes si no existe
CREATE TABLE IF NOT EXISTS estadoSolicitudes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

-- Insertar algunos estados por defecto en estadoSolicitudes
INSERT IGNORE INTO estadoSolicitudes (id, nombre) VALUES
    (1,"PENDIENTE"),
    (2,"VERIFICADA"),
    (3,"APROBADA"),
    (4,"RECHAZADA");

-- Crear la tabla estadoTickets si no existe
CREATE TABLE IF NOT EXISTS estadoTickets (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

-- Insertar algunos estados por defecto en estadoTickets
INSERT IGNORE INTO estadoTickets (id, nombre) VALUES
    (1, "ASIGNADO"),
    (2, "EN EJECUCION"),
    (3, "FINALIZADO");
