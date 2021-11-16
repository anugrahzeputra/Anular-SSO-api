package com.enigma.anularssoapi.dto.enumeration;

import com.enigma.anularssoapi.entity.AnularUser;

public enum AnularUserStat {
    VERIFIED("verified"),
    UNVERIFIED("unverified");

    private final String values;

    AnularUserStat(String values){
        this.values = values;
    }

    public String getValues() {
        return values;
    }
}
