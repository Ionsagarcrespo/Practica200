package org.example.repositories;

import org.example.dao.DoctorDaoImpl;
import org.example.dao.PatientDaoImpl;
import org.example.idao.DoctorDao;
import org.example.idao.PatientDao;
import org.example.irepositories.PatientRepository;
import org.example.models.Doctor;
import org.example.models.Patient;

public class PatientRepositoryImpl implements PatientRepository {

    private final DoctorDao doctorDao = new DoctorDaoImpl();
    private final PatientDao patientDao = new PatientDaoImpl();

    @Override
    public Patient getPatient(int id) {

        Patient patient = patientDao.getPatient(id);

        if (patient == null) {
            return null;
        }

        Doctor doctor = doctorDao.getDoctorByPatientId(patient.getId());

        patient.setDoctor(doctor);

        return patient;
    }


    @Override
    public boolean isPatientAttendedByDoctor(int patientId, int doctorId) {

        Patient patient = getPatient(patientId);

        if (patient == null) {
            return false;
        }

        if (patient.getDoctor() == null) {
            return false;
        }

        return patient.getDoctor().getId() == doctorId;
    }
}