package com.enigma.anularssoapi.controller;

import com.enigma.anularssoapi.dto.customresponse.StatResp;
import com.enigma.anularssoapi.entity.AnularSiteType;
import com.enigma.anularssoapi.entity.AnularUser;
import com.enigma.anularssoapi.service.anularsitetype.AnularSiteTypeService;
import com.enigma.anularssoapi.service.anularuser.AnularUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {

    @Autowired
    AnularSiteTypeService anularSiteTypeService;

    @Autowired
    AnularUserService anularUserService;

    @PostMapping("/api/admin/type")
    public AnularSiteType addType(@RequestBody AnularSiteType anularSiteType){
        return anularSiteTypeService.create(anularSiteType);
    }

    @PostMapping("/api/admin/user")
    public AnularUser addUser(@RequestBody AnularUser anularUser){
        return anularUserService.createByAdmin(anularUser);
    }
}
