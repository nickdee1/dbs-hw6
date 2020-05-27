package service.parsers;

import dao.BarberDAO;
import dao.DetailDAO;
import dao.EmployeeDAO;
import model.Barber;
import model.Detail;
import model.Employee;

import java.time.LocalDate;

public class BarberParser implements EmployeeParserStrategy {
    @Override
    public void parse(Object[] data) {
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
}
