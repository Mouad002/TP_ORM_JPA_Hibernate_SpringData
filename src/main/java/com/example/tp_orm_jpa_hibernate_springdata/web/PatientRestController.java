package com.example.tp_orm_jpa_hibernate_springdata.web;

import com.example.tp_orm_jpa_hibernate_springdata.entities.Patient;
import com.example.tp_orm_jpa_hibernate_springdata.repositores.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PatientRestController {
    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/patients")
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }
}
