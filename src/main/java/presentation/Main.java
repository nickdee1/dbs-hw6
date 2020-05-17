package presentation;

import dao.AppointmentDAO;
import dao.DetailDAO;
import service.AppointmentService;
import service.DetailService;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(800, 600);
        AppointmentService service = new AppointmentService();

        JTabbedPane jTabbedPane = new JTabbedPane();

        String[][] data = service.getAppointments();
        AppointmentsPanel panel = new AppointmentsPanel(data);


        jTabbedPane.addTab("Appointments", panel);

        frame.add(jTabbedPane);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
