package presentation;

import dao.BarberDAO;
import dao.DetailDAO;
import dao.EmployeeDAO;
import dao.ManagerDAO;
import model.Barber;
import model.Detail;
import model.Employee;
import model.Manager;

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


    private void parseEmployeeDetail(Object[] data) {
        Detail detail = new Detail();
        Employee employee = new Employee();
        EmployeeDAO employeeDAO = new EmployeeDAO();
        employee.setId((Integer) data[0]);
        detail.setEmployee_id((Integer) data[0]);
        detail.setFirst_name((String) data[1]);
        detail.setSecond_name((String) data[2]);
        detail.setManager((Boolean) data[3]);
        detail.setBirthday((LocalDate) data[4]);
        detail.setSalary((Double) data[5]);
        employeeDAO.persist(employee);
        if (detail.isManager()) {
            Manager manager = new Manager();
            ManagerDAO managerDAO = new ManagerDAO();
            manager.setManager_id(employee.getId());
            managerDAO.persist(manager);
        } else {
            Barber barber = new Barber();
            BarberDAO barberDAO = new BarberDAO();
            barber.setBarber_id(employee.getId());
            barberDAO.persist(barber);
        }

        dao.persist(detail);
    }

    private Object[] checkData(String[] data) {
        Object[] output = new Object[6];
        Integer id;
        Double salary;
        String firstName;
        String secondName;
        LocalDate date;
        Boolean isManager;

        try {
            id = Integer.parseInt(data[0]);
            salary = Double.parseDouble(data[5]);
        } catch (NumberFormatException e) {
            return null;
        }

        if (data[3].toLowerCase().equals("true") || data[3].toLowerCase().equals("false"))
            isManager = Boolean.parseBoolean(data[3].toLowerCase());
        else
            return null;

        try {
            date = LocalDate.parse(data[4]);
        } catch (DateTimeException e) {
            return null;
        }

        if (data[1].length() == 0 || data[2].length() == 0) return null;

        firstName = data[1];
        secondName = data[2];

        output[0] = id;
        output[1] = firstName;
        output[2] = secondName;
        output[3] = isManager;
        output[4] = date;
        output[5] = salary;

        return output;
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
                    JTextField tx1 = new JTextField();
                    JTextField tx2 = new JTextField();
                    JTextField tx3 = new JTextField();
                    JTextField tx4 = new JTextField();
                    JTextField tx5 = new JTextField();
                    JTextField tx6 = new JTextField();
                    Object[] a = {"Id", tx1, "First Name", tx2, "Second Name", tx3, "Is Manager (true/false)", tx4, "Birth Day (YYYY-MM-DD)", tx5, "Salary", tx6};
                    int opt = JOptionPane.showConfirmDialog(panel, a);

                    if (opt == JOptionPane.OK_OPTION) {
                        String[] data = new String[]{
                                tx1.getText(),
                                tx2.getText(),
                                tx3.getText(),
                                tx4.getText(),
                                tx5.getText(),
                                tx6.getText()};
                        Object[] formattedData = checkData(data);
                        if (formattedData != null) {
                            EmployeeDAO employeeDAO = new EmployeeDAO();
                            Employee employee = employeeDAO.find((Integer) formattedData[0]);
                            if (employee != null) {
                                JOptionPane.showMessageDialog(panel, "Employee already exists!", "error", JOptionPane.ERROR_MESSAGE);
                                break;
                            }

                            parseEmployeeDetail(formattedData);
                            model.addRow(data);
                        } else {
                            JOptionPane.showMessageDialog(panel, "Data is invalid", "Data error", JOptionPane.ERROR_MESSAGE);
                        }
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
                        Object[] formattedData = checkData(data);
                        if (formattedData != null) {
                            Integer idEmp = (Integer) formattedData[0];
                            Detail det = dao.find(idEmp);
                            det.setFirst_name((String) formattedData[1]);
                            det.setSecond_name((String) formattedData[2]);
                            det.setManager((Boolean) formattedData[3]);
                            det.setBirthday((LocalDate) formattedData[4]);
                            det.setSalary((Double) formattedData[5]);
                            dao.update(det);

                            for (int i = 1; i < 6; i++)
                                model.setValueAt(data[i], row, i);


                        } else {
                            JOptionPane.showMessageDialog(panel, "Data is invalid", "Data error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    break;
            }
        }
    }
}
