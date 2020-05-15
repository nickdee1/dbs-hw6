package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Employee implements Serializable {

    @Id
    private Integer id_emp;

    public Integer getId() {
        return id_emp;
    }

    public void setId(Integer id) {
        this.id_emp = id;
    }
}
