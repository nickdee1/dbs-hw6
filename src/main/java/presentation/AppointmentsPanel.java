package presentation;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AppointmentsPanel extends JPanel {

    private static final String[] COLUMNS = {"Services", "Client Email", "Client Name", "Barber Id", "Day", "Time", "Price"};
    private String[][] data;

    public AppointmentsPanel(String[][] data) {
        super(null);
        this.data = data;

        final JTable dataTable = new JTable(data, COLUMNS);
        JScrollPane scrollPane = new JScrollPane(dataTable);

        scrollPane.setSize(780, 400);

        JPanel buttonPanel = new JPanel();
        JButton button = new JButton("Add Appointment");
        JButton button1 = new JButton("Edit Appointment");
        JButton button2 = new JButton("Delete Appointment");

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
                System.out.println(e.getFirstRow());
                DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
                Object a = model.getDataVector().elementAt((e.getFirstRow()));


                System.out.println(e.getColumn());

            }
        });
    }
}
