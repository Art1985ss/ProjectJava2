#drop database shoppinglist;
create database if not exists shoppingList default character set utf8;
use shoppingList;
CREATE TABLE IF NOT EXISTS Products (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) UNIQUE NOT NULL,
    category ENUM('Fruits', 'Vegitables', 'Electronics', 'Toys') NOT NULL,
    price DOUBLE(10 , 3 ) NOT NULL,
    discount DOUBLE(10 , 3 ) NOT NULL DEFAULT '0.00',
    description VARCHAR(100) DEFAULT 'No description',
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)  ENGINE=INNODB AUTO_INCREMENT=1;

CREATE TABLE IF NOT EXISTS Shopping_carts (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) UNIQUE NOT NULL,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)  ENGINE=INNODB AUTO_INCREMENT=1;

CREATE TABLE IF NOT EXISTS product_list (
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


Delimiter //
drop procedure if exists fillProducts;
create procedure fillProducts()
begin
	if	(select if(count(*) = 0, true, false) from Products) then
		insert into Products (name, category, price, discount)
        values
			("Apple",	"Fruits",		1.28,	0),
            ("Bananas",	"Fruits",		1.33,	0),
            ("TV",		"Electronics",	560,	10),
            ("Carrots",	"Vegitables",	0.86,	0);
    end if;
end
//

Delimiter //
drop procedure if exists fillCarts;
create procedure fillCarts()
begin
	if(select if(count(*) = 0, true, false) from Shopping_carts) then
		insert into Shopping_carts (name) 
        values 
			("Cart1"),
            ("Cart2"),
            ("Cart3");
    end if;
end
//

Delimiter //
drop procedure if exists addProductsToCarts;
create procedure addProductsToCarts() 
begin
	declare tableIsEmpty tinyint default 0;
    set tableIsEmpty = (select if(count(*) = 0, true, false) from product_list);
	if(tableIsEmpty) then
		insert into product_list (shopping_cart_id, product_id) 
        select cart.id, prod.id from shopping_carts cart, products prod 
        where prod.category = "Fruits";
    end if;
end
//

CREATE OR REPLACE VIEW test AS
    SELECT 
        cart.name AS 'Cart name', prod.name AS 'Product name'
    FROM
        product_list plist
            INNER JOIN
        shopping_carts cart ON cart.id = plist.shopping_cart_id
            INNER JOIN
        products prod ON prod.id = plist.product_id;

call fillProducts();
call fillCarts();
call addProductsToCarts();
SELECT 
    *
FROM
    Products;
SELECT 
    *
FROM
    Shopping_carts;
SELECT 
    *
FROM
    product_list;
SELECT 
    *
FROM
    test;

#delete from shopping_carts where name = "Cart1";
#delete from products where id = 2;

SELECT 
    p.id, p.name, p.price, p.discount, p.category, p.description
FROM
    products p
        INNER JOIN
    product_list plist ON plist.product_id = p.id
WHERE
    plist.shopping_cart_id = 2;