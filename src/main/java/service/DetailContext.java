package service;

import service.parsers.employee_parser.EmployeeParserStrategy;

public class DetailContext {

    private EmployeeParserStrategy strategy;

    private DataValidationStrategy dataValidationStrategy;

    protected void setParserStrategy(EmployeeParserStrategy strategy) {
        this.strategy = strategy;
    }

    protected void executeDataPersistence(Object[] data) {
        strategy.persist(data);
    }

    protected void executeDataUpdate(Object[] data) {
        strategy.update(data);
    }

    protected void setDataValidationStrategy(DataValidationStrategy dataValidationStrategy) {
        this.dataValidationStrategy = dataValidationStrategy;
    }

    protected Object[] validateData(String[] data) {
        return dataValidationStrategy.validateData(data);
    }
}
