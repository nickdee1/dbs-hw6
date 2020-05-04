package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class daoUnit {
    private static final EntityManager ENTITY_MANAGER = Persistence
            .createEntityManagerFactory("DBS").createEntityManager();
    private static final EntityTransaction TRANSACTION = ENTITY_MANAGER.getTransaction();

}
