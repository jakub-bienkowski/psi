package org.bienkowski.psi;

import org.bienkowski.psi.dto.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class LoginThread extends Thread {



    public void run() {
        while (true) {
            showAuthentication();
            try {
                sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void showAuthentication () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String currentPrincipalName = authentication.getName();
            System.out.println(currentPrincipalName);
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            System.out.println(userDetails);
            UserDetails userDetailsA = (UserDetails) authentication.getPrincipal();
            System.out.println(userDetailsA);
        } else {
            System.out.println("auth null");
        }

    }
}
