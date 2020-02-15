CREATE SEQUENCE IF NOT EXISTS ID_GEN
INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS VEHICLE
(
    ID BIGINT NOT NULL
        PRIMARY KEY,
    BRAND VARCHAR(50),
    COLOR VARCHAR(50),
    CREATION_DATE TIMESTAMP,
    MANUFACTURED_COUNTRY VARCHAR(2),
    MODEL VARCHAR(50),
    PLATE_COUNTRY VARCHAR(2),
    PLATE_NUMBER VARCHAR(30) NOT NULL,
    VEHICLE_TYPE VARCHAR(50) NOT NULL,
    VIN VARCHAR(17) NOT NULL
        CONSTRAINT VEHICLE_UNIQUE_VIN
            UNIQUE
);


