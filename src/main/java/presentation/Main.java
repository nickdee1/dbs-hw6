package presentation;

import dao.AppointmentDAO;
import service.AppointmentService;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(800, 600);
        AppointmentService service = new AppointmentService();

        JTabbedPane jTabbedPane = new JTabbedPane();

        String[] columns = {"Services", "Client Email", "Client Name", "Barber Id", "Day", "Time", "Price"};
        String[][] data = service.getAppointments();
        JTable table = new JTable(data, columns);
        table.setBounds(0,0,800,400);
        JScrollPane scrollPane = new JScrollPane(table);

        jTabbedPane.addTab("Appointments", scrollPane);
        frame.add(jTabbedPane);




        frame.setVisible(true);
    }
}
