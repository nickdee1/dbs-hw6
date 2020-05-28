package service.exceptions;

public class EmployeeExistsException extends Exception {
    public EmployeeExistsException(String message) {
        super(message);
    }
}
