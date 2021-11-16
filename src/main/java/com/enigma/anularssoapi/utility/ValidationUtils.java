package com.enigma.anularssoapi.utility;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class ValidationUtils {

    public void idIsNull(String id) {
        if(!(id == null)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "your id has been exist somewhere");
        }
    }

    public void idIsNotNull(String id) {
        if(id == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "oops, are you a new one ?");
        }
    }
}
