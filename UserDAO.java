import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // ─── Login: cari user berdasarkan email + password + tipe ───────────────
    public User login(String email, String password, String tipe) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ? AND tipe = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);
            ps.setString(3, tipe);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapUser(rs);
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Login: " + e.getMessage());
        }
        return null;
    }

    // ─── Tambah customer baru ────────────────────────────────────────────────
    public boolean tambahCustomer(String nama, String email,
                                   String password, String noHp) {
        String sql = "INSERT INTO users (nama, email, password, no_hp, tipe) "
                   + "VALUES (?, ?, ?, ?, 'customer')";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nama);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, noHp);

            return ps.executeUpdate() > 0;
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Email sudah terdaftar!");
        } catch (SQLException e) {
            System.err.println("[ERROR] Tambah customer: " + e.getMessage());
        }
        return false;
    }

    // ─── Semua customer ─────────────────────────────────────────────────────
    public List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE tipe = 'customer'";
        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Customer(
                        rs.getInt("id_user"),
                        rs.getString("nama"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("no_hp")));
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Get customers: " + e.getMessage());
        }
        return list;
    }

    // ─── Helper: ResultSet → User/Admin/Customer ────────────────────────────
    private User mapUser(ResultSet rs) throws SQLException {
        String tipe = rs.getString("tipe");
        if ("admin".equalsIgnoreCase(tipe)) {
            return new Admin(
                    rs.getInt("id_user"),
                    rs.getString("nama"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("no_hp"),
                    rs.getString("role"));
        } else {
            return new Customer(
                    rs.getInt("id_user"),
                    rs.getString("nama"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("no_hp"));
        }
    }
}
