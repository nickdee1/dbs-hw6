package service;

import dao.BarberDAO;
import model.Barber;

import java.util.List;

public class BarberService {

    private final BarberDAO dao;

    public BarberService() {
        this.dao = new BarberDAO();
    }

    public Integer[] getBarberIds() {
        List<Barber> barberList = dao.findAll();
        Integer[] ids = new Integer[barberList.size()];
        int i = 0;

        for (Barber b : barberList) {
            ids[i] = (b.getBarber_id());
            i++;
        }

        return ids;
    }
}
