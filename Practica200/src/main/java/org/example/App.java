package org.example;

import org.example.dao.DoctorDaoImpl;
import org.example.dao.PatientDaoImpl;
import org.example.idao.DoctorDao;
import org.example.idao.PatientDao;
import org.example.irepositories.DoctorRepository;
import org.example.irepositories.PatientRepository;
import org.example.models.Doctor;
import org.example.models.Patient;
import org.example.repositories.DoctorRepositoryImpl;
import org.example.repositories.PatientRepositoryImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class App {

    private static final BufferedReader br =
            new BufferedReader(new InputStreamReader(System.in));

    private static final DoctorDao doctorDao = new DoctorDaoImpl();
    private static final PatientDao patientDao = new PatientDaoImpl();

    private static final DoctorRepository doctorRepository =
            new DoctorRepositoryImpl();

    private static final PatientRepository patientRepository =
            new PatientRepositoryImpl();

    public static void main(String[] args) {

        int option = -1;

        try {
            do {
                showMenu();
                System.out.print("elige una opcion: ");
                option = Integer.parseInt(br.readLine());;

                switch (option) {
                    case 1:
                        listaDotor();
                        break;

                    case 2:
                        buscarDoctor();
                        break;

                    case 3:
                        listaPacientes();
                        break;

                    case 4:
                        buscarPacientes();
                        break;

                    case 5:
                        PacAtendidos();
                        break;

                    case 0:
                        System.out.println("Saliendo del programa...");
                        break;

                    default:
                        System.out.println("Opcion no valida.");
                }

                System.out.println();

            } while (option != 0);

        } catch (IOException e) {
            System.out.println("Error al leer los datos introducidos.");
            e.printStackTrace();
        }
    }

    private static void showMenu() {
        System.out.println("===== GESTION DE DOCTORES Y PACIENTES =====");
        System.out.println("1. Mostrar todos los doctores");
        System.out.println("2. Buscar doctor por ID (con sus pacientes)");
        System.out.println("3. Mostrar todos los pacientes");
        System.out.println("4. Buscar paciente por ID (con su doctor)");
        System.out.println("5. Comprobar si un doctor ha atendido a un paciente");
        System.out.println("0. Salir");
    }

    private static void listaDotor() {
        List<Doctor> doctors = doctorDao.getDoctors();

        if (doctors == null || doctors.isEmpty()) {
            System.out.println("No hay doctores registrados.");
            return;
        }

        System.out.println("Lista de doctores:");

        for (Doctor doctor : doctors) {
            System.out.println(doctor);
        }
    }

    private static void buscarDoctor() throws IOException {
        System.out.print("ID del doctor: ");
        int id = Integer.parseInt(br.readLine());;

        Doctor doctor = doctorRepository.getDoctor(id);

        if (doctor == null) {
            System.out.print("No se ha encontrado ese doctor.");
            return;
        }

        System.out.println("Datos del doctor:");
        System.out.println(doctor);
        System.out.println("Pacientes atendidos:");

        List<Patient> patients = doctor.getAttendedPatients();

        if (patients == null || patients.isEmpty()) {
            System.out.println("Este doctor no tiene pacientes asociados.");
        } else {
            for (Patient patient : patients) {
                System.out.println(patient);
            }
        }
    }

    private static void listaPacientes() {
        List<Patient> patients = patientDao.getPatients();

        if (patients == null || patients.isEmpty()) {
            System.out.println("No hay pacientes registrados.");
            return;
        }

        System.out.println("Lista de pacientes:");

        for (Patient patient : patients) {
            System.out.println(patient);
        }
    }

    private static void buscarPacientes() throws IOException {

        System.out.print("ID del paciente: ");
        int id = Integer.parseInt(br.readLine());;

        Patient patient = patientRepository.getPatient(id);

        if (patient == null) {
            System.out.println("No se ha encontrado ese paciente.");
            return;
        }

        System.out.println("Datos del paciente:");
        System.out.println(patient);

        Doctor doctor = patient.getDoctor();

        if (doctor != null) {
            System.out.println("Doctor que lo atiende:");
            System.out.println(doctor);
        } else {
            System.out.println("Este paciente no tiene un doctor asociado.");
        }
    }

    private static void PacAtendidos()
            throws IOException {

        System.out.print("ID del paciente: ");
        int patientId = Integer.parseInt(br.readLine());

        System.out.print("ID del doctor: ");
        int doctorId = Integer.parseInt(br.readLine());

        boolean attended = patientRepository.isPatientAttendedByDoctor(patientId, doctorId);

        if (attended) {
            System.out.println("El doctor ha atendido a ese paciente.");
        } else {
            System.out.println("El doctor no ha atendido a ese paciente "
                    + "o el paciente no existe.");
        }
    }


}
