package com.javaweb.course.repository;


import com.javaweb.course.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByUsername(String username);

    public User findById(int id);

    @Query("SELECT u FROM User u WHERE u.username LIKE %:username%")
    List<User> searchByUsername(@Param("username") String username);
}
