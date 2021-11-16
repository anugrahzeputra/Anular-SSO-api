package com.enigma.anularssoapi.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "mst_site_type")
public class AnularSiteType {

    @Id
    private String id;
    @NotBlank
    private String nameType;

    public AnularSiteType() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameType() {
        return nameType;
    }
}
