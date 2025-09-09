package com.maverick.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.maverick.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

    @Query("select u from User u where u.username=?1")
    User getByUsername(String username);

    @Query("select count(u)>1 from User u where u.username=?1")
    boolean existsByUsername(String username);
}