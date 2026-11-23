# SISTEM BOOKING LAPANGAN - Versi Database (MySQL)
## Panduan Setup & Menjalankan

---

## 1. PERSIAPAN DATABASE

### A. Import database ke phpMyAdmin
1. Buka **phpMyAdmin** di browser (biasanya http://localhost/phpmyadmin)
2. Klik tab **"Import"** di bagian atas
3. Klik **"Choose File"** → pilih file `database.sql`
4. Klik **"Go"** / **"Import"**
5. Database `booking_lapangan` akan otomatis terbuat lengkap dengan data awal

---

## 2. KONFIGURASI KONEKSI DATABASE

Buka file `DatabaseConnection.java` dan sesuaikan:

```java
private static final String HOST     = "localhost";
private static final String PORT     = "3306";
private static final String DATABASE = "booking_lapangan";
private static final String USERNAME = "root";
private static final String PASSWORD = "";   // isi password MySQL Anda
```

---

## 3. DOWNLOAD DRIVER MySQL

Program Java memerlukan **MySQL Connector/J** untuk konek ke MySQL.

1. Download dari: https://dev.mysql.com/downloads/connector/j/
   - Pilih **"Platform Independent"** → download file `.zip`
2. Ekstrak → ambil file `mysql-connector-j-X.X.X.jar`
3. Simpan di folder yang sama dengan file Java (misalnya `lib/mysql-connector-j.jar`)

---

## 4. COMPILE & JALANKAN

### Menggunakan terminal / command prompt:

```bash
# Masuk ke folder proyek
cd "sistem booking lapangan java"

# Compile semua file Java (sesuaikan path .jar)
javac -cp ".;lib/mysql-connector-j.jar" *.java

# Jalankan program
java -cp ".;lib/mysql-connector-j.jar" Main
```

**Catatan untuk Linux/Mac:** gunakan `:` bukan `;`
```bash
javac -cp ".:lib/mysql-connector-j.jar" *.java
java  -cp ".:lib/mysql-connector-j.jar" Main
```

### Menggunakan IDE (IntelliJ / Eclipse / NetBeans):
1. Buat project baru → masukkan semua file `.java`
2. Tambahkan `mysql-connector-j.jar` ke **Build Path** / **Libraries**
3. Run `Main.java`

---

## 5. AKUN BAWAAN (dari database.sql)

| Tipe     | Email              | Password  |
|----------|--------------------|-----------|
| Admin    | admin@gmail.com    | admin123  |
| Customer | budi@gmail.com     | budi123   |

---

## 6. STRUKTUR FILE

```
sistem_booking_lapangan_db/
├── database.sql           ← Import ke phpMyAdmin
├── DatabaseConnection.java← Konfigurasi koneksi DB
├── User.java              ← Model User
├── Admin.java             ← Model Admin (extends User)
├── Customer.java          ← Model Customer (extends User)
├── Lapangan.java          ← Model Lapangan
├── Jadwal.java            ← Model Jadwal
├── Booking.java           ← Model Booking
├── Pembayaran.java        ← Model Pembayaran
├── Notifikasi.java        ← Model Notifikasi
├── UserDAO.java           ← CRUD tabel users
├── LapanganDAO.java       ← CRUD tabel lapangan
├── JadwalDAO.java         ← CRUD tabel jadwal
├── BookingDAO.java        ← CRUD tabel booking
├── PembayaranDAO.java     ← CRUD tabel pembayaran
├── NotifikasiDAO.java     ← CRUD tabel notifikasi
└── Main.java              ← Program utama (menu)
```

---

## 7. FITUR PROGRAM

### Menu Admin:
- Lihat semua booking
- Verifikasi pembayaran (update status booking ke "Dikonfirmasi")
- Kelola lapangan (tambah, edit, hapus)
- Kelola jadwal (tambah, hapus)
- Lihat semua customer
- Laporan pembayaran + total pendapatan

### Menu Customer:
- Lihat daftar lapangan
- Lihat jadwal tersedia
- Booking lapangan (pilih lapangan + jadwal + durasi)
- Bayar booking (pilih metode, input bukti)
- Lihat riwayat booking + status
- Notifikasi otomatis setelah booking & pembayaran

---

## 8. CATATAN PENTING

- Data tersimpan permanen di MySQL — tidak hilang saat program ditutup
- Jadwal otomatis berubah status ke "Tidak Tersedia" setelah di-booking
- Verifikasi pembayaran menggunakan **transaksi database** (rollback jika gagal)
- Password disimpan plain text — untuk produksi sebaiknya di-hash (bcrypt)
