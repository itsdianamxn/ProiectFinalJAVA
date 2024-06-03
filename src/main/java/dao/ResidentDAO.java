package dao;

import classes.Resident;
import classes.Specialization;
import db.Database;

import java.sql.ResultSet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResidentDAO {
    private final Connection con = Database.getInstance().getConnection();
    private static ResidentDAO singleton = null;
    private ResidentDAO () {}
    public static ResidentDAO getInstance()
    {
        if (singleton == null)
            singleton = new ResidentDAO();
        return singleton;
    }
    public int getMaxId() {
        String sql_statement = "SELECT MAX(resident_id) FROM residents";
        int max_id = 200;
        try (PreparedStatement pstmt = con.prepareStatement(sql_statement)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                max_id = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return max_id;
    }


    public void insert(Resident resident) throws SQLException {
        String sql_statement = "INSERT INTO residents (name, assigned, grade) VALUES (?, ?, ?)";

        if(ResidentDAO.getInstance().findByName(resident.getName()) != null)
            return;
        try (PreparedStatement pstmt = con.prepareStatement(sql_statement)) {
            pstmt.setString(1, resident.getName());
            pstmt.setBoolean(2, resident.isAssigned());
            pstmt.setInt(3, resident.getGrade());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Resident resident1 = ResidentDAO.getInstance().findByName(resident.getName());

        resident1.setSpecialization(resident.getSpecialization());
        resident1.setHospitalList(resident.getHospitalList());
        resident = resident1;

        for (Specialization specialization : resident.getSpecialization()) {
            Specialization specialization1 = SpecializationDAO.getInstance().findByName(specialization.getName());
            if (specialization1 == null) {
                SpecializationDAO.getInstance().insert(specialization);
                specialization1 = SpecializationDAO.getInstance().findByName(specialization.getName());
            }
            if(ResidentsSpecializationDAO.getInstance().findResidentSpecialization(resident, specialization1) == null)
                ResidentsSpecializationDAO.getInstance().insert(resident, specialization1);
        }
    }

    public Resident findById(int id) {
        String sql_statement = "SELECT * FROM residents WHERE resident_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql_statement)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Resident(id, rs.getString("name"), rs.getBoolean("assigned"), rs.getInt("grade"));
            }
            else
                return null;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Resident findByName(String name){
        String sql_statement = "SELECT * FROM residents WHERE name = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql_statement)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Resident(rs.getInt("resident_id"), name, rs.getBoolean("assigned"), rs.getInt("grade"));
            }
            else
                return null;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Resident> getAllResidents() {
        String query = "select * from specializations s join residents_specialization rs on rs.specialization_id = s.specialization_id where rs.resident_id = ?";
        List <Resident> residents = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement("select * from residents")) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Resident resident = new Resident(rs.getInt("resident_id"), rs.getString("name"), rs.getBoolean("assigned"), rs.getInt("grade"));

                //residents.add(ResidentDAO.findById(rs.getInt("resident_id")));
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setInt(1, resident.getResident_id());
                ResultSet specializations = stmt.executeQuery();
                while (specializations.next())
                {
                    Specialization s = new Specialization(specializations.getInt("specialization_id"), specializations.getString("name"));
                    resident.getSpecialization().add(s);
                }
                specializations.close();
                stmt.close();
                residents.add(resident);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return residents;
    }

}