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
    }
}
