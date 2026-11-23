import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class FormLapangan extends JPanel {
    private JTable table;
    private DefaultTableModel model;

    private JTextField txtNama;
    private JTextField txtJenis;
    private JTextField txtHarga;
    private JComboBox<String> cmbStatus;

    private LapanganDAO lapanganDAO = new LapanganDAO();

    public FormLapangan() {
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(248, 249, 250));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelAtas = new JPanel(new BorderLayout(0, 10));
        panelAtas.setOpaque(false);

        JLabel lblTitle = new JLabel("Manajemen Data Lapangan");
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

        JLabel lblNama = new JLabel("Nama Lapangan");
        lblNama.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblNama.setForeground(new Color(100, 100, 100));
        gbc.gridx = 0; gbc.gridy = 0;
        panelInput.add(lblNama, gbc);

        txtNama = createModernTextField();
        gbc.gridx = 0; gbc.gridy = 1;
        panelInput.add(txtNama, gbc);

        JLabel lblJenis = new JLabel("Jenis Olahraga");
        lblJenis.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblJenis.setForeground(new Color(100, 100, 100));
        gbc.gridx = 1; gbc.gridy = 0;
        panelInput.add(lblJenis, gbc);

        txtJenis = createModernTextField();
        gbc.gridx = 1; gbc.gridy = 1;
        panelInput.add(txtJenis, gbc);

        JLabel lblHarga = new JLabel("Harga Per Jam");
        lblHarga.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblHarga.setForeground(new Color(100, 100, 100));
        gbc.gridx = 0; gbc.gridy = 2;
        panelInput.add(lblHarga, gbc);

        txtHarga = createModernTextField();
        gbc.gridx = 0; gbc.gridy = 3;
        panelInput.add(txtHarga, gbc);

        JLabel lblStatus = new JLabel("Status");
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblStatus.setForeground(new Color(100, 100, 100));
        gbc.gridx = 1; gbc.gridy = 2;
        panelInput.add(lblStatus, gbc);

        cmbStatus = new JComboBox<>(new String[]{"Tersedia", "Tidak Tersedia"});
        cmbStatus.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbStatus.setBackground(Color.WHITE);
        gbc.gridx = 1; gbc.gridy = 3;
        panelInput.add(cmbStatus, gbc);

        panelAtas.add(panelInput, BorderLayout.CENTER);

        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("ID");
        model.addColumn("Nama Lapangan");
        model.addColumn("Jenis");
        model.addColumn("Harga/Jam");
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
        JButton btnUpdate = createModernButton("Update", new Color(70, 70, 70));
        JButton btnHapus = createModernButton("Hapus", new Color(220, 53, 69));

        panelButton.add(btnTambah);
        panelButton.add(btnUpdate);
        panelButton.add(btnHapus);

        add(panelAtas, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelButton, BorderLayout.SOUTH);

        loadData();

        btnTambah.addActionListener(e -> tambahLapangan());
        btnUpdate.addActionListener(e -> updateLapangan());
        btnHapus.addActionListener(e -> hapusLapangan());

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                txtNama.setText(model.getValueAt(row, 1).toString());
                txtJenis.setText(model.getValueAt(row, 2).toString());
                txtHarga.setText(model.getValueAt(row, 3).toString());
                cmbStatus.setSelectedItem(model.getValueAt(row, 4).toString());
            }
        });
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
            List<Lapangan> list = lapanganDAO.getAll();
            for (Lapangan l : list) {
                model.addRow(new Object[]{
                        l.getIdLapangan(),
                        l.getNamaLapangan(),
                        l.getJenisOlahraga(),
                        l.getHargaPerJam(),
                        l.getStatus()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void tambahLapangan() {
        if (txtNama.getText().isEmpty() || txtJenis.getText().isEmpty() || txtHarga.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harap isi semua field!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            String nama = txtNama.getText();
            String jenis = txtJenis.getText();
            double harga = Double.parseDouble(txtHarga.getText());

            if (lapanganDAO.tambah(nama, jenis, harga)) {
                JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan");
                loadData();
                clearFields();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Input harga harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateLapangan() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Pilih data terlebih dahulu", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int id = Integer.parseInt(model.getValueAt(row, 0).toString());
            String nama = txtNama.getText();
            String jenis = txtJenis.getText();
            double harga = Double.parseDouble(txtHarga.getText());
            String status = cmbStatus.getSelectedItem().toString();

            if (lapanganDAO.update(id, nama, jenis, harga, status)) {
                JOptionPane.showMessageDialog(this, "Data berhasil diupdate");
                loadData();
                clearFields();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Input harga harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hapusLapangan() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Pilih data terlebih dahulu", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = Integer.parseInt(model.getValueAt(row, 0).toString());
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (lapanganDAO.hapus(id)) {
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus");
                loadData();
                clearFields();
            }
        }
    }

    private void clearFields() {
        txtNama.setText("");
        txtJenis.setText("");
        txtHarga.setText("");
        cmbStatus.setSelectedIndex(0);
        table.clearSelection();
    }
}