package service.parsers.appointment_parser;

import dao.AppointmentDAO;
import dao.BarberDAO;
import model.Appointment;
import model.Barber;
import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentParser implements AppointmentParserInterface {

    @Override
    public boolean persist(Object[] data) {
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        Appointment appointment = appointmentDAO.find((Long) data[0]);

        if (appointment == null) {
            appointment = new Appointment();
            appointment.setId((Long) data[0]);
            appointment.setService_type((String) data[1]);
            appointment.setPrice((Double) data[7]);
            appointment.setClient_email((String) data[2]);
            appointment.setClient_name((String) data[3]);
            appointment.setDay((LocalDate) data[5]);
            appointment.setTime((LocalTime) data[6]);
            appointment.setBarber_id((Integer) data[4]);

            BarberDAO barberDAO = new BarberDAO();
            Barber b = barberDAO.find(appointment.getBarber_id());
            b.getAppointments().add(appointment);
            barberDAO.update(b);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Object[] data) {
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        Appointment appointment = appointmentDAO.find((Long) data[0]);

        if (appointment != null) {
            appointment.setDay((LocalDate) data[1]);
            appointment.setTime((LocalTime) data[2]);
            appointmentDAO.update(appointment);
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(Long id) {
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        Appointment appointment = appointmentDAO.find(id);

        if (appointment != null) {
            appointmentDAO.removeAppointment(id);
            return true;
        }

        return false;
    }
}
