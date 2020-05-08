package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Objects;

public abstract class DAOUnit<T> implements DAOInterface<T> {

    private static final EntityManager em = Persistence
            .createEntityManagerFactory("DBS").createEntityManager();
    private static final EntityTransaction et = em.getTransaction();

    private final Class<T> type;

    protected DAOUnit(Class<T> type) {
        this.type = type;
    }

    public void persist(T entity) {
        Objects.requireNonNull(entity);
        et.begin();
        em.persist(entity);
        et.commit();
    }

    public T find(Integer id) {
        Objects.requireNonNull(id);
        et.begin();
        T entity = em.getReference(type, id);
        et.commit();
        return entity;
    }

    public void update(T entity) {
        Objects.requireNonNull(entity);
        et.begin();
        em.merge(entity);
        et.commit();
    }


    public void remove(Integer id) {
        Objects.requireNonNull(id);
        et.begin();
        T entity = em.getReference(type, id);
        em.remove(entity);
        et.commit();
    }
}
