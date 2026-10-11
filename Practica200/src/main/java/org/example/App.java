package org.example;

import org.example.dao.DoctorDaoImpl;
import org.example.idao.DoctorDao;
import org.example.models.Doctor;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        DoctorDao doctorDao = new DoctorDaoImpl();

        System.out.println("Lista de doctores:");
        for (Doctor doctor : doctorDao.getDoctors()) {
            System.out.println(doctor);
        }

        Doctor doctor = doctorDao.getDoctor(1);
        System.out.println("Doctor con ID 1: " + doctor);
    }
}
