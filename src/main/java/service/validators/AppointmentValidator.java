package service.validators;

import model.Appointment;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AppointmentValidator implements DataValidationStrategy {

    @Override
    public Object validateData(String[] data) {

        if (data.length == 1)
            return validateForDelete(data);
        else if (data.length == 3)
            return validateForUpdate(data);

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

        Appointment appointment = new Appointment();
        appointment.setId(id);
        appointment.setService_type(data[1]);
        appointment.setClient_email(data[2]);
        appointment.setClient_name(data[3]);
        appointment.setBarber_id(Integer.parseInt(data[4]));
        appointment.setDay(date);
        appointment.setTime(time);
        appointment.setPrice(Double.parseDouble(data[7]));


        return appointment;
    }

    private Object validateForUpdate(String[] data) {
        String id = data[0];
        String dateNew = data[1];
        String timeNew = data[2];
        LocalDate newDate;
        LocalTime newTime;
        Long idAppointment;

        try {
            idAppointment = Long.parseLong(id);
        } catch (NumberFormatException e) {
            return null;
        }

        try {
            newDate = LocalDate.parse(dateNew);
            newTime = LocalTime.parse(timeNew, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeException ex) {
            return null;
        }

        Appointment appointment = new Appointment();
        appointment.setId(idAppointment);
        appointment.setDay(newDate);
        appointment.setTime(newTime);

        return appointment;
    }

    private Object validateForDelete(String[] data) {
        Long id;
        try {
            id = Long.parseLong(data[0]);
        } catch (NumberFormatException e) {
            return null;
        }

        Appointment appointment = new Appointment();
        appointment.setId(id);
        return appointment;
    }
}
