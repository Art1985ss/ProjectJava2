package com.javaguru.shoppinglist.repository.product;

import com.javaguru.shoppinglist.entity.Product;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@Profile({"inMemory"})
public class RamProductRepository extends ProductRepository {
    private Long productID = 0L;
    private Map<Long, Product> productMap = new HashMap<>();

    @Override
    public Optional<Product> add(Product product) {
        product.setId(productID);
        productMap.put(productID, product);
        productID++;
        return Optional.of(product);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(productMap.get(id));
    }

    @Override
    public Optional<Product> findByName(String name) {
        return productMap.values().stream().filter(product -> product.getName().equals(name)).findAny();
    }

    @Override
    public Map<Long, Product> getAll() {
        return productMap;
    }

    @Override
    public Optional<Product> delete(Long id) {
        return Optional.ofNullable(productMap.remove(id));
    }

    @Override
    public boolean existsByName(String name) {
        return productMap.values().stream().anyMatch(product -> product.getName().equalsIgnoreCase(name));
    }
}
