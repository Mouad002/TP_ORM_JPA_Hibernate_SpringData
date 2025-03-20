package com.example.tp_orm_jpa_hibernate_springdata.services;

import com.example.tp_orm_jpa_hibernate_springdata.entities.Role;
import com.example.tp_orm_jpa_hibernate_springdata.entities.User;

public interface IUserService {
    User addNewUser(User user);
    Role addNewRole(Role role);
    User findUserByUserName(String userName);
    Role findRoleByRoleName(String roleName);
    void addRoleToUser(String userName, String roleName);
    User authenticate(String username, String password);

}
