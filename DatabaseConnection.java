import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // ============================================
    // Sesuaikan konfigurasi ini dengan server Anda
    // ============================================
    private static final String HOST     = "localhost";
    private static final String PORT     = "3306";
    private static final String DATABASE = "booking_lapangan";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";  // kosongkan jika tidak ada password

    private static final String URL =
        "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE
        + "?useSSL=false&serverTimezone=Asia/Jakarta&allowPublicKeyRetrieval=true";

    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("[ERROR] Driver MySQL tidak ditemukan!");
            System.err.println("Pastikan mysql-connector-java.jar sudah ada di classpath.");
            System.exit(1);
        } catch (SQLException e) {
            System.err.println("[ERROR] Koneksi database gagal: " + e.getMessage());
            System.exit(1);
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Gagal menutup koneksi: " + e.getMessage());
        }
    }
}
