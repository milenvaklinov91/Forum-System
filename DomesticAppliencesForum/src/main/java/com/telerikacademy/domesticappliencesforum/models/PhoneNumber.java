package com.telerikacademy.domesticappliencesforum.models;

import javax.persistence.*;

@Entity
@Table(name = "phone_number")
public class PhoneNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phone_number_id")
    private int phoneNumberId;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User admin;
   @Column(name = "phone_number")
    private String phoneNumber;

    public PhoneNumber() {

    }

    public int getPhoneNumberId() {
        return phoneNumberId;
    }

    public void setPhoneNumberId(int adminID) {
        this.phoneNumberId = adminID;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
