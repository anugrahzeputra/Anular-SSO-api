package com.enigma.anularssoapi.service.anularuser.details;

import com.enigma.anularssoapi.dto.sign.AnularUserDetails;
import com.enigma.anularssoapi.entity.AnularUser;
import com.enigma.anularssoapi.repository.AnularUserDetailsRepository;
import com.enigma.anularssoapi.repository.AnularUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnularUserDetailsService implements UserDetailsService {

    @Autowired
    AnularUserDetailsRepository anularUserDetailsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        validateUsernameIsExist(username);
        AnularUser anularUser = anularUserDetailsRepository.findByUserName(username).get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("user"));

        return new AnularUserDetails(
                anularUser.getUserName(),
                anularUser.getPassword(),
                authorities
        );
    }

    public void validateUsernameIsExist(String username) {
        if(!anularUserDetailsRepository.findByUserName(username).isPresent()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "username is empty");
        }
    }

    public void validateUsernameIsNotExist(String username) {
        if(anularUserDetailsRepository.findByUserName(username).isPresent()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "username already exist");
        }
    }
}
