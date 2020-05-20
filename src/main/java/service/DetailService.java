package service;

import dao.DetailDAO;
import model.Appointment;
import model.Detail;

import java.util.List;

/**
 * Service class for fetching Detail info.
 */
public class DetailService {

    private final DetailDAO dao;

    public DetailService() {
        this.dao = new DetailDAO();
    }

    /**
     * Fetch all data from DB.
     * @return Double dimensional array with all records
     */
    public String[][] getDetails() {
        List<Detail> data = dao.findAll();
        String[][] output = new String[data.size()][];
        int i = 0;

        for (Detail a : data) {
            String[] record = {
                    a.getEmployee_id().toString(),
                    a.getFirst_name(),
                    a.getSecond_name(),
                    a.isManager().toString(),
                    a.getBirthday().toString(),
                    a.getSalary().toString(),
            };
            output[i] = record;
            i++;
        }
        return output;
    }


}
