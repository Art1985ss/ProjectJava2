package com.javaguru.shoppinglist.repository.shoppingcart;

import com.javaguru.shoppinglist.entity.ShoppingCart;
import com.javaguru.shoppinglist.service.validation.shoppingcart.ShoppingCartNotFoundException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Profile({"hibernate"})
@Transactional
public class HibernateShoppingCartRepository implements ShoppingCartRepository {
    private SessionFactory sessionFactory;

    @Autowired
    public HibernateShoppingCartRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public Optional<ShoppingCart> save(ShoppingCart shoppingCart) {
        sessionFactory.getCurrentSession().save(shoppingCart);
        return Optional.of(shoppingCart);
    }

    @Override
    public Optional<ShoppingCart> findById(Long id) {
        ShoppingCart shoppingCart = (ShoppingCart) sessionFactory.getCurrentSession().
                createCriteria(ShoppingCart.class).add(Restrictions.eq("id", id)).uniqueResult();
        return Optional.ofNullable(shoppingCart);
    }

    @Override
    public Optional<ShoppingCart> findByName(String name) {
        ShoppingCart shoppingCart = (ShoppingCart) sessionFactory.getCurrentSession().
                createCriteria(ShoppingCart.class).add(Restrictions.eq("name", name)).uniqueResult();
        return Optional.ofNullable(shoppingCart);
    }

    @Override
    public Optional<List<ShoppingCart>> findAll() {
        List<ShoppingCart> shoppingCarts = sessionFactory.getCurrentSession().createCriteria(ShoppingCart.class).list();
        if (shoppingCarts.isEmpty()) return Optional.empty();
        shoppingCarts = shoppingCarts.stream().distinct().collect(Collectors.toList());
        return Optional.of(shoppingCarts);
    }

    @Override
    public Optional<ShoppingCart> delete(Long id) {
        ShoppingCart shoppingCart = this.findById(id).orElseThrow(() -> new ShoppingCartNotFoundException("No such product in the database to delete"));
        sessionFactory.getCurrentSession().delete(shoppingCart);
        return Optional.ofNullable(shoppingCart);
    }

    @Override
    public Optional<ShoppingCart> update(ShoppingCart shoppingCart) {
        sessionFactory.getCurrentSession().saveOrUpdate(shoppingCart);
        return Optional.of(shoppingCart);
    }
}
