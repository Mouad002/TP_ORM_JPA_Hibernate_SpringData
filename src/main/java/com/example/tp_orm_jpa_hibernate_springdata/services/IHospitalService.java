package com.example.tp_orm_jpa_hibernate_springdata.services;

import com.example.tp_orm_jpa_hibernate_springdata.entities.Consultation;
import com.example.tp_orm_jpa_hibernate_springdata.entities.Medecin;
import com.example.tp_orm_jpa_hibernate_springdata.entities.Patient;
import com.example.tp_orm_jpa_hibernate_springdata.entities.RendezVous;

public interface IHospitalService {
    Patient savePatient(Patient patient);
    Patient findPatientById(Long id);
    Patient findPatientByNom(String name);
    Medecin saveMedecin(Medecin medecin);
    Medecin findMedecinByNom(String name);
    RendezVous saveRendezVous(RendezVous rendezVous);
    RendezVous findRendezVousById(Long id);
    Consultation saveConsultation(Consultation consultation);
}
