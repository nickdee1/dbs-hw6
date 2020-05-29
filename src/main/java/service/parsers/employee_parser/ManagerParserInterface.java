package service.parsers.employee_parser;

import dao.BarberDAO;
import dao.DetailDAO;
import dao.EmployeeDAO;
import dao.ManagerDAO;
import model.Detail;
import model.Employee;
import model.Manager;


public class ManagerParserInterface implements EmployeeParserInterface {
    @Override
    public boolean persist(Detail data) {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        Employee employee = employeeDAO.findEmp(data.getEmployee_id());

        if (employee == null) {

            employee = new Employee();
            DetailDAO detailDAO = new DetailDAO();
            employee.setId(data.getEmployee_id());
            employeeDAO.persist(employee);

            Manager manager = new Manager();
            ManagerDAO managerDAO = new ManagerDAO();
            manager.setManager_id(employee.getId());
            managerDAO.persist(manager);

            detailDAO.persist(data);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Detail data) {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        BarberDAO barberDAO = new BarberDAO();
        ManagerDAO managerDAO = new ManagerDAO();
        Employee employee = employeeDAO.findEmp(data.getEmployee_id());

        if (employee != null) {
            DetailDAO detailDAO = new DetailDAO();

            Manager manager = managerDAO.find(data.getEmployee_id());

            if (manager == null) {
                manager = new Manager();
                manager.setManager_id(data.getEmployee_id());
                barberDAO.remove(data.getEmployee_id());
                managerDAO.persist(manager);
            }

            detailDAO.update(data);
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(Integer id) {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        ManagerDAO managerDAO = new ManagerDAO();
        DetailDAO detailDAO = new DetailDAO();

        Manager manager = managerDAO.find(id);

        if (manager != null) {
            detailDAO.remove(id);
            managerDAO.remove(id);
            employeeDAO.remove(id);
            return true;
        }

        return false;
    }
}
