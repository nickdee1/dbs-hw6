package model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Employee {

    @Id
    private Integer id_emp;

    public Integer getId() {
        return id_emp;
    }

    public void setId(Integer id) {
        this.id_emp = id;
    }
}
