package service;

import dao.AppointmentDAO;
import model.Appointment;

import java.util.List;

public class AppointmentService {

    private final AppointmentDAO dao;

    public AppointmentService() {
        dao = new AppointmentDAO();
    }

    public String[][] getAppointments() {
        List<Appointment> data = dao.findAll();
        String[][] output = new String[data.size()][];
        int i = 0;

        for (Appointment a : data) {
            String[] record = {
                    a.getId().toString(),
                    a.getService_type(),
                    a.getClient_email(),
                    a.getClient_name(),
                    a.getBarber_id().toString(),
                    a.getDay().toString(),
                    a.getTime().toString(),
                    a.getPrice().toString()
            };
            output[i] = record;
            i++;
        }
        return output;
    }
}
