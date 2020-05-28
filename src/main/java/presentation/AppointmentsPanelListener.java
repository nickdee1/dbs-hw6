package presentation;

import dao.AppointmentDAO;
import dao.BarberDAO;
import dao.ClientDAO;
import dao.ServiceDAO;
import model.Barber;
import model.Client;
import model.Service;
import service.AppointmentService;
import service.BarberService;
import service.ClientService;
import service.exceptions.AppointmentExistsException;
import service.exceptions.InvalidInputDataException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppointmentsPanelListener implements ActionListener {

    private final JTable dataTable;
    private final DefaultTableModel model;
    private final AppointmentsPanel panel;
    private final ClientService clientService;
    private final BarberService barberService;
    private final AppointmentService appointmentService;
    private static final String[] COLUMNS = {"Id", "Services", "Client Email", "Client Name", "Barber Id", "Day", "Time", "Price"};

    public AppointmentsPanelListener(JTable dataTable, DefaultTableModel model, AppointmentsPanel panel) {
        this.dataTable = dataTable;
        this.model = model;
        this.panel = panel;
        this.clientService = new ClientService();
        this.barberService = new BarberService();
        this.appointmentService = new AppointmentService();
    }

    private void addActionPerformed() {
        String[] services = {"Beard trim", "Head shave", "Hot Towel Shave", "Men haircut"};
        JComboBox<String> serviceBox = new JComboBox<>(services);
        JComboBox<Integer> barberIds = new JComboBox<>(barberService.getBarberIds());
        JComboBox<Integer> clientsIds = new JComboBox<>(clientService.getClientData());
        JTextField idAppointment = new JTextField();
        JTextField date = new JTextField();
        JTextField time = new JTextField();

        Object[] popUp = {"Id", idAppointment, "Service", serviceBox, "Barber", barberIds, "Client", clientsIds, "Date (YYYY-MM-DD)", date, "Time", time };

        int option = JOptionPane.showConfirmDialog(panel, popUp);

        if (option == JOptionPane.OK_OPTION) {
            ServiceDAO serviceDAO = new ServiceDAO();
            ClientDAO clientDAO = new ClientDAO();
            BarberDAO barberDAO = new BarberDAO();
            Service service = serviceDAO.find(String.valueOf(serviceBox.getModel().getSelectedItem()));
            Client client = clientDAO.find((Integer) clientsIds.getModel().getSelectedItem());
            Barber barber = barberDAO.find((Integer) barberIds.getModel().getSelectedItem());

            String[] rowData = {
                    idAppointment.getText(),
                    service.getType(),
                    client.getEmail(),
                    client.getName(),
                    barber.getBarber_id().toString(),
                    date.getText(),
                    time.getText(),
                    service.getPrice().toString()
            };

            try {
                appointmentService.persistAppointmentData(rowData);
            } catch (InvalidInputDataException e) {
                JOptionPane.showMessageDialog(panel, "The input data is invalid! Try again.");
                return;
            } catch (AppointmentExistsException e) {
                JOptionPane.showMessageDialog(panel, "Appointment already exists.");
                return;
            }
            model.addRow(rowData);
        }
    }

    private void editActionPerformed() {
        int row = dataTable.getSelectedRow();
        JTextField dateEdit = new JTextField();
        JTextField timeEdit = new JTextField();

        Object[] editPopup = {
                "Date (YYYY-MM-DD)", dateEdit,
                "Time", timeEdit
        };

        int opt = JOptionPane.showConfirmDialog(panel, editPopup);

        if (opt == JOptionPane.OK_OPTION) {

            String dateNew = dateEdit.getText();
            String timeNew = timeEdit.getText();
            String[] data;

            try {
                data = new String[]{
                        (String) model.getValueAt(row, 0),
                        dateNew,
                        timeNew
                };
            } catch (ArrayIndexOutOfBoundsException e) {
                JOptionPane.showMessageDialog(panel, "Unable to edit! Try again.");
                return;
            }

            try {
                appointmentService.updateAppointmentData(data);
            } catch (InvalidInputDataException e) {
                JOptionPane.showMessageDialog(panel, "The input data is invalid! Try again.");
                return;
            } catch (AppointmentExistsException e) {
                JOptionPane.showMessageDialog(panel, "Appointment does not exist.");
                return;
            }
            model.setValueAt(dateNew, row, 5);
            model.setValueAt(timeNew, row, 6);
        }
    }

    private void deleteActionPerformed() {
        int selectedRow = dataTable.getSelectedRow();

        String[] data;

        try {
            data = new String[]{
                    (String) dataTable.getValueAt(selectedRow, 0)
            };
        } catch (ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(panel, "Unable to delete! Try again.");
            return;
        }

        try {
            appointmentService.deleteAppointmentData(data);
        } catch (InvalidInputDataException e) {
            JOptionPane.showMessageDialog(panel, "The input data is invalid! Try again.");
            return;
        } catch (AppointmentExistsException e) {
            JOptionPane.showMessageDialog(panel, "Appointment does not exist.");
            return;
        }
        model.removeRow(selectedRow);
    }

    private void refreshActionPerformed() {
        String[][] data = appointmentService.getAppointments();
        model.setDataVector(data, COLUMNS);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        switch (action) {
            case "Add App":
                addActionPerformed();
                break;
            case "Edit App":
                editActionPerformed();
                break;
            case "Delete App":
                deleteActionPerformed();
                break;
            case "Refresh App":
                refreshActionPerformed();
                break;
        }
    }
}
