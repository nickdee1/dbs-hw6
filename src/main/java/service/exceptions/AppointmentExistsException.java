package service.exceptions;

public class AppointmentExistsException extends Exception {
    public AppointmentExistsException(String message) {
        super(message);
    }
}
