package com.es.core.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T> {
    long save(T model);

    Optional<T> find(Long id);

    void update(T model);

    void delete(T model);

    List<T> findAll(int offset, int limit);
}
