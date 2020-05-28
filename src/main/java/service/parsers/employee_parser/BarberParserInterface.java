package service.parsers.employee_parser;

import dao.BarberDAO;
import dao.DetailDAO;
import dao.EmployeeDAO;
import dao.ManagerDAO;
import model.Barber;
import model.Detail;
import model.Employee;

import java.time.LocalDate;

public class BarberParserInterface implements EmployeeParserInterface {

    @Override
    public void persist(Object[] data) {
        Detail detail = new Detail();
        Employee employee = new Employee();
        EmployeeDAO employeeDAO = new EmployeeDAO();
        DetailDAO detailDAO = new DetailDAO();
        employee.setId((Integer) data[0]);
        detail.setEmployee_id((Integer) data[0]);
        detail.setFirst_name((String) data[1]);
        detail.setSecond_name((String) data[2]);
        detail.setManager((Boolean) data[3]);
        detail.setBirthday((LocalDate) data[4]);
        detail.setSalary((Double) data[5]);
        employeeDAO.persist(employee);

        Barber barber = new Barber();
        BarberDAO barberDAO = new BarberDAO();
        barber.setBarber_id(employee.getId());
        barberDAO.persist(barber);

        detailDAO.persist(detail);
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

            Barber barber = barberDAO.find(detail.getEmployee_id());

            if (barber == null) {
                barber = new Barber();
                barber.setBarber_id(detail.getEmployee_id());
                managerDAO.remove(detail.getEmployee_id());
                barberDAO.persist(barber);
            }

            detailDAO.update(detail);
        }
    }

    @Override
    public void delete(Integer id) {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        BarberDAO barberDAO = new BarberDAO();
        DetailDAO detailDAO = new DetailDAO();

        Barber barber = barberDAO.find(id);

        if (barber != null) {
            detailDAO.remove(id);
            barberDAO.remove(id);
            employeeDAO.remove(id);
        }

    }
}
