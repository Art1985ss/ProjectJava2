package com.javaguru.shoppinglist.repository.product;

import com.javaguru.shoppinglist.entity.Product;
import com.javaguru.shoppinglist.service.validation.product.ProductNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Repository
@Profile({"hibernate"})
public class HibernateProductRepository implements ProductRepository {
    private SessionFactory sessionFactory;

    @Autowired
    public HibernateProductRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Product> add(Product product) {
        Session session = sessionFactory.getCurrentSession();
        session.save(product);
        return Optional.of(product);
    }

    @Override
    public Optional<Product> findById(Long id) {
        Product product = (Product) sessionFactory.getCurrentSession().
                createCriteria(Product.class).add(Restrictions.eq("id", id)).uniqueResult();
        return Optional.ofNullable(product);
    }

    @Override
    public Optional<Product> findByName(String name) {
        Product product = (Product) sessionFactory.getCurrentSession().
                createCriteria(Product.class).add(Restrictions.eq("name", name)).uniqueResult();
        return Optional.ofNullable(product);
    }

    @Override
    public Map<Long, Product> getAll() {
        List<Product> products = sessionFactory.getCurrentSession().createCriteria(Product.class).list();
        return products.stream().collect(Collectors.toMap(Product::getId, product -> product));
    }

    @Override
    public Optional<Product> delete(Long id) {
        Product product = this.findById(id).orElseThrow(() -> new ProductNotFoundException("No such product in the database to delete"));
        sessionFactory.getCurrentSession().delete(product);
        return Optional.ofNullable(product);
    }

    @Override
    public boolean existsByName(String name) {
        return this.findByName(name).isPresent();
    }
}
