package org.bienkowski.psi.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.manager.util.SessionUtils;
import org.bienkowski.psi.dto.UserDTO;
import org.bienkowski.psi.dto.UserLoginDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Service
public class AuthService {

    @Autowired(required = true)
    private SessionRegistry sessionRegistry;

    @Autowired
    private AuthenticationManager authenticationManager;

    public UserDTO login(String ip, String sessionID, UserDTO loginUser) {
        System.out.println(loginUser.getEmail() + loginUser.getPassword());
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getEmail(),
                        loginUser.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserLoginDetails principal = ((UserLoginDetails) authentication.getPrincipal());
        principal.setIpAddress(ip);
        principal.setSessionID(sessionID);

        sessionRegistry.registerNewSession(sessionID, principal);

        UserDTO loggedUser = new UserDTO();
        loggedUser.setIdUsr(principal.getIdUsr());
        loggedUser.setName(principal.getName());
        loggedUser.setSurname(principal.getSurname());
        loggedUser.setEmail(principal.getEmail());

        return loggedUser;
    }

    public void logOut(HttpServletRequest request) {
        if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            request.getSession().invalidate();
            SecurityContextHolder.getContext().setAuthentication(null);
        }
    }

}
