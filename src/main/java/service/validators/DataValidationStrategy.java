package service.validators;

/**
 * Interface for data validation and persistence strategy.
 */
public interface DataValidationStrategy {

    /**
     * Validates unprocessed data.
     * @param data - the data to be processed.
     * @return Object[] array with valid data if no error occurs, null otherwise.
     */
    Object validateData(String[] data);

}
