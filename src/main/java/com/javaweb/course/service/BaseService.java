package com.javaweb.course.service;

import java.util.List;

public interface BaseService<T,D,R> {

    void save(D d);

    void update(Integer id,D d);

    void delete(Integer id);

//    List<?> findAll();

    R findById(Integer id);
}
