public class Booking {

    private int      idBooking;
    private String   tanggalBooking;
    private int      durasi;
    private double   totalHarga;
    private String   statusBooking;

    private Customer customer;
    private Lapangan lapangan;
    private Jadwal   jadwal;

    public Booking(int idBooking, String tanggalBooking, int durasi,
                   Customer customer, Lapangan lapangan, Jadwal jadwal) {
        this.idBooking      = idBooking;
        this.tanggalBooking = tanggalBooking;
        this.durasi         = durasi;
        this.customer       = customer;
        this.lapangan       = lapangan;
        this.jadwal         = jadwal;
        this.statusBooking  = "Menunggu Pembayaran";
        hitungTotal();
    }

    // Konstruktor untuk load dari DB (tanpa object relasi lengkap)
    public Booking(int idBooking, String tanggalBooking, int durasi,
                   double totalHarga, String statusBooking) {
        this.idBooking      = idBooking;
        this.tanggalBooking = tanggalBooking;
        this.durasi         = durasi;
        this.totalHarga     = totalHarga;
        this.statusBooking  = statusBooking;
    }

    public void hitungTotal() {
        this.totalHarga = durasi * lapangan.getHargaPerJam();
    }

    public void konfirmasiBooking() { this.statusBooking = "Dikonfirmasi"; }
    public void batalBooking()      { this.statusBooking = "Dibatalkan";   }

    // Getters & Setters
    public int      getIdBooking()      { return idBooking;      }
    public String   getTanggalBooking() { return tanggalBooking; }
    public int      getDurasi()         { return durasi;         }
    public double   getTotalHarga()     { return totalHarga;     }
    public String   getStatusBooking()  { return statusBooking;  }
    public Customer getCustomer()       { return customer;       }
    public Lapangan getLapangan()       { return lapangan;       }
    public Jadwal   getJadwal()         { return jadwal;         }

    public void setStatusBooking(String s) { this.statusBooking = s; }
    public void setCustomer(Customer c)    { this.customer = c;      }
    public void setLapangan(Lapangan l)    { this.lapangan = l;      }
    public void setJadwal(Jadwal j)        { this.jadwal   = j;      }
}
