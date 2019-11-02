#drop database shoppinglist;
create database if not exists shoppingList default character set utf8;
use shoppingList;
create table if not exists Products(
	id bigint primary key auto_increment,
    name varchar(50) unique not null,
    category enum("Fruits", "Vegitables", "Electronics", "Toys") not null,
    price double(10, 3) not null,
    discount double(10, 3) not null default '0.00',
    description varchar(100) default "No description",
    created timestamp default current_timestamp
)
engine = InnoDB
Auto_increment = 1;

create table if not exists Shopping_carts(
	id bigint primary key auto_increment,
    name varchar(50) unique not null,
    created timestamp default current_timestamp
)
engine = InnoDB
Auto_increment = 1;

create table if not exists productList(
	shopping_cart_id bigint,
    product_id bigint,
    created timestamp default current_timestamp,
    foreign key(shopping_cart_id) 
		references shopping_carts(id)
        on delete cascade,
	foreign key(product_id)
		references products(id)
        on delete cascade
)
engine = InnoDB;


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
    set tableIsEmpty = (select if(count(*) = 0, true, false) from productList);
	if(tableIsEmpty) then
		insert into productList (shopping_cart_id, product_id) 
        select cart.id, prod.id from shopping_carts cart, products prod 
        where prod.category = "Fruits";
    end if;
end
//

create or replace view test as
	select cart.name as "Cart name",  prod.name as "Product name" from productList plist 
    inner join shopping_carts cart on cart.id = plist.shopping_cart_id
    inner join products prod on prod.id = plist.product_id;

call fillProducts();
call fillCarts();
call addProductsToCarts();
select * from Products;
select * from Shopping_carts;
select * from productList;
select * from test;

#delete from shopping_carts where name = "Cart1";
#delete from products where id = 2;

select p.id, p.name, p.price, p.discount, p.category, p.description from products p
	inner join productList plist on plist.product_id = p.id
    where plist.shopping_cart_id = 2;