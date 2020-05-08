package service;

import dao.DetailDAO;
import dao.EmployeeDAO;
import model.Employee;

public class EmployeeService {


    public static void main(String[] args) {
        Employee em = new Employee();
        EmployeeDAO dao = new EmployeeDAO();

        em.setId(25);
        dao.persist(em);

        Employee em1 = dao.find(25);
        System.out.println(em1.getId());

        DetailDAO dao1 = new DetailDAO();

    }
}
