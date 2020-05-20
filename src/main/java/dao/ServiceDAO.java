package dao;

import model.Service;

import java.io.Serializable;
import java.util.Objects;

public class ServiceDAO extends DAOUnit<Service> {
    public ServiceDAO() {
        super(Service.class);
    }

    public Service find(String typeService) {
        Objects.requireNonNull(typeService);
        et.begin();
        Service entity = (Service) em.createQuery("SELECT e FROM " + type.getSimpleName() + " e WHERE e.type=:arg1 ")
                .setParameter("arg1", typeService)
                .setMaxResults(1)
                .getSingleResult();
        et.commit();
        return entity;
    }
}
