import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class FormBookingAdmin extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private BookingDAO bookingDAO = new BookingDAO();

    public FormBookingAdmin() {
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(248, 249, 250));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitle = new JLabel("Manajemen Data Booking");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(new Color(43, 43, 43));
        add(lblTitle, BorderLayout.NORTH);

        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.addColumn("ID");
        model.addColumn("Customer");
        model.addColumn("Lapangan");
        model.addColumn("Tanggal");
        model.addColumn("Durasi");
        model.addColumn("Total");
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
        add(scrollPane, BorderLayout.CENTER);

        loadData();
    }

    public Container getContentPane() {
        return this;
    }

    private void loadData() {
        model.setRowCount(0);
        try {
            List<Booking> list = bookingDAO.getAll();
            for (Booking b : list) {
                model.addRow(new Object[]{
                        b.getIdBooking(),
                        b.getCustomer().getNama(),
                        b.getLapangan().getNamaLapangan(),
                        b.getTanggalBooking(),
                        b.getDurasi(),
                        b.getTotalHarga(),
                        b.getStatusBooking()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}