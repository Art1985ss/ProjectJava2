SET AUTOCOMMIT = 0;
START TRANSACTION;
INSERT INTO Products (name, category, price, discount)
	VALUES
		("Apple",	    "Fruits",		1.28,	0),
		("Bananas",	    "Fruits",		1.33,	0),
		("TV",		    "Electronics",	560,	10),
		("Carrots",	    "Vegetables",	0.86,	0),
        ("Notebook",	"Electronics",	1150,	10),
        ("Monitor",	    "Electronics",	360,	5);
        
INSERT INTO Shopping_carts (name) 
	VALUES 
		("Cart1"),
		("Cart2"),
		("Cart3");
        
INSERT INTO product_list (shopping_cart_id, product_id) 
	SELECT 
		cart.id, prod.id
	FROM
		shopping_carts cart,
		products prod
	WHERE
		prod.category = 'Fruits'
		OR prod.category = 'Electronics'
		AND prod.price < 1000;

CREATE OR REPLACE VIEW test AS
    SELECT 
        cart.name AS 'Cart name',
        prod.name AS 'Product name',
        prod.price AS 'Product price',
        prod.discount AS 'Product discount',
        prod.category AS 'Product category',
        prod.description AS 'Product description'
    FROM
        product_list plist
            INNER JOIN
        shopping_carts cart ON cart.id = plist.shopping_cart_id
            INNER JOIN
        products prod ON prod.id = plist.product_id;
        
CREATE OR REPLACE VIEW total_cart_price_view AS
	SELECT 
		cart.name AS 'Cart name',
		SUM(prod.price - (prod.price * (prod.discount * 0.01))) AS 'Total price',
		COUNT(prod.id) AS 'Products in cart'
	FROM
		product_list plist
			INNER JOIN
		shopping_carts cart ON plist.shopping_cart_id = cart.id
			INNER JOIN
		products prod ON plist.product_id = prod.id
	GROUP BY plist.shopping_cart_id;
        
COMMIT;
SET AUTOCOMMIT = 1;


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
SELECT
	*
FROM
	total_cart_price_view;


SELECT 
    p.id, p.name, p.price, p.discount, p.category, p.description
FROM
    products p
        INNER JOIN
    product_list plist ON plist.product_id = p.id
WHERE
    plist.shopping_cart_id = 2;
