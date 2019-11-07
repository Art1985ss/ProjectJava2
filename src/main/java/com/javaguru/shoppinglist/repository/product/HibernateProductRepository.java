package com.javaguru.shoppinglist.repository.product;

import com.javaguru.shoppinglist.entity.Product;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
@Profile({"hibernate"})
public class HibernateProductRepository extends ProductRepository {
    private SessionFactory sessionFactory;

    @Autowired
    public HibernateProductRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Product> add(Product product) {
        sessionFactory.getCurrentSession().save(product);
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
