package service;

import dao.AppointmentDAO;
import model.Appointment;
import service.parsers.appointment_parser.AppointmentParser;

import java.util.List;


/**
 * Service class for fetching Appointment info.
 */
public class AppointmentService {

    private final AppointmentDAO dao;

    private DataContext context;

    public AppointmentService() {
        dao = new AppointmentDAO();
    }

    /**
     * Fetch all data from DB.
     * @return Double dimensional array with all records
     */
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

    public void persistAppointmentData(String[] data) {
        context = new DataContext();

        Object[] validData = processData(data);
        context.setParserStrategy(new AppointmentParser());

        context.executeAppointmentDataPersistence(validData);
    }

    public void updateAppointmentData(String[] data) {
        context = new DataContext();

        Object[] validData = processData(data);
        context.setParserStrategy(new AppointmentParser());

        context.executeAppointmentDataUpdate(validData);
    }

    public void deleteAppointmentData(String[] data) {
        context = new DataContext();

        Object[] validData = processData(data);
        Long id = (Long) validData[0];
        context.setParserStrategy(new AppointmentParser());

        context.executeAppointmentDelete(id);
    }

    private Object[] processData(String[] data) {
        context.setDataValidationStrategy(new AppointmentValidator());
        return context.validateData(data);
    }
}
