package presentation;

import dao.DetailDAO;
import model.Detail;

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

public class EmployeesPanel extends JPanel implements ActionListener {

    private static final String[] COLUMNS = {"Id", "First Name", "Second Name", "Is Manager", "Birth Day", "Salary"};
    private String[][] data;
    private DetailDAO dao;
    private JTable dataTable;
    private DefaultTableModel model;

    public EmployeesPanel(final String[][] data) {
        super(null);
        this.data = data;

        dao = new DetailDAO();

        model = new DefaultTableModel(data, COLUMNS);

        dataTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(dataTable);

        scrollPane.setSize(780, 400);

        JPanel buttonPanel = new JPanel();
        JButton button = new JButton("Add Employee");
        JButton button1 = new JButton("Edit Employee");
        JButton button2 = new JButton("Delete Employee");

        button.addActionListener(this);
        button.setActionCommand("Add");
        button1.addActionListener(this);
        button1.setActionCommand("Edit");
        button2.addActionListener(this);
        button2.setActionCommand("Delete");

        buttonPanel.add(button);
        buttonPanel.add(button1);
        buttonPanel.add(button2);

        buttonPanel.setLayout(new GridLayout());
        buttonPanel.setBounds(0, 400, 780, 50);

        add(scrollPane);
        add(buttonPanel);

        dataTable.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {

            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case "Delete":
                int selectedRow = dataTable.getSelectedRow();
                Integer id = Integer.parseInt((String) dataTable.getValueAt(selectedRow, 0));
                model.removeRow(selectedRow);
                dao.remove(id);
                break;
            case "Add":

                JTextField tx1 = new JTextField();
                JTextField tx2 = new JTextField();
                JTextField tx3 = new JTextField();
                JTextField tx4 = new JTextField();
                JTextField tx5 = new JTextField();
                JTextField tx6 = new JTextField();
                Object[] a = {"Id", tx1, "First Name", tx2, "Second Name", tx3, "Is Manager", tx4, "Birth Day", tx5, "Salary", tx6};
                int opt = JOptionPane.showConfirmDialog(this, a);

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
                        parseEmployeeDetail(formattedData);
                        model.addRow(data);
                    } else {
                        JOptionPane.showMessageDialog(this, "Data is invalid", "Data error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                break;
            case "Edit":
                // TODO: - FINISH
                break;
        }
    }

    private void parseEmployeeDetail(Object[] data) {
        Detail detail = new Detail();
        detail.setEmployee_id((Integer) data[0]);
        detail.setFirst_name((String) data[1]);
        detail.setSecond_name((String) data[2]);
        detail.setManager((Boolean) data[3]);
        detail.setBirthday((LocalDate) data[4]);
        detail.setSalary((Double) data[5]);
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
            isManager = Boolean.getBoolean(data[3]);
        else
            return null;

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-DD");
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
}
