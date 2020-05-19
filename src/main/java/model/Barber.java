package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Barber {

    @Id
    @Column(columnDefinition = "barber_id")
    private Integer barber_id;

    @ManyToMany
    @JoinTable(name = "Appointment", joinColumns = @JoinColumn(name = "barbers_id"),
            inverseJoinColumns={@JoinColumn(name = "id"),
                    @JoinColumn(name="service_type"),
                    @JoinColumn(name = "client_id"),
                    @JoinColumn(name = "client_email"),
                    @JoinColumn(name = "barber_id"),
                    @JoinColumn(name = "day"),
                    @JoinColumn(name = "time")})
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

    public void addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
    }
}
