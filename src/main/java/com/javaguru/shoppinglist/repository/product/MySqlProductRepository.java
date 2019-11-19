package com.javaguru.shoppinglist.repository.product;

import com.javaguru.shoppinglist.entity.Product;
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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Profile({"mysql"})
public class MySqlProductRepository implements ProductRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MySqlProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Optional<Product> save(Product product) {
        String query = "insert into Products (name, category, price, discount, description) value (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getCategory());
            preparedStatement.setBigDecimal(3, product.getPrice());
            preparedStatement.setBigDecimal(4, product.getDiscount());
            preparedStatement.setString(5, product.getDescription());
            return preparedStatement;
        }, keyHolder);
        product.setId(keyHolder.getKey().longValue());
        return Optional.of(product);
    }

    @Override
    public Optional<Product> findById(Long id) {
        String query = "select id, name, price, discount, category, description from products where id = ?";
        List<Product> productList = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Product.class), id);
        if (!productList.isEmpty()) {
            return Optional.ofNullable(productList.get(0));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Product> findByName(String name) {
        String query = "select id, name, price, discount, category, description from products where name = ?";
        List<Product> productList = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Product.class), name);
        if (!productList.isEmpty()) {
            return Optional.ofNullable(productList.get(0));
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<Product>> findAll() {
        String query = "select id, name, price, discount, category, description from products limit 100";
        List<Product> productList = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Product.class));
        return Optional.ofNullable(productList);
    }

    @Override
    public Optional<Product> delete(Long id) {
        Optional<Product> productOptional = this.findById(id);
        if (productOptional.isEmpty()) {
            return productOptional;
        }
        String query = "delete from products where id = ?";
        int rowsDeleted = jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            return preparedStatement;
        });
        if (rowsDeleted == 0) {
            return Optional.empty();
        }
        return productOptional;
    }

    @Override
    public boolean existsByName(String name) {
        String query = "select case when count(*) > 0 then true else false end from products where name = ?";
        return jdbcTemplate.queryForObject(query, Boolean.class, name);
    }
}
