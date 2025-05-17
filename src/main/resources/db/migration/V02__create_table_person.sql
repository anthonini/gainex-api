CREATE TABLE person (
    id_person BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(50) NOT NULL,
    active BOOLEAN NOT NULL,
    street_address VARCHAR(30),
    number VARCHAR(30),
    address_line_2 VARCHAR(30),
    neighborhood VARCHAR(30),
    postal_code VARCHAR(30),
    city VARCHAR(30),
    state_or_region VARCHAR(30)
);