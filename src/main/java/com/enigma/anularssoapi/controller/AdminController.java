package com.enigma.anularssoapi.controller;

import com.enigma.anularssoapi.dto.customresponse.StatResp;
import com.enigma.anularssoapi.entity.AnularSiteType;
import com.enigma.anularssoapi.service.anularsitetype.AnularSiteTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {

    @Autowired
    AnularSiteTypeService anularSiteTypeService;

    @PostMapping("/api/admin/type")
    public AnularSiteType addType(@RequestBody AnularSiteType anularSiteType){
        return anularSiteTypeService.create(anularSiteType);
    }

    @GetMapping("/api/type")
    public AnularSiteType getType(@RequestParam String id){
        return anularSiteTypeService.getById(id);
    }

    @GetMapping("/api/type/list")
    public List<AnularSiteType> getListType(){
        return anularSiteTypeService.getAllList();
    }

    @PutMapping("/api/type")
    public AnularSiteType updateType(@RequestBody AnularSiteType anularSiteType){
        return anularSiteTypeService.update(anularSiteType);
    }

    @DeleteMapping("/api/type")
    public StatResp deleteType(@RequestParam String id){
        return anularSiteTypeService.delete(id);
    }
}
