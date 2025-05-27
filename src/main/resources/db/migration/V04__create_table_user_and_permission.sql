CREATE TABLE "user" (
    id_user BIGINT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(150) NOT NULL
);

CREATE TABLE permission (
    id_permission BIGINT PRIMARY KEY,
    description VARCHAR(50) NOT NULL
);

CREATE TABLE user_permission (
    id_user BIGINT NOT NULL,
    id_permission BIGINT NOT NULL,
    PRIMARY KEY (id_user, id_permission),
    FOREIGN KEY (id_user) REFERENCES "user"(id_user),
    FOREIGN KEY (id_permission) REFERENCES permission(id_permission)
);