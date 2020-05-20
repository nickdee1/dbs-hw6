package dao;

import model.Appointment;
import model.Employee;

import java.util.Objects;

public class EmployeeDAO extends DAOUnit<Employee> {
    public EmployeeDAO() {
        super(Employee.class);
    }

    public Employee findEmp(Integer id) {
        Objects.requireNonNull(id);
        et.begin();
        Employee entity;
        entity = em.find(type, id);
        et.commit();
        return entity;
    }
}
