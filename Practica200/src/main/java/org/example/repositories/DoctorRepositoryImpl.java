package org.example.repositories;

import org.example.dao.DoctorDaoImpl;
import org.example.dao.PatientDaoImpl;
import org.example.idao.DoctorDao;
import org.example.idao.PatientDao;
import org.example.irepositories.DoctorRepository;
import org.example.models.Doctor;
import org.example.models.Patient;

import java.util.List;

public class DoctorRepositoryImpl implements DoctorRepository {

    private DoctorDao doctorDao = new DoctorDaoImpl();
    private PatientDao patientDao = new PatientDaoImpl();


    @Override
    public Doctor getDoctor(int doctorId) {

        Doctor doctor = doctorDao.getDoctor(doctorId);

        if (doctor == null) {
            return null;
        }

        List<Patient> patients = patientDao.getPatientsByDoctorId(doctorId);

        doctor.setAttendedPatients(patients);

        return doctor;
    }
}