CREATE TABLE categories (
    id   BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),
    CONSTRAINT pk_categories PRIMARY KEY (id),
    CONSTRAINT uk_categories_name UNIQUE (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE books (
    id          BIGINT NOT NULL AUTO_INCREMENT,
    uuid        BINARY(16) NOT NULL,
    title       VARCHAR(255) NOT NULL,
    author      VARCHAR(255) NOT NULL,
    isbn        VARCHAR(255) NOT NULL,
    category_id BIGINT,
    created_at  DATETIME NOT NULL,
    updated_at  DATETIME NOT NULL,
    CONSTRAINT pk_books PRIMARY KEY (id),
    CONSTRAINT uk_books_uuid UNIQUE (uuid),
    CONSTRAINT uk_books_isbn UNIQUE (isbn),
    CONSTRAINT fk_books_categories FOREIGN KEY (category_id)
        REFERENCES categories(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE,
        index idx_books_category_id (category_id),
        index idx_books_author (author)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;