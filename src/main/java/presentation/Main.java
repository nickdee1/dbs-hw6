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
        DetailService detailService = new DetailService();

        JTabbedPane jTabbedPane = new JTabbedPane();

        String[] columns = {"Services", "Client Email", "Client Name", "Barber Id", "Day", "Time", "Price"};
        String[][] data = service.getAppointments();
        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);

        jTabbedPane.addTab("Appointments", scrollPane);



        String[] detailColumns  = {"Employee Id", "First Name", "Last Name", "Is Manager", "Birth day", "Salary"};
        String[][] detailData = detailService.getDetails();
        JTable tableDetail = new JTable(detailData, detailColumns);
        JScrollPane scrollPane1 = new JScrollPane(tableDetail);

        jTabbedPane.addTab("Employees", scrollPane1);
        jTabbedPane.setBounds(0,0,800, 400);

        JButton button = new JButton("Add Employee");
        button.setBounds(10, 400, 50, 50);


        frame.add(jTabbedPane);
        frame.add(button);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
