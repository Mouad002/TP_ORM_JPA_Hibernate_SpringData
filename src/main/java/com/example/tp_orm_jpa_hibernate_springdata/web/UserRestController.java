package com.example.tp_orm_jpa_hibernate_springdata.web;

import com.example.tp_orm_jpa_hibernate_springdata.entities.User;
import com.example.tp_orm_jpa_hibernate_springdata.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {
    @Autowired
    private UserService userService;
    @GetMapping("/users/{username}")
    public User user(@PathVariable String username) {
        return userService.findUserByUserName(username);
    }
}
