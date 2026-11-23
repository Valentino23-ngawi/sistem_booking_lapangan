import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FormBookingCustomer extends JPanel {

    private JComboBox<String> cmbLapangan;
    private JComboBox<String> cmbJadwal;
    private JTextField txtDurasi;

    private LapanganDAO lapanganDAO = new LapanganDAO();
    private JadwalDAO jadwalDAO = new JadwalDAO();
    private BookingDAO bookingDAO = new BookingDAO();

    public FormBookingCustomer() {
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(248, 249, 250));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitle = new JLabel("Formulir Pengajuan Booking");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(new Color(43, 43, 43));
        add(lblTitle, BorderLayout.NORTH);

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(Color.WHITE);
        panelForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 233, 236), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.weightx = 1.0;

        JLabel lblLapangan = new JLabel("Pilih Lapangan");
        lblLapangan.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblLapangan.setForeground(new Color(100, 100, 100));
        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(lblLapangan, gbc);

        cmbLapangan = new JComboBox<>();
        cmbLapangan.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbLapangan.setBackground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 1;
        panelForm.add(cmbLapangan, gbc);

        JLabel lblJadwal = new JLabel("Pilih Jadwal");
        lblJadwal.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblJadwal.setForeground(new Color(100, 100, 100));
        gbc.gridx = 0; gbc.gridy = 2;
        panelForm.add(lblJadwal, gbc);

        cmbJadwal = new JComboBox<>();
        cmbJadwal.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbJadwal.setBackground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 3;
        panelForm.add(cmbJadwal, gbc);

        JLabel lblDurasi = new JLabel("Durasi Sewa (Jam)");
        lblDurasi.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblDurasi.setForeground(new Color(100, 100, 100));
        gbc.gridx = 0; gbc.gridy = 4;
        panelForm.add(lblDurasi, gbc);

        txtDurasi = new JTextField();
        txtDurasi.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtDurasi.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 214, 219), 1),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        gbc.gridx = 0; gbc.gridy = 5;
        panelForm.add(txtDurasi, gbc);

        add(panelForm, BorderLayout.CENTER);

        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        panelButton.setOpaque(false);

        JButton btnBooking = new JButton("Proses Booking");
        btnBooking.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnBooking.setForeground(Color.WHITE);
        btnBooking.setBackground(new Color(46, 117, 89));
        btnBooking.setFocusPainted(false);
        btnBooking.setContentAreaFilled(true);
        btnBooking.setOpaque(true);
        btnBooking.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btnBooking.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelButton.add(btnBooking);

        add(panelButton, BorderLayout.SOUTH);

        loadLapangan();
        loadJadwal();

        btnBooking.addActionListener(e -> prosesBooking());
    }

    public Container getContentPane() {
        return this;
    }

    private void loadLapangan() {
        try {
            cmbLapangan.removeAllItems();
            List<Lapangan> list = lapanganDAO.getAll();
            for (Lapangan l : list) {
                cmbLapangan.addItem(l.getIdLapangan() + " - " + l.getNamaLapangan());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadJadwal() {
        try {
            cmbJadwal.removeAllItems();
            List<Jadwal> list = jadwalDAO.getJadwalTersedia();
            for (Jadwal j : list) {
                cmbJadwal.addItem(j.getIdJadwal() + " - " + j.getTanggal() + " " + j.getJamMulai());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void prosesBooking() {
        if (txtDurasi.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harap isi durasi booking!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int idLapangan = Integer.parseInt(cmbLapangan.getSelectedItem().toString().split(" - ")[0]);
            String namaLapangan = cmbLapangan.getSelectedItem().toString().split(" - ")[1];
            int idJadwal = Integer.parseInt(cmbJadwal.getSelectedItem().toString().split(" - ")[0]);
            int durasi = Integer.parseInt(txtDurasi.getText());

            Lapangan lapangan = lapanganDAO.getById(idLapangan);
            double total = durasi * lapangan.getHargaPerJam();
            int idCustomer = 2; // Default user Budi dari database kamu

            int idBooking = bookingDAO.buat(idCustomer, idLapangan, idJadwal, 
                    java.time.LocalDate.now().toString(), durasi, total);

            if (idBooking > 0) {
                jadwalDAO.updateStatus(idJadwal, "Dibooking");
                
                // MENGIRIM NOTIFIKASI LANGSUNG KE DATABASE
                NotifikasiDAO notifDAO = new NotifikasiDAO();
                String pesanNotif = "Booking baru masuk untuk " + namaLapangan + " (ID Booking: #" + idBooking + ")";
                notifDAO.simpanNotifikasi(pesanNotif);

                JOptionPane.showMessageDialog(this, "Booking berhasil!\nTotal Pembayaran = Rp " + total);
                txtDurasi.setText("");
                loadJadwal(); 
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Gagal memproses booking: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}