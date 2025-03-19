package com.example.tp_orm_jpa_hibernate_springdata.repositores;

import com.example.tp_orm_jpa_hibernate_springdata.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
//    @Transactional
//    @Modifying
//    @Query("update Patient p set p.score = :score where p.id = :id")
//    public void updatePatientScoreById(@Param("id") long id, @Param("score") double score);

    public Patient findByNom(String name);
}
