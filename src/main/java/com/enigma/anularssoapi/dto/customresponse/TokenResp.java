package com.enigma.anularssoapi.dto.customresponse;

public class TokenResp {

    private String stat;
    private String token;

    public TokenResp() {
    }

    public TokenResp(String stat, String token) {
        this.stat = stat;
        this.token = token;
    }

    public String getStat() {
        return stat;
    }

    public String getToken() {
        return token;
    }
}
