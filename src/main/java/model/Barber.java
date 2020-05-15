package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Barber {

    @Id
    private Integer barber_id;

    private Integer manager_superior_id;

    @ManyToMany
    private List<Appointment> appointments;

    public Barber(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Barber() {
        this.appointments = new ArrayList<>();
    }

    public Integer getBarber_id() {
        return barber_id;
    }

    public void setBarber_id(Integer barber_id) {
        this.barber_id = barber_id;
    }

    public Integer getManager_superior_id() {
        return manager_superior_id;
    }

    public void setManager_superior_id(Integer manager_superior_id) {
        this.manager_superior_id = manager_superior_id;
    }

    public void addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
    }
}
