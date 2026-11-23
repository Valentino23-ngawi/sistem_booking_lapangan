public class Jadwal {

    private int    idJadwal;
    private String tanggal;
    private String jamMulai;
    private String jamSelesai;
    private String status;

    public Jadwal(int idJadwal, String tanggal,
                  String jamMulai, String jamSelesai, String status) {
        this.idJadwal   = idJadwal;
        this.tanggal    = tanggal;
        this.jamMulai   = jamMulai;
        this.jamSelesai = jamSelesai;
        this.status     = status;
    }

    public boolean cekKetersediaan() {
        return status.equalsIgnoreCase("Tersedia");
    }

    public void tampilJadwal() {
        System.out.println(idJadwal + " | " + tanggal
                + " | " + jamMulai + " - " + jamSelesai
                + " | " + status);
    }

    // Getters & Setters
    public int    getIdJadwal()   { return idJadwal;   }
    public String getTanggal()    { return tanggal;    }
    public String getJamMulai()   { return jamMulai;   }
    public String getJamSelesai() { return jamSelesai; }
    public String getStatus()     { return status;     }

    public void setStatus(String status) { this.status = status; }
}
