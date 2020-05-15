package dao;

import model.Service;

import java.io.Serializable;

public class ServiceDAO extends DAOUnit<Service> {
    protected ServiceDAO() {
        super(Service.class);
    }
}
