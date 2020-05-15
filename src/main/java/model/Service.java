package model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Service {

    @Id
    private String type;

    private Double price;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
