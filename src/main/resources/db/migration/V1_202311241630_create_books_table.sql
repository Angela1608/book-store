CREATE TABLE IF NOT EXISTS books (

    id          BIGINT         NOT NULL,
    title       VARCHAR(255)   NOT NULL,
    author      VARCHAR(255)   NOT NULL,
    isbn        VARCHAR(255)   NOT NULL,
    price       DECIMAL(19, 2) NOT NULL,
    description VARCHAR(255)   NOT NULL,
    coverImage  VARCHAR(255)   NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT unique_isbn UNIQUE (isbn)
    );
