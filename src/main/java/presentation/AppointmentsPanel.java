package presentation;

import javax.swing.*;
import java.awt.*;

public class AppointmentsPanel extends JPanel {

    private String[][] data;
    private static final String[] COLUMNS = {"Services", "Client Email", "Client Name", "Barber Id", "Day", "Time", "Price"};

    public AppointmentsPanel(String[][] data) {
        super(null);
        this.data = data;

        JTable dataTable = new JTable(data, COLUMNS);
        JScrollPane scrollPane = new JScrollPane(dataTable);

        scrollPane.setSize(780, 400);

        JPanel buttonPanel = new JPanel();
        JButton button = new JButton("Add Appointment");
        JButton button1 = new JButton("Edit Appointment");
        JButton button2 = new JButton("Delete Appointment");
        buttonPanel.add(button);
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.setLayout(new GridLayout());
        buttonPanel.setBounds(0, 400, 780, 50);


        add(scrollPane);
        add(buttonPanel);
    }
}
