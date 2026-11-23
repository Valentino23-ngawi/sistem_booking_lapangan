import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DashboardCustomer extends JFrame {

    private JPanel mainContentPanel;
    private CardLayout cardLayout;
    private List<JButton> sidebarButtons = new ArrayList<>();

    public DashboardCustomer() {
        setTitle("Sistem Booking Lapangan - Dashboard Customer");
        setSize(950, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel rootPanel = new JPanel(new BorderLayout());

        // Sidebar Panel
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(43, 43, 43));
        sidebar.setPreferredSize(new Dimension(230, 580));
        sidebar.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 15, 5, 15);
        gbc.gridx = 0;

        JLabel lblTitle = new JLabel("CUSTOMER");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(new Color(240, 240, 240));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 0;
        gbc.insets = new Insets(25, 15, 30, 15);
        sidebar.add(lblTitle, gbc);

        gbc.insets = new Insets(5, 15, 5, 15);

        JButton btnLapangan = createSidebarButton("Lihat Lapangan");
        gbc.gridy = 1; sidebar.add(btnLapangan, gbc);

        JButton btnJadwal = createSidebarButton("Lihat Jadwal");
        gbc.gridy = 2; sidebar.add(btnJadwal, gbc);

        JButton btnBooking = createSidebarButton("Booking Lapangan");
        gbc.gridy = 3; sidebar.add(btnBooking, gbc);

        JButton btnPembayaran = createSidebarButton("Pembayaran");
        gbc.gridy = 4; sidebar.add(btnPembayaran, gbc);

        gbc.weighty = 1.0; gbc.gridy = 5;
        sidebar.add(Box.createGlue(), gbc);

        JButton btnLogout = new JButton("Log Out");
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setBackground(new Color(220, 53, 69));
        btnLogout.setFocusPainted(false);
        btnLogout.setBorderPainted(false);
        btnLogout.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        btnLogout.setContentAreaFilled(true);
        btnLogout.setOpaque(true);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.weighty = 0.0; gbc.gridy = 6;
        gbc.insets = new Insets(10, 15, 25, 15);
        sidebar.add(btnLogout, gbc);

        // Content panel utama kanan
        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);
        mainContentPanel.setBackground(new Color(248, 249, 250));

        JPanel homePanel = createWelcomePanel("Selamat Datang di SIstem Booking Lapangan Futsal dan Badminton");
        mainContentPanel.add(homePanel, "Home");

        rootPanel.add(sidebar, BorderLayout.WEST);
        rootPanel.add(mainContentPanel, BorderLayout.CENTER);
        add(rootPanel);

        // Menghubungkan Sub-Panel Formulir
        btnLapangan.addActionListener(e -> {
            setAktifButton(btnLapangan);
            switchPanel("Lapangan", new FormLapanganCustomer().getContentPane());
        });
        btnJadwal.addActionListener(e -> {
            setAktifButton(btnJadwal);
            switchPanel("Jadwal", new FormJadwalCustomer().getContentPane());
        });
        btnBooking.addActionListener(e -> {
            setAktifButton(btnBooking);
            switchPanel("Booking", new FormBookingCustomer().getContentPane());
        });
        btnPembayaran.addActionListener(e -> {
            setAktifButton(btnPembayaran);
            switchPanel("Pembayaran", new FormPembayaranCustomer().getContentPane());
        });

        btnLogout.addActionListener(e -> {
            dispose();
            new LoginForm().setVisible(true);
        });

        setVisible(true);
    }

    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(new Color(200, 200, 200));
        button.setBackground(new Color(55, 55, 55));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sidebarButtons.add(button);
        return button;
    }

    private void setAktifButton(JButton tombolAktif) {
        for (JButton btn : sidebarButtons) {
            btn.setBackground(new Color(55, 55, 55));
            btn.setForeground(new Color(200, 200, 200));
        }
        tombolAktif.setBackground(new Color(0, 123, 255));
        tombolAktif.setForeground(Color.WHITE);
    }

    private JPanel createWelcomePanel(String message) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(248, 249, 250));
        JLabel label = new JLabel(message);
        label.setFont(new Font("Segoe UI", Font.BOLD, 22));
        label.setForeground(new Color(43, 43, 43));
        panel.add(label);
        return panel;
    }

    private void switchPanel(String name, Container content) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(new Color(248, 249, 250));
        
        wrapper.add(content, BorderLayout.CENTER);
        mainContentPanel.add(wrapper, name);
        cardLayout.show(mainContentPanel, name);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DashboardCustomer();
        });
    }
}