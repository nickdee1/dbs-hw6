package presentation;

import service.AppointmentService;
import service.DetailService;
import javax.swing.*;

/**
 * Runner class for app
 */
public class Main {

    public static void main(String[] args) {
        /* Init frame */
        JFrame frame = new JFrame("DB App");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* Services for fetching info from DB */
        AppointmentService service = new AppointmentService();
        DetailService detailService = new DetailService();

        JTabbedPane jTabbedPane = new JTabbedPane();

        /* Data for panels */
        String[][] data = service.getAppointments();
        String[][] detailData = detailService.getDetails();

        /* Two main panels with data */
        AppointmentsPanel panel = new AppointmentsPanel(data);
        EmployeesPanel employeesPanel = new EmployeesPanel(detailData);

        jTabbedPane.addTab("Appointments", panel);
        jTabbedPane.addTab("Employees", employeesPanel);

        frame.add(jTabbedPane);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
