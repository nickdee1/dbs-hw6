package dao;

import model.Appointment;

public class AppointmentDAO extends DAOUnit<Appointment> {
    protected AppointmentDAO() {
        super(Appointment.class);
    }
}
