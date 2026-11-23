import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NotifikasiDAO {

    public boolean simpanNotifikasi(String pesan) {
        String sql = "INSERT INTO notifikasi (pesan, status) VALUES (?, 'Belum Dibaca')";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pesan);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Notifikasi> getBelumDibaca() {
        List<Notifikasi> list = new ArrayList<>();
        String sql = "SELECT id_notifikasi, pesan, status, tanggal FROM notifikasi WHERE status = 'Belum Dibaca'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Notifikasi notif = new Notifikasi();
                notif.setIdNotifikasi(rs.getInt("id_notifikasi"));
                notif.setPesan(rs.getString("pesan"));
                notif.setStatus(rs.getString("status"));
                notif.setTanggal(rs.getTimestamp("tanggal"));

                list.add(notif);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean tandaiSemuaDibaca() {
        String sql = "UPDATE notifikasi SET status = 'Dibaca' WHERE status = 'Belum Dibaca'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}