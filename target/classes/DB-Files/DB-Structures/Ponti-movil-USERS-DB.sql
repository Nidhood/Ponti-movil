-- Esta parte de la estructura de la BD, es la que se encarga de crear las tablas que se van a utilizar para
-- almacenar la información de los usuarios.

-----------------------------------------------------------------------------------------------------------------------

-- Table: TIPOS_USUARIOS

CREATE TABLE IF NOT EXISTS TIPOS_USUARIOS
(
    ID          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    TIPO_USUARIO VARCHAR(20) NOT NULL,
    PERMISO_ID  UUID NOT NULL,
    UNIQUE (TIPO_USUARIO),
    CONSTRAINT ok_user_type CHECK (TIPO_USUARIO IN ('Coordinador', 'Administrador', 'Pasajero'))
);

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
    TIPO_ID        UUID NOT NULL,
    UNIQUE (NOMBRE_USUARIO),
    UNIQUE (CORREO),
    UNIQUE (TIPO_ID),
    CONSTRAINT fk_user_type FOREIGN KEY (TIPO_ID) REFERENCES TIPOS_USUARIOS (ID)
);

-----------------------------------------------------------------------------------------------------------------------

-- Table: PERMISOS

CREATE TABLE IF NOT EXISTS PERMISOS
(
    ID          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    PERMISO     VARCHAR(20) NOT NULL,
    UNIQUE (PERMISO),
    CONSTRAINT ok_permission CHECK (PERMISO IN ('Crear', 'Leer', 'Actualizar', 'Eliminar'))
);