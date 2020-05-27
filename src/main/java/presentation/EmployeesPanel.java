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

        Handler h = new Handler(this);

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


    private class Handler implements ActionListener {

        EmployeesPanel panel;

        public Handler(EmployeesPanel panel) {
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String action = e.getActionCommand();
            switch (action) {
                case "Delete":
                    int selectedRow = dataTable.getSelectedRow();
                    Integer id = Integer.parseInt((String) dataTable.getValueAt(selectedRow, 0));
                    model.removeRow(selectedRow);
                    if (dao.find(id).isManager()) {
                        dao.remove(id);
                        ManagerDAO managerDAO = new ManagerDAO();
                        managerDAO.remove(id);
                    }
                    else {
                        dao.remove(id);
                        BarberDAO d = new BarberDAO();
                        d.remove(id);
                    }
                    EmployeeDAO em = new EmployeeDAO();
                    em.remove(id);
                    break;
                case "Add":
                    JTextField[] textFields = {
                            new JTextField(),
                            new JTextField(),
                            new JTextField(),
                            new JTextField(),
                            new JTextField(),
                            new JTextField()
                    };

                    Object[] a = {"Id", textFields[0], "First Name", textFields[1], "Second Name", textFields[2], "Is Manager (true/false)", textFields[3], "Birth Day (YYYY-MM-DD)", textFields[4], "Salary", textFields[5]};
                    int opt = JOptionPane.showConfirmDialog(panel, a);

                    if (opt == JOptionPane.OK_OPTION) {
                        String[] data = new String[] {
                                textFields[0].getText(),
                                textFields[1].getText(),
                                textFields[2].getText(),
                                textFields[3].getText(),
                                textFields[4].getText(),
                                textFields[5].getText()
                        };

                        DetailService service = new DetailService();
                        service.persistEmployeeData(data);

                        model.addRow(data);
//                      JOptionPane.showMessageDialog(panel, "Data is invalid", "Data error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case "Edit":
                    int row = dataTable.getSelectedRow();
                    JTextField firstName = new JTextField((String) model.getValueAt(row, 1));
                    JTextField secondName = new JTextField((String) model.getValueAt(row, 2));
                    JTextField isManager = new JTextField((String) model.getValueAt(row, 3));
                    JTextField birthDay = new JTextField((String) model.getValueAt(row, 4));
                    JTextField salary = new JTextField((String) model.getValueAt(row, 5));
                    Object[] popupData = {"First Name", firstName, "Second Name", secondName, "Is Manager(true/false)", isManager, "Birth Day (YYYY-MM-DD)", birthDay, "Salary", salary};
                    int option = JOptionPane.showConfirmDialog(panel, popupData);

                    if (option == JOptionPane.OK_OPTION) {
                        String[] data = new String[]{
                                (String) model.getValueAt(row, 0),
                                firstName.getText(),
                                secondName.getText(),
                                isManager.getText(),
                                birthDay.getText(),
                                salary.getText()};

                        DetailService service = new DetailService();
                        service.updateEmployeeData(data);
                    }
                    break;
            }
        }
    }
}
