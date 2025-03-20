package com.example.tp_orm_jpa_hibernate_springdata;

import com.example.tp_orm_jpa_hibernate_springdata.entities.*;
import com.example.tp_orm_jpa_hibernate_springdata.repositores.*;
import com.example.tp_orm_jpa_hibernate_springdata.services.IHospitalService;
import com.example.tp_orm_jpa_hibernate_springdata.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class TpOrmJpaHibernateSpringDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(TpOrmJpaHibernateSpringDataApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
////        // add two new patients
////        Patient p1 = new Patient(0, "1", new Date(), false, 45,null);
////        Patient p2 = Patient.builder()
////                .type("2")
////                .dateNaissance(new Date())
////                .malade(true)
////                .score(12)
////                .build();
////        patientRepository.save(p1);
////        patientRepository.save(p2);
////
////        // get all the patients and display them
////        List<Patient> patients = patientRepository.findAll();
////        patients.forEach(p -> {
////            System.out.println(p);
////        });
////
////        // get a patient by id 1 and display its information
////        Patient patient = patientRepository.findById(Long.valueOf(1)).get();
////        System.out.println("***************");
////        System.out.println(patient.getId());
////        System.out.println(patient.getType());
////        System.out.println(patient.getDateNaissance());
////        System.out.println(patient.isMalade());
////        System.out.println(patient.getScore());
////        System.out.println("***************");
////
////        // update a patient
////        patientRepository.updatePatientScoreById(patient.getId(),39);
////
////        // delete a patient
////        patientRepository.delete(patient);
//    }
    @Bean
    CommandLineRunner start(IHospitalService hospitalService, UserService userService) {
        return args -> {

            // the block of @OneToMany @ManyToOne @OneToOne
            Stream.of("mouad", "monim", "amine")
                    .forEach(name -> {
                        Patient p = new Patient();
                        p.setNom(name);
                        p.setMalade(false);
                        p.setDateNaissance(new Date());
                        hospitalService.savePatient(p);
                    });

            Stream.of("boujmaa", "elhachmi", "said")
                    .forEach(name -> {
                        Medecin m = new Medecin();
                        m.setNom(name);
                        m.setSpecialite(Math.random() > 0.5 ? "Cardio":"Dentist");
                        m.setEmail(name+"@gmail.com");
                        hospitalService.saveMedecin(m);
                    });

            Patient p1 = hospitalService.findPatientById(1L);
            Patient p2 = hospitalService.findPatientByNom("monim");

            Medecin m = hospitalService.findMedecinByNom("elhachmi");

            RendezVous rv1 = RendezVous.builder()
                    .date(new Date())
                    .status(StatusRDV.DONE)
                    .patient(p2)
                    .medecin(m)
                    .build();
            hospitalService.saveRendezVous(rv1);

            RendezVous rv2 = hospitalService.findRendezVousById(1L);

            Consultation c = Consultation.builder()
                    .dateConsultation(new Date())
                    .rendezVous(rv2)
                    .rapport("report of the consulting service")
                    .build();
            hospitalService.saveConsultation(c);

            // the block of @ManyToMany

            User user1 = new User();
            user1.setUserName("xavier");
            user1.setPassword("X12345");
            userService.addNewUser(user1);

            User user2 = new User();
            user2.setUserName("admin1");
            user2.setPassword("V45678");
            userService.addNewUser(user2);

            Stream.of("STUDENT", "ADMIN", "USER").forEach(name -> {
                Role role = new Role();
                role.setRoleName(name);
                userService.addNewRole(role);
            });


            userService.addRoleToUser("xavier","STUDENT");
            userService.addRoleToUser("xavier","USER");
            userService.addRoleToUser("admin1","ADMIN");
            userService.addRoleToUser("admin1","USER");

            try {
                User user = userService.authenticate("xavier","X12345");
                System.out.println(user.getUserId());
                System.out.println(user.getUserName());
                System.out.println("Roles => ");
                Stream.of(user.getRoles()).forEach(role -> {
                    System.out.println(role);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
