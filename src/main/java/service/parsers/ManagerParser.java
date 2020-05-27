package service.parsers;

import dao.DetailDAO;
import dao.EmployeeDAO;
import dao.ManagerDAO;
import model.Detail;
import model.Employee;
import model.Manager;
import service.parsers.exceptions.EmployeeExistsException;

import java.time.LocalDate;

public class ManagerParser implements EmployeeParserStrategy {
    @Override
    public void parse(Object[] data) {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        Employee employee = employeeDAO.findEmp((Integer) data[0]);

        if (employee != null) {
            Detail detail = new Detail();
            employee = new Employee();
            DetailDAO detailDAO = new DetailDAO();
            employee.setId((Integer) data[0]);
            detail.setEmployee_id((Integer) data[0]);
            detail.setFirst_name((String) data[1]);
            detail.setSecond_name((String) data[2]);
            detail.setManager((Boolean) data[3]);
            detail.setBirthday((LocalDate) data[4]);
            detail.setSalary((Double) data[5]);
            employeeDAO.persist(employee);

            Manager manager = new Manager();
            ManagerDAO managerDAO = new ManagerDAO();
            manager.setManager_id(employee.getId());
            managerDAO.persist(manager);

            detailDAO.persist(detail);
        }

    }
}
