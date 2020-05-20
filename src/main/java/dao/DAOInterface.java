package dao;

import java.util.List;

public interface DAOInterface<T> {

    /**
     * Persist data to DB.
     * @param entity - entity to persist.
     */
    void persist(T entity);

    /** Find in DB by ID.
     * @param id - id of entity.
     * @return output entity.
     */
    T find(Integer id);

    /**
     * Update entity.
     * @param entity - entity to update.
     */
    void update(T entity);

    /**
     * Remove entity.
     * @param id ID of entity to remove.
     */
    void remove(Integer id);


    /**
     * Find all records.
     * @return List with records.
     */
    List<T> findAll();
}
