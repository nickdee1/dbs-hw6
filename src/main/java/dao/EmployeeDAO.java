package dao;

import model.Employee;

public class EmployeeDAO extends DAOUnit<Employee> {
    protected EmployeeDAO() {
        super(Employee.class);
    }
}
