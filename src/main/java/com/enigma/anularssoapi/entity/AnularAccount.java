package com.enigma.anularssoapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "mst_account")
public class AnularAccount {

    @Id
    @GeneratedValue(generator = "uid")
    @GenericGenerator(name = "uid", strategy = "uuid")
    private String id;

    @Transient
    private String AUID;

    @ManyToOne
    @JoinColumn(name = "auid")
    private AnularUser anularUser;

    @Transient
    private String AGID;

    @ManyToOne
    @JoinColumn(name = "agid")
    private AnularGroup anularGroup;

    public AnularAccount() {
    }

    public AnularAccount(String AUID, String AGID) {
        this.AUID = AUID;
        this.AGID = AGID;
    }

    public void setAUID(String AUID) {
        this.AUID = AUID;
    }

    public void setAnularUser(AnularUser anularUser) {
        this.anularUser = anularUser;
    }

    public void setAGID(String AGID) {
        this.AGID = AGID;
    }

    public void setAnularGroup(AnularGroup anularGroup) {
        this.anularGroup = anularGroup;
    }

    public String getId() {
        return id;
    }

    public String getAUID() {
        return AUID;
    }

    @JsonIgnore
    public AnularUser getAnularUser() {
        return anularUser;
    }

    public String getAGID() {
        return AGID;
    }

    @JsonIgnore
    public AnularGroup getAnularGroup() {
        return anularGroup;
    }
}
