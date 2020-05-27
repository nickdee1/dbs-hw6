package service.parsers.employee_parser;


public interface EmployeeParserStrategy {

    void persist(Object[] data);

    void update(Object[] data);
}
