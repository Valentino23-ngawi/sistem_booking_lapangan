import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JadwalDAO {

    // ─── Semua jadwal yang tersedia ──────────────────────────────────────────
    public List<Jadwal> getJadwalTersedia() {
        List<Jadwal> list = new ArrayList<>();
        String sql = "SELECT * FROM jadwal WHERE status = 'Tersedia' ORDER BY tanggal, jam_mulai";
        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Get jadwal tersedia: " + e.getMessage());
        }
        return list;
    }

    // ─── Semua jadwal (untuk admin) ──────────────────────────────────────────
    public List<Jadwal> getAll() {
        List<Jadwal> list = new ArrayList<>();
        String sql = "SELECT * FROM jadwal ORDER BY tanggal, jam_mulai";
        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Get all jadwal: " + e.getMessage());
        }
        return list;
    }

    // ─── Cari jadwal berdasarkan ID ──────────────────────────────────────────
    public Jadwal getById(int id) {
        String sql = "SELECT * FROM jadwal WHERE id_jadwal = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        } catch (SQLException e) {
            System.err.println("[ERROR] Get jadwal by id: " + e.getMessage());
        }
        return null;
    }

    // ─── Tambah jadwal baru ──────────────────────────────────────────────────
    public boolean tambah(String tanggal, String jamMulai, String jamSelesai) {
        String sql = "INSERT INTO jadwal (tanggal, jam_mulai, jam_selesai, status) "
                   + "VALUES (?, ?, ?, 'Tersedia')";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tanggal);
            ps.setString(2, jamMulai);
            ps.setString(3, jamSelesai);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[ERROR] Tambah jadwal: " + e.getMessage());
        }
        return false;
    }

    // ─── Update status jadwal ────────────────────────────────────────────────
    public boolean updateStatus(int id, String status) {
        String sql = "UPDATE jadwal SET status = ? WHERE id_jadwal = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[ERROR] Update status jadwal: " + e.getMessage());
        }
        return false;
    }

    // ─── Hapus jadwal ────────────────────────────────────────────────────────
    public boolean hapus(int id) {
        String sql = "DELETE FROM jadwal WHERE id_jadwal = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[ERROR] Hapus jadwal: " + e.getMessage());
        }
        return false;
    }

    // ─── Helper ──────────────────────────────────────────────────────────────
    private Jadwal map(ResultSet rs) throws SQLException {
        return new Jadwal(
                rs.getInt("id_jadwal"),
                rs.getString("tanggal"),
                rs.getString("jam_mulai"),
                rs.getString("jam_selesai"),
                rs.getString("status"));
    }
}
