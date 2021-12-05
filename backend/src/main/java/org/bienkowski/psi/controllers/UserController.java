package org.bienkowski.psi.controllers;

import lombok.extern.slf4j.Slf4j;
import org.bienkowski.psi.dto.CustomUserDetails;
import org.bienkowski.psi.dto.UserDTO;
import org.bienkowski.psi.exception.UserAlreadyExistsException;
import org.bienkowski.psi.services.AuthService;
import org.bienkowski.psi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    @GetMapping(value = "/check")
    public ResponseEntity<Object> checkauth() {
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
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping(value = "/login", consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> login(HttpServletRequest request, @RequestBody UserDTO userDTO, BindingResult bindingResult)  {

        if (bindingResult.hasErrors()) {
            log.error(bindingResult.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            UserDTO loggedUser = authService.login(request, userDTO);
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
            return new ResponseEntity<>(loggedUser, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/addUser", consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error(bindingResult.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            UserDTO savedUser = userService.saveUser(userDTO);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

}
