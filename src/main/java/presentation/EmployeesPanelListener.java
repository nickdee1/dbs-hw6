package presentation;

import dao.BarberDAO;
import dao.DetailDAO;
import dao.EmployeeDAO;
import dao.ManagerDAO;
import service.DetailService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class EmployeesPanelListener implements ActionListener {

    private final DetailDAO dao;
    private final JTable dataTable;
    private final DefaultTableModel model;
    private final EmployeesPanel panel;

    EmployeesPanelListener(DetailDAO dao, JTable dataTable, DefaultTableModel model, EmployeesPanel panel) {
        this.dao = dao;
        this.dataTable = dataTable;
        this.model = model;
        this.panel = panel;
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

            DetailService service = new DetailService();
            service.persistEmployeeData(data);

            model.addRow(data);
        }
    }

    private void editActionPerformed() {
        int row = dataTable.getSelectedRow();
        JTextField[] textFields = {
                new JTextField((String) model.getValueAt(row, 1)),
                new JTextField((String) model.getValueAt(row, 2)),
                new JTextField((String) model.getValueAt(row, 3)),
                new JTextField((String) model.getValueAt(row, 4)),
                new JTextField((String) model.getValueAt(row, 5))
        };

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

            DetailService service = new DetailService();
            service.updateEmployeeData(data);
        }
    }

    private void deleteActionPerformed() {
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
    }

}
