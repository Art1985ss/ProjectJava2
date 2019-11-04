package com.javaguru.shoppinglist.repository.shoppingcart;

import com.javaguru.shoppinglist.entity.ShoppingCart;
import com.javaguru.shoppinglist.repository.RepositoryTemplate;

import java.util.Optional;

public interface ShoppingCartRepository extends RepositoryTemplate<ShoppingCart> {
    Optional<ShoppingCart> update(ShoppingCart shoppingCart);
}
