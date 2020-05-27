package service;

import dao.DetailDAO;
import model.Detail;
import service.parsers.employee_parser.BarberParser;
import service.parsers.employee_parser.ManagerParser;

import java.util.List;

/**
 * Service class for fetching Detail info.
 */
public class DetailService {

    private final DetailDAO dao;

    private DetailContext context;

    public DetailService() {
        this.dao = new DetailDAO();
    }

    /**
     * Fetch all data from DB.
     * @return Double dimensional array with all records
     */
    public String[][] getDetails() {
        List<Detail> data = dao.findAll();
        String[][] output = new String[data.size()][];
        int i = 0;

        for (Detail a : data) {
            String[] record = {
                    a.getEmployee_id().toString(),
                    a.getFirst_name(),
                    a.getSecond_name(),
                    a.isManager().toString(),
                    a.getBirthday().toString(),
                    a.getSalary().toString(),
            };
            output[i] = record;
            i++;
        }
        return output;
    }

    public void persistEmployeeData(String[] data) {
        context = new DetailContext();

        Object[] validData = processData(data);
        Boolean isManager = (Boolean) validData[3];

        if (isManager)
            context.setParserStrategy(new ManagerParser());
        else
            context.setParserStrategy(new BarberParser());

        context.executeDataPersistence(validData);
    }

    public void updateEmployeeData(String[] data) {
        context = new DetailContext();

        Object[] validData = processData(data);
        Boolean isManager = (Boolean) validData[3];

        if (isManager)
            context.setParserStrategy(new ManagerParser());
        else
            context.setParserStrategy(new BarberParser());

        context.executeDataUpdate(validData);
    }

    private Object[] processData(String[] data) {
        context.setDataValidationStrategy(new DetailValidator());
        return context.validateData(data);
    }
}
