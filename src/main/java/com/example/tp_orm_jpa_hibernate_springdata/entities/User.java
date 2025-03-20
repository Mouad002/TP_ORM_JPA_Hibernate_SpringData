package com.example.tp_orm_jpa_hibernate_springdata.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    private String userId;
    private String userName;
    private String password;
    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();
}
