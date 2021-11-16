package com.enigma.anularssoapi.service.anularuser;

import com.enigma.anularssoapi.dto.customresponse.StatResp;
import com.enigma.anularssoapi.dto.enumeration.AnularUserStat;
import com.enigma.anularssoapi.entity.AnularUser;
import com.enigma.anularssoapi.repository.AnularUserRepository;
import com.enigma.anularssoapi.service.anulargroup.AnularGroupService;
import com.enigma.anularssoapi.service.anularuser.details.AnularUserDetailsService;
import com.enigma.anularssoapi.utility.IdGenerator;
import com.enigma.anularssoapi.utility.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnularUserServiceImpl implements AnularUserService{

    @Autowired
    AnularUserRepository anularUserRepository;

    @Autowired
    AnularUserDetailsService anularUserDetailsService;

    @Autowired
    AnularGroupService anularGroupService;

    @Autowired
    IdGenerator idGenerator;

    @Autowired
    ValidationUtils validationUtils;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public AnularUser create(AnularUser anularUser) {
        anularUser.setAnularGroup(anularGroupService.getById(anularUser.getAGID()));
        setAnularUser(anularUser);
        anularUserRepository.save(anularUser);
        return anularUser;
    }

    @Override
    public AnularUser createByAdmin(AnularUser anularUser){
        anularUser.setAGID(idGenerator.simpleEncode("000000"));
        setAnularUser(anularUser);
        anularUserRepository.save(anularUser);
        return anularUser;
    }

    @Override
    public List<AnularUser> getAllList() {
        return anularUserRepository
                .findAll()
                .stream()
                .peek(anularUser -> anularUser
                        .setAGID(anularUser.getAnularGroup().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public AnularUser getById(String id) {
        validateIdDidExist(id);
        AnularUser anularUser = anularUserRepository.getById(id);
        anularUser.setAGID(anularUser.getAnularGroup().getId());
        return anularUser;
    }

    @Override
    public AnularUser update(AnularUser anularUser) {
        validationUtils.idIsNotNull(anularUser.getId());
        validateIdDidExist(anularUser.getId());
        anularUser.setPassword(anularUserRepository.getById(anularUser.getId()).getPassword());
        anularUserRepository.save(anularUser);
        return anularUser;
    }

    @Override
    public StatResp delete(String id) {
        AnularUser anularUser = getById(id);
        anularUserRepository.delete(anularUser);
        return new StatResp("success");
    }

    private void validateIdDidExist(String id) {
        if(!anularUserRepository.findById(id).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user id isn't exist");
        }
    }

    private void setAnularUser(AnularUser anularUser) {
        validateAnularUser(anularUser);
        anularUser.setId(idGenerator.getUserId(anularUserRepository.getUserId(), anularUser.getAGID().split("-")[0]));
        anularUser.setAnularUserStat(AnularUserStat.UNVERIFIED.getValues());
        anularUser.setPassword(passwordEncoder.encode(anularUser.getPassword()));
    }

    private void validateAnularUser(AnularUser anularUser) {
        validationUtils.idIsNull(anularUser.getId());
        anularUserDetailsService.validateUsernameIsNotExist(anularUser.getUserName());
    }
}
