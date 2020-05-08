package dao;

import model.Employee;

public class EmployeeDAO extends DAOUnit<Employee> {
    public EmployeeDAO() {
        super(Employee.class);
    }
}
