package org.bienkowski.psi.controllers;

import lombok.extern.slf4j.Slf4j;
import org.bienkowski.psi.dto.UserDTO;
import org.bienkowski.psi.exception.BindingException;
import org.bienkowski.psi.services.UserService;
import org.modelmapper.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Object> addUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error(bindingResult.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.saveUser(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
