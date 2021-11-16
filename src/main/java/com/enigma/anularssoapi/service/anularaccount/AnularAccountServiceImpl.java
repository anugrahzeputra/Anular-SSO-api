package com.enigma.anularssoapi.service.anularaccount;

import com.enigma.anularssoapi.dto.customresponse.StatResp;
import com.enigma.anularssoapi.entity.AnularAccount;
import com.enigma.anularssoapi.repository.AnularAccountRepository;
import com.enigma.anularssoapi.service.anulargroup.AnularGroupService;
import com.enigma.anularssoapi.service.anularuser.AnularUserService;
import com.enigma.anularssoapi.utility.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnularAccountServiceImpl implements AnularAccountService {

    @Autowired
    AnularAccountRepository anularAccountRepository;

    @Autowired
    AnularUserService anularUserService;

    @Autowired
    AnularGroupService anularGroupService;

    @Autowired
    ValidationUtils validationUtils;

    @Override
    public AnularAccount create(AnularAccount anularAccount) {
        validationUtils.idIsNull(anularAccount.getId());
        anularAccount.setAnularGroup(anularGroupService.getById(anularAccount.getAGID()));
        anularAccount.setAnularUser(anularUserService.getById(anularAccount.getAUID()));
        anularAccountRepository.save(anularAccount);
        return anularAccount;
    }

    @Override
    public List<AnularAccount> getAllList() {
        return anularAccountRepository
                .findAll()
                .stream()
                .peek(anularAccount -> {
                    anularAccount.setAGID(anularAccount.getAnularGroup().getId());
                    anularAccount.setAUID(anularAccount.getAnularUser().getId());
                }).collect(Collectors.toList());
    }

    @Override
    public AnularAccount getById(String id) {
        validateIdIsExist(id);
        AnularAccount anularAccount = anularAccountRepository.getById(id);
        anularAccount.setAGID(anularAccount.getAnularGroup().getId());
        anularAccount.setAUID(anularAccount.getAnularUser().getId());
        return anularAccount;
    }

    private void validateIdIsExist(String id) {
        if(!anularAccountRepository.findById(id).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "account id isn't exist");
        }
    }

    @Override
    public AnularAccount update(AnularAccount anularAccount) {
        validateIdIsExist(anularAccount.getId());
        validationUtils.idIsNotNull(anularAccount.getId());
        anularAccountRepository.save(anularAccount);
        return anularAccount;
    }

    @Override
    public StatResp delete(String id) {
        validateIdIsExist(id);
        anularAccountRepository.delete(anularAccountRepository.getById(id));
        return new StatResp("success");
    }
}
