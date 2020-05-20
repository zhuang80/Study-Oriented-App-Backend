package com.wequan.bu.repository.model;

/**
 * @author ChrisChen
 */
public class Tutor {

    private Integer id;
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
