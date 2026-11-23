import javax.swing.*;
import java.awt.*;

public class DashboardForm extends JFrame {

    public DashboardForm() {

        setTitle("Dashboard");
        setSize(500,300);
        setLocationRelativeTo(null);
        setVisible(true);

        JButton btnLapangan =
                new JButton("Data Lapangan");

        JButton btnJadwal =
                new JButton("Data Jadwal");

        JPanel panel = new JPanel();

        panel.add(btnLapangan);
        panel.add(btnJadwal);

        add(panel, BorderLayout.CENTER);

        btnLapangan.addActionListener(e -> {
            new FormLapangan();
        });

        btnJadwal.addActionListener(e -> {
            new FormJadwal();
        });

        setVisible(true);
    }
}