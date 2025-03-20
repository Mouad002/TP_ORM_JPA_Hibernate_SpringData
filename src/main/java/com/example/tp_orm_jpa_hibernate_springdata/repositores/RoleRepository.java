package com.example.tp_orm_jpa_hibernate_springdata.repositores;

import com.example.tp_orm_jpa_hibernate_springdata.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(String name);
}
