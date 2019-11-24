package com.javaguru.shoppinglist.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "shopping_carts")
public class ShoppingCart {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "product_list",
            joinColumns = @JoinColumn(name = "shopping_cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> productList;

    public ShoppingCart(){
        productList = new ArrayList<>();
    }

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

    public Optional<Product> removeProduct(Product product) {
        if (productList.remove(product)) {
            return Optional.of(product);
        }
        return Optional.empty();
    }

    public Optional<BigDecimal> getPriceTotal() {
        if (productList.isEmpty()) return Optional.empty();
        BigDecimal totalPrice = productList.stream().map(Product::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        return Optional.ofNullable(totalPrice);
    }

    @Override
    public String toString() {
        String text = "ShoppingCart{" +
                "id=" + id +
                ", name='" + name + "}\n" +
                "Products in shopping cart : ";
        if (!productList.isEmpty()) {
            text += "\n";
            text += productList.stream().map(product -> product.toString() + "\n").collect(Collectors.joining());
            text += String.format("Total price : %.2f", this.getPriceTotal().orElse(new BigDecimal("0")));
        }else{
            text += "none";
        }
        return text;
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
