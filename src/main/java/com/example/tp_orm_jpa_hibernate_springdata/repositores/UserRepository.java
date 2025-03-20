package com.example.tp_orm_jpa_hibernate_springdata.repositores;

import com.example.tp_orm_jpa_hibernate_springdata.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUserName(String username);
}
