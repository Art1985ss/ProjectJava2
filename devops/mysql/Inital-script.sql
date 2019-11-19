#drop database shopping_list;
create database if not exists shopping_list default character set utf8;
use shopping_list;
CREATE TABLE IF NOT EXISTS Products (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) UNIQUE NOT NULL,
    #category ENUM('Fruits', 'Vegetables', 'Electronics', 'Toys') NOT NULL,
    category VARCHAR(50) NOT NULL,
    price DECIMAL(10 , 3 ) NOT NULL,
    discount DECIMAL(10 , 3 ) NOT NULL DEFAULT '0.00',
    description VARCHAR(100) DEFAULT 'No description',
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)  ENGINE=INNODB AUTO_INCREMENT=1;

CREATE TABLE IF NOT EXISTS Shopping_carts (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) UNIQUE NOT NULL,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)  ENGINE=INNODB AUTO_INCREMENT=1;

CREATE TABLE IF NOT EXISTS product_list (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    shopping_cart_id BIGINT,
    product_id BIGINT,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (shopping_cart_id)
        REFERENCES shopping_carts (id)
        ON DELETE CASCADE,
    FOREIGN KEY (product_id)
        REFERENCES products (id)
        ON DELETE CASCADE
)  ENGINE=INNODB;
