package org.bienkowski.psi.services;

import lombok.extern.slf4j.Slf4j;
import org.bienkowski.psi.dto.CustomUserDetails;
import org.bienkowski.psi.dto.UserDTO;
import org.bienkowski.psi.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    public UserDTO login(UserDTO loginFormData) {
        if (loginFormData == null) {
            return null;
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginFormData.getUsername(),
                        loginFormData.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtUtils.generateJwtToken(authentication);
        CustomUserDetails userDetails = ((CustomUserDetails) authentication.getPrincipal());
        return buildUserDto(userDetails, token);
    }

    private UserDTO buildUserDto(CustomUserDetails userDetails, String token) {
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return new UserDTO(
                userDetails.getIdUsr(),
                userDetails.getName(),
                userDetails.getSurname(),
                userDetails.getEmail(),
                userDetails.getUsername(),
                userDetails.getPassword(),
                token,
                roles
        );
    }

}
