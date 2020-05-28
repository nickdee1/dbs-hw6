package presentation;

import dao.DetailDAO;
import service.DetailService;
import service.exceptions.EmployeeExistsException;
import service.exceptions.InvalidInputDataException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class EmployeesPanelListener implements ActionListener {

    private final JTable dataTable;
    private final DefaultTableModel model;
    private final EmployeesPanel panel;
    private final DetailService service;

    EmployeesPanelListener(DetailDAO dao, JTable dataTable, DefaultTableModel model, EmployeesPanel panel) {
        this.dataTable = dataTable;
        this.model = model;
        this.panel = panel;
        this.service = new DetailService();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case "Delete":
                deleteActionPerformed();
                break;
            case "Add":
                addActionPerformed();
                break;
            case "Edit":
                editActionPerformed();
                break;
        }
    }

    private void addActionPerformed() {
        JTextField[] textFields = {
                new JTextField(),
                new JTextField(),
                new JTextField(),
                new JTextField(),
                new JTextField(),
                new JTextField()
        };

        Object[] dialogFields = {
                "Id", textFields[0],
                "First Name", textFields[1],
                "Second Name", textFields[2],
                "Is Manager (true/false)", textFields[3],
                "Birth Day (YYYY-MM-DD)", textFields[4],
                "Salary", textFields[5]
        };

        int opt = JOptionPane.showConfirmDialog(panel, dialogFields);

        if (opt == JOptionPane.OK_OPTION) {
            String[] data = new String[] {
                    textFields[0].getText(),
                    textFields[1].getText(),
                    textFields[2].getText(),
                    textFields[3].getText(),
                    textFields[4].getText(),
                    textFields[5].getText()
            };

            try {
                service.persistEmployeeData(data);
            } catch (InvalidInputDataException e) {
                JOptionPane.showMessageDialog(panel, "The input data is invalid! Try again.");
                return;
            } catch (EmployeeExistsException e) {
                JOptionPane.showMessageDialog(panel, "Employee already exists.");
                return;
            }

            model.addRow(data);
        }
    }

    private void editActionPerformed() {
        int row = dataTable.getSelectedRow();
        JTextField[] textFields;

        try {
            textFields = new JTextField[] {
                    new JTextField((String) model.getValueAt(row, 1)),
                    new JTextField((String) model.getValueAt(row, 2)),
                    new JTextField((String) model.getValueAt(row, 3)),
                    new JTextField((String) model.getValueAt(row, 4)),
                    new JTextField((String) model.getValueAt(row, 5))
            };
        } catch (ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(panel, "Unable to edit. Try again.");
            return;
        }


        Object[] dialogFields = {
                "First Name", textFields[0],
                "Second Name", textFields[1],
                "Is Manager(true/false)", textFields[2],
                "Birth Day (YYYY-MM-DD)", textFields[3],
                "Salary", textFields[4]
        };

        int option = JOptionPane.showConfirmDialog(panel, dialogFields);

        if (option == JOptionPane.OK_OPTION) {
            String[] data = new String[]{
                    (String) model.getValueAt(row, 0),
                    textFields[0].getText(),
                    textFields[1].getText(),
                    textFields[2].getText(),
                    textFields[3].getText(),
                    textFields[4].getText()};

            try {
                service.updateEmployeeData(data);
            } catch (InvalidInputDataException e) {
                JOptionPane.showMessageDialog(panel, "The input data is invalid! Try again.");
                return;
            } catch (EmployeeExistsException e) {
                JOptionPane.showMessageDialog(panel, "Employee does not exist.");
                return;
            }

            for (int i = 1; i < 6; i++)
                model.setValueAt(data[i], row, i);
        }
    }

    private void deleteActionPerformed() {
        int selectedRow = dataTable.getSelectedRow();
        String[] data;

        try {
            data = new String[] {
                    (String) dataTable.getValueAt(selectedRow, 0),
                    (String) model.getValueAt(selectedRow, 3)
            };
        } catch (ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(panel, "Unable to delete. Try again");
            return;
        }

        try {
            service.deleteEmployeeData(data);
        } catch (InvalidInputDataException e) {
            JOptionPane.showMessageDialog(panel, "Unable to delete. Try again");
            return;
        } catch (EmployeeExistsException e) {
            JOptionPane.showMessageDialog(panel, "Employee does not exist.");
            return;
        }

        model.removeRow(selectedRow);
    }

}
