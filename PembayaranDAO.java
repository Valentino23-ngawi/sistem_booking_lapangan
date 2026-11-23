import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PembayaranDAO {

    // ─── Simpan pembayaran baru ───────────────────────────────────────────────
    public boolean simpan(int idBooking, String metode,
                           double jumlah, String bukti) {
        String sql = "INSERT INTO pembayaran "
                   + "(metode_pembayaran, jumlah_bayar, bukti_pembayaran, status_pembayaran, id_booking) "
                   + "VALUES (?, ?, ?, 'Menunggu Verifikasi', ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, metode);
            ps.setDouble(2, jumlah);
            ps.setString(3, bukti);
            ps.setInt(4, idBooking);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[ERROR] Simpan pembayaran: " + e.getMessage());
        }
        return false;
    }

    // ─── Semua pembayaran menunggu verifikasi (untuk admin) ──────────────────
    public List<Pembayaran> getMenungguVerifikasi() {
        String sql = "SELECT p.*, b.id_booking, b.total_harga, b.status_booking, "
                   + "b.tanggal_booking, b.durasi "
                   + "FROM pembayaran p "
                   + "JOIN booking b ON p.id_booking = b.id_booking "
                   + "WHERE p.status_pembayaran = 'Menunggu Verifikasi' "
                   + "ORDER BY p.id_pembayaran";
        return query(sql);
    }

    // ─── Semua pembayaran ─────────────────────────────────────────────────────
    public List<Pembayaran> getAll() {
        String sql = "SELECT p.*, b.id_booking, b.total_harga, b.status_booking, "
                   + "b.tanggal_booking, b.durasi "
                   + "FROM pembayaran p "
                   + "JOIN booking b ON p.id_booking = b.id_booking "
                   + "ORDER BY p.id_pembayaran DESC";
        return query(sql);
    }

    // ─── Verifikasi: update status pembayaran + booking ──────────────────────
    public boolean verifikasi(int idPembayaran) {
        Connection con = null;
        try {
            con = DatabaseConnection.getConnection();
            con.setAutoCommit(false);

            // 1. Update status pembayaran
            String sqlP = "UPDATE pembayaran SET status_pembayaran = 'Terverifikasi' "
                        + "WHERE id_pembayaran = ?";
            PreparedStatement psP = con.prepareStatement(sqlP);
            psP.setInt(1, idPembayaran);
            psP.executeUpdate();

            // 2. Ambil id_booking dari pembayaran
            String sqlGet = "SELECT id_booking FROM pembayaran WHERE id_pembayaran = ?";
            PreparedStatement psGet = con.prepareStatement(sqlGet);
            psGet.setInt(1, idPembayaran);
            ResultSet rs = psGet.executeQuery();
            if (!rs.next()) { con.rollback(); return false; }
            int idBooking = rs.getInt("id_booking");

            // 3. Update status booking
            String sqlB = "UPDATE booking SET status_booking = 'Dikonfirmasi' "
                        + "WHERE id_booking = ?";
            PreparedStatement psB = con.prepareStatement(sqlB);
            psB.setInt(1, idBooking);
            psB.executeUpdate();

            con.commit();
            return true;

        } catch (SQLException e) {
            System.err.println("[ERROR] Verifikasi pembayaran: " + e.getMessage());
            try { if (con != null) con.rollback(); } catch (SQLException ex) {}
        } finally {
            try { if (con != null) con.setAutoCommit(true); } catch (SQLException ex) {}
        }
        return false;
    }

    // ─── Helper ──────────────────────────────────────────────────────────────
    private List<Pembayaran> query(String sql) {
        List<Pembayaran> list = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Booking b = new Booking(
                        rs.getInt("id_booking"),
                        rs.getString("tanggal_booking"),
                        rs.getInt("durasi"),
                        rs.getDouble("total_harga"),
                        rs.getString("status_booking"));

                Pembayaran p = new Pembayaran(
                        rs.getInt("id_pembayaran"),
                        rs.getString("metode_pembayaran"),
                        rs.getDouble("jumlah_bayar"),
                        rs.getString("bukti_pembayaran"),
                        b);
                p.setStatusPembayaran(rs.getString("status_pembayaran"));
                list.add(p);
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Query pembayaran: " + e.getMessage());
        }
        return list;
    }
}
