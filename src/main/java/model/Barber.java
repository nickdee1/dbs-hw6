package model;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Barber")
public class Barber {

    @Id
    @Column(columnDefinition = "barber_id")
    private Integer barber_id;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinTable(name = "barb_app", joinColumns = @JoinColumn(name = "barber_id"),
            inverseJoinColumns = {@JoinColumn(name = "appointment_id", referencedColumnName = "id")})
    private List<Appointment> appointments = new ArrayList<>();


    public Integer getBarber_id() {
        return barber_id;
    }

    public void setBarber_id(Integer barber_id) {
        this.barber_id = barber_id;
    }

    public void addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
