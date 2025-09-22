package org.digitalnao.jared.trujillo.classes;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Simple user data model for JSON/CSV serialization.
 */
@JsonPropertyOrder({ "id", "name", "email" }) // Property order made with jackson
public class User {
    private int id;
    private String name;
    private String email;

    /**
     * No-args constructor required by Jackson.
     */
    public User() { }

    /**
     * Creates a user with the given fields.
     *
     * @param id    user identifier
     * @param name  user name
     * @param email user email
     */
    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /** @return user identifier */
    public int getId() {
        return id;
    }

    /** @param id user identifier */
    public void setId(int id) {
        this.id = id;
    }

    /** @return user name */
    public String getName() {
        return name;
    }

    /** @param name user name */
    public void setName(String name) {
        this.name = name;
    }

    /** @return user email */
    public String getEmail() {
        return email;
    }

    /** @param email user email */
    public void setEmail(String email) {
        this.email = email;
    }

    /** @return string representation for debugging/logging */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
