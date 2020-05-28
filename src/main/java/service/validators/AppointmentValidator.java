package service.validators;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AppointmentValidator implements DataValidationStrategy {

    @Override
    public Object[] validateData(String[] data) {

        if (data.length == 1)
            return validateForDelete(data);
        else if (data.length == 3)
            return validateForUpdate(data);

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

    private Object[] validateForUpdate(String[] data) {
        Object[] out = new Object[data.length];
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

        out[0] = idAppointment;
        out[1] = newDate;
        out[2] = newTime;

        return out;
    }

    private Object[] validateForDelete(String[] data) {
        Object[] out = new Object[1];
        Long id;
        try {
            id = Long.parseLong(data[0]);
        } catch (NumberFormatException e) {
            return null;
        }

        out[0] = id;
        return out;
    }
}
