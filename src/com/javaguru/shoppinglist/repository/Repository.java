package com.javaguru.shoppinglist.repository;

import java.util.Map;
import java.util.Optional;

public interface Repository<T> {
    Optional<T> add(T t);
    Optional<T> findById(Long id);
    Optional<T> findByName(String name);
    Map<Long, T> getAll();
    Optional<T> delete(Long id);
}
