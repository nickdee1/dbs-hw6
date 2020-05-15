package dao;

import model.Service;

import java.io.Serializable;

public class ServiceDAO extends DAOUnit<Service> {
    public ServiceDAO() {
        super(Service.class);
    }
}
