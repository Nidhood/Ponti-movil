-- Esta parte de la estructura de la BD, es la que se encarga de crear las funciones que se van a utilizar para
-- permitir los servicios de la aplicación de Ponti-Movil (Gestión de Rutas del Transporte Público Transmilenio).

-----------------------------------------------------------------------------------------------------------------------

-- Table: DIRECCIONES

CREATE TABLE IF NOT EXISTS DIRECCIONES
(
    ID             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    CALLE          VARCHAR(320) NOT NULL,
    CARRERA        VARCHAR(320) NOT NULL,
    NUMERO         VARCHAR(320) NOT NULL,
    LOCALIDAD      VARCHAR(320) NOT NULL,
    BARRIO         VARCHAR(320) NOT NULL,
    UNIQUE (CALLE, CARRERA, NUMERO)
);

-----------------------------------------------------------------------------------------------------------------------

-- Table: ESTACIONES

CREATE TABLE IF NOT EXISTS ESTACIONES
(
    ID             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    NOMBRE         VARCHAR(320) NOT NULL,
    DIRECCION_ID   UUID NOT NULL,
    UNIQUE (NOMBRE),
    CONSTRAINT fk_station_address FOREIGN KEY (DIRECCION_ID) REFERENCES DIRECCIONES (ID)
);

-----------------------------------------------------------------------------------------------------------------------

-- Table: HORARIOS

CREATE TABLE IF NOT EXISTS HORARIOS
(
    ID             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    DIA            VARCHAR(20) NOT NULL,
    HORA_INICIO    TIME NOT NULL,
    HORA_FIN       TIME NOT NULL,
    UNIQUE (DIA, HORA_INICIO, HORA_FIN)
);

-----------------------------------------------------------------------------------------------------------------------

-- Table: CONDUCTORES

CREATE TABLE IF NOT EXISTS CONDUCTORES
(
    ID             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    NOMBRE         VARCHAR(20) NOT NULL,
    APELLIDO       VARCHAR(20) NOT NULL,
    CEDULA         VARCHAR(20) NOT NULL,
    TELEFONO       VARCHAR(20) NOT NULL,
    DIRECCION_ID   UUID NOT NULL,
    UNIQUE (CEDULA),
    CONSTRAINT fk_driver_address FOREIGN KEY (DIRECCION_ID) REFERENCES DIRECCIONES (ID)
);

-----------------------------------------------------------------------------------------------------------------------

-- Table: BUSES

CREATE TABLE IF NOT EXISTS BUSES
(
    ID             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    PLACA          VARCHAR(20) NOT NULL,
    MODELO         VARCHAR(20) NOT NULL,
    UNIQUE (PLACA)
);

-----------------------------------------------------------------------------------------------------------------------

-- Table: RUTAS

CREATE TABLE IF NOT EXISTS RUTAS
(
    ID             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    CODIGO         VARCHAR(20) NOT NULL,
    HORARIO_ID     UUID NOT NULL,
    UNIQUE (CODIGO, HORARIO_ID),
    CONSTRAINT fk_route_schedule FOREIGN KEY (HORARIO_ID) REFERENCES HORARIOS (ID)
);

-----------------------------------------------------------------------------------------------------------------------

-- Table: ASIGNACIONES

CREATE TABLE IF NOT EXISTS ASIGNACIONES
(
    ID             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    CONDUCTOR_ID   UUID NOT NULL,
    BUS_ID         UUID NOT NULL,
    RUTA_ID        UUID,
    DIA_SEMANA     VARCHAR(20) NOT NULL,
    UNIQUE (CONDUCTOR_ID, BUS_ID, RUTA_ID, DIA_SEMANA),
    CHECK (DIA_SEMANA IN ('Lunes', 'Martes', 'Miercoles', 'Jueves', 'Viernes', 'Sabado', 'Domingo')),
    CONSTRAINT fk_assignment_driver FOREIGN KEY (CONDUCTOR_ID) REFERENCES CONDUCTORES (ID),
    CONSTRAINT fk_assignment_bus FOREIGN KEY (BUS_ID) REFERENCES BUSES (ID),
    CONSTRAINT fk_assignment_route FOREIGN KEY (RUTA_ID) REFERENCES RUTAS (ID)
);

-----------------------------------------------------------------------------------------------------------------------

-- Table: RUTAS_ESTACIONES

CREATE TABLE IF NOT EXISTS RUTAS_ESTACIONES
(
    RUTA_ID        UUID NOT NULL,
    ESTACION_ID    UUID NOT NULL,
    ORDEN          INT NOT NULL,
    PRIMARY KEY (RUTA_ID, ESTACION_ID),
    UNIQUE (RUTA_ID, ORDEN),
    CONSTRAINT fk_route_station FOREIGN KEY (RUTA_ID) REFERENCES RUTAS (ID),
    CONSTRAINT fk_station_route FOREIGN KEY (ESTACION_ID) REFERENCES ESTACIONES (ID)
);