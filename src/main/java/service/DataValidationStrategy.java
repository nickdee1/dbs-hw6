package service;

/**
 * Interface for data validation and persistence strategy.
 */
interface DataValidationStrategy {

    /**
     * Validates unprocessed data.
     * @param data - the data to be processed.
     * @return Object[] array with valid data if no error occurs, null otherwise.
     */
    Object[] validateData(String[] data);

}
