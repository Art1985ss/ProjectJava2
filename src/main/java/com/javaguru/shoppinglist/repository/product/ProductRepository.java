package com.javaguru.shoppinglist.repository.product;

import com.javaguru.shoppinglist.entity.Product;
import com.javaguru.shoppinglist.repository.RepositoryTemplate;

public interface ProductRepository extends RepositoryTemplate<Product> {
    boolean existsByName(String name);
}
