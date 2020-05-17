package presentation;

import javax.swing.*;
import java.awt.*;

public class EmployeesPanel extends JPanel {

    private static final String[] COLUMNS = {"Id", "First Name", "Second Name", "Is Manager", "Birth Day", "Salary"};
    private String[][] data;

    public EmployeesPanel(String[][] data) {
        super(null);
        this.data = data;

        JTable dataTable = new JTable(data, COLUMNS);
        JScrollPane scrollPane = new JScrollPane(dataTable);

        scrollPane.setSize(780, 400);

        JPanel buttonPanel = new JPanel();
        JButton button = new JButton("Add Employee");
        JButton button1 = new JButton("Edit Employee");
        JButton button2 = new JButton("Delete Employee");
        buttonPanel.add(button);
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.setLayout(new GridLayout());
        buttonPanel.setBounds(0, 400, 780, 50);


        add(scrollPane);
        add(buttonPanel);
    }
}
