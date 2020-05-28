package service.parsers.employee_parser;


public interface EmployeeParserInterface {

    boolean persist(Object[] data);

    boolean update(Object[] data);

    boolean delete(Integer id);
}
