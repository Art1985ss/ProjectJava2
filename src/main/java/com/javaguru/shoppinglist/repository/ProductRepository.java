package com.javaguru.shoppinglist.repository;

import com.javaguru.shoppinglist.entity.Product;

public abstract class ProductRepository implements RepositoryTemplate<Product> {
    public abstract boolean existsByName(String name);
}
