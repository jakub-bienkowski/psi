package org.bienkowski.psi.services;

import lombok.extern.slf4j.Slf4j;
import org.bienkowski.psi.dto.CustomUserDetails;
import org.bienkowski.psi.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Slf4j
@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    public UserDTO login(HttpServletRequest request, UserDTO loginFormData) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginFormData.getEmail(),
                        loginFormData.getPassword()));

        SecurityContext securytyContext = SecurityContextHolder.getContext();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        HttpSession session = request.getSession(true);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, securytyContext);

        CustomUserDetails principal = ((CustomUserDetails) authentication.getPrincipal());
        return buildLoggedUserDTO(principal);
    }

    private UserDTO buildLoggedUserDTO(CustomUserDetails principal) {
        UserDTO loggedUser = new UserDTO();
        loggedUser.setIdUsr(principal.getIdUsr());
        loggedUser.setEmail(principal.getUsername());
        loggedUser.setName(principal.getName());
        loggedUser.setSurname(principal.getSurname());
        return loggedUser;
    }

    public void logOut(HttpServletRequest request) {
        if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            request.getSession().invalidate();
            SecurityContextHolder.getContext().setAuthentication(null);
        }
    }

}
