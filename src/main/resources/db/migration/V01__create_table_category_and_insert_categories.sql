CREATE TABLE category (
	id_category BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	name VARCHAR(50) NOT NULL
);

INSERT INTO category (name) values ('Leisure');
INSERT INTO category (name) values ('Food');
INSERT INTO category (name) values ('Supermarket');
INSERT INTO category (name) values ('Pharmacy');
INSERT INTO category (name) values ('Others');