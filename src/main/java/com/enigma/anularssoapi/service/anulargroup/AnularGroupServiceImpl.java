package com.enigma.anularssoapi.service.anulargroup;

import com.enigma.anularssoapi.dto.customresponse.StatResp;
import com.enigma.anularssoapi.dto.pojos.AnularGroupFirstState;
import com.enigma.anularssoapi.entity.AnularAccount;
import com.enigma.anularssoapi.entity.AnularGroup;
import com.enigma.anularssoapi.repository.AnularGroupRepository;
import com.enigma.anularssoapi.service.anularaccount.AnularAccountService;
import com.enigma.anularssoapi.service.anularsitetype.AnularSiteTypeService;
import com.enigma.anularssoapi.service.anularuser.AnularUserService;
import com.enigma.anularssoapi.utility.IdGenerator;
import com.enigma.anularssoapi.utility.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnularGroupServiceImpl implements AnularGroupService {

    @Autowired
    AnularGroupRepository anularGroupRepository;

    @Autowired
    AnularSiteTypeService anularSiteTypeService;

    @Autowired
    AnularUserService anularUserService;

    @Autowired
    AnularAccountService anularAccountService;

    @Autowired
    IdGenerator idGenerator;

    @Autowired
    ValidationUtils validationUtils;

    @Override
    public AnularGroup create(AnularGroup anularGroup) {
        anularGroup.setAnularSiteType(anularSiteTypeService.getById(anularGroup.getAST()));
        anularGroupRepository.save(anularGroup);
        return anularGroup;
    }

    @Override
    public AnularGroup createByUser(AnularGroupFirstState anularGroupFirstState){
        validationUtils.idIsNull(anularGroupFirstState.getId());;
        anularGroupFirstState.setId(idGenerator.getGroupId(
                anularGroupRepository.getGroupId(),
                anularUserService.getById(anularGroupFirstState.getUserId())
                        .getId()
                        .split("-")[1])
        );
        validateIdIsNotExist(anularGroupFirstState);
        AnularGroup anularGroup = create(new AnularGroup(anularGroupFirstState));
        saveAccount(anularGroupFirstState);
        return anularGroup;
    }

    @Override
    public AnularGroup createByAdmin(AnularGroupFirstState anularGroupFirstState){
        validationUtils.idIsNull(anularGroupFirstState.getId());;
        anularGroupFirstState.setId(idGenerator.getGroupId(
                anularGroupRepository.getGroupId(),
                anularGroupFirstState.getUserId().split("-")[1])
        );
        validateIdIsNotExist(anularGroupFirstState);
        AnularGroup anularGroup = create(new AnularGroup(anularGroupFirstState));
        anularUserService.updateUserByAdmin(anularGroupFirstState.getUserId(), anularGroupFirstState.getId());
        return anularGroup;
    }

    @Override
    public List<AnularGroup> getAllList() {
        return anularGroupRepository
                .findAll()
                .stream()
                .peek(anularGroup -> anularGroup.setAST(anularGroup.getAnularSiteType().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public AnularGroup getById(String id) {
        validateIdDidExist(id);
        AnularGroup anularGroup = anularGroupRepository.getById(id);
        anularGroup.setAST(anularGroup.getAnularSiteType().getId());
        return anularGroup;
    }

    @Override
    public AnularGroup update(AnularGroup anularGroup) {
        validationUtils.idIsNotNull(anularGroup.getId());
        validateIdDidExist(anularGroup.getId());
        anularGroupRepository.save(anularGroup);
        return anularGroup;
    }

    @Override
    public StatResp delete(String id) {
        AnularGroup anularGroup = getById(id);
        anularGroupRepository.delete(anularGroup);
        return new StatResp("success");
    }

    private void validateIdDidExist(String id) {
        if(!anularGroupRepository.findById(id).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "group id isn't exist");
        }
    }

    private void saveAccount(AnularGroupFirstState anularGroupFirstState) {
        AnularAccount anularAccount = new AnularAccount(
                anularGroupFirstState.getUserId(),
                anularGroupFirstState.getId()
        );
        anularAccountService.create(anularAccount);
    }

    private void validateIdIsNotExist(AnularGroupFirstState anularGroupFirstState) {
        if(anularGroupRepository.getGroupIdALIke(
                        anularGroupFirstState.getId().split("-")[0],
                        anularGroupFirstState.getUserId())
                .isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "group id is exist");
        }
    }
}
