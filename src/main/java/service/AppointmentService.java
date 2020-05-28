package service;

import dao.AppointmentDAO;
import model.Appointment;
import service.exceptions.AppointmentExistsException;
import service.exceptions.InvalidInputDataException;
import service.parsers.appointment_parser.AppointmentParser;
import service.validators.AppointmentValidator;

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

    public void persistAppointmentData(String[] data) throws AppointmentExistsException, InvalidInputDataException {
        context = new DataContext();

        Object[] validData = processData(data);
        if (validData == null)
            throw new InvalidInputDataException("Invalid input data!");

        context.setParserStrategy(new AppointmentParser());

        if (!context.executeAppointmentDataPersistence(validData))
            throw new AppointmentExistsException("Appointment already exists");
    }

    public void updateAppointmentData(String[] data) throws AppointmentExistsException, InvalidInputDataException {
        context = new DataContext();

        Object[] validData = processData(data);
        if (validData == null)
            throw new InvalidInputDataException("Invalid input data!");

        context.setParserStrategy(new AppointmentParser());

        if (!context.executeAppointmentDataUpdate(validData))
            throw new AppointmentExistsException("Appointment does not exist");
    }

    public void deleteAppointmentData(String[] data) throws AppointmentExistsException, InvalidInputDataException {
        context = new DataContext();

        Object[] validData = processData(data);
        if (validData == null)
            throw new InvalidInputDataException("Invalid input data!");

        Long id = (Long) validData[0];
        context.setParserStrategy(new AppointmentParser());

        if (!context.executeAppointmentDelete(id))
            throw new AppointmentExistsException("Appointment does not exist");
    }

    private Object[] processData(String[] data) {
        context.setDataValidationStrategy(new AppointmentValidator());
        return context.validateData(data);
    }
}
