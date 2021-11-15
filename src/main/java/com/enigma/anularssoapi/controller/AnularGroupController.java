package com.enigma.anularssoapi.controller;

import com.enigma.anularssoapi.dto.customresponse.StatResp;
import com.enigma.anularssoapi.dto.pojos.AnularGroupFirstState;
import com.enigma.anularssoapi.entity.AnularGroup;
import com.enigma.anularssoapi.service.anulargroup.AnularGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AnularGroupController {

    @Autowired
    AnularGroupService anularGroupService;

    @PostMapping("/api/group/request")
    public AnularGroup requestGroup(@RequestBody AnularGroupFirstState anularGroupFirstState){
        return anularGroupService.createByUser(anularGroupFirstState);
    }

    @GetMapping("/api/group")
    public AnularGroup getGroup(@RequestParam String id){
        return anularGroupService.getById(id);
    }

    @GetMapping("/api/group/list")
    public List<AnularGroup> getAllGroup(){
        return anularGroupService.getAllList();
    }

    @PutMapping("/api/group")
    public AnularGroup updateGroup(@RequestBody AnularGroup anularGroup){
        return anularGroupService.update(anularGroup);
    }

    @DeleteMapping("/api/group")
    public StatResp deleteGroup(@RequestParam String id){
        return anularGroupService.delete(id);
    }
}
