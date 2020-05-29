package service.validators;

import model.Detail;

import java.time.DateTimeException;
import java.time.LocalDate;

public class DetailValidator implements DataValidationStrategy {

    @Override
    public Object validateData(String[] data) {

        if (data.length == 2)
            return validateForDelete(data);

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

        Detail detail = new Detail();
        detail.setEmployee_id(id);
        detail.setFirst_name(firstName);
        detail.setSecond_name(secondName);
        detail.setBirthday(date);
        detail.setSalary(salary);
        detail.setManager(isManager);

        return detail;
    }

    private Object validateForDelete(String[] data) {
        Integer id;
        Boolean isManager;

        try {
            id = Integer.parseInt((String) data[0]);
        } catch (NumberFormatException e) {
            return null;
        }

        if ( data[1].toLowerCase().equals("true") || data[1].toLowerCase().equals("false"))
            isManager = Boolean.parseBoolean(data[1].toLowerCase());
        else
            return null;


        Detail detail = new Detail();
        detail.setEmployee_id(id);
        detail.setManager(isManager);

        return detail;
    }
}
