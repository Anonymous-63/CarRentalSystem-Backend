package com.anonymous63.crs.services;

import java.util.List;

public interface CrudService<T, ID> {
    T save(T entity);

    T update(ID id, T entity);

    T findById(ID id);

    List<T> findAll();

    List<T> enable(List<ID> ids);

    List<T> disable(List<ID> ids);

    List<T> delete(List<ID> ids);

    void deleteAll();
}
