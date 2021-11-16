package com.enigma.anularssoapi.service.anulargroup;

import com.enigma.anularssoapi.dto.customresponse.StatResp;
import com.enigma.anularssoapi.dto.pojos.AnularGroupFirstState;
import com.enigma.anularssoapi.entity.AnularGroup;
import com.enigma.anularssoapi.repository.AnularGroupRepository;
import com.enigma.anularssoapi.service.anularsitetype.AnularSiteTypeService;
import com.enigma.anularssoapi.service.anularuser.AnularUserService;
import com.enigma.anularssoapi.utility.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AnularGroupServiceImpl implements AnularGroupService {

    @Autowired
    AnularGroupRepository anularGroupRepository;

    @Autowired
    AnularSiteTypeService anularSiteTypeService;

    @Autowired
    AnularUserService anularUserService;

    @Autowired
    IdGenerator idGenerator;

    @Override
    public AnularGroup create(AnularGroup anularGroup) {
        anularGroup.setAnularSiteType(anularSiteTypeService.getById(anularGroup.getAST()));
        return anularGroupRepository.save(anularGroup);
    }

    @Override
    public AnularGroup createByUser(AnularGroupFirstState anularGroupFirstState){
        anularUserService.getById(anularGroupFirstState.getUserId());
        anularGroupFirstState.setId(idGenerator.getGroupId(
                anularGroupRepository.getGroupId(),
                anularGroupFirstState.getUserId().split("-")[1])
        );
        if(anularGroupRepository.getGroupIdALIke(
                anularGroupFirstState.getId().split("-")[1],
                anularGroupFirstState.getUserId())
                .isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id is exist");
        }
        return create(new AnularGroup(anularGroupFirstState));
    }

    @Override
    public List<AnularGroup> getAllList() {
        return anularGroupRepository.findAll();
    }

    @Override
    public AnularGroup getById(String id) {
        validateIdDidExist(id);
        return anularGroupRepository.getById(id);
    }

    private void validateIdDidExist(String id) {
        if(!anularGroupRepository.findById(id).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id didn't exist");
        }
    }

    @Override
    public AnularGroup update(AnularGroup anularGroup) {
        validateIdDidExist(anularGroup.getId());
        return anularGroupRepository.save(anularGroup);
    }

    @Override
    public StatResp delete(String id) {
        AnularGroup anularGroup = getById(id);
        anularGroupRepository.delete(anularGroup);
        return new StatResp("success");
    }
}
