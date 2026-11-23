import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    // ─── Buat booking baru, return ID yang di-generate ──────────────────────
    public int buat(int idCustomer, int idLapangan, int idJadwal,
                    String tanggalBooking, int durasi, double totalHarga) {
        String sql = "INSERT INTO booking "
                   + "(tanggal_booking, durasi, total_harga, status_booking, id_customer, id_lapangan, id_jadwal) "
                   + "VALUES (?, ?, ?, 'Menunggu Pembayaran', ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, tanggalBooking);
            ps.setInt(2, durasi);
            ps.setDouble(3, totalHarga);
            ps.setInt(4, idCustomer);
            ps.setInt(5, idLapangan);
            ps.setInt(6, idJadwal);

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) return keys.getInt(1);

        } catch (SQLException e) {
            System.err.println("[ERROR] Buat booking: " + e.getMessage());
        }
        return -1;
    }

    // ─── Semua booking (admin) ───────────────────────────────────────────────
    public List<Booking> getAll() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT b.*, "
                   + "u.nama AS nama_customer, "
                   + "l.nama_lapangan, l.jenis_olahraga, l.harga_per_jam, l.status AS status_lapangan, "
                   + "j.tanggal AS tgl_jadwal, j.jam_mulai, j.jam_selesai, j.status AS status_jadwal "
                   + "FROM booking b "
                   + "JOIN users    u ON b.id_customer  = u.id_user "
                   + "JOIN lapangan l ON b.id_lapangan  = l.id_lapangan "
                   + "JOIN jadwal   j ON b.id_jadwal    = j.id_jadwal "
                   + "ORDER BY b.id_booking DESC";
        return query(sql);
    }

    // ─── Booking milik satu customer ─────────────────────────────────────────
    public List<Booking> getByCustomer(int idCustomer) {
        String sql = "SELECT b.*, "
                   + "u.nama AS nama_customer, "
                   + "l.nama_lapangan, l.jenis_olahraga, l.harga_per_jam, l.status AS status_lapangan, "
                   + "j.tanggal AS tgl_jadwal, j.jam_mulai, j.jam_selesai, j.status AS status_jadwal "
                   + "FROM booking b "
                   + "JOIN users    u ON b.id_customer  = u.id_user "
                   + "JOIN lapangan l ON b.id_lapangan  = l.id_lapangan "
                   + "JOIN jadwal   j ON b.id_jadwal    = j.id_jadwal "
                   + "WHERE b.id_customer = ? "
                   + "ORDER BY b.id_booking DESC";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCustomer);
            return parseResultSet(ps.executeQuery());
        } catch (SQLException e) {
            System.err.println("[ERROR] Get booking by customer: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    // ─── Booking belum dibayar milik customer ────────────────────────────────
    public List<Booking> getMenungguPembayaran(int idCustomer) {
        String sql = "SELECT b.*, "
                   + "u.nama AS nama_customer, "
                   + "l.nama_lapangan, l.jenis_olahraga, l.harga_per_jam, l.status AS status_lapangan, "
                   + "j.tanggal AS tgl_jadwal, j.jam_mulai, j.jam_selesai, j.status AS status_jadwal "
                   + "FROM booking b "
                   + "JOIN users    u ON b.id_customer  = u.id_user "
                   + "JOIN lapangan l ON b.id_lapangan  = l.id_lapangan "
                   + "JOIN jadwal   j ON b.id_jadwal    = j.id_jadwal "
                   + "WHERE b.id_customer = ? AND b.status_booking = 'Menunggu Pembayaran' "
                   + "ORDER BY b.id_booking";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCustomer);
            return parseResultSet(ps.executeQuery());
        } catch (SQLException e) {
            System.err.println("[ERROR] Get booking menunggu pembayaran: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    // ─── Update status booking ───────────────────────────────────────────────
    public boolean updateStatus(int idBooking, String status) {
        String sql = "UPDATE booking SET status_booking = ? WHERE id_booking = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, idBooking);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[ERROR] Update status booking: " + e.getMessage());
        }
        return false;
    }

    // ─── Helper: jalankan query tanpa parameter ──────────────────────────────
    private List<Booking> query(String sql) {
        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            return parseResultSet(rs);
        } catch (SQLException e) {
            System.err.println("[ERROR] Query booking: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    // ─── Helper: ResultSet → List<Booking> ───────────────────────────────────
    private List<Booking> parseResultSet(ResultSet rs) throws SQLException {
        List<Booking> list = new ArrayList<>();
        while (rs.next()) {
            Customer c = new Customer(
                    rs.getInt("id_customer"),
                    rs.getString("nama_customer"),
                    "", "", "");

            Lapangan l = new Lapangan(
                    rs.getInt("id_lapangan"),
                    rs.getString("nama_lapangan"),
                    rs.getString("jenis_olahraga"),
                    rs.getDouble("harga_per_jam"),
                    rs.getString("status_lapangan"));

            Jadwal j = new Jadwal(
                    rs.getInt("id_jadwal"),
                    rs.getString("tgl_jadwal"),
                    rs.getString("jam_mulai"),
                    rs.getString("jam_selesai"),
                    rs.getString("status_jadwal"));

            Booking b = new Booking(
                    rs.getInt("id_booking"),
                    rs.getString("tanggal_booking"),
                    rs.getInt("durasi"),
                    c, l, j);

            b.setStatusBooking(rs.getString("status_booking"));

            list.add(b);
        }
        return list;
    }
}
