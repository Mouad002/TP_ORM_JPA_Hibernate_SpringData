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

    }
}
