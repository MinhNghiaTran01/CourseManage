package com.javaweb.course.service;

import java.util.List;

public interface BaseService<T,D,R> {

    void save(D d);

    void update(Integer id,D d);

    void delete(Integer id);

    List<R> findAll();

    R findById(Integer id);
}
