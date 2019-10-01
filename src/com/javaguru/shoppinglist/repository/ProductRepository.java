package com.javaguru.shoppinglist.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProductRepository {
    private Long productID = 0L;
    private Map<Long, Product> productMap = new HashMap<>();

    public Product add(Product product) {
        product.setId(productID);
        productMap.put(productID, product);
        productID++;
        return product;
    }

    public Product findById(Long id) {
        return productMap.get(id);
    }

    public Map<Long, Product> getAll() {
        return productMap;
    }
}
