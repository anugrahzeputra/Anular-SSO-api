package com.enigma.anularssoapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "MST_USER")
public class AnularUser {

    @Id
    private String id;
    @NotBlank
    private String fullName;
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
    @NotBlank
    private String gender;
    @NotBlank
    private String phoneNumber;

    @Transient
    private String AGID;

    @ManyToOne
    @JoinColumn(name = "AGID")
    private AnularGroup anularGroup;

    private String anularUserStat;

    public AnularUser() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAGID() {
        return AGID;
    }

    public void setAGID(String AGID) {
        this.AGID = AGID;
    }

    public void setAnularGroup(AnularGroup anularGroup) {
        this.anularGroup = anularGroup;
    }

    @JsonIgnore
    public AnularGroup getAnularGroup() {
        return anularGroup;
    }

    public String getAnularUserStat() {
        return anularUserStat;
    }

    public void setAnularUserStat(String anularUserStat) {
        this.anularUserStat = anularUserStat;
    }
}
