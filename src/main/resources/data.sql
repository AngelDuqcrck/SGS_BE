-- Create role table if this isn't exist
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
-- Insert default roles if these aren't exist
INSERT
IGNORE INTO roles (id,description) VALUES
    (1,"ROLE_SERVICE_BOSS"),
    (2,"ROLE_EMPLOYEE"),
    (3,"ROLE_DEPENDENCE_BOSS"),
    (4,"ROLE_SERVICE_EMPLOYEE");

-- Create dependence table if this isn't exist
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

-- Insert default dependences into the dependencies table if these isn't exist
INSERT
IGNORE INTO dependencies (id, description) VALUES
(1,"MARKETING"),
(2,"PRODUCTION"),
(3,"FINANCIAL"),
(4, "RRHH"),
(5, "SERVICES");

-- Create table status request if this isn't exist
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

-- Insert some status request into the status request table if these aren't exist
INSERT
IGNORE INTO status_request (id, description) VALUES
    (1,"STAND_BY"),
    (2,"VERIFIED"),
    (3,"APPROVE"),
    (4,"REJECT"),
    (5,"SENT"),
    (6,"CANCELLED");

-- Create status tickets table if this isn't exist
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

-- Insert some status tickets if these aren't exist
INSERT
IGNORE INTO status_tickets (id, description) VALUES
    (1, "ASSIGNED"),
    (2, "RUNNING"),
    (3, "FINISHED");

