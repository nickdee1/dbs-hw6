package service.parsers.employee_parser;

import dao.BarberDAO;
import dao.DetailDAO;
import dao.EmployeeDAO;
import dao.ManagerDAO;
import model.Detail;
import model.Employee;
import model.Manager;

import java.time.LocalDate;

public class ManagerParserInterface implements EmployeeParserInterface {
    @Override
    public void persist(Object[] data) {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        Employee employee = employeeDAO.findEmp((Integer) data[0]);

        if (employee == null) {
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

    @Override
    public void update(Object[] data) {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        BarberDAO barberDAO = new BarberDAO();
        ManagerDAO managerDAO = new ManagerDAO();
        Employee employee = employeeDAO.findEmp((Integer) data[0]);

        if (employee != null) {
            Detail detail = new Detail();
            DetailDAO detailDAO = new DetailDAO();
            detail.setEmployee_id((Integer) data[0]);
            detail.setFirst_name((String) data[1]);
            detail.setSecond_name((String) data[2]);
            detail.setManager((Boolean) data[3]);
            detail.setBirthday((LocalDate) data[4]);
            detail.setSalary((Double) data[5]);

            Manager manager = managerDAO.find(detail.getEmployee_id());

            if (manager == null) {
                manager = new Manager();
                manager.setManager_id(detail.getEmployee_id());
                barberDAO.remove(detail.getEmployee_id());
                managerDAO.persist(manager);
            }

            detailDAO.update(detail);
        }
    }

    @Override
    public void delete(Integer id) {

    }
}
