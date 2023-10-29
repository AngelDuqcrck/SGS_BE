-- Crear la tabla roles si no existe
CREATE TABLE IF NOT EXISTS roles
(
    id
    INT
    NOT
    NULL
    AUTO_INCREMENT,
    description
    VARCHAR (40) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY (description)
    );
-- Insertar roles por defecto en la tabla roles si aún no existen
INSERT
IGNORE INTO roles (id,description) VALUES
    (1,"ROLE_SERVICE_BOSS"),
    (2,"ROLE_EMPLOYEE"),
    (3,"ROLE_DEPENDENCE_BOSS"),
    (4,"ROLE_SERVICE_EMPLOYEE");

-- Crear la tabla dependencias si no existe
CREATE TABLE IF NOT EXISTS dependencies
(
    id
    INT
    NOT
    NULL
    AUTO_INCREMENT,
    description
    VARCHAR (100) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY ( description )
    );

-- Insertar dependencias por defecto en la tabla dependencias si aún no existen
INSERT
IGNORE INTO dependencies (id, description) VALUES
(1,"MARKETING"),
(2,"PRODUCTION"),
(3,"FINANCIAL"),
(4, "RRHH"),
(5, "SERVICES");

-- Crear la tabla estadoSolicitudes si no existe
CREATE TABLE IF NOT EXISTS status_request
(
    id
    INT
    AUTO_INCREMENT
    PRIMARY
    KEY,
    description
    VARCHAR (50) NOT NULL
    );

-- Insertar algunos estados por defecto en estadoSolicitudes
INSERT
IGNORE INTO status_request (id, description) VALUES
    (1,"STAND_BY"),
    (2,"VERIFIED"),
    (3,"APPROVE"),
    (4,"REJECT"),
    (5,"SENT"),
    (6,"CANCELLED");

-- Crear la tabla estadoTickets si no existe
CREATE TABLE IF NOT EXISTS status_tickets
(
    id
    INT
    AUTO_INCREMENT
    PRIMARY
    KEY,
    description
    VARCHAR (50) NOT NULL
    );

-- Insertar algunos estados por defecto en estadoTickets
INSERT
IGNORE INTO status_tickets (id, description) VALUES
    (1, "ASSIGNED"),
    (2, "RUNNING"),
    (3, "FINISHED");
