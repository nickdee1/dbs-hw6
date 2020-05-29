package service.parsers.appointment_parser;

import model.Appointment;

public interface AppointmentParserInterface {

    boolean persist(Appointment data);

    boolean update(Appointment data);

    boolean delete(Long id);
}
