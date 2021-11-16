package com.enigma.anularssoapi.controller;

import com.enigma.anularssoapi.dto.customresponse.StatResp;
import com.enigma.anularssoapi.dto.customresponse.TokenResp;
import com.enigma.anularssoapi.dto.pojos.AnularUserCredential;
import com.enigma.anularssoapi.entity.AnularGroup;
import com.enigma.anularssoapi.entity.AnularUser;
import com.enigma.anularssoapi.service.anularuser.AnularUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AnularUserController {

    @Autowired
    AnularUserService anularUserService;

    @PostMapping("/api/user/register")
    public AnularUser register(@RequestBody AnularUser anularUser){
        return anularUserService.create(anularUser);
    }

    @GetMapping("/api/user")
    public AnularUser getUser(@RequestParam String id){
        return anularUserService.getById(id);
    }

    @GetMapping("/api/user/list")
    public List<AnularUser> getAllUser(){
        return anularUserService.getAllList();
    }

    @PutMapping("/api/user")
    public AnularUser updateUser(@RequestBody AnularUser anularUser){
        return anularUserService.update(anularUser);
    }

    @DeleteMapping("/api/user")
    public StatResp deleteUser(@RequestParam String id){
        return anularUserService.delete(id);
    }

    @GetMapping("/api/user/signin")
    public TokenResp getToken(@RequestBody AnularUserCredential anularUserCredential){
        return anularUserService.login(anularUserCredential);
    }

    @PostMapping("/api/authenticate/{token}")
    public StatResp authenticate(@PathVariable String token){
        return anularUserService.authenticate(token);
    }
}
