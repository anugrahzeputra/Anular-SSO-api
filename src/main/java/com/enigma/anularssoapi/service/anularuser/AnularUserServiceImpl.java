package com.enigma.anularssoapi.service.anularuser;

import com.enigma.anularssoapi.dto.customresponse.StatResp;
import com.enigma.anularssoapi.dto.customresponse.TokenResp;
import com.enigma.anularssoapi.dto.enumeration.AnularUserStat;
import com.enigma.anularssoapi.dto.pojos.AnularUserCredential;
import com.enigma.anularssoapi.entity.AnularUser;
import com.enigma.anularssoapi.repository.AnularUserRepository;
import com.enigma.anularssoapi.service.anulargroup.AnularGroupService;
import com.enigma.anularssoapi.service.anularuser.details.AnularUserDetailsService;
import com.enigma.anularssoapi.service.mail.MailService;
import com.enigma.anularssoapi.utility.IdGenerator;
import com.enigma.anularssoapi.utility.JwtTokenUtils;
import com.enigma.anularssoapi.utility.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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
    MailService mailService;

    @Autowired
    IdGenerator idGenerator;

    @Autowired
    ValidationUtils validationUtils;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenUtils jwtTokenUtils;

    @Override
    public AnularUser create(AnularUser anularUser) {
        String password = anularUser.getPassword();
        anularUser.setAnularGroup(anularGroupService.getById(anularUser.getAGID()));
        setAnularUser(anularUser);
        anularUserRepository.save(anularUser);
        sendMail(anularUser.getUserName(), password, anularUser.getEmail());
        return anularUser;
    }

    private void sendMail(String username, String password, String email) {
        String token = getToken(
                username,
                password
        );
        sendMail(
                email,
                String.format(
                        "https://anular-sso-api.herokuapp.com/api/authenticate/%s",
                        token
                )
        );
    }

    @Override
    public AnularUser createByAdmin(AnularUser anularUser){
        anularUser.setAGID(idGenerator.simpleEncode("000000"));
        setAnularUser(anularUser);
        anularUser.setAnularUserStat(AnularUserStat.VERIFIED.getValues());
        anularUserRepository.save(anularUser);
        return anularUser;
    }

    @Override
    public TokenResp login(AnularUserCredential anularUserCredential) {
        validateUserEmailVerification(anularUserCredential.getUserName());
        UsernamePasswordAuthenticationToken userPassToken =
                new UsernamePasswordAuthenticationToken(
                        anularUserCredential.getUserName(),
                        anularUserCredential.getPassword()
                );
        authenticationManager.authenticate(userPassToken);
        UserDetails userDetails = anularUserDetailsService
                .loadUserByUsername(anularUserCredential.getUserName());
        return new TokenResp("success", jwtTokenUtils.generateToken(userDetails, 60));
    }

    @Override
    public StatResp authenticate(String token) {
        UserDetails userDetails = jwtTokenUtils.parseToken(token);
        AnularUser anularUser = anularUserRepository.findByUserName(userDetails.getUsername()).get();
        anularUser.setAnularUserStat(AnularUserStat.VERIFIED.getValues());
        update(anularUser);
        return new StatResp("success");
    }

    private void validateUserEmailVerification(String username) {
        anularUserDetailsService.validateUsernameIsExist(username);
        AnularUser anularUser = anularUserRepository.findByUserName(username).get();
        if(!anularUser.getAnularUserStat().equals(AnularUserStat.VERIFIED.getValues())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "you must verif your email first");
        }
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
    public AnularUser updateUserByAdmin(String id, String groupId){
        AnularUser anularUser = getAnularUserWithValidation(id);
        anularUser.setAnularGroup(anularGroupService.getById(groupId));
        update(anularUser);
        anularUser.setAGID(anularUser.getAnularGroup().getId());
        return anularUser;
    }

    @Override
    public AnularUser getById(String id) {
        AnularUser anularUser = getAnularUserWithValidation(id);
        anularUser.setAGID(anularUser.getAnularGroup().getId());
        return anularUser;
    }

    private AnularUser getAnularUserWithValidation(String id) {
        validateIdDidExist(id);
        return anularUserRepository.getById(id);
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

    private String getToken(String username, String password) {
        System.out.println(username + " " + password);
        UsernamePasswordAuthenticationToken userPassAuthToken =
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                );
        authenticationManager.authenticate(userPassAuthToken);
        UserDetails userDetails = anularUserDetailsService.loadUserByUsername(username);
        return jwtTokenUtils.generateToken(userDetails, 60);
    }

    private void sendMail(String email, String tokenUrl) {
        mailService.sendSimpleMessage(
                email,
                "Aktivasi Akun Anular SSO Apps",
                String.format(
                        "Dear Pengguna Anular SSO Apps " +
                                "\n" +
                                "\n" +
                                "Berikut adalah link untuk registrasi Anular SSO Apps. " +
                                "mohon diperhatikan bahwa link akan kadaluarsa dalam waktu 30 menit" +
                                "\n" +
                                "\n" +
                                "%s " +
                                "\n" +
                                "terima kasih telah menggunakan layanan kami", tokenUrl
                )
        );
    }
}
