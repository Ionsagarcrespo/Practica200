package org.example.dao;

import org.example.idao.PatientDao;
import org.example.models.Patient;
import org.example.utils.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class PatientDaoImpl implements PatientDao {


    @Override
    public int add(Patient patient) {

        String query = "INSERT INTO patients (name, lastname, dni, age, phone, disease, doctor_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        int resul = 0;

        try {

            PreparedStatement ps =
                    DatabaseConnection.getInstance()
                            .getConnection()
                            .prepareStatement(query);

            ps.setString(1, patient.getName());
            ps.setString(2, patient.getLastname());
            ps.setString(3, patient.getDni());
            ps.setInt(4, patient.getAge());
            ps.setString(5, patient.getPhone());
            ps.setString(6, patient.getDisease());

            if (patient.getDoctor() != null) {

                ps.setInt(
                        7,
                        patient.getDoctor().getId()
                );

            } else {

                ps.setNull(7, Types.INTEGER);
            }

            resul = ps.executeUpdate();

            ps.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return resul;
    }
    

    @Override
    public void delete(int id) {

        String query = "DELETE FROM patients WHERE id=?";

        try {

            PreparedStatement ps =
                    DatabaseConnection.getInstance()
                            .getConnection()
                            .prepareStatement(query);

            ps.setInt(1, id);

            ps.executeUpdate();

            ps.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }


    @Override
    public Patient getPatient(int id) {

        String query = "SELECT * FROM patients WHERE id=?";

        Patient patient = null;

        try {

            PreparedStatement ps =
                    DatabaseConnection.getInstance()
                            .getConnection()
                            .prepareStatement(query);

            ps.setInt(1, id);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                patient = new Patient(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("lastname"),
                        rs.getString("dni"),
                        rs.getInt("age"),
                        rs.getString("phone"),
                        rs.getString("disease")
                );
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return patient;
    }


    @Override
    public List<Patient> getPatients() {

        ArrayList<Patient> patients =
                new ArrayList<>();

        String query = "SELECT * FROM patients";

        try {

            PreparedStatement ps =
                    DatabaseConnection.getInstance()
                            .getConnection()
                            .prepareStatement(query);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                Patient patient = new Patient(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("lastname"),
                        rs.getString("dni"),
                        rs.getInt("age"),
                        rs.getString("phone"),
                        rs.getString("disease")
                );

                patients.add(patient);
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return patients;
    }


    @Override
    public boolean update(Patient patient) {

        String query = "UPDATE patients SET name=?, lastname=?, dni=?, age=?, phone=?, disease=?, doctor_id=? WHERE id=?";

        int resul = 0;

        try {

            PreparedStatement ps =
                    DatabaseConnection.getInstance()
                            .getConnection()
                            .prepareStatement(query);

            ps.setString(1, patient.getName());
            ps.setString(2, patient.getLastname());
            ps.setString(3, patient.getDni());
            ps.setInt(4, patient.getAge());
            ps.setString(5, patient.getPhone());
            ps.setString(6, patient.getDisease());

            if (patient.getDoctor() != null) {

                ps.setInt(
                        7,
                        patient.getDoctor().getId()
                );

            } else {

                ps.setNull(7, Types.INTEGER);
            }

            ps.setInt(8, patient.getId());

            resul =
                    ps.executeUpdate();

            ps.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return resul > 0;
    }


    @Override
    public List<Patient> getPatientsByDoctorId(int doctorId) {

        ArrayList<Patient> patients =
                new ArrayList<>();

        String query = "SELECT * FROM patients WHERE doctor_id=?";

        try {

            PreparedStatement ps =
                    DatabaseConnection.getInstance()
                            .getConnection()
                            .prepareStatement(query);

            ps.setInt(1, doctorId);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                Patient patient = new Patient(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("lastname"),
                        rs.getString("dni"),
                        rs.getInt("age"),
                        rs.getString("phone"),
                        rs.getString("disease")
                );

                patients.add(patient);
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return patients;
    }
    
    
}
