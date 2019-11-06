package com.javaguru.shoppinglist.repository.shoppingcart;

import com.javaguru.shoppinglist.entity.ShoppingCart;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@Profile({"inMemory"})
public class RamShoppingCartRepository implements ShoppingCartRepository {
    private Long id = 0L;
    private Map<Long, ShoppingCart> shoppingCartMap = new HashMap<>();


    @Override
    public Optional<ShoppingCart> add(ShoppingCart shoppingCart) {
        shoppingCart.setId(id);
        shoppingCartMap.put(id, shoppingCart);
        id++;
        return Optional.of(shoppingCart);
    }

    @Override
    public Optional<ShoppingCart> findById(Long id) {
        return Optional.ofNullable(shoppingCartMap.get(id));
    }

    @Override
    public Optional<ShoppingCart> findByName(String name) {
        return shoppingCartMap.values().stream().filter(shoppingCart -> shoppingCart.getName().equals(name)).findAny();
    }

    @Override
    public Map<Long, ShoppingCart> getAll() {
        return shoppingCartMap;
    }

    @Override
    public Optional<ShoppingCart> delete(Long id) {
        return Optional.ofNullable(shoppingCartMap.remove(id));
    }

    @Override
    public Optional<ShoppingCart> update(ShoppingCart shoppingCart) {
        return Optional.of(shoppingCart);
    }
}
