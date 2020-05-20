package model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "Appointment")
public class Appointment implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "service_type")
    private String service_type;


    @Column(name = "client_id")
    private Integer client_id;


    @Column(name = "client_email")
    private String client_email;


    @Column(name = "barber_id")
    private Integer barber_id;


    @Column(name = "day")
    private LocalDate day;


    @Column(name = "time")
    private LocalTime time;

    private Double price;

    private String client_name;

    public Long getId() {
        return id;
    }


    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
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

}
