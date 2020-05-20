package dao;

import model.Appointment;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;

public class AppointmentDAO extends DAOUnit<Appointment> {
    public AppointmentDAO() {
        super(Appointment.class);
    }

    public void removeAppointment(Long id) {
        Objects.requireNonNull(id);
        et.begin();
        Appointment a = (Appointment) em.createQuery("SELECT e FROM " + type.getSimpleName() + " e WHERE e.id=:arg1 ")
                .setParameter("arg1", id)
                .getSingleResult();
        em.remove(a);
        et.commit();
    }

    public Appointment find(Long id) {
        Objects.requireNonNull(id);
        et.begin();
        Appointment entity;
        entity = em.find(type, id);
        et.commit();
        return entity;
    }
}
