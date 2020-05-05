package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import java.util.Objects;

public abstract class DAOUnit<T> implements DAOInterface<T> {

    private static final EntityManager em = Persistence
            .createEntityManagerFactory("DBS").createEntityManager();
    private static final EntityTransaction TRANSACTION = em.getTransaction();

    private final Class<T> type;

    protected DAOUnit(Class<T> type) {
        this.type = type;
    }

    public void persist(T entity) {
        Objects.requireNonNull(entity);
        try {
           em.persist(entity);
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }

    public T find(Integer id) {
        Objects.requireNonNull(id);
        return em.find(type, id);
    }

    public void update(T entity) {
        Objects.requireNonNull(entity);
        try {
            em.merge(entity);
        } catch (RuntimeException e) {
            throw new PersistenceException();
        }
    }

    // Probably should be rewritten
    public void remove(T entity) {
        Objects.requireNonNull(entity);
        try {
            em.remove(entity);
        } catch (RuntimeException e) {
            throw new PersistenceException();
        }
    }
}
