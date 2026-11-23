import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    private JPanel panelKontainer;
    private CardLayout cardLayout;

    public Main() {
        setTitle("Aplikasi Booking Lapangan");
        setSize(500, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        panelKontainer = new JPanel(cardLayout);

        LoginForm panelLogin = new LoginForm(); 
        FormRegisterCustomer panelRegister = new FormRegisterCustomer();

        panelKontainer.add(panelLogin, "TAMPILAN_LOGIN");
        panelKontainer.add(panelRegister, "TAMPILAN_REGISTER");

        add(panelKontainer);
        cardLayout.show(panelKontainer, "TAMPILAN_LOGIN");

        panelLogin.getBtnKeRegister().addActionListener(e -> {
            cardLayout.show(panelKontainer, "TAMPILAN_REGISTER");
        });

        panelRegister.getBtnKeLogin().addActionListener(e -> {
            cardLayout.show(panelKontainer, "TAMPILAN_LOGIN");
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
}