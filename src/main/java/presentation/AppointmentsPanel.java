package presentation;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppointmentsPanel extends JPanel {

    private static final String[] COLUMNS = {"Services", "Client Email", "Client Name", "Barber Id", "Day", "Time", "Price"};
    private String[][] data;
    private JScrollPane scrollPane;

    public AppointmentsPanel(String[][] data) {
        super(null);
        this.data = data;

        final JTable dataTable = new JTable(data, COLUMNS);
        scrollPane = new JScrollPane(dataTable);

        scrollPane.setSize(780, 400);

        Handler h = new Handler(this);

        JPanel buttonPanel = new JPanel();
        JButton button = new JButton("Add Appointment");
        JButton button1 = new JButton("Edit Appointment");
        JButton button2 = new JButton("Delete Appointment");
        button.addActionListener(h);
        button.setActionCommand("Add App");
        button1.addActionListener(h);
        button1.setActionCommand("Edit App");
        button2.addActionListener(h);
        button2.setActionCommand("Delete App");

        buttonPanel.add(button);
        buttonPanel.add(button1);
        buttonPanel.add(button2);

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
                    String[] services = {"Beard Trim", "Head shave", "Hot Towel Shave", "Men Haircut"};
                    JComboBox<String> box = new JComboBox<>(services);
                    Object[] popUp = {"Service", box};
                    int option = JOptionPane.showConfirmDialog(panel, popUp);
                    System.out.println("tabbed");

                    if (option == JOptionPane.OK_OPTION) {

                    }
                    break;
                case "Edit App":
                    break;
            }
        }
    }
}
