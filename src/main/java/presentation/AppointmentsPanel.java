package presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AppointmentsPanel extends JPanel {

    private static final String[] COLUMNS = {"Id", "Services", "Client Email", "Client Name", "Barber Id", "Day", "Time", "Price"};
    private String[][] data;
    private final JScrollPane scrollPane;
    private final JTable dataTable;
    private final DefaultTableModel model;

    public AppointmentsPanel(String[][] data) {
        super(null);
        this.data = data;

        model = new DefaultTableModel(data, COLUMNS);
        dataTable = new JTable(model);
        scrollPane = new JScrollPane(dataTable);

        scrollPane.setSize(780, 400);

        AppointmentsPanelListener h = new AppointmentsPanelListener(dataTable, model, this);

        JPanel buttonPanel = new JPanel();
        JButton button = new JButton("Add Appointment");
        JButton button1 = new JButton("Edit Appointment");
        JButton button2 = new JButton("Delete Appointment");
        JButton button3 = new JButton("Refresh");
        button.addActionListener(h);
        button.setActionCommand("Add App");
        button1.addActionListener(h);
        button1.setActionCommand("Edit App");
        button2.addActionListener(h);
        button2.setActionCommand("Delete App");
        button3.addActionListener(h);
        button3.setActionCommand("Refresh App");

        buttonPanel.add(button);
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);

        buttonPanel.setLayout(new GridLayout());
        buttonPanel.setBounds(0, 400, 780, 50);

        add(scrollPane);
        add(buttonPanel);
    }
}
