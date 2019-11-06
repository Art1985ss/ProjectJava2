package com.javaguru.shoppinglist.repository.product;

import com.javaguru.shoppinglist.entity.Product;
import com.javaguru.shoppinglist.repository.RepositoryTemplate;

public abstract class ProductRepository implements RepositoryTemplate<Product> {
    public abstract boolean existsByName(String name);
}
