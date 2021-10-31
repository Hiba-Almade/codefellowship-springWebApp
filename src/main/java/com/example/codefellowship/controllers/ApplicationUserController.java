package com.example.codefellowship.controllers;

import com.example.codefellowship.models.ApplicationUser;
import com.example.codefellowship.repositories.ApplicationUserRepository;
import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ApplicationUserController {

    @Autowired
    PasswordEncoder encoder;
    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @GetMapping("/signup")
    public String signupPage(){
        return "signup";
    }

    @PostMapping("/signup")
    public String signUpUser(@RequestParam String username, @RequestParam String password, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String dataOfBirth, @RequestParam String bio){
        ApplicationUser appUser = new ApplicationUser(username,encoder.encode(password),firstName,lastName,dataOfBirth,bio);
        applicationUserRepository.save(appUser);
        return "login";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
}
