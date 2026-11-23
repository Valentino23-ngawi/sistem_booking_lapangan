public class Pembayaran {

    private int    idPembayaran;
    private String metodePembayaran;
    private double jumlahBayar;
    private String buktiPembayaran;
    private String statusPembayaran;
    private Booking booking;

    public Pembayaran(int idPembayaran, String metodePembayaran,
                      double jumlahBayar, String buktiPembayaran, Booking booking) {
        this.idPembayaran     = idPembayaran;
        this.metodePembayaran = metodePembayaran;
        this.jumlahBayar      = jumlahBayar;
        this.buktiPembayaran  = buktiPembayaran;
        this.booking          = booking;
        this.statusPembayaran = "Menunggu Verifikasi";
    }

    public void prosesPembayaran() {
        this.statusPembayaran = "Menunggu Verifikasi";
    }

    public void verifikasiPembayaran() {
        this.statusPembayaran = "Terverifikasi";
        if (booking != null) booking.konfirmasiBooking();
    }

    // Getters & Setters
    public int    getIdPembayaran()     { return idPembayaran;     }
    public String getMetodePembayaran() { return metodePembayaran; }
    public double getJumlahBayar()      { return jumlahBayar;      }
    public String getBuktiPembayaran()  { return buktiPembayaran;  }
    public String getStatusPembayaran() { return statusPembayaran; }
    public Booking getBooking()         { return booking;          }

    public void setStatusPembayaran(String s) { this.statusPembayaran = s; }
    public void setBooking(Booking b)         { this.booking = b;          }
}
