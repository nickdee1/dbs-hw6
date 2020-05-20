package presentation;

import dao.AppointmentDAO;
import dao.BarberDAO;
import dao.ClientDAO;
import dao.ServiceDAO;
import model.Appointment;
import model.Barber;
import model.Client;
import model.Service;
import service.AppointmentService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AppointmentsPanel extends JPanel {

    private static final String[] COLUMNS = {"Id", "Services", "Client Email", "Client Name", "Barber Id", "Day", "Time", "Price"};
    private String[][] data;
    private JScrollPane scrollPane;
    private final JTable dataTable;
    private final DefaultTableModel model;

    public AppointmentsPanel(String[][] data) {
        super(null);
        this.data = data;

        model = new DefaultTableModel(data, COLUMNS);
        dataTable = new JTable(model);
        scrollPane = new JScrollPane(dataTable);

        scrollPane.setSize(780, 400);

        Handler h = new Handler(this);

        JPanel buttonPanel = new JPanel();
        JButton button = new JButton("Add Appointment");
        JButton button1 = new JButton("Edit Appointment");
        JButton button2 = new JButton("Delete Appointment");
        JButton button3 = new JButton("Refresh");
        button.addActionListener(h);
        button.setActionCommand("Add App");
        button1.addActionListener(h);
        button1.setActionCommand("Edit App");
        button2.addActionListener(h);
        button2.setActionCommand("Delete App");
        button3.addActionListener(h);
        button3.setActionCommand("Refresh App");

        buttonPanel.add(button);
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);

        buttonPanel.setLayout(new GridLayout());
        buttonPanel.setBounds(0, 400, 780, 50);

        add(scrollPane);
        add(buttonPanel);
    }


    private class Handler implements ActionListener {

        AppointmentsPanel panel;

        public Handler(AppointmentsPanel panel) {
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String action = e.getActionCommand();

            switch (action) {
                case "Add App":
                    String[] services = {"Beard trim", "Head shave", "Hot Towel Shave", "Men haircut"};
                    Integer[] ids = getBarberIds();
                    Integer[] clientIds = getClientData();
                    JComboBox<String> box = new JComboBox<>(services);
                    JComboBox<Integer> barberIds = new JComboBox<>(ids);
                    JComboBox<Integer> clientsIds = new JComboBox<>(clientIds);
                    JTextField idA = new JTextField();
                    JTextField date = new JTextField();
                    JTextField time = new JTextField();
                    Object[] popUp = {"Id", idA, "Service", box, "Barber", barberIds, "Client", clientsIds, "Date (YYYY-MM-DD)", date, "Time", time};


                    int option = JOptionPane.showConfirmDialog(panel, popUp);
                    System.out.println("tabbed");

                    if (option == JOptionPane.OK_OPTION) {
                        ServiceDAO serviceDAO = new ServiceDAO();
                        ClientDAO clientDAO = new ClientDAO();
                        BarberDAO barberDAO = new BarberDAO();
                        Service service = serviceDAO.find(String.valueOf(box.getModel().getSelectedItem()));
                        Client client = clientDAO.find((Integer) clientsIds.getModel().getSelectedItem());
                        Barber barber = barberDAO.find((Integer) barberIds.getModel().getSelectedItem());

                        String[] rowData = {
                                idA.getText(),
                                service.getType(),
                                client.getEmail(),
                                client.getName(),
                                barber.getBarber_id().toString(),
                                date.getText(),
                                time.getText(),
                                service.getPrice().toString()
                        };
                        Object[] formattedData = checkData(rowData);
                        if (formattedData != null) {
                            AppointmentDAO appointmentDAO = new AppointmentDAO();
                            Appointment test = appointmentDAO.find((Long) formattedData[0]);
                            if (test != null) {
                                JOptionPane.showMessageDialog(panel, "Appointment already exists!", "error", JOptionPane.ERROR_MESSAGE);
                                break;
                            }

                            Appointment appointment = new Appointment();
                            appointment.setId((Long) formattedData[0]);
                            appointment.setService_type((String) formattedData[1]);
                            appointment.setPrice((Double) formattedData[7]);
                            appointment.setClient_email((String) formattedData[2]);
                            appointment.setClient_name((String) formattedData[3]);
                            appointment.setDay((LocalDate) formattedData[5]);
                            appointment.setTime((LocalTime) formattedData[6]);
                            appointment.setBarber_id((Integer) formattedData[4]);
                            Barber b = barberDAO.find(appointment.getBarber_id());
                            b.getAppointments().add(appointment);
                            barberDAO.update(b);
                            model.addRow(rowData);
                        } else {
                            JOptionPane.showMessageDialog(panel, "Data is invalid", "Data error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    break;
                case "Edit App":
                    int row = dataTable.getSelectedRow();
                    JTextField dateEdit = new JTextField();
                    JTextField timeEdit = new JTextField();
                    Object[] editPopup = { "Date (YYYY-MM-DD)", dateEdit, "Time", timeEdit };
                    int opt = JOptionPane.showConfirmDialog(panel, editPopup);
                    if (opt == JOptionPane.OK_OPTION) {

                        String dateNew = dateEdit.getText();
                        String timeNew = timeEdit.getText();
                        LocalDate newDate;
                        LocalTime newTime;

                        try {
                            newDate = LocalDate.parse(dateNew);
                            newTime = LocalTime.parse(timeNew, DateTimeFormatter.ofPattern("HH:mm"));
                        } catch (DateTimeException ex) {
                            JOptionPane.showMessageDialog(panel, "Data is invalid", "Data error", JOptionPane.ERROR_MESSAGE);
                            break;
                        }

                        Long id = Long.parseLong((String) model.getValueAt(row, 0));
                        AppointmentDAO dao = new AppointmentDAO();
                        Appointment a = dao.find(id);
                        a.setDay(newDate);
                        a.setTime(newTime);
                        dao.update(a);

                        model.setValueAt(newDate.toString(), row, 5);
                        model.setValueAt(newTime.toString(), row, 6);
                    }

                    break;
                case "Delete App":
                    AppointmentDAO dao = new AppointmentDAO();
                    int selectedRow = dataTable.getSelectedRow();
                    Long id = Long.parseLong((String) dataTable.getValueAt(selectedRow, 0));
                    model.removeRow(selectedRow);
                    dao.removeAppointment(id);
                    break;
                case "Refresh App":
                    AppointmentService service = new AppointmentService();
                    data = service.getAppointments();
                    model.setDataVector(data, COLUMNS);
                    break;
            }
        }

        private Object[] checkData(String[] data) {
            Object[] out = new Object[data.length];
            LocalDate date;
            LocalTime time;
            Long id;

            try {
                id = Long.parseLong(data[0]);
            } catch (NumberFormatException e) {
                return null;
            }

            try {
                date = LocalDate.parse(data[5]);
            } catch (DateTimeException e) {
                return null;
            }

            try {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
                time = LocalTime.parse(data[6], dtf);
            } catch (DateTimeException e) {
                return null;
            }

            out[0] = id;
            out[1] = data[1];
            out[2] = data[2];
            out[3] = data[3];
            out[4] = Integer.parseInt(data[4]);
            out[5] = date;
            out[6] = time;
            out[7] = Double.parseDouble(data[7]);

            return out;
        }

        private Integer[] getBarberIds() {
            BarberDAO barberDAO = new BarberDAO();
            List<Barber> barberList = barberDAO.findAll();
            Integer[] ids = new Integer[barberList.size()];
            int i = 0;

            for (Barber b : barberList) {
                ids[i] = (b.getBarber_id());
                i++;
            }

            return ids;
        }

        private Integer[] getClientData() {
            ClientDAO clientDAO = new ClientDAO();
            List<Client> clientList = clientDAO.findAll();
            Integer[] ids = new Integer[clientList.size()];
            int i = 0;

            for (Client c : clientList) {
                ids[i] = (c.getId());
                i++;
            }
            return ids;
        }
    }
}
