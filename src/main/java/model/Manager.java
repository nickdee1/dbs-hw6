package model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Manager {

    @Id
    private Integer manager_id;

    public Integer getManager_id() {
        return manager_id;
    }

    public void setManager_id(Integer manager_id) {
        this.manager_id = manager_id;
    }

}
