package com.javaguru.shoppinglist.repository.shoppingcart;

import com.javaguru.shoppinglist.entity.Product;
import com.javaguru.shoppinglist.entity.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Profile({"mysql"})
public class MysqlShoppingCartRepository implements ShoppingCartRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MysqlShoppingCartRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<ShoppingCart> save(ShoppingCart shoppingCart) {
        String query = "insert into shopping_carts (name) value (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, shoppingCart.getName());
            return preparedStatement;
        }, keyHolder);
        shoppingCart.setId(keyHolder.getKey().longValue());
        if (shoppingCart.getProductList().isEmpty()) {
            return Optional.of(shoppingCart);
        }
        return Optional.of(this.saveProductsFromShoppingCart(shoppingCart));
    }

    @Override
    public Optional<ShoppingCart> findById(Long id) {
        String query = "select id, name from shopping_carts where id = ?";
        List<ShoppingCart> shoppingCartList = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(ShoppingCart.class), id);
        if (shoppingCartList.isEmpty()) {
            return Optional.empty();
        }
        ShoppingCart shoppingCart = shoppingCartList.get(0);
        return Optional.of(this.fillProductListInCart(shoppingCart));
    }

    @Override
    public Optional<ShoppingCart> findByName(String name) {
        String query = "select id, name from shopping_carts where name = ?";
        List<ShoppingCart> shoppingCartList = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(ShoppingCart.class), name);
        if (shoppingCartList.isEmpty()) {
            return Optional.empty();
        }
        ShoppingCart shoppingCart = shoppingCartList.get(0);
        return Optional.of(this.fillProductListInCart(shoppingCart));
    }

    @Override
    public Optional<List<ShoppingCart>> findAll() {
        String query = "select id, name from shopping_carts limit 100";
        List<ShoppingCart> shoppingCartList = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(ShoppingCart.class));
        return Optional.ofNullable(shoppingCartList);
    }

    @Override
    public Optional<ShoppingCart> delete(Long id) {
        Optional<ShoppingCart> shoppingCartOptional = this.findById(id);
        if (shoppingCartOptional.isEmpty()) {
            return shoppingCartOptional;
        }
        this.deleteProductsFromCart(shoppingCartOptional.get());
        String query = "delete from shopping_carts where id = ?";
        int rowsDeleted = jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            return preparedStatement;
        });
        if (rowsDeleted == 0) {
            return Optional.empty();
        }
        return shoppingCartOptional;
    }

    @Override
    public Optional<ShoppingCart> update(ShoppingCart shoppingCart) {
        this.deleteProductsFromCart(shoppingCart);
        this.saveProductsFromShoppingCart(shoppingCart);
        return Optional.of(shoppingCart);
    }

    private ShoppingCart fillProductListInCart(ShoppingCart shoppingCart) {
        String query = "select p.id, p.name, p.price, p.discount, p.category, p.description from products p\n" +
                "inner join productList plist on plist.product_id = p.id\n" +
                "where plist.shopping_cart_id = ?";
        List<Product> productList =
                jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Product.class), shoppingCart.getId());
        if (productList.isEmpty()) {
            return shoppingCart;
        }
        productList.forEach(shoppingCart::addProduct);
        return shoppingCart;
    }

    private ShoppingCart saveProductsFromShoppingCart(ShoppingCart shoppingCart) {
        String query = "insert into productList (shopping_cart_id, product_id) value ( ?, ?)";
        shoppingCart.getProductList().forEach(product -> jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, shoppingCart.getId());
            preparedStatement.setLong(2, product.getId());
            return preparedStatement;
        }));
        return shoppingCart;
    }

    private void deleteProductsFromCart(ShoppingCart shoppingCart) {
        String query = "delete from productList where shopping_cart_id = ?";
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, shoppingCart.getId());
            return preparedStatement;
        });
    }
}
