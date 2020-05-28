package service;

import dao.DetailDAO;
import model.Detail;
import service.parsers.employee_parser.BarberParserInterface;
import service.parsers.employee_parser.ManagerParserInterface;

import java.util.List;

/**
 * Service class for fetching Detail info.
 */
public class DetailService {

    private final DetailDAO dao;

    private DataContext context;

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

    /**
     * Validate and persist employee data to DB.
     * @param data - unprocessed data to be persisted.
     */
    public void persistEmployeeData(String[] data) {
        context = new DataContext();

        Object[] validData = processData(data);
        Boolean isManager = (Boolean) validData[3];

        if (isManager)
            context.setParserStrategy(new ManagerParserInterface());
        else
            context.setParserStrategy(new BarberParserInterface());

        context.executeEmployeeDataPersistence(validData);
    }

    /**
     * Validate and update employee data in DB.
     * @param data - unprocessed data to be edited.
     */
    public void updateEmployeeData(String[] data) {
        context = new DataContext();

        Object[] validData = processData(data);
        Boolean isManager = (Boolean) validData[3];

        if (isManager)
            context.setParserStrategy(new ManagerParserInterface());
        else
            context.setParserStrategy(new BarberParserInterface());

        context.executeEmployeeDataUpdate(validData);
    }

    public void deleteEmployeeData(String[] data) {
        context = new DataContext();

        Object[] validData = processData(data);
        Integer id = (Integer) validData[0];
        Boolean isManager = (Boolean) validData[1];

        if (isManager)
            context.setParserStrategy(new ManagerParserInterface());
        else
            context.setParserStrategy(new BarberParserInterface());

        context.executeEmployeeDelete(id);
    }

    private Object[] processData(String[] data) {
        context.setDataValidationStrategy(new DetailValidator());
        return context.validateData(data);
    }
}
