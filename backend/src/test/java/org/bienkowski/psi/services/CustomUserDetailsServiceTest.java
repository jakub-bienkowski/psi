package org.bienkowski.psi.services;

import org.bienkowski.psi.dto.CustomUserDetails;
import org.bienkowski.psi.model.Role;
import org.bienkowski.psi.model.User;
import org.bienkowski.psi.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;

import static org.bienkowski.psi.enums.ERole.USER_ROLE;
import static org.mockito.Mockito.when;

@SpringBootTest
class CustomUserDetailsServiceTest {

    @MockBean
    UserRepository userRepository;

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Test
    void wrongUserName() {
        when(userRepository.findByUsername("johnNotRegistered")).thenReturn(Optional.empty());
        Assertions.assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("johnNotRegistered"));
    }

    @Test
    void validName() {
        User user = createUser();
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        CustomUserDetails customUserDetails = (CustomUserDetails) userDetailsService.loadUserByUsername("testUser");
        Assertions.assertNotNull(customUserDetails);
        Assertions.assertNotNull(customUserDetails.getAuthorities());
        Assertions.assertEquals("john", customUserDetails.getName());
        Assertions.assertEquals("testUser", customUserDetails.getUsername());
        Assertions.assertEquals("smith", customUserDetails.getSurname());
        Assertions.assertEquals("test@email.com", customUserDetails.getEmail());
        Assertions.assertEquals("testPassword", customUserDetails.getPassword());
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

}