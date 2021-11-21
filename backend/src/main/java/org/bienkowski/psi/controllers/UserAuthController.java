package org.bienkowski.psi.controllers;

import org.bienkowski.psi.dto.UserLoginDetails;
import org.bienkowski.psi.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("auth/users")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class UserAuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response, @AuthenticationPrincipal UserLoginDetails userDetails) {
        authService.logOut(request);
        System.out.println("logged out");
    }
}
