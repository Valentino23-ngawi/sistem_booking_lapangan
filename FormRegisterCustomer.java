import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class FormRegisterCustomer extends JPanel {

    private JTextField txtNamaLengkap;
    private JTextField txtEmail;
    private JTextField txtNoTelepon;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtKonfirmasiPassword;
    private JButton btnRegister;
    private JButton btnKeLogin;

    public FormRegisterCustomer() {
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(248, 249, 250));
        setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JPanel panelHeader = new JPanel(new GridLayout(2, 1, 5, 5));
        panelHeader.setOpaque(false);
        
        JLabel lblTitle = new JLabel("Registrasi Akun Customer", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(43, 43, 43));
        
        JLabel lblSubtitle = new JLabel("Silakan lengkapi data diri Anda untuk memesan lapangan", SwingConstants.CENTER);
        lblSubtitle.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblSubtitle.setForeground(Color.GRAY);
        
        panelHeader.add(lblTitle);
        panelHeader.add(lblSubtitle);
        add(panelHeader, BorderLayout.NORTH);

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(Color.WHITE);
        panelForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 233, 236), 1),
                BorderFactory.createEmptyBorder(15, 25, 15, 25)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 10, 4, 10);
        gbc.weightx = 1.0;

        JLabel lblNama = new JLabel("Nama Lengkap");
        lblNama.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblNama.setForeground(new Color(100, 100, 100));
        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(lblNama, gbc);

        txtNamaLengkap = buatStyleTextField();
        gbc.gridx = 0; gbc.gridy = 1;
        panelForm.add(txtNamaLengkap, gbc);

        JLabel lblEmail = new JLabel("Email");
        lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblEmail.setForeground(new Color(100, 100, 100));
        gbc.gridx = 0; gbc.gridy = 2;
        panelForm.add(lblEmail, gbc);

        txtEmail = buatStyleTextField();
        gbc.gridx = 0; gbc.gridy = 3;
        panelForm.add(txtEmail, gbc);

        JLabel lblNotelp = new JLabel("No. Telepon / WhatsApp");
        lblNotelp.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblNotelp.setForeground(new Color(100, 100, 100));
        gbc.gridx = 0; gbc.gridy = 4;
        panelForm.add(lblNotelp, gbc);

        txtNoTelepon = buatStyleTextField();
        gbc.gridx = 0; gbc.gridy = 5;
        panelForm.add(txtNoTelepon, gbc);

        JLabel lblUsername = new JLabel("Username Baru");
        lblUsername.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblUsername.setForeground(new Color(100, 100, 100));
        gbc.gridx = 0; gbc.gridy = 6;
        panelForm.add(lblUsername, gbc);

        txtUsername = buatStyleTextField();
        gbc.gridx = 0; gbc.gridy = 7;
        panelForm.add(txtUsername, gbc);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblPassword.setForeground(new Color(100, 100, 100));
        gbc.gridx = 0; gbc.gridy = 8;
        panelForm.add(lblPassword, gbc);

        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 214, 219), 1),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        gbc.gridx = 0; gbc.gridy = 9;
        panelForm.add(txtPassword, gbc);

        JLabel lblKonfirmasi = new JLabel("Konfirmasi Password");
        lblKonfirmasi.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblKonfirmasi.setForeground(new Color(100, 100, 100));
        gbc.gridx = 0; gbc.gridy = 10;
        panelForm.add(lblKonfirmasi, gbc);

        txtKonfirmasiPassword = new JPasswordField();
        txtKonfirmasiPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtKonfirmasiPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 214, 219), 1),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        gbc.gridx = 0; gbc.gridy = 11;
        panelForm.add(txtKonfirmasiPassword, gbc);

        add(panelForm, BorderLayout.CENTER);

        JPanel panelBawah = new JPanel(new BorderLayout(0, 10));
        panelBawah.setOpaque(false);

        btnRegister = new JButton("Daftar Akun Customer");
        btnRegister.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setBackground(new Color(46, 117, 89)); 
        btnRegister.setBorderPainted(false);
        btnRegister.setOpaque(true);
        btnRegister.setFocusPainted(false);
        btnRegister.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));
        btnRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelBawah.add(btnRegister, BorderLayout.NORTH);

        btnKeLogin = new JButton("Sudah punya akun? Silakan Login di sini");
        btnKeLogin.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnKeLogin.setForeground(new Color(46, 117, 89));
        btnKeLogin.setContentAreaFilled(false);
        btnKeLogin.setBorderPainted(false);
        btnKeLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelBawah.add(btnKeLogin, BorderLayout.SOUTH);

        add(panelBawah, BorderLayout.SOUTH);

        btnRegister.addActionListener(e -> prosesPendaftaranCustomerDB());
    }

    private JTextField buatStyleTextField() {
        JTextField tf = new JTextField();
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 214, 219), 1),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        return tf;
    }

    private void prosesPendaftaranCustomerDB() {
        String nama = txtNamaLengkap.getText().trim();
        String email = txtEmail.getText().trim();
        String notelp = txtNoTelepon.getText().trim();
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        String konfirmasi = new String(txtKonfirmasiPassword.getPassword());

        if (nama.isEmpty() || email.isEmpty() || notelp.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua data wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!password.equals(konfirmasi)) {
            JOptionPane.showMessageDialog(this, "Password dan Konfirmasi Password tidak cocok!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = "INSERT INTO users (nama, email, password, no_hp, tipe) VALUES (?, ?, ?, ?, 'customer')";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, nama);
            ps.setString(2, email); 
            ps.setString(3, password);
            ps.setString(4, notelp);

            int hasil = ps.executeUpdate();
            if (hasil > 0) {
                JOptionPane.showMessageDialog(this, "Registrasi Berhasil!\nSilakan gunakan akun Anda untuk masuk.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                
                txtNamaLengkap.setText("");
                txtEmail.setText("");
                txtNoTelepon.setText("");
                txtUsername.setText("");
                txtPassword.setText("");
                txtKonfirmasiPassword.setText("");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Gagal registrasi! Terjadi kesalahan koneksi atau data duplikat.", "Error Database", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public JButton getBtnKeLogin() {
        return btnKeLogin;
    }
}