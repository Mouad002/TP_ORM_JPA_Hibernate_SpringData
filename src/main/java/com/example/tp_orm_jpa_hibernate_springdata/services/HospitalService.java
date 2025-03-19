package com.example.tp_orm_jpa_hibernate_springdata.services;

import com.example.tp_orm_jpa_hibernate_springdata.entities.Consultation;
import com.example.tp_orm_jpa_hibernate_springdata.entities.Medecin;
import com.example.tp_orm_jpa_hibernate_springdata.entities.Patient;
import com.example.tp_orm_jpa_hibernate_springdata.entities.RendezVous;
import com.example.tp_orm_jpa_hibernate_springdata.repositores.ConsultationRepository;
import com.example.tp_orm_jpa_hibernate_springdata.repositores.MedecinRepository;
import com.example.tp_orm_jpa_hibernate_springdata.repositores.PatientRepository;
import com.example.tp_orm_jpa_hibernate_springdata.repositores.RendezVousRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class HospitalService implements IHospitalService {
    private final PatientRepository patientRepository;
    private final MedecinRepository medecinRepository;
    private final RendezVousRepository rendezVousRepository;
    private final ConsultationRepository consultationRepository;

    public HospitalService(PatientRepository patientRepository,
                           MedecinRepository medecinRepository,
                           RendezVousRepository rendezVousRepository,
                           ConsultationRepository consultationRepository) {
        this.patientRepository = patientRepository;
        this.medecinRepository = medecinRepository;
        this.rendezVousRepository = rendezVousRepository;
        this.consultationRepository = consultationRepository;
    }


    @Override
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Patient findPatientById(Long id) {
        return patientRepository.findById(id).orElse(null);
    }

    @Override
    public Patient findPatientByNom(String name) {
        return patientRepository.findByNom(name);
    }

    @Override
    public Medecin saveMedecin(Medecin medecin) {
        return medecinRepository.save(medecin);
    }

    @Override
    public Medecin findMedecinByNom(String name) {
        return medecinRepository.findByNom(name);
    }

    @Override
    public RendezVous saveRendezVous(RendezVous rendezVous) {
        rendezVous.setId(UUID.randomUUID().toString());
        return rendezVousRepository.save(rendezVous);
    }

    @Override
    public RendezVous findRendezVousById(Long id) {
        return rendezVousRepository.findAll().get(0);
    }

    @Override
    public Consultation saveConsultation(Consultation consultation) {
        return consultationRepository.save(consultation);
    }
}
