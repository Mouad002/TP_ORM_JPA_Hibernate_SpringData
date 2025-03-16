package com.example.tp_orm_jpa_hibernate_springdata;

import com.example.tp_orm_jpa_hibernate_springdata.entities.Patient;
import com.example.tp_orm_jpa_hibernate_springdata.repositores.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class TpOrmJpaHibernateSpringDataApplication implements CommandLineRunner {
    @Autowired
    private PatientRepository patientRepository;

    public static void main(String[] args) {
        SpringApplication.run(TpOrmJpaHibernateSpringDataApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // add two new patients
        Patient p1 = new Patient(0, "1", new Date(), false, 45);
        Patient p2 = Patient.builder()
                .type("2")
                .dateNaissance(new Date())
                .malade(true)
                .score(12)
                .build();
        patientRepository.save(p1);
        patientRepository.save(p2);

        // get all the patients and display them
        List<Patient> patients = patientRepository.findAll();
        patients.forEach(p -> {
            System.out.println(p);
        });

        // get a patient by id 1 and display its information
        Patient patient = patientRepository.findById(Long.valueOf(1)).get();
        System.out.println("***************");
        System.out.println(patient.getId());
        System.out.println(patient.getType());
        System.out.println(patient.getDateNaissance());
        System.out.println(patient.isMalade());
        System.out.println(patient.getScore());
        System.out.println("***************");

        // update a patient
        patientRepository.updatePatientScoreById(patient.getId(),39);

        // delete a patient
        patientRepository.delete(patient);
    }
}
