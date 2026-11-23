import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LapanganDAO {

    public List<Lapangan> getAll() {
        List<Lapangan> list = new ArrayList<>();
        String sql = "SELECT * FROM lapangan ORDER BY id_lapangan";

        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Get lapangan: " + e.getMessage());
        }

        return list;
    }

    public Lapangan getById(int id) {
        String sql = "SELECT * FROM lapangan WHERE id_lapangan=?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1,id);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return map(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean tambah(String namaLapangan,
                          String jenisOlahraga,
                          double hargaPerJam) {

        String sql =
        "INSERT INTO lapangan(nama_lapangan,jenis_olahraga,harga_per_jam,status) VALUES(?,?,?,'Tersedia')";

        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setString(1,namaLapangan);
            ps.setString(2,jenisOlahraga);
            ps.setDouble(3,hargaPerJam);

            return ps.executeUpdate() > 0;

        }catch(SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    public boolean update(int id,
                          String namaLapangan,
                          String jenisOlahraga,
                          double hargaPerJam,
                          String status){

        String sql =
        "UPDATE lapangan SET nama_lapangan=?,jenis_olahraga=?,harga_per_jam=?,status=? WHERE id_lapangan=?";

        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setString(1,namaLapangan);
            ps.setString(2,jenisOlahraga);
            ps.setDouble(3,hargaPerJam);
            ps.setString(4,status);
            ps.setInt(5,id);

            return ps.executeUpdate() > 0;

        }catch(SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    public boolean hapus(int id){

        String sql =
        "DELETE FROM lapangan WHERE id_lapangan=?";

        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1,id);

            return ps.executeUpdate() > 0;

        }catch(SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    private Lapangan map(ResultSet rs)
            throws SQLException {

        return new Lapangan(
            rs.getInt("id_lapangan"),
            rs.getString("nama_lapangan"),
            rs.getString("jenis_olahraga"),
            rs.getDouble("harga_per_jam"),
            rs.getString("status")
        );
    }
}