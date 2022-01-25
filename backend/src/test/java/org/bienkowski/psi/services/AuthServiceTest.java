package org.bienkowski.psi.services;



import org.bienkowski.psi.dto.CustomUserDetails;
import org.bienkowski.psi.dto.UserDTO;
import org.bienkowski.psi.model.Role;
import org.bienkowski.psi.model.User;
import org.bienkowski.psi.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;
import java.util.Set;

import static org.bienkowski.psi.enums.ERole.USER_ROLE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ContextConfiguration
@SpringBootTest
class AuthServiceTest {

    @MockBean
    UserRepository userRepository;

    @MockBean
    AuthenticationManager authenticationManager;

    @Autowired
    AuthService authService;

    private UserDTO userDTO;

    @Test
    void loginWithNullCredentials() {
        authService.login(null);
        Assertions.assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void loginWithBadCredentials() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new BadCredentialsException("bad credentials"));
        Assertions.assertThrows(BadCredentialsException.class, () -> authService.login(userDTO));
    }

    @Test
    void loginCorrectCredentials() {
        User user = createUser();
        Authentication authentication = mock(Authentication.class);
        authentication.setAuthenticated(true);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(CustomUserDetails.build(user));
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        UserDTO loggedUser = authService.login(userDTO);

        Assertions.assertEquals("john", loggedUser.getName());
        Assertions.assertEquals("testUser", loggedUser.getUsername());
        Assertions.assertEquals("smith", loggedUser.getSurname());
        Assertions.assertEquals("test@email.com", loggedUser.getEmail());
        Assertions.assertEquals("testPassword", loggedUser.getPassword());
        Assertions.assertEquals( USER_ROLE.name(), loggedUser.getRoles().get(0));
        Assertions.assertEquals("john", loggedUser.getName());
        Assertions.assertNotNull(loggedUser.getToken());

        Assertions.assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        Assertions.assertNotNull(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    private User createUser() {
        User user = new User();
        user.setUsername("testUser");
        user.setName("john");
        user.setSurname("smith");
        user.setEmail("test@email.com");
        user.setPassword("testPassword");

        Role role = new Role();
        role.setName(USER_ROLE);
        user.setRoles(Set.of(role));

        return user;
    }

    @BeforeEach
    void beforeEach() {
        SecurityContextHolder.clearContext();
        userDTO = new UserDTO();
        userDTO.setUsername("testUser");
        userDTO.setPassword("testPassword");
    }

}
