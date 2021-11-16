package com.enigma.anularssoapi.service.anularuser;

import com.enigma.anularssoapi.dto.customresponse.StatResp;
import com.enigma.anularssoapi.dto.enumeration.AnularUserStat;
import com.enigma.anularssoapi.entity.AnularUser;
import com.enigma.anularssoapi.repository.AnularUserRepository;
import com.enigma.anularssoapi.service.anulargroup.AnularGroupService;
import com.enigma.anularssoapi.utility.IdGenerator;
import com.enigma.anularssoapi.utility.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AnularUserServiceImpl implements AnularUserService{

    @Autowired
    AnularUserRepository anularUserRepository;

    @Autowired
    AnularGroupService anularGroupService;

    @Autowired
    IdGenerator idGenerator;

    @Autowired
    ValidationUtils validationUtils;

    @Override
    public AnularUser create(AnularUser anularUser) {
        validationUtils.idIsNull(anularUser.getId());
        anularGroupService.getById(anularUser.getAGID());
        setAnularUser(anularUser);
        anularUserRepository.save(anularUser);
        return anularUser;
    }

    @Override
    public AnularUser createByAdmin(AnularUser anularUser){
        validationUtils.idIsNull(anularUser.getId());
        setAnularUser(anularUser);
        anularUserRepository.save(anularUser);
        return anularUser;
    }

    @Override
    public List<AnularUser> getAllList() {
        return anularUserRepository.findAll();
    }

    @Override
    public AnularUser getById(String id) {
        validateIdDidExist(id);
        return anularUserRepository.findById(id).get();
    }

    @Override
    public AnularUser update(AnularUser anularUser) {
        validationUtils.idIsNotNull(anularUser.getId());
        validateIdDidExist(anularUser.getId());
        return anularUserRepository.save(anularUser);
    }

    @Override
    public StatResp delete(String id) {
        AnularUser anularUser = getById(id);
        anularUserRepository.delete(anularUser);
        return new StatResp("success");
    }

    private void validateIdDidExist(String id) {
        if(!anularUserRepository.findById(id).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user id didn't exist");
        }
    }

    private void setAnularUser(AnularUser anularUser) {
        anularUser.setId(idGenerator.getUserId(anularUserRepository.getUserId(), anularUser.getAGID().split("-")[0]));
        anularUser.setAnularUserStat(AnularUserStat.UNVERIFIED.getValues());
    }
}
