package com.example.codefellowship.controllers;

import com.example.codefellowship.models.ApplicationUser;
import com.example.codefellowship.models.Post;
import com.example.codefellowship.repositories.ApplicationUserRepository;
import com.example.codefellowship.repositories.PostRepository;
import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.io.Console;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Controller
public class ApplicationUserController {

    @Autowired
    PasswordEncoder encoder;
    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @Autowired
    PostRepository postRepository;
    @GetMapping("/signup")
    public String signupPage(){
        return "signup";
    }

    @PostMapping("/signup")
    public RedirectView signUpUser(@RequestParam String username,
                                   @RequestParam String password,
                                   @RequestParam String firstName,
                                   @RequestParam String lastName,
                                   @RequestParam String dataOfBirth,
                                   @RequestParam String bio){
      try {
          ApplicationUser appUser = new ApplicationUser(username,encoder.encode(password),firstName,lastName,dataOfBirth,bio);
          applicationUserRepository.save(appUser);
          // maybe autologin?
          Authentication authentication = new UsernamePasswordAuthenticationToken(appUser, null, new ArrayList<>());
          SecurityContextHolder.getContext().setAuthentication(authentication);
          return new RedirectView("/");
      }catch (Exception e){
          return new RedirectView("error");
      }

    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/user/{id}")
    public String profilePage(@PathVariable int id , Model model ,Principal p){
        try {
            ApplicationUser logUser=applicationUserRepository.findByUsername(p.getName());
            ApplicationUser applicationUser = applicationUserRepository.findById(id).get();
            model.addAttribute("userData", applicationUser);
            return "profile";
        }catch (Exception e){
            return "error";
        }
    }

    @GetMapping("/myprofile")
    public  String profilePage(Principal principal, Model model){
        ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
        model.addAttribute("userData",user);
        return "profile";
    }
    @GetMapping("/alluser")
    public String allUserPage(Model model){
        List<ApplicationUser> all= (List<ApplicationUser>) applicationUserRepository.findAll();
        model.addAttribute("usersList",all);
        return "users";
    }

//    @PostMapping("/follow/{id}")
//    public RedirectView addFollow(Principal principal, @RequestParam int id){
//        ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
//        ApplicationUser toFollow = applicationUserRepository.findById(id).get();
//        user.getFollowers().add(toFollow);
//        applicationUserRepository.save(user);
//        return new RedirectView("/user/"+toFollow.getId());
//    }

    @PostMapping("/follow/{username}")
    public RedirectView follow(@PathVariable String username , Principal p){
        ApplicationUser user = applicationUserRepository.findByUsername(p.getName());
        ApplicationUser toFollow = applicationUserRepository.findByUsername(username);
        user.setFollowers(toFollow);
        toFollow.setFollowing(user);
        applicationUserRepository.save(user);
        applicationUserRepository.save(toFollow);
        return new RedirectView("/");
    }

    @GetMapping("/feed")
    public String feedView(Principal p, Model model) {
        ApplicationUser user = applicationUserRepository.findByUsername(p.getName());
        Set<ApplicationUser> followings = (Set<ApplicationUser>) user.getFollowers();
        ArrayList<Post> feed = new ArrayList<>();
        for (ApplicationUser following : followings) {
            Post[] posts = postRepository.findByUsersPost(following);
            Collections.addAll(feed, posts);
        }
        model.addAttribute("posts", feed);
        return "feed";
    }



}
