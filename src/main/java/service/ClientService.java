package service;

import dao.ClientDAO;
import model.Client;

import java.util.List;

public class ClientService {

    private final ClientDAO dao;

    public ClientService() {
        this.dao = new ClientDAO();
    }

    public Integer[] getClientData() {
        List<Client> clientList = dao.findAll();
        Integer[] ids = new Integer[clientList.size()];
        int i = 0;

        for (Client c : clientList) {
            ids[i] = (c.getId());
            i++;
        }
        return ids;
    }
}
