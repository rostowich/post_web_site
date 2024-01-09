
CREATE TABLE users (
    id SERIAL  PRIMARY KEY,
    email VARCHAR(100) UNIQUE,
    password VARCHAR(255) NOT NULL,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL ,
    creation_date timestamp  NOT NULL
);