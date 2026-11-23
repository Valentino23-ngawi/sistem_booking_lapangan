import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class FormJadwal extends JPanel {
    private JTable table;
    private DefaultTableModel model;

    private JTextField txtTanggal;
    private JTextField txtMulai;
    private JTextField txtSelesai;

    private JadwalDAO jadwalDAO = new JadwalDAO();

    public FormJadwal() {
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(248, 249, 250));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelAtas = new JPanel(new BorderLayout(0, 10));
        panelAtas.setOpaque(false);

        JLabel lblTitle = new JLabel("Manajemen Data Jadwal");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(new Color(43, 43, 43));
        panelAtas.add(lblTitle, BorderLayout.NORTH);

        JPanel panelInput = new JPanel(new GridBagLayout());
        panelInput.setBackground(Color.WHITE);
        panelInput.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 233, 236), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.weightx = 1.0;

        JLabel lblTanggal = new JLabel("Tanggal (YYYY-MM-DD)");
        lblTanggal.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTanggal.setForeground(new Color(100, 100, 100));
        gbc.gridx = 0; gbc.gridy = 0;
        panelInput.add(lblTanggal, gbc);

        txtTanggal = createModernTextField();
        gbc.gridx = 0; gbc.gridy = 1;
        panelInput.add(txtTanggal, gbc);

        JLabel lblMulai = new JLabel("Jam Mulai (HH:MM)");
        lblMulai.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblMulai.setForeground(new Color(100, 100, 100));
        gbc.gridx = 1; gbc.gridy = 0;
        panelInput.add(lblMulai, gbc);

        txtMulai = createModernTextField();
        gbc.gridx = 1; gbc.gridy = 1;
        panelInput.add(txtMulai, gbc);

        JLabel lblSelesai = new JLabel("Jam Selesai (HH:MM)");
        lblSelesai.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblSelesai.setForeground(new Color(100, 100, 100));
        gbc.gridx = 0; gbc.gridy = 2;
        panelInput.add(lblSelesai, gbc);

        txtSelesai = createModernTextField();
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        panelInput.add(txtSelesai, gbc);

        panelAtas.add(panelInput, BorderLayout.CENTER);

        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("ID");
        model.addColumn("Tanggal");
        model.addColumn("Jam Mulai");
        model.addColumn("Jam Selesai");
        model.addColumn("Status");

        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setGridColor(new Color(240, 240, 240));
        table.setSelectionBackground(new Color(230, 245, 238));
        table.setSelectionForeground(new Color(43, 43, 43));
        table.setShowVerticalLines(false);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(43, 43, 43));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 35));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 233, 236)));
        scrollPane.getViewport().setBackground(Color.WHITE);

        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelButton.setOpaque(false);

        JButton btnTambah = createModernButton("Tambah", new Color(46, 117, 89));
        JButton btnHapus = createModernButton("Hapus", new Color(220, 53, 69));
        JButton btnRefresh = createModernButton("Refresh", new Color(70, 70, 70));

        panelButton.add(btnTambah);
        panelButton.add(btnHapus);
        panelButton.add(btnRefresh);

        add(panelAtas, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelButton, BorderLayout.SOUTH);

        loadData();

        btnTambah.addActionListener(e -> tambahJadwal());
        btnHapus.addActionListener(e -> hapusJadwal());
        btnRefresh.addActionListener(e -> loadData());
    }

    private JTextField createModernTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 214, 219), 1),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        return textField;
    }

    private JButton createModernButton(String text, Color bg) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(bg);
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    public Container getContentPane() {
        return this;
    }

    private void loadData() {
        model.setRowCount(0);
        try {
            List<Jadwal> list = jadwalDAO.getAll();
            for (Jadwal j : list) {
                model.addRow(new Object[] {
                        j.getIdJadwal(),
                        j.getTanggal(),
                        j.getJamMulai(),
                        j.getJamSelesai(),
                        j.getStatus()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void tambahJadwal() {
        String tanggal = txtTanggal.getText();
        String mulai = txtMulai.getText();
        String selesai = txtSelesai.getText();

        if (tanggal.isEmpty() || mulai.isEmpty() || selesai.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harap isi semua field!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (jadwalDAO.tambah(tanggal, mulai, selesai)) {
            JOptionPane.showMessageDialog(this, "Jadwal berhasil ditambahkan");
            loadData();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menambahkan jadwal", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hapusJadwal() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Pilih data terlebih dahulu", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = Integer.parseInt(model.getValueAt(row, 0).toString());
        int konfirmasi = JOptionPane.showConfirmDialog(this, "Hapus jadwal ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            if (jadwalDAO.hapus(id)) {
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus");
                loadData();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearFields() {
        txtTanggal.setText("");
        txtMulai.setText("");
        txtSelesai.setText("");
        table.clearSelection();
    }
}