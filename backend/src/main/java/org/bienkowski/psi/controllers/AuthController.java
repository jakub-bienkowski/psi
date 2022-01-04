package org.bienkowski.psi.controllers;

import lombok.extern.slf4j.Slf4j;
import org.bienkowski.psi.dto.UserDTO;
import org.bienkowski.psi.exception.UserAlreadyExistsException;
import org.bienkowski.psi.services.AuthService;
import org.bienkowski.psi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    @PostMapping(value = "/login", consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> login(HttpServletRequest request, @RequestBody UserDTO userDTO)  {
        try {
            Optional<UserDTO> loggedUser = authService.login(userDTO);
            return loggedUser.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/addUser", consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addUser(@Valid @RequestBody UserDTO userDTO) {
        try {
            UserDTO savedUser = userService.saveUser(userDTO);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

}
