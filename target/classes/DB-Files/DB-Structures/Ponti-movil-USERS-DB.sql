-- Esta parte de la estructura de la BD, es la que se encarga de crear las tablas que se van a utilizar para
-- almacenar la información de los usuarios.

-----------------------------------------------------------------------------------------------------------------------

-- Table: USUARIOS

CREATE TABLE IF NOT EXISTS USUARIOS
(
    ID             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    NOMBRE         VARCHAR(20),
    APELLIDO       VARCHAR(20),
    NOMBRE_USUARIO VARCHAR(20) NOT NULL,
    CONTRASENA     VARCHAR(320) NOT NULL,
    CORREO         VARCHAR(320) NOT NULL,
    TIPO_USUARIO VARCHAR(20) NOT NULL,
    UNIQUE (NOMBRE_USUARIO),
    UNIQUE (CORREO),
    CONSTRAINT ok_user_type CHECK (TIPO_USUARIO IN ('Coordinador', 'Administrador', 'Pasajero'))
);