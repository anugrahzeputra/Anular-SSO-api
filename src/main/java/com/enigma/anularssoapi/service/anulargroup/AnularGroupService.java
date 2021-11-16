package com.enigma.anularssoapi.service.anulargroup;

import com.enigma.anularssoapi.dto.pojos.AnularGroupFirstState;
import com.enigma.anularssoapi.entity.AnularGroup;
import com.enigma.anularssoapi.service.CRUDService;

public interface AnularGroupService extends CRUDService<AnularGroup> {
    AnularGroup createByUser(AnularGroupFirstState anularGroupFirstState);
    AnularGroup createByAdmin(AnularGroupFirstState anularGroupFirstState);
}
