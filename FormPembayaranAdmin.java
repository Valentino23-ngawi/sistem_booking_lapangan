import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FormPembayaranAdmin extends JPanel {

    private JTable tabelPembayaran;
    private DefaultTableModel tableModel;
    private JLabel lblPreviewBukti;
    private JButton btnKonfirmasi;
    private JButton btnCancel; // Tombol baru untuk pembatalan
    private String pathFotoTerpilih = "";
    private int idBookingTerpilih = -1;
    private int idPembayaranTerpilih = -1;

    public FormPembayaranAdmin() {
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(248, 249, 250));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel lblTitle = new JLabel("Manajemen Verifikasi Pembayaran Customer");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(new Color(43, 43, 43));
        add(lblTitle, BorderLayout.NORTH);

        // Main Panel Split
        JPanel panelKonten = new JPanel(new GridLayout(1, 2, 15, 0));
        panelKonten.setOpaque(false);

        // --- SISI KIRI: TABEL DATA PEMBAYARAN ---
        JPanel panelKiri = new JPanel(new BorderLayout());
        panelKiri.setBackground(Color.WHITE);
        panelKiri.setBorder(BorderFactory.createLineBorder(new Color(230, 233, 236)));

        String[] kolom = {"ID Bayar", "ID Booking", "Total Bayar", "Status"};
        tableModel = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelPembayaran = new JTable(tableModel);
        tabelPembayaran.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabelPembayaran.setRowHeight(25);
        JScrollPane scrollTabel = new JScrollPane(tabelPembayaran);
        panelKiri.add(scrollTabel, BorderLayout.CENTER);
        panelKonten.add(panelKiri);

        // --- SISI KANAN: PREVIEW FOTO BUKTI & AKSI ---
        JPanel panelKanan = new JPanel(new BorderLayout(10, 10));
        panelKanan.setBackground(Color.WHITE);
        panelKanan.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 233, 236)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel lblDetailTitle = new JLabel("Review Bukti Transfer :");
        lblDetailTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panelKanan.add(lblDetailTitle, BorderLayout.NORTH);

        lblPreviewBukti = new JLabel("Pilih salah satu baris data untuk melihat bukti", SwingConstants.CENTER);
        lblPreviewBukti.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        lblPreviewBukti.setForeground(Color.GRAY);
        lblPreviewBukti.setBorder(BorderFactory.createLineBorder(new Color(220, 224, 230)));
        panelKanan.add(lblPreviewBukti, BorderLayout.CENTER);

        // Panel Tombol Aksi (Konfirmasi & Cancel)
        JPanel panelAksi = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelAksi.setOpaque(false);

        // Inisialisasi Tombol Cancel (Warna Merah)
        btnCancel = new JButton("Tolak / Cancel");
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setBackground(new Color(192, 57, 43)); // Merah Alizarin
        btnCancel.setEnabled(false);
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelAksi.add(btnCancel);

        // Inisialisasi Tombol Konfirmasi (Warna Hijau)
        btnKonfirmasi = new JButton("Konfirmasi Valid");
        btnKonfirmasi.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnKonfirmasi.setForeground(Color.WHITE);
        btnKonfirmasi.setBackground(new Color(46, 117, 89));
        btnKonfirmasi.setEnabled(false);
        btnKonfirmasi.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelAksi.add(btnKonfirmasi);
        
        panelKanan.add(panelAksi, BorderLayout.SOUTH);
        panelKonten.add(panelKanan);
        add(panelKonten, BorderLayout.CENTER);

        // --- LISTENERS ---
        loadDataPembayaran();

        tabelPembayaran.getSelectionModel().addListSelectionListener(e -> {
            int row = tabelPembayaran.getSelectedRow();
            if (row != -1) {
                idPembayaranTerpilih = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                idBookingTerpilih = Integer.parseInt(tableModel.getValueAt(row, 1).toString());
                String status = tableModel.getValueAt(row, 3).toString();

                ambilPathGambarDariDB(idPembayaranTerpilih);

                // Aktifkan tombol aksi hanya jika statusnya masih dalam proses verifikasi
                if (status.equalsIgnoreCase("Menunggu Verifikasi")) {
                    btnKonfirmasi.setEnabled(true);
                    btnCancel.setEnabled(true);
                } else {
                    btnKonfirmasi.setEnabled(false);
                    btnCancel.setEnabled(false);
                }
            }
        });

        btnKonfirmasi.addActionListener(e -> prosesKonfirmasiAdmin());
        btnCancel.addActionListener(e -> prosesCancelAdmin()); // Event tombol cancel
    }

    public Container getContentPane() {
        return this;
    }

    private void loadDataPembayaran() {
        tableModel.setRowCount(0);
        String sql = "SELECT id_pembayaran, id_booking, total_bayar, status FROM pembayaran ORDER BY id_pembayaran DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id_pembayaran"),
                        rs.getInt("id_booking"),
                        rs.getDouble("total_bayar"),
                        rs.getString("status")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ambilPathGambarDariDB(int idBayar) {
        String sql = "SELECT bukti_pembayaran FROM pembayaran WHERE id_pembayaran = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idBayar);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    pathFotoTerpilih = rs.getString("bukti_pembayaran");
                    
                    if (pathFotoTerpilih != null && !pathFotoTerpilih.isEmpty()) {
                        File fileGambar = new File(pathFotoTerpilih);
                        if (fileGambar.exists()) {
                            ImageIcon imgIcon = new ImageIcon(pathFotoTerpilih);
                            Image imgScale = imgIcon.getImage().getScaledInstance(320, 380, Image.SCALE_SMOOTH);
                            lblPreviewBukti.setText("");
                            lblPreviewBukti.setIcon(new ImageIcon(imgScale));
                        } else {
                            lblPreviewBukti.setIcon(null);
                            lblPreviewBukti.setText("File gambar tidak ditemukan di path: " + pathFotoTerpilih);
                        }
                    } else {
                        lblPreviewBukti.setIcon(null);
                        lblPreviewBukti.setText("Customer tidak melampirkan foto.");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void prosesKonfirmasiAdmin() {
        if (idBookingTerpilih == -1 || idPembayaranTerpilih == -1) return;

        String sqlUpdateBayar = "UPDATE pembayaran SET status = 'Dikonfirmasi' WHERE id_pembayaran = ?";
        String sqlUpdateBooking = "UPDATE booking SET status_booking = 'Dikonfirmasi' WHERE id_booking = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps1 = conn.prepareStatement(sqlUpdateBayar);
                 PreparedStatement ps2 = conn.prepareStatement(sqlUpdateBooking)) {
                
                ps1.setInt(1, idPembayaranTerpilih);
                ps1.executeUpdate();

                ps2.setInt(1, idBookingTerpilih);
                ps2.executeUpdate();

                conn.commit();
                JOptionPane.showMessageDialog(this, "Sukses mengonfirmasi pembayaran valid!");
                resetViewSetelahAksi();
            } catch (Exception ex) {
                conn.rollback();
                throw ex;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal konfirmasi: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- LOGIC BARU: PROSES CANCEL/TOLAK BUKTI TRANSFER ---
    private void prosesCancelAdmin() {
        if (idBookingTerpilih == -1 || idPembayaranTerpilih == -1) return;

        // Tampilkan dialog alasan penolakan atau konfirmasi pembatalan biasa
        int konfirmasi = JOptionPane.showConfirmDialog(this, 
                "Apakah Anda yakin ingin MENOLAK bukti pembayaran ini?\nStatus Booking Customer akan dikembalikan ke 'Menunggu Pembayaran'.", 
                "Konfirmasi Pembatalan", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            // Ubah status pembayaran jadi 'Ditolak' dan kembalikan booking ke status awal agar bisa di-upload ulang
            String sqlUpdateBayar = "UPDATE pembayaran SET status = 'Ditolak' WHERE id_pembayaran = ?";
            String sqlUpdateBooking = "UPDATE booking SET status_booking = 'Menunggu Pembayaran' WHERE id_booking = ?";

            try (Connection conn = DatabaseConnection.getConnection()) {
                conn.setAutoCommit(false);
                try (PreparedStatement ps1 = conn.prepareStatement(sqlUpdateBayar);
                     PreparedStatement ps2 = conn.prepareStatement(sqlUpdateBooking)) {
                    
                    ps1.setInt(1, idPembayaranTerpilih);
                    ps1.executeUpdate();

                    ps2.setInt(1, idBookingTerpilih);
                    ps2.executeUpdate();

                    conn.commit();
                    JOptionPane.showMessageDialog(this, "Pembayaran ditolak. Customer kini dapat mengirim ulang bukti transfer baru.");
                    resetViewSetelahAksi();
                } catch (Exception ex) {
                    conn.rollback();
                    throw ex;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Gagal membatalkan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void resetViewSetelahAksi() {
        btnKonfirmasi.setEnabled(false);
        btnCancel.setEnabled(false);
        lblPreviewBukti.setIcon(null);
        lblPreviewBukti.setText("Pilih salah satu baris data untuk melihat bukti");
        loadDataPembayaran();
    }
}