import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Couche d'accès aux données pour le staff.
 * Même principe que StudentRepository, mais plus simple : pas de cours associés.
 */
public class StaffRepository {

    public boolean existsById(String staffId) {
        String sql = "SELECT 1 FROM staff WHERE staff_id = ?";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {
            ps.setString(1, staffId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la vérification du staff", e);
        }
    }

    public void save(Staff staff) {
        String sql = "INSERT INTO staff (staff_id, name, email, contact_number, role, department) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {
            ps.setString(1, staff.getStaffID());
            ps.setString(2, staff.getName());
            ps.setString(3, staff.getEmail());
            ps.setString(4, staff.getContactNumber());
            ps.setString(5, staff.getRole());
            ps.setString(6, staff.getDepartment());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement du staff", e);
        }
    }

    public void update(Staff staff) {
        String sql = "UPDATE staff SET name = ?, email = ?, contact_number = ?, role = ?, department = ? " +
                     "WHERE staff_id = ?";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {
            ps.setString(1, staff.getName());
            ps.setString(2, staff.getEmail());
            ps.setString(3, staff.getContactNumber());
            ps.setString(4, staff.getRole());
            ps.setString(5, staff.getDepartment());
            ps.setString(6, staff.getStaffID());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour du staff", e);
        }
    }

    public void delete(String staffId) {
        String sql = "DELETE FROM staff WHERE staff_id = ?";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {
            ps.setString(1, staffId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du staff", e);
        }
    }

    public Staff findById(String staffId) {
        String sql = "SELECT * FROM staff WHERE staff_id = ?";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {
            ps.setString(1, staffId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToStaff(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche du staff", e);
        }
    }

    public List<Staff> findAll() {
        List<Staff> staffList = new ArrayList<>();
        String sql = "SELECT * FROM staff ORDER BY name";
        Connection conn = Database.getConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                staffList.add(mapRowToStaff(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du chargement du staff", e);
        }
        return staffList;
    }

    private Staff mapRowToStaff(ResultSet rs) throws SQLException {
        return new Staff(
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("contact_number"),
                rs.getString("staff_id"),
                rs.getString("role"),
                rs.getString("department")
        );
    }
}
