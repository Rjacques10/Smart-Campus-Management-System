import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Couche d'accès aux données pour les étudiants.
 * Toute la logique SQL est ici : le reste du programme (Main.java) ne devrait
 * jamais écrire de SQL directement, il appelle juste ces méthodes.
 */
public class StudentRepository {

    public boolean existsById(String studentId) {
        String sql = "SELECT 1 FROM students WHERE student_id = ?";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {
            ps.setString(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la vérification de l'étudiant", e);
        }
    }

    public void save(Student student) {
        String sql = "INSERT INTO students (student_id, name, email, contact_number, department, fees_paid) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {
            ps.setString(1, student.getStudentID());
            ps.setString(2, student.getName());
            ps.setString(3, student.getEmail());
            ps.setString(4, student.getContactNumber());
            ps.setString(5, student.getDepartment());
            ps.setDouble(6, student.getFeesPaid());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement de l'étudiant", e);
        }
    }

    public void update(Student student) {
        String sql = "UPDATE students SET name = ?, email = ?, contact_number = ?, department = ?, fees_paid = ? " +
                     "WHERE student_id = ?";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {
            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setString(3, student.getContactNumber());
            ps.setString(4, student.getDepartment());
            ps.setDouble(5, student.getFeesPaid());
            ps.setString(6, student.getStudentID());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de l'étudiant", e);
        }
    }

    public void delete(String studentId) {
        // Grâce à "ON DELETE CASCADE" + PRAGMA foreign_keys=ON, les cours de
        // l'étudiant sont automatiquement supprimés avec lui.
        String sql = "DELETE FROM students WHERE student_id = ?";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {
            ps.setString(1, studentId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression de l'étudiant", e);
        }
    }

    public Student findById(String studentId) {
        String sql = "SELECT * FROM students WHERE student_id = ?";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {
            ps.setString(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Student student = mapRowToStudent(rs);
                    loadCourses(student);
                    return student;
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche de l'étudiant", e);
        }
    }

    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY name";
        Connection conn = Database.getConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Student student = mapRowToStudent(rs);
                loadCourses(student);
                students.add(student);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du chargement des étudiants", e);
        }
        return students;
    }

    public void addCourse(String studentId, Course course) {
        String sql = "INSERT INTO courses (student_id, name, code, credit_hours) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {
            ps.setString(1, studentId);
            ps.setString(2, course.getName());
            ps.setString(3, course.getCode());
            ps.setInt(4, course.getCreditHours());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout du cours", e);
        }
    }

    public void removeCourse(String studentId, String courseCode) {
        String sql = "DELETE FROM courses WHERE student_id = ? AND code = ?";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {
            ps.setString(1, studentId);
            ps.setString(2, courseCode);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du cours", e);
        }
    }

    public void clearCourses(String studentId) {
        String sql = "DELETE FROM courses WHERE student_id = ?";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {
            ps.setString(1, studentId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression des cours", e);
        }
    }

    private void loadCourses(Student student) {
        String sql = "SELECT name, code, credit_hours FROM courses WHERE student_id = ?";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {
            ps.setString(1, student.getStudentID());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Course course = new Course(
                            rs.getString("name"),
                            rs.getString("code"),
                            rs.getInt("credit_hours")
                    );
                    student.getCourses().add(course);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du chargement des cours", e);
        }
    }

    private Student mapRowToStudent(ResultSet rs) throws SQLException {
        Student student = new Student(
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("contact_number"),
                rs.getString("student_id"),
                rs.getString("department")
        );
        student.setFeesPaid(rs.getDouble("fees_paid"));
        return student;
    }
}
