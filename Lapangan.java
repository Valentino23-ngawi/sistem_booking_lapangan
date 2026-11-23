public class Lapangan {

    private int    idLapangan;
    private String namaLapangan;
    private String jenisOlahraga;
    private double hargaPerJam;
    private String status;

    public Lapangan(int idLapangan, String namaLapangan,
                    String jenisOlahraga, double hargaPerJam, String status) {
        this.idLapangan    = idLapangan;
        this.namaLapangan  = namaLapangan;
        this.jenisOlahraga = jenisOlahraga;
        this.hargaPerJam   = hargaPerJam;
        this.status        = status;
    }

    public boolean cekKetersediaan() {
        return status.equalsIgnoreCase("Tersedia");
    }

    // Getters & Setters
    public int    getIdLapangan()    { return idLapangan;    }
    public String getNamaLapangan()  { return namaLapangan;  }
    public String getJenisOlahraga() { return jenisOlahraga; }
    public double getHargaPerJam()   { return hargaPerJam;   }
    public String getStatus()        { return status;        }

    public void setNamaLapangan(String v)  { this.namaLapangan  = v; }
    public void setJenisOlahraga(String v) { this.jenisOlahraga = v; }
    public void setHargaPerJam(double v)   { this.hargaPerJam   = v; }
    public void setStatus(String v)        { this.status        = v; }
}
