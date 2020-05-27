package service;

import service.parsers.EmployeeParserStrategy;

public class DetailContext {

    private EmployeeParserStrategy strategy;

    protected void setStrategy(EmployeeParserStrategy strategy) {
        this.strategy = strategy;
    }

    protected void executeParser(Object[] data) {
        strategy.parse(data);
    }
}
