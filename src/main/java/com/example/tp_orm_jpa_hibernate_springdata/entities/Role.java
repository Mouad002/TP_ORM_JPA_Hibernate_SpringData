package com.example.tp_orm_jpa_hibernate_springdata.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;
    private String desc;
    @Column(unique = true, length = 20)
    private String roleName;
    @ToString.Exclude
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "USERS_ROLES")
    private List<User> users = new ArrayList<>();
}
