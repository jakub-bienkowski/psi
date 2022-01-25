package org.bienkowski.psi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bienkowski.psi.dto.UserDTO;

import org.bienkowski.psi.exception.UserAlreadyExistsException;
import org.bienkowski.psi.services.AuthService;
import org.bienkowski.psi.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class AuthControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    AuthService authService;

    @MockBean
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    private UserDTO userDto;

    @Test
    void loginSuccessWhenUserNotNull() throws Exception {
        String url = "/api/auth/login";
        ResultActions result = mvc.perform(MockMvcRequestBuilders
                .post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk());
    }

    @Test
    void loginFailWhenUserNull() throws Exception {
        String url = "/api/auth/login";
        userDto = null;
        ResultActions result = mvc.perform(MockMvcRequestBuilders
                .post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().is(400));
    }

    @Test
    void loginFailBadCredentials() throws Exception {
        when(authService.login(any(UserDTO.class))).thenThrow(new BadCredentialsException("bad credentials"));
        String url = "/api/auth/login";
        ResultActions result = mvc.perform(MockMvcRequestBuilders
                .post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addUserSuccessWhenUserNotNull() throws Exception {
        when(userService.saveUser(any(UserDTO.class))).thenReturn(new UserDTO());

        String url = "/api/auth/addUser";
        userDto.setName("john");
        userDto.setSurname("smith");
        userDto.setEmail("j@sm.com");
        ResultActions result = mvc.perform(MockMvcRequestBuilders
                .post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void addUserFailWhenUserNull() throws Exception {
        String url = "/api/auth/addUser";
        userDto = null;
        ResultActions result = mvc.perform(MockMvcRequestBuilders
                .post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().is(400));
    }

    @Test
    void addUserFailUserAlreadyExists() throws Exception {
        when(userService.saveUser(any(UserDTO.class))).thenThrow(new UserAlreadyExistsException());
        String url = "/api/auth/addUser";
        ResultActions result = mvc.perform(MockMvcRequestBuilders
                .post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isUnprocessableEntity());
    }


    @BeforeEach()
    void beforeEach() {
        userDto = new UserDTO();
        userDto.setUsername("john");
        userDto.setPassword("password");
    }

    @AfterEach
    void afterEach() {
        userDto = null;
    }

}