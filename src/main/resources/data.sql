CREATE TABLE IF NOT EXISTS roles (
    id INT NOT NULL AUTO_INCREMENT,
    descripcion VARCHAR(40) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY (descripcion)
);

INSERT IGNORE INTO roles (id,descripcion) VALUES
    (1,"ROLE_ADMIN"),
    (2,"ROLE_EMPLEADO"),
    (3,"ROLE_JEFE");

CREATE TABLE IF NOT EXISTS dependencias (
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY (nombre)
);

INSERT IGNORE INTO dependencias (id, nombre) VALUES
(1,"Marketing"),
(2,"Produccion"),
(3,"Financiero"),
(4, "RRHH"),
(5, "Servicios");