import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FormPembayaranCustomer extends JPanel {

    private JComboBox<String> cmbBooking;
    private JTextField txtTotalBayar;
    private JTextField txtMetodeBayar;
    private JLabel lblPreviewFoto;
    private File fileFotoTerpilih = null;

    public FormPembayaranCustomer() {
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(248, 249, 250));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitle = new JLabel("Konfirmasi Pembayaran Lapangan");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(new Color(43, 43, 43));
        add(lblTitle, BorderLayout.NORTH);

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(Color.WHITE);
        panelForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 233, 236), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.weightx = 1.0;

        JLabel lblBooking = new JLabel("Pilih ID Booking Anda");
        lblBooking.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblBooking.setForeground(new Color(100, 100, 100));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelForm.add(lblBooking, gbc);

        cmbBooking = new JComboBox<>();
        cmbBooking.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbBooking.setBackground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelForm.add(cmbBooking, gbc);

        JLabel lblMetode = new JLabel("Metode Pembayaran");
        lblMetode.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblMetode.setForeground(new Color(100, 100, 100));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelForm.add(lblMetode, gbc);

        txtMetodeBayar = new JTextField("Transfer Bank / QRIS");
        txtMetodeBayar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtMetodeBayar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 214, 219), 1),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelForm.add(txtMetodeBayar, gbc);

        JLabel lblTotal = new JLabel("Total Yang Dibayarkan (Rp)");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTotal.setForeground(new Color(100, 100, 100));
        gbc.gridx = 0;
        gbc.gridy = 4;
        panelForm.add(lblTotal, gbc);

        txtTotalBayar = new JTextField();
        txtTotalBayar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtTotalBayar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 214, 219), 1),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        gbc.gridx = 0;
        gbc.gridy = 5;
        panelForm.add(txtTotalBayar, gbc);

        JLabel lblUpload = new JLabel("Upload Bukti Transfer (Foto/Gambar)");
        lblUpload.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblUpload.setForeground(new Color(100, 100, 100));
        gbc.gridx = 0;
        gbc.gridy = 6;
        panelForm.add(lblUpload, gbc);

        JButton btnPilihFoto = new JButton("Pilih Gambar dari Galeri...");
        btnPilihFoto.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnPilihFoto.setBackground(new Color(240, 240, 240));
        btnPilihFoto.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridx = 0;
        gbc.gridy = 7;
        panelForm.add(btnPilihFoto, gbc);

        lblPreviewFoto = new JLabel("Belum ada foto terpilih", SwingConstants.CENTER);
        lblPreviewFoto.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblPreviewFoto.setForeground(Color.GRAY);
        lblPreviewFoto.setBorder(BorderFactory.createLineBorder(new Color(230, 233, 236), 1));
        lblPreviewFoto.setPreferredSize(new Dimension(150, 150));
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        panelForm.add(lblPreviewFoto, gbc);

        add(panelForm, BorderLayout.CENTER);

        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        panelButton.setOpaque(false);

        JButton btnKirim = new JButton("Kirim Bukti Pembayaran");
        btnKirim.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnKirim.setForeground(Color.WHITE);
        btnKirim.setBackground(new Color(46, 117, 89));
        btnKirim.setFocusPainted(false);
        btnKirim.setContentAreaFilled(true);
        btnKirim.setOpaque(true);
        btnKirim.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btnKirim.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelButton.add(btnKirim);

        add(panelButton, BorderLayout.SOUTH);

        // Memuat data booking dari database
        loadBookingCustomer();

        btnPilihFoto.addActionListener(e -> bukaGaleriFoto());
        btnKirim.addActionListener(e -> prosesKirimPembayaran());
    }

    public Container getContentPane() {
        return this;
    }

    private void loadBookingCustomer() {
        cmbBooking.removeAllItems();
        String sql = "SELECT id_booking, total_harga FROM booking WHERE status_booking = 'Menunggu Pembayaran'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            int jumlahData = 0;
            while (rs.next()) {
                int id = rs.getInt("id_booking");
                double total = rs.getDouble("total_harga");

                cmbBooking.addItem(id + " - Total Tagihan: Rp " + total);
                jumlahData++;
            }

            if (jumlahData == 0) {
                cmbBooking.addItem("Tidak ada tagihan booking aktif");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Gagal memuat data booking dari database!\nError: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void bukaGaleriFoto() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Pilih Foto Bukti Pembayaran");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Format Gambar (JPG, PNG)", "jpg", "jpeg", "png");
        fileChooser.setFileFilter(filter);

        int hasil = fileChooser.showOpenDialog(this);
        if (hasil == JFileChooser.APPROVE_OPTION) {
            fileFotoTerpilih = fileChooser.getSelectedFile();

            ImageIcon iconAsli = new ImageIcon(fileFotoTerpilih.getAbsolutePath());
            Image imgSkala = iconAsli.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);

            lblPreviewFoto.setText("");
            lblPreviewFoto.setIcon(new ImageIcon(imgSkala));
        }
    }

    private void prosesKirimPembayaran() {
        if (cmbBooking.getSelectedItem() == null || cmbBooking.getSelectedItem().toString().startsWith("Tidak ada")) {
            JOptionPane.showMessageDialog(this, "Tidak ada data booking yang dipilih!", "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (txtTotalBayar.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harap isi nominal total pembayaran!", "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (fileFotoTerpilih == null) {
            JOptionPane.showMessageDialog(this, "Wajib mengunggah/upload foto bukti transfer!", "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String selectedItem = cmbBooking.getSelectedItem().toString();
            int idBooking = Integer.parseInt(selectedItem.split(" - ")[0]);
            double totalBayar = Double.parseDouble(txtTotalBayar.getText());
            String pathFoto = fileFotoTerpilih.getAbsolutePath().replace("\\", "/");

            String sql = "INSERT INTO pembayaran (id_booking, total_bayar, bukti_pembayaran, status) VALUES (?, ?, ?, 'Menunggu Verifikasi')";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, idBooking);
                ps.setDouble(2, totalBayar);
                ps.setString(3, pathFoto);

                int rowsInserted = ps.executeUpdate();
                if (rowsInserted > 0) {
                    String sqlUpdateBooking = "UPDATE booking SET status_booking = 'Menunggu Verifikasi' WHERE id_booking = ?";
                    try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdateBooking)) {
                        psUpdate.setInt(1, idBooking);
                        psUpdate.executeUpdate();
                    }

                    NotifikasiDAO notifDAO = new NotifikasiDAO();
                    notifDAO.simpanNotifikasi("Pembayaran Baru masuk dari Booking ID #" + idBooking);

                    JOptionPane.showMessageDialog(this,
                            "Sukses! Bukti pembayaran berhasil terkirim ke database.\nMenunggu konfirmasi admin.");

                    txtTotalBayar.setText("");
                    lblPreviewFoto.setIcon(null);
                    lblPreviewFoto.setText("Belum ada foto terpilih");
                    fileFotoTerpilih = null;
                    loadBookingCustomer(); 
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan ke DB: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}