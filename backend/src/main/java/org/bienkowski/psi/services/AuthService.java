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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Slf4j
@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired(required = true)
    private SessionRegistry sessionRegistry;

    public UserDTO login(HttpServletRequest request, UserDTO loginFormData) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginFormData.getEmail(),
                        loginFormData.getPassword()));

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        CustomUserDetails principal = ((CustomUserDetails) authentication.getPrincipal());

        sessionRegistry.registerNewSession(request.getSession().getId(), principal);
        HttpSession session = request.getSession(true);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, securityContext);
        System.out.println(principal);
       showAuthentication();

        return buildLoggedUserDTO(principal);
    }

    private void showAuthentication() {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                String currentPrincipalName = authentication.getName();
                System.out.println(currentPrincipalName + "AUTH SERVIVE");
                CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
                System.out.println(userDetails  + "AUTH SERVIVE");

                UserDetails userDetailsA = (UserDetails) authentication.getPrincipal();
                System.out.println(userDetailsA + "AUTH SERVIVE");
            } else {
                System.out.println("auth null AUTH SERVICE");
            }


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
            System.out.println("logged out");
        }
        System.out.println("not loggged out");
    }

}
