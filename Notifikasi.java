import java.util.Date;

public class Notifikasi {
    private int idNotifikasi;
    private String pesan;
    private String status;
    private Date tanggal; // Tambahan Baru

    public Notifikasi() {}

    public Date getTanggal() { return tanggal; }
    public void setTanggal(Date tanggal) { this.tanggal = tanggal; }

    public int getIdNotifikasi() { return idNotifikasi; }
    public void setIdNotifikasi(int idNotifikasi) { this.idNotifikasi = idNotifikasi; }
    public String getPesan() { return pesan; }
    public void setPesan(String pesan) { this.pesan = pesan; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}