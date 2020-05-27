package presentation;

import dao.BarberDAO;
import dao.DetailDAO;
import dao.EmployeeDAO;
import dao.ManagerDAO;
import model.Barber;
import model.Detail;
import model.Employee;
import model.Manager;
import service.DetailService;
import service.parsers.exceptions.EmployeeExistsException;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EmployeesPanel extends JPanel {

    private static final String[] COLUMNS = {"Id", "First Name", "Second Name", "Is Manager", "Birth Day", "Salary"};
    private final DetailDAO dao;
    private final JTable dataTable;
    private final DefaultTableModel model;

    public EmployeesPanel(final String[][] data) {
        super(null);

        dao = new DetailDAO();

        model = new DefaultTableModel(data, COLUMNS);

        dataTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(dataTable);

        scrollPane.setSize(780, 400);

        EmployeesPanelListener h = new EmployeesPanelListener(dao, dataTable, model,this);

        JPanel buttonPanel = new JPanel();
        JButton button = new JButton("Add Employee");
        JButton button1 = new JButton("Edit Employee");
        JButton button2 = new JButton("Delete Employee");

        button.addActionListener(h);
        button.setActionCommand("Add");
        button1.addActionListener(h);
        button1.setActionCommand("Edit");
        button2.addActionListener(h);
        button2.setActionCommand("Delete");

        buttonPanel.add(button);
        buttonPanel.add(button1);
        buttonPanel.add(button2);

        buttonPanel.setLayout(new GridLayout());
        buttonPanel.setBounds(0, 400, 780, 50);

        add(scrollPane);
        add(buttonPanel);
    }
}
