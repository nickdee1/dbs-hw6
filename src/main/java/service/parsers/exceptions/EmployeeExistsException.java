package service.parsers.exceptions;

public class EmployeeExistsException extends Exception {
    public EmployeeExistsException(String message) {
        super(message);
    }
}
