package com.javaguru.shoppinglist.repository;

import com.javaguru.shoppinglist.entity.ShoppingCart;

import java.util.Optional;

public interface ShoppingCartRepository extends RepositoryTemplate<ShoppingCart> {
    Optional<ShoppingCart> update(ShoppingCart shoppingCart);
}
