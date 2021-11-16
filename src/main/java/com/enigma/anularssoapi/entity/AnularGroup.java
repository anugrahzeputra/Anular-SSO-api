package com.enigma.anularssoapi.entity;

import com.enigma.anularssoapi.dto.pojos.AnularGroupFirstState;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "mst_group")
public class AnularGroup {

    @Id
    private String id;
    @NotBlank
    private String groupName;

    @Transient
    private String AST;

    @ManyToOne
    @JoinColumn(name = "ast")
    private AnularSiteType anularSiteType;

    public AnularGroup() {
    }

    public AnularGroup(AnularGroupFirstState anularGroupFirstState) {
        this.id = anularGroupFirstState.getId();
        this.groupName = anularGroupFirstState.getGroupName();
        this.AST = anularGroupFirstState.getAST();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    @JsonIgnore
    public AnularSiteType getAnularSiteType() {
        return anularSiteType;
    }

    public void setAnularSiteType(AnularSiteType anularSiteType) {
        this.anularSiteType = anularSiteType;
    }

    public String getAST() {
        return AST;
    }

    public void setAST(String AST) {
        this.AST = AST;
    }

    @Override
    public String toString() {
        return "AnularGroup{" +
                "id='" + id + '\'' +
                ", groupName='" + groupName + '\'' +
                ", AST='" + AST + '\'' +
                ", anularSiteType=" + anularSiteType +
                '}';
    }
}
