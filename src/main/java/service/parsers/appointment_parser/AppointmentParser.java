package service.parsers.appointment_parser;

import dao.AppointmentDAO;
import dao.BarberDAO;
import model.Appointment;
import model.Barber;
import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentParser implements AppointmentParserInterface {

    @Override
    public boolean persist(Appointment data) {
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        Appointment appointment = appointmentDAO.find(data.getId());

        if (appointment == null) {

            BarberDAO barberDAO = new BarberDAO();
            Barber b = barberDAO.find(data.getBarber_id());
            b.getAppointments().add(data);
            barberDAO.update(b);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Appointment data) {
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        Appointment appointment = appointmentDAO.find(data.getId());

        if (appointment != null) {
            appointment.setDay(data.getDay());
            appointment.setTime(data.getTime());
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
