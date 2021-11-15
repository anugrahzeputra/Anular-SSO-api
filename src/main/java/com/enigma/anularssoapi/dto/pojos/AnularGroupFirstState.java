package com.enigma.anularssoapi.dto.pojos;

import com.enigma.anularssoapi.entity.AnularGroup;

public class AnularGroupFirstState extends AnularGroup {

    private String userId;

    public AnularGroupFirstState() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
