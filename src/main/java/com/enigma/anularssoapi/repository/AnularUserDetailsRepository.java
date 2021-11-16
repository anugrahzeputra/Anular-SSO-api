package com.enigma.anularssoapi.repository;

import com.enigma.anularssoapi.entity.AnularUser;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnularUserDetailsRepository extends AnularUserRepository{
    Optional<AnularUser> findByUserName(String username);
}
