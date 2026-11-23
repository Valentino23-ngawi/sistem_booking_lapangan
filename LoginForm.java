import javax.swing.*;
import java.awt.*;

public class LoginForm extends JPanel {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnSubmit;
    private JButton btnCancel;
    private JButton btnKeRegister;

    public LoginForm() {
        setLayout(new GridLayout(1, 2));

        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(new Color(43, 43, 43));

        GridBagConstraints gbcLeft = new GridBagConstraints();
        gbcLeft.insets = new Insets(10, 20, 10, 20);
        gbcLeft.gridx = 0;

        JLabel lblBrandTitle = new JLabel("WELCOME BACK");
        lblBrandTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblBrandTitle.setForeground(new Color(240, 240, 240));
        gbcLeft.gridy = 0;
        leftPanel.add(lblBrandTitle, gbcLeft);

        JLabel lblBrandDesc = new JLabel("<html><center>Silahkan masuk untuk mengakses<br>sistem booking Anda.</center></html>");
        lblBrandDesc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblBrandDesc.setForeground(new Color(170, 170, 170));
        gbcLeft.gridy = 1;
        leftPanel.add(lblBrandDesc, gbcLeft);

        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(new Color(248, 249, 250));

        GridBagConstraints gbcRight = new GridBagConstraints();
        gbcRight.insets = new Insets(8, 30, 8, 30);
        gbcRight.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblFormHeader = new JLabel("Sign In");
        lblFormHeader.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblFormHeader.setForeground(new Color(43, 43, 43));
        gbcRight.gridx = 0;
        gbcRight.gridy = 0;
        gbcRight.gridwidth = 2;
        gbcRight.insets = new Insets(10, 30, 20, 30);
        rightPanel.add(lblFormHeader, gbcRight);

        gbcRight.gridwidth = 1;
        gbcRight.insets = new Insets(4, 30, 4, 30);

        JLabel lblUsername = new JLabel("Email");
        lblUsername.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblUsername.setForeground(new Color(100, 100, 100));
        gbcRight.gridx = 0;
        gbcRight.gridy = 1;
        rightPanel.add(lblUsername, gbcRight);

        txtUsername = new JTextField(15);
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsername.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 214, 219), 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        gbcRight.gridx = 0;
        gbcRight.gridy = 2;
        rightPanel.add(txtUsername, gbcRight);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblPassword.setForeground(new Color(100, 100, 100));
        gbcRight.gridx = 0;
        gbcRight.gridy = 3;
        rightPanel.add(lblPassword, gbcRight);

        txtPassword = new JPasswordField(15);
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 214, 219), 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        gbcRight.gridx = 0;
        gbcRight.gridy = 4;
        rightPanel.add(txtPassword, gbcRight);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 12, 0));
        buttonPanel.setOpaque(false);

        btnSubmit = new JButton("Login");
        btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setBackground(new Color(46, 117, 89));
        btnSubmit.setBorderPainted(false);
        btnSubmit.setOpaque(true);
        btnSubmit.setFocusPainted(false);
        btnSubmit.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        btnSubmit.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnCancel = new JButton("Batal");
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setBackground(new Color(220, 53, 69));
        btnCancel.setBorderPainted(false);
        btnCancel.setOpaque(true);
        btnCancel.setFocusPainted(false);
        btnCancel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        buttonPanel.add(btnSubmit);
        buttonPanel.add(btnCancel);

        gbcRight.gridx = 0;
        gbcRight.gridy = 5;
        gbcRight.insets = new Insets(20, 30, 10, 30);
        rightPanel.add(buttonPanel, gbcRight);

        btnKeRegister = new JButton("Belum punya akun? Daftar Customer di sini");
        btnKeRegister.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnKeRegister.setForeground(new Color(46, 117, 89));
        btnKeRegister.setContentAreaFilled(false);
        btnKeRegister.setBorderPainted(false);
        btnKeRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        gbcRight.gridx = 0;
        gbcRight.gridy = 6;
        gbcRight.insets = new Insets(10, 30, 5, 30);
        rightPanel.add(btnKeRegister, gbcRight);

        add(leftPanel);
        add(rightPanel);

        btnSubmit.addActionListener(e -> login());
        btnCancel.addActionListener(e -> System.exit(0));
    }

    private void login() {
        String emailInput = txtUsername.getText();
        String passwordInput = new String(txtPassword.getPassword());

        if (emailInput.isEmpty() || passwordInput.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harap isi semua field!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        UserDAO userDAO = new UserDAO();
        User user = userDAO.login(emailInput, passwordInput, "admin");

        if (user == null) {
            user = userDAO.login(emailInput, passwordInput, "customer");
        }

        if (user != null) {
            String roleSistem = user.getTipe();

            if (roleSistem.equalsIgnoreCase("admin")) {
                new DashboardAdmin();
            } else {
                new DashboardCustomer();
            }
            
            Component comp = SwingUtilities.getWindowAncestor(this);
            if (comp instanceof Window) {
                ((Window) comp).dispose();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Email atau Password Salah!", "Login Gagal", JOptionPane.ERROR_MESSAGE);
        }
    }

    public JButton getBtnKeRegister() {
        return btnKeRegister;
    }
}