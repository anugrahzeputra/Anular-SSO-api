package com.enigma.anularssoapi.service;

import com.enigma.anularssoapi.dto.customresponse.StatResp;

import java.util.List;

public interface CRUDService<T> {
    T create(T t);
    List<T> getAllList();
    T getById(String id);
    T update(T t);
    StatResp delete(String id);
}
