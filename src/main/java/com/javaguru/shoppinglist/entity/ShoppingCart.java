package com.javaguru.shoppinglist.entity;

import java.math.BigDecimal;
import java.util.*;

public class ShoppingCart {
    private Long id;
    private String name;
    private List<Product> productList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public Optional<List<Product>> addProduct(Product product) {
        this.productList.add(product);
        return Optional.ofNullable(productList);
    }

    public Optional<BigDecimal> getPriceTotal() {
        if (productList.isEmpty()) return Optional.empty();
        BigDecimal totalPrice = productList.stream().map(Product::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        return Optional.ofNullable(totalPrice);
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", productSet=" + productList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCart that = (ShoppingCart) o;
        return id.equals(that.id) &&
                name.equals(that.name) &&
                productList.equals(that.productList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, productList);
    }
}
