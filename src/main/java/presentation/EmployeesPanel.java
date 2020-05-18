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
import java.time.LocalDate;

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
        if (action.equals("Delete")) {
            int selectedRow = dataTable.getSelectedRow();
            Integer id = Integer.parseInt((String) dataTable.getValueAt(selectedRow, 0));
            model.removeRow(selectedRow);
            dao.remove(id);
        }
        else if (action.equals("Add")) {

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
                parseEmployeeDetail(data);
                model.addRow(data);
            }
        }
        else if (action.equals("Edit")) {
            // TODO: - FINISH
        }
    }

    private void parseEmployeeDetail(String[] data) {
        Integer id = Integer.parseInt(data[0]);
        String firstName = data[1];
        String secondName = data[2];
        Boolean manager = Boolean.parseBoolean(data[3]);
        LocalDate date = LocalDate.parse(data[4]);
        Double salary = Double.parseDouble(data[5]);

        Detail detail = new Detail();
        detail.setEmployee_id(id);
        detail.setFirst_name(firstName);
        detail.setSecond_name(secondName);
        detail.setManager(manager);
        detail.setBirthday(date);
        detail.setSalary(salary);
        dao.persist(detail);
    }
}
