package dao;

public interface DAOInterface<T> {

    /**
     * @param entity
     */
    void persist(T entity);

    /**
     * @param id
     * @return
     */
    T find(Integer id);

    /**
     * @param entity
     * @return
     */
    void update(T entity);

    /**
     * @param id
     * @return
     */
    void remove(Integer id);
}