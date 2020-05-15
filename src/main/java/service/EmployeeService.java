package service;

import dao.DetailDAO;
import dao.EmployeeDAO;
import model.Employee;

import java.util.List;

public class EmployeeService {


    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();



        List<Employee> list = dao.findAll();

    }
}
