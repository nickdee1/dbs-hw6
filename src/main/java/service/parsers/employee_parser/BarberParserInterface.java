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
    public boolean persist(Detail data) {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        Employee employee = employeeDAO.findEmp(data.getEmployee_id());

        if (employee == null) {

            employee = new Employee();
            DetailDAO detailDAO = new DetailDAO();
            employee.setId(data.getEmployee_id());
            employeeDAO.persist(employee);

            Barber barber = new Barber();
            BarberDAO barberDAO = new BarberDAO();
            barber.setBarber_id(employee.getId());
            barberDAO.persist(barber);

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

            Barber barber = barberDAO.find(data.getEmployee_id());

            if (barber == null) {
                barber = new Barber();
                barber.setBarber_id(data.getEmployee_id());
                managerDAO.remove(data.getEmployee_id());
                barberDAO.persist(barber);
            }

            detailDAO.update(data);
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(Integer id) {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        BarberDAO barberDAO = new BarberDAO();
        DetailDAO detailDAO = new DetailDAO();

        Barber barber = barberDAO.find(id);

        if (barber != null) {
            detailDAO.remove(id);
            barberDAO.remove(id);
            employeeDAO.remove(id);
            return true;
        }

        return false;
    }
}
