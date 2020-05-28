package service.parsers.appointment_parser;

public interface AppointmentParserInterface {

    boolean persist(Object[] data);

    boolean update(Object[] data);

    boolean delete(Long id);
}
