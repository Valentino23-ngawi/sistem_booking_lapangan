import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class DashboardAdmin extends JFrame {

    private JPanel mainContentPanel;
    private CardLayout cardLayout;
    private JLabel lblNotifBadge;
    private NotifikasiDAO notifikasiDAO = new NotifikasiDAO();
    private int jumlahNotifikasi = 0;
    private List<JButton> sidebarButtons = new ArrayList<>();

    public DashboardAdmin() {
        setTitle("Sistem Booking Lapangan Futsal - Dashboard Admin");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel rootPanel = new JPanel(new BorderLayout());

        // Sidebar Panel
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(43, 43, 43));
        sidebar.setPreferredSize(new Dimension(230, 650));
        sidebar.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 15, 5, 15);
        gbc.gridx = 0;

        JLabel lblTitle = new JLabel("ADMIN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(new Color(240, 240, 240));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 0;
        gbc.insets = new Insets(25, 15, 30, 15);
        sidebar.add(lblTitle, gbc);

        gbc.insets = new Insets(5, 15, 5, 15);

        JButton btnLapangan = createSidebarButton("Data Lapangan");
        gbc.gridy = 1;
        sidebar.add(btnLapangan, gbc);

        JButton btnJadwal = createSidebarButton("Data Jadwal");
        gbc.gridy = 2;
        sidebar.add(btnJadwal, gbc);

        JButton btnBooking = createSidebarButton("Data Booking");
        gbc.gridy = 3;
        sidebar.add(btnBooking, gbc);

        JButton btnPembayaran = createSidebarButton("Data Pembayaran");
        gbc.gridy = 4;
        sidebar.add(btnPembayaran, gbc);

        JButton btnUser = createSidebarButton("Data User");
        gbc.gridy = 5;
        sidebar.add(btnUser, gbc);

        gbc.weighty = 1.0;
        gbc.gridy = 6;
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
        gbc.weighty = 0.0;
        gbc.gridy = 7;
        gbc.insets = new Insets(10, 15, 25, 15);
        sidebar.add(btnLogout, gbc);

        // Bagian Kanan Container
        JPanel kananContainer = new JPanel(new BorderLayout());

        JPanel headerBar = new JPanel(new BorderLayout());
        headerBar.setBackground(Color.WHITE);
        headerBar.setPreferredSize(new Dimension(770, 60));
        headerBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 233, 236)));

        JLabel lblUserAdmin = new JLabel("Login sebagai: Admin Utama");
        lblUserAdmin.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblUserAdmin.setForeground(new Color(100, 100, 100));
        lblUserAdmin.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        headerBar.add(lblUserAdmin, BorderLayout.WEST);

        JPanel panelNotifWrapper = new JPanel(null);
        panelNotifWrapper.setOpaque(false);
        panelNotifWrapper.setPreferredSize(new Dimension(50, 60));

        JLabel lblNotifIcon = new JLabel("\uD83D\uDD14");
        lblNotifIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        lblNotifIcon.setBounds(5, 15, 35, 35);
        lblNotifIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelNotifWrapper.add(lblNotifIcon);

        lblNotifBadge = new JLabel("0");
        lblNotifBadge.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblNotifBadge.setForeground(Color.WHITE);
        lblNotifBadge.setBackground(new Color(220, 53, 69));
        lblNotifBadge.setOpaque(true);
        lblNotifBadge.setHorizontalAlignment(SwingConstants.CENTER);
        lblNotifBadge.setBounds(24, 12, 18, 18);
        lblNotifBadge.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        panelNotifWrapper.add(lblNotifBadge);

        JPanel panelAksiHeader = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        panelAksiHeader.setOpaque(false);
        panelAksiHeader.add(panelNotifWrapper);
        headerBar.add(panelAksiHeader, BorderLayout.EAST);

        lblNotifIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tampilkanPopupNotifikasi(lblNotifIcon);
            }
        });

        kananContainer.add(headerBar, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);
        mainContentPanel.setBackground(new Color(248, 249, 250));

        JPanel homePanel = createWelcomePanel("Selamat Datang di Dashboard Admin Sistem Booking Lapangan");
        mainContentPanel.add(homePanel, "Home");

        kananContainer.add(mainContentPanel, BorderLayout.CENTER);

        rootPanel.add(sidebar, BorderLayout.WEST);
        rootPanel.add(kananContainer, BorderLayout.CENTER);
        add(rootPanel);

        // Actions Menu Sidebar
        btnLapangan.addActionListener(e -> {
            setAktifButton(btnLapangan);
            switchPanel("Lapangan", new FormLapangan().getContentPane());
        });
        btnJadwal.addActionListener(e -> {
            setAktifButton(btnJadwal);
            switchPanel("Jadwal", new FormJadwal().getContentPane());
        });
        btnBooking.addActionListener(e -> {
            setAktifButton(btnBooking);
            switchPanel("Booking", new FormBookingAdmin().getContentPane());
        });
        btnPembayaran.addActionListener(e -> {
            setAktifButton(btnPembayaran);
            switchPanel("Pembayaran", new FormPembayaranAdmin().getContentPane());
        });
        btnUser.addActionListener(e -> {
            setAktifButton(btnUser);
            switchPanel("User", new FormUser().getContentPane());
        });

        btnLogout.addActionListener(e -> {
            dispose();
            new LoginForm().setVisible(true);
        });

        refreshNotifikasi();
        Timer timer = new Timer(3000, e -> refreshNotifikasi());
        timer.start();

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

    private void refreshNotifikasi() {
        try {
            List<Notifikasi> list = notifikasiDAO.getBelumDibaca();
            jumlahNotifikasi = list.size();
            if (jumlahNotifikasi > 0) {
                lblNotifBadge.setText(String.valueOf(jumlahNotifikasi));
                lblNotifBadge.setVisible(true);
            } else {
                lblNotifBadge.setVisible(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void tampilkanPopupNotifikasi(Component invoker) {
        JPopupMenu popNotif = new JPopupMenu();
        popNotif.setBackground(Color.WHITE);
        popNotif.setBorder(BorderFactory.createLineBorder(new Color(220, 224, 230), 1));

        try {
            List<Notifikasi> list = notifikasiDAO.getBelumDibaca();
            if (list != null && !list.isEmpty()) {
                for (Notifikasi n : list) {
                    popNotif.add(createNotifItem(n));
                }
                popNotif.addSeparator();

                JMenuItem btnClear = new JMenuItem("Tandai Semua Telah Dibaca");
                btnClear.setFont(new Font("Segoe UI", Font.BOLD, 12));
                btnClear.setForeground(new Color(46, 117, 89));
                btnClear.setHorizontalAlignment(SwingConstants.CENTER);
                btnClear.addActionListener(e -> {
                    if (notifikasiDAO.tandaiSemuaDibaca()) {
                        refreshNotifikasi();
                        JOptionPane.showMessageDialog(this, "Semua notifikasi telah dibaca.");
                    }
                });
                popNotif.add(btnClear);
            } else {
                JMenuItem itemKosong = new JMenuItem("Tidak ada notifikasi baru");
                itemKosong.setFont(new Font("Segoe UI", Font.ITALIC, 13));
                itemKosong.setEnabled(false);
                popNotif.add(itemKosong);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        popNotif.show(invoker, -180, invoker.getHeight());
    }

    private JMenuItem createNotifItem(Notifikasi n) {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm");
        String strTanggal = (n.getTanggal() != null) ? sdf.format(n.getTanggal()) : "--:--";

        String formatPesan = "[" + strTanggal + "]  •  " + n.getPesan();

        JMenuItem item = new JMenuItem(formatPesan);
        item.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        item.setForeground(new Color(43, 43, 43));
        item.setBackground(Color.WHITE);
        item.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        return item;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DashboardAdmin();
        });
    }
}