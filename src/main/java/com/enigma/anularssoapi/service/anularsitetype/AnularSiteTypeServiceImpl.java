package com.enigma.anularssoapi.service.anularsitetype;

import com.enigma.anularssoapi.dto.customresponse.StatResp;
import com.enigma.anularssoapi.entity.AnularSiteType;
import com.enigma.anularssoapi.repository.AnularSiteTypeRepository;
import com.enigma.anularssoapi.utility.IdGenerator;
import com.enigma.anularssoapi.utility.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AnularSiteTypeServiceImpl implements AnularSiteTypeService {

    @Autowired
    AnularSiteTypeRepository anularSiteTypeRepository;

    @Autowired
    IdGenerator idGenerator;

    @Autowired
    ValidationUtils validationUtils;

    @Override
    public AnularSiteType create(AnularSiteType anularSiteType) {
        validationUtils.idIsNull(anularSiteType.getId());
        anularSiteType.setId(idGenerator.getTypeId());
        validateIdIsNotExist(anularSiteType.getId());
        validateNameIsNotExist(anularSiteType.getNameType());
        return anularSiteTypeRepository.save(anularSiteType);
    }

    @Override
    public List<AnularSiteType> getAllList() {
        return anularSiteTypeRepository.findAll();
    }

    @Override
    public AnularSiteType getById(String id) {
        validateIdDidExist(id);
        return anularSiteTypeRepository.getById(id);
    }

    @Override
    public AnularSiteType update(AnularSiteType anularSiteType) {
        validationUtils.idIsNotNull(anularSiteType.getId());
        validateIdDidExist(anularSiteType.getId());
        return anularSiteTypeRepository.save(anularSiteType);
    }

    @Override
    public StatResp delete(String id) {
        AnularSiteType anularSiteType = getById(id);
        anularSiteTypeRepository.delete(anularSiteType);
        return new StatResp("success");
    }

    private void validateIdDidExist(String id) {
        if(!anularSiteTypeRepository.findById(id).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "type id didn't exist");
        }
    }

    private void validateNameIsNotExist(String name) {
        if(anularSiteTypeRepository.findByNameType(name).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "type name already exist");
        }
    }

    private void validateIdIsNotExist(String id) {
        if(anularSiteTypeRepository.findById(id).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "type id is exist");
        }
    }
}
