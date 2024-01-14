CREATE TABLE IF NOT EXISTS orders (
    id INT PRIMARY KEY NOT NULL,
    date DATE NULL,
    cost INT NULL
);

CREATE TABLE IF NOT EXISTS products (
    id INT PRIMARY KEY NOT NULL,
    name VARCHAR(255) NULL,
    cost INT NULL,
    order_id INT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id)
);