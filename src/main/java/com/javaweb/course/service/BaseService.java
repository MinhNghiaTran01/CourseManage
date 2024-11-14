package com.javaweb.course.service;

public interface BaseService<T, D, R> {

    void save(D d);

    void update(Integer id, D d);

    void delete(Integer id);

//    List<?> findAll();

    R findById(Integer id);
}
