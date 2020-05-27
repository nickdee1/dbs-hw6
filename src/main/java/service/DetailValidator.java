package service;

import java.time.DateTimeException;
import java.time.LocalDate;

public class DetailValidator implements DataValidationStrategy{
    @Override
    public Object[] validateData(String[] data) {
        Object[] output = new Object[6];
        Integer id;
        Double salary;
        String firstName;
        String secondName;
        LocalDate date;
        Boolean isManager;

        try {
            id = Integer.parseInt((String) data[0]);
            salary = Double.parseDouble((String) data[5]);
        } catch (NumberFormatException e) {
            return null;
        }

        if ( data[3].toLowerCase().equals("true") || data[3].toLowerCase().equals("false"))
            isManager = Boolean.parseBoolean(data[3].toLowerCase());
        else
            return null;

        try {
            date = LocalDate.parse(data[4]);
        } catch (DateTimeException e) {
            return null;
        }

        if (data[1].length() == 0 || data[2].length() == 0) return null;

        firstName = data[1];
        secondName = data[2];

        output[0] = id;
        output[1] = firstName;
        output[2] = secondName;
        output[3] = isManager;
        output[4] = date;
        output[5] = salary;

        return output;
    }
}
