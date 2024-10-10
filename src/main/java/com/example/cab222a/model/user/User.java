package com.example.cab222a.model.user;

import com.example.cab222a.model.core.IdentifiedObject;

import java.sql.Date;

/**
 * Data class for users. Used in Data Access Objects (DAOs)
 * and Model View Controllers (MVCs) for this application.
 */
public class User extends IdentifiedObject {
    private Date created;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;

    /**
     * Constructor with ID (for when users have been assigned an ID in the database).
     * @param id The ID of the user.
     * @param created The date the user's account was created.
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param email The email address of the user.
     * @param phone The phone number of the user.
     * @param password The password of the user.
     */
    public User(int id, Date created, String firstName, String lastName, String email, String phone, String password) {
        super(id);
        this.created = created;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    /**
     * Constructor with ID (for when users have not been assigned an ID in the database yet).
     * @param created The date the user's account was created.
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param email The email address of the user.
     * @param phone The phone number of the user.
     * @param password The password of the user.
     */
    public User(Date created, String firstName, String lastName, String email, String password, String phone) {
        this.created = created;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    /**
     *
     * @return The date when this user's account was created.
     */
    public Date getCreated() {
        return created;
    }

    /**
     *
     * @param created The date when this user's account was created.
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     *
     * @return The first name of this user.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param firstName The first name of this user.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return The last name of this user.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param lastName The last name of this user.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return The email address of this user.
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email The email address of this user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return The phone number of this user.
     */
    public String getPhone() {
        return phone;
    }

    /**
     *
     * @param phone The phone number of this user.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     *
     * @return The password of this user.
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password The password of this user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Concatenates user information into a human-readable format.
     * @return The String containing the said user information.
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", created='" + getCreated() + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", password='" + getPassword() + '\'' +
                '}';
    }
}
