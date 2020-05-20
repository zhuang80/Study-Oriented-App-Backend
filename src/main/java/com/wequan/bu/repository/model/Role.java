package com.wequan.bu.repository.model;

import java.util.Objects;

/**
 * @author ChrisChen
 */
public class Role {

    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()){
            return false;
        }
        if (this == obj) {
            return true;
        }
        Role that = (Role) obj;
        return Objects.equals(id,that.id);
    }

}
