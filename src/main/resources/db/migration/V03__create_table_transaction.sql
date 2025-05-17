CREATE TABLE transaction (
    id_transaction BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    description VARCHAR(50) NOT NULL,
    due_date DATE NOT NULL,
    payment_date DATE,
    amount DECIMAL(10,2) NOT NULL,
    note VARCHAR(100),
    type VARCHAR(20) NOT NULL,
    id_category BIGINT NOT NULL,
    id_person BIGINT NOT NULL,
    FOREIGN KEY (id_category) REFERENCES category(id_category),
    FOREIGN KEY (id_person) REFERENCES person(id_person)
);