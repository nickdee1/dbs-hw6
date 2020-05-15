package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Appointment implements Serializable {

    @Id
    private String service_type;

    @Id
    private Integer client_id;

    @Id
    private String client_email;

    @Id
    private Integer barber_id;

    @Id
    private Date day;

    @Id
    private Time time;

    private Double price;

    private String client_name;

    @ManyToMany(mappedBy = "appointments")
    private List<Barber> barbers;

    public Appointment(List<Barber> barbers) {
        this.barbers = barbers;
    }

    public Appointment() {
        this.barbers = new ArrayList<>();
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public Integer getClient_id() {
        return client_id;
    }

    public void setClient_id(Integer client_id) {
        this.client_id = client_id;
    }

    public String getClient_email() {
        return client_email;
    }

    public void setClient_email(String client_email) {
        this.client_email = client_email;
    }

    public Integer getBarber_id() {
        return barber_id;
    }

    public void setBarber_id(Integer barber_id) {
        this.barber_id = barber_id;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public void addBarber(Barber barber) {
        this.barbers.add(barber);
    }
}
