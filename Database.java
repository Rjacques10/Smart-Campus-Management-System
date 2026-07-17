import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Gère la connexion unique vers la base SQLite pour toute l'application.
 * On garde une seule Connection ouverte pendant toute la durée du programme
 * (pour un petit programme console, c'est plus simple que d'ouvrir/fermer
 * une connexion à chaque opération).
 */
public class Database {
    private static Connection connection;
    private static final String DB_FILE = "campus.db";

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Charge explicitement le pilote JDBC SQLite.
                // (Optionnel avec les pilotes récents, mais ça rend l'intention claire.)
                Class.forName("org.sqlite.JDBC");

                // "jdbc:sqlite:campus.db" -> crée le fichier campus.db s'il n'existe pas encore,
                // dans le dossier depuis lequel le programme est lancé.
                connection = DriverManager.getConnection("jdbc:sqlite:" + DB_FILE);

                // Active la vérification des clés étrangères (désactivée par défaut sur SQLite).
                try (Statement pragma = connection.createStatement()) {
                    pragma.execute("PRAGMA foreign_keys = ON");
                }

                createTables();
            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException("Impossible de se connecter à la base de données", e);
            }
        }
        return connection;
    }

    private static void createTables() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS students (
                    student_id     TEXT PRIMARY KEY,
                    name           TEXT NOT NULL,
                    email          TEXT,
                    contact_number TEXT,
                    department     TEXT,
                    fees_paid      REAL DEFAULT 0
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS courses (
                    id            INTEGER PRIMARY KEY AUTOINCREMENT,
                    student_id    TEXT NOT NULL,
                    name          TEXT NOT NULL,
                    code          TEXT NOT NULL,
                    credit_hours  INTEGER NOT NULL,
                    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS staff (
                    staff_id       TEXT PRIMARY KEY,
                    name           TEXT NOT NULL,
                    email          TEXT,
                    contact_number TEXT,
                    role           TEXT,
                    department     TEXT
                )
            """);
        }
    }

    /** À appeler une seule fois, juste avant la fin du programme. */
    public static void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la fermeture de la base : " + e.getMessage());
        }
    }
}
