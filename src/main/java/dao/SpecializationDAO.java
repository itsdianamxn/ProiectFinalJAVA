package dao;

import classes.Specialization;
import db.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SpecializationDAO {
    private static SpecializationDAO singleton = null;
    private SpecializationDAO () {}
    public static SpecializationDAO getInstance()
    {
        if (singleton == null)
            singleton = new SpecializationDAO();
        return singleton;
    }

    private final Connection con = Database.getInstance().getConnection();
    private int getMaxSpecializationId()
    {
        String sql_stmt = "SELECT MAX(specialization_id) FROM specializations";
        int max_id = 1;
        try (PreparedStatement pstmt = con.prepareStatement(sql_stmt)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                max_id = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return max_id;
    }

    public void insert(Specialization specialization) {
        if(findByName(specialization.getName()) != null)
            return;

        String sql_stmt = "INSERT INTO specializations (specialization_id, name) VALUES (?,?)";
        int id = new SpecializationDAO().getMaxSpecializationId() + 1;
        specialization.setSpecialization_id(id);

        try (PreparedStatement pstmt = con.prepareStatement(sql_stmt)) {
            pstmt.setInt(1, specialization.getSpecialization_id());
            pstmt.setString(2, specialization.getName());
            pstmt.executeUpdate();
            findByName(specialization.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Specialization findById(int id) {
        String sql_stmt = "SELECT * FROM specializations WHERE specialization_id = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql_stmt)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Specialization(rs.getInt("specialization_id"), rs.getString("name"));
            } else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public Specialization findByName(String name) {
        String sql_stmt = "SELECT * FROM specializations WHERE name = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql_stmt)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Specialization(rs.getInt("specialization_id"), rs.getString("name"));
            } else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
