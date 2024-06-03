package dao;

import classes.Hospital;
import classes.Specialization;
import db.Database;
import org.jgrapht.alg.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HospitalSpecializationDAO {
    private final Connection con = Database.getInstance().getConnection();
    private static HospitalSpecializationDAO singleton = null;
    private HospitalSpecializationDAO () {}
    public static HospitalSpecializationDAO getInstance()
    {
        if (singleton == null)
            singleton = new HospitalSpecializationDAO();
        return singleton;
    }

    public void insert(Hospital hospital, Specialization specialization) {
        String sql = "INSERT INTO hospital_specialization (hospital_id, specialization_id) VALUES (?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, hospital.getHospital_id());
            pstmt.setInt(2, specialization.getSpecialization_id());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Pair<Hospital, Specialization> findHospitalSpecialization(Hospital hospital, Specialization specialization) throws SQLException {
        String sql = "SELECT * from hospital_specialization WHERE hospital_id = ? AND specialization_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, hospital.getHospital_id());
            pstmt.setInt(2, specialization.getSpecialization_id());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Pair<>(HospitalDAO.getInstance().findById(rs.getInt("hospital_id")),
                        SpecializationDAO.getInstance().findById(rs.getInt("specialization_id")));
            }
            else
                return null;
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
        return null;
    }

    public void delete(Hospital hospital, Specialization specialization) {
        String sql = "DELETE FROM hospital_specialization WHERE hospital_id = ? AND specialization_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, hospital.getHospital_id());
            pstmt.setInt(2, specialization.getSpecialization_id());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Specialization> gethospitalSpecializations(Hospital hospital) {
        String sql = "SELECT * from hospital_specialization WHERE hospital_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, hospital.getHospital_id());
            ResultSet rs = pstmt.executeQuery();
            List<Specialization> specializations = new ArrayList<>();
            while (rs.next()) {
                specializations.add(SpecializationDAO.getInstance().findById(rs.getInt("specialization_id")));
            }
            return specializations;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}