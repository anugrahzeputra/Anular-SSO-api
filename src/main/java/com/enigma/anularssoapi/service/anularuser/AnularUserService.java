package com.enigma.anularssoapi.service.anularuser;

import com.enigma.anularssoapi.dto.customresponse.StatResp;
import com.enigma.anularssoapi.dto.customresponse.TokenResp;
import com.enigma.anularssoapi.dto.pojos.AnularUserCredential;
import com.enigma.anularssoapi.entity.AnularUser;
import com.enigma.anularssoapi.service.CRUDService;

public interface AnularUserService extends CRUDService<AnularUser> {
    AnularUser createByAdmin(AnularUser anularUser);
    TokenResp login(AnularUserCredential anularUserCredential);
    StatResp authenticate(String token);
    AnularUser updateUserByAdmin(String id, String groupId);
}
