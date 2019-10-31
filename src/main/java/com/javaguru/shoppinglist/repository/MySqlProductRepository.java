package com.javaguru.shoppinglist.repository;

import com.javaguru.shoppinglist.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Map;
import java.util.Optional;

@Repository
@Profile({"mysql"})
public class MySqlProductRepository extends ProductRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MySqlProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Optional<Product> add(Product product) {
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
        return Optional.empty();
    }

    @Override
    public Optional<Product> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public Map<Long, Product> getAll() {
        return null;
    }

    @Override
    public Optional<Product> delete(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean existsByName(String name) {
        return false;
    }
}
