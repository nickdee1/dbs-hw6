package service.parsers.employee_parser;


import model.Detail;

public interface EmployeeParserInterface {

    boolean persist(Detail data);

    boolean update(Detail data);

    boolean delete(Integer id);
}
