package service;

import dao.*;
import model.*;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EmployeeService {


    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();
        BarberDAO barberDAO = new BarberDAO();
        ClientDAO clientDAO = new ClientDAO();
        ManagerDAO managerDAO = new ManagerDAO();
        ServiceDAO serviceDAO = new ServiceDAO();
        TimestampDAO timestampDAO = new TimestampDAO();
        AppointmentDAO appointmentDAO = new AppointmentDAO();

        Barber b = new Barber();
        Manager m = new Manager();
        Client c = new Client();
        Service s = new Service();
        Timestamp t = new Timestamp();
        Appointment a = new Appointment();




        m.setManager_id(21);
        b.setBarber_id(23);
        b.setManager_superior_id(21);
        c.setName("Vojteh");
        c.setId(1);
        c.setEmail("onet@two.cz");
        s.setType("Haircut");
        s.setPrice(600.0);

        String day = "2020/10/29";
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

        t.setDay(new Date(1589552910));
        t.setTime(new Time(1589552991));

        a.setBarber_id(b.getBarber_id());
        a.setClient_email(c.getEmail());
        a.setClient_id(c.getId());
        a.setDay(t.getDay());
        a.setTime(t.getTime());
        a.setService_type(s.getType());
        a.setClient_name(c.getName());
        a.setPrice(s.getPrice());



        managerDAO.persist(m);
        barberDAO.persist(b);
        clientDAO.persist(c);
        serviceDAO.persist(s);
        timestampDAO.persist(t);
        appointmentDAO.persist(a);


        List<Employee> list = dao.findAll();

    }
}
