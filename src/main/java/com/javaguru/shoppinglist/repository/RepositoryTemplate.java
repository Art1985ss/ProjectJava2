package com.javaguru.shoppinglist.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RepositoryTemplate<T> {
    Optional<T> save(T t);
    Optional<T> findById(Long id);
    Optional<T> findByName(String name);
    Optional<List<T>> findAll();
    Optional<T> delete(Long id);
}
