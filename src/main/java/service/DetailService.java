package service;

import dao.DetailDAO;
import model.Detail;
import service.exceptions.EmployeeExistsException;
import service.exceptions.InvalidInputDataException;
import service.parsers.employee_parser.BarberParserInterface;
import service.parsers.employee_parser.ManagerParserInterface;
import service.validators.DetailValidator;

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
    public void persistEmployeeData(String[] data) throws InvalidInputDataException, EmployeeExistsException {
        context = new DataContext();

        Detail validData = (Detail) processData(data);

        if (validData == null)
            throw new InvalidInputDataException("The input data is invalid");

        Boolean isManager = validData.isManager();

        if (isManager)
            context.setParserStrategy(new ManagerParserInterface());
        else
            context.setParserStrategy(new BarberParserInterface());

        if (!context.executeEmployeeDataPersistence(validData))
            throw new EmployeeExistsException("Employee already exixts!");
    }

    /**
     * Validate and update employee data in DB.
     * @param data - unprocessed data to be edited.
     */
    public void updateEmployeeData(String[] data) throws InvalidInputDataException, EmployeeExistsException {
        context = new DataContext();

        Detail validData = (Detail) processData(data);

        if (validData == null)
            throw new InvalidInputDataException("The input data is invalid");

        Boolean isManager = validData.isManager();

        if (isManager)
            context.setParserStrategy(new ManagerParserInterface());
        else
            context.setParserStrategy(new BarberParserInterface());

        if (!context.executeEmployeeDataUpdate(validData))
            throw new EmployeeExistsException("Employee does not exist!");
    }

    public void deleteEmployeeData(String[] data) throws InvalidInputDataException, EmployeeExistsException {
        context = new DataContext();

        Detail validData = (Detail) processData(data);

        if (validData == null)
            throw new InvalidInputDataException("The input data is invalid");

        Integer id =  validData.getEmployee_id();
        Boolean isManager = validData.isManager();

        if (isManager)
            context.setParserStrategy(new ManagerParserInterface());
        else
            context.setParserStrategy(new BarberParserInterface());

        if (!context.executeEmployeeDelete(id))
            throw new EmployeeExistsException("Employee does not exist!");
    }

    private Object processData(String[] data) {
        context.setDataValidationStrategy(new DetailValidator());
        return context.validateData(data);
    }
}
