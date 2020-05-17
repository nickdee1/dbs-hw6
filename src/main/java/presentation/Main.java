package presentation;

import dao.AppointmentDAO;
import dao.DetailDAO;
import service.AppointmentService;
import service.DetailService;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("DB App");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        AppointmentService service = new AppointmentService();
        DetailService detailService = new DetailService();

        JTabbedPane jTabbedPane = new JTabbedPane();

        String[][] data = service.getAppointments();
        String[][] detailData = detailService.getDetails();

        AppointmentsPanel panel = new AppointmentsPanel(data);
        EmployeesPanel employeesPanel = new EmployeesPanel(detailData);

        jTabbedPane.addTab("Appointments", panel);
        jTabbedPane.addTab("Employees", employeesPanel);

        frame.add(jTabbedPane);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
