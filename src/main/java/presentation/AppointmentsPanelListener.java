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


    public AppointmentsPanelListener(JTable dataTable, DefaultTableModel model, AppointmentsPanel panel) {
        this.dataTable = dataTable;
        this.model = model;
        this.panel = panel;
        this.clientService = new ClientService();
        this.barberService = new BarberService();
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

            AppointmentService appointmentService = new AppointmentService();
            appointmentService.persistAppointmentData(rowData);

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

            String[] data = {
                    (String) model.getValueAt(row, 0),
                    dateNew,
                    timeNew
            };
            AppointmentService appointmentService = new AppointmentService();
            appointmentService.updateAppointmentData(data);

            model.setValueAt(dateNew, row, 5);
            model.setValueAt(timeNew, row, 6);
        }
    }

    private void deleteActionPerformed() {
        AppointmentDAO dao = new AppointmentDAO();
        int selectedRow = dataTable.getSelectedRow();
        Long id = Long.parseLong((String) dataTable.getValueAt(selectedRow, 0));
        model.removeRow(selectedRow);
        dao.removeAppointment(id);
    }

    private void refreshActionPerformed() {

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
//                AppointmentService service = new AppointmentService();
//                data = service.getAppointments();
//                model.setDataVector(data, COLUMNS);
                break;
        }
    }
}
