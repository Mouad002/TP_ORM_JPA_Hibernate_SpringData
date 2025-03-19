package com.example.tp_orm_jpa_hibernate_springdata.repositores;

import com.example.tp_orm_jpa_hibernate_springdata.entities.Medecin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedecinRepository extends JpaRepository<Medecin, Long> {
    public Medecin findByNom(String nom);
}
