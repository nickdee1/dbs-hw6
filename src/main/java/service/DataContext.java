package service;

import model.Appointment;
import model.Detail;
import service.exceptions.EmployeeExistsException;
import service.parsers.appointment_parser.AppointmentParserInterface;
import service.parsers.employee_parser.EmployeeParserInterface;
import service.validators.DataValidationStrategy;

public class DataContext {

    private EmployeeParserInterface employeeStrategy;

    private AppointmentParserInterface appointmentStrategy;

    private DataValidationStrategy dataValidationStrategy;

    protected void setParserStrategy(EmployeeParserInterface strategy) {
        employeeStrategy = strategy;
    }

    protected void setParserStrategy(AppointmentParserInterface strategy) {
        appointmentStrategy = strategy;
    }

    protected boolean executeEmployeeDataPersistence(Detail data){
        return employeeStrategy.persist(data);
    }

    protected boolean executeEmployeeDataUpdate(Detail data) {
        return employeeStrategy.update(data);
    }

    protected boolean executeAppointmentDataPersistence(Appointment data) {
        return appointmentStrategy.persist(data);
    }

    protected boolean executeEmployeeDelete(Integer id) {
        return employeeStrategy.delete(id);
    }

    protected boolean executeAppointmentDelete(Long id) {
        return appointmentStrategy.delete(id);
    }

    protected boolean executeAppointmentDataUpdate(Appointment data) {
        return appointmentStrategy.update(data);
    }

    protected void setDataValidationStrategy(DataValidationStrategy dataValidationStrategy) {
        this.dataValidationStrategy = dataValidationStrategy;
    }

    protected Object validateData(String[] data) {
        return dataValidationStrategy.validateData(data);
    }
}
