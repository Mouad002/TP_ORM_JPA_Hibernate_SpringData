package com.example.tp_orm_jpa_hibernate_springdata.repositores;

import com.example.tp_orm_jpa_hibernate_springdata.entities.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {
}
