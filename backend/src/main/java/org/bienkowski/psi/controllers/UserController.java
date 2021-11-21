package org.bienkowski.psi.controllers;

import lombok.extern.slf4j.Slf4j;
import org.bienkowski.psi.dto.UserDTO;
import org.bienkowski.psi.services.AuthService;
import org.bienkowski.psi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;


    @PostMapping(value = "/login", consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> login(HttpServletRequest request, @RequestBody UserDTO userDTO, BindingResult bindingResult)  {

       try {
           String test = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
           System.out.println(test);
       } catch (Exception e) {
           System.out.println(e);
       }

        System.out.println("logging");
        if (bindingResult.hasErrors()) {
            System.out.println("binding wrong");
            log.error(bindingResult.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserDTO loggedUser = null;
        try {
            loggedUser = authService.login(request.getRemoteAddr(), request.getSession().getId(), userDTO);
            System.out.println("logged in");
            return new ResponseEntity<>(loggedUser, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            System.out.println("bad credentials");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/addUser", consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error(bindingResult.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userDTO.setCreatedAt(LocalDateTime.now());
        UserDTO savedUser = userService.saveUser(userDTO);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

}
