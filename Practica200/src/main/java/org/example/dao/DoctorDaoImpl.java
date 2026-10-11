package org.example.dao;

import org.example.idao.DoctorDao;
import org.example.models.Doctor;
import org.example.utils.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorDaoImpl implements DoctorDao{

    @Override
    public int add(Doctor doctor) {

        String query = "INSERT INTO doctors (name, lastname, dni, salary, speciality) VALUES (?, ?, ?, ?, ?)";

        int resul = 0;

        try {

            PreparedStatement ps =
                    DatabaseConnection.getInstance()
                            .getConnection()
                            .prepareStatement(query);

            ps.setString(1, doctor.getName());
            ps.setString(2, doctor.getLastname());
            ps.setString(3, doctor.getDni());
            ps.setDouble(4, doctor.getSalary());
            ps.setString(5, doctor.getSpeciality());

            resul = ps.executeUpdate();

            ps.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return resul;
    }


    @Override
    public void delete(int id) {

        String query = "DELETE FROM doctors WHERE id=?";

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
    public Doctor getDoctor(int id) {

        String query = "SELECT * FROM doctors WHERE id=?";

        Doctor doctor = null;

        try {

            PreparedStatement ps =
                    DatabaseConnection.getInstance()
                            .getConnection()
                            .prepareStatement(query);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                doctor = new Doctor(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("lastname"),
                        rs.getString("dni"),
                        rs.getDouble("salary"),
                        rs.getString("speciality")
                );
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return doctor;
    }


    @Override
    public List<Doctor> getDoctors() {

        ArrayList<Doctor> doctors =
                new ArrayList<>();

        String query = "SELECT * FROM doctors";

        try {
            PreparedStatement ps =
                    DatabaseConnection.getInstance()
                            .getConnection()
                            .prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Doctor doctor = new Doctor(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("lastname"),
                        rs.getString("dni"),
                        rs.getDouble("salary"),
                        rs.getString("speciality")
                );

                doctors.add(doctor);
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return doctors;
    }


    @Override
    public boolean update(Doctor doctor) {

        String query = "UPDATE doctors SET name=?, lastname=?, dni=?, salary=?, speciality=? WHERE id=?";

        int resul = 0;

        try {

            PreparedStatement ps =
                    DatabaseConnection.getInstance()
                            .getConnection()
                            .prepareStatement(query);

            ps.setString(1, doctor.getName());
            ps.setString(2, doctor.getLastname());
            ps.setString(3, doctor.getDni());
            ps.setDouble(4, doctor.getSalary());
            ps.setString(5, doctor.getSpeciality());
            ps.setInt(6, doctor.getId());

            resul = ps.executeUpdate();

            ps.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return resul > 0;
    }


    @Override
    public Doctor getDoctorByPatientId(int patientId) {

        String query ="SELECT d. FROM doctors d, patients WHERE d.id = p.doctor_id AND p.id = ?";

        Doctor doctor = null;

        try {

            PreparedStatement ps =
                    DatabaseConnection.getInstance()
                            .getConnection()
                            .prepareStatement(query);

            ps.setInt(1, patientId);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                doctor = new Doctor(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("lastname"),
                        rs.getString("dni"),
                        rs.getDouble("salary"),
                        rs.getString("speciality")
                );
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return doctor;
    }

}
