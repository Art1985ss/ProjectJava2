package com.javaguru.shoppinglist.repository.shoppingcart;

import com.javaguru.shoppinglist.entity.Product;
import com.javaguru.shoppinglist.entity.ShoppingCart;
import com.javaguru.shoppinglist.service.validation.shoppingcart.ShoppingCartNotFoundException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Profile({"hibernate"})
public class HibernateShoppingCartRepository implements ShoppingCartRepository {
    private SessionFactory sessionFactory;
    @Autowired
    public HibernateShoppingCartRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public Optional<ShoppingCart> add(ShoppingCart shoppingCart) {
        sessionFactory.openSession().save(shoppingCart);
        return Optional.of(shoppingCart);
    }

    @Override
    public Optional<ShoppingCart> findById(Long id) {
        ShoppingCart shoppingCart = (ShoppingCart) sessionFactory.openSession().
                createCriteria(ShoppingCart.class).add(Restrictions.eq("id", id)).uniqueResult();
        return Optional.ofNullable(shoppingCart);
    }

    @Override
    public Optional<ShoppingCart> findByName(String name) {
        ShoppingCart shoppingCart = (ShoppingCart) sessionFactory.openSession().
                createCriteria(ShoppingCart.class).add(Restrictions.eq("name", name)).uniqueResult();
        return Optional.ofNullable(shoppingCart);
    }

    @Override
    public Map<Long, ShoppingCart> getAll() {
        List<ShoppingCart> products = sessionFactory.openSession().createCriteria(ShoppingCart.class).list();
        return products.stream().collect(Collectors.toMap(ShoppingCart::getId, product -> product));
    }

    @Override
    public Optional<ShoppingCart> delete(Long id) {
        ShoppingCart shoppingCart = this.findById(id).orElseThrow(() -> new ShoppingCartNotFoundException("No such product in the database to delete"));
        sessionFactory.openSession().delete(shoppingCart);
        return Optional.ofNullable(shoppingCart);
    }

    @Override
    public Optional<ShoppingCart> update(ShoppingCart shoppingCart) {
        this.removeProducts(shoppingCart);
        this.addProducts(shoppingCart);
        return Optional.of(shoppingCart);
    }

    private void removeProducts(ShoppingCart shoppingCart){
        String query = "delete from product_list plist where plist.shopping_cart_id = :id";
        sessionFactory.openSession().createQuery(query).setParameter("id", shoppingCart.getId());
    }
    private void addProducts(ShoppingCart shoppingCart){
        StringBuilder query = new StringBuilder("insert into product_list (shopping_cart_is, product_id) values");
        for (Product product : shoppingCart.getProductList()) {
            query.append("(").append(shoppingCart.getId()).append(" , ").append(product.getId()).append("),");
        }
        query.replace(query.length() -1, query.length(), ";");
        sessionFactory.openSession().createQuery(query.toString());
    }
}
