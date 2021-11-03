package com.example.codefellowship.controllers;


import com.example.codefellowship.models.ApplicationUser;
import com.example.codefellowship.models.Post;
import com.example.codefellowship.repositories.ApplicationUserRepository;
import com.example.codefellowship.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class PostController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @Autowired
    PostRepository postRepository;

//    @PostMapping("/addSong")
//    public RedirectView addAlbum(Model model, @RequestParam(value = "albumId") int albumID,
//                                 @RequestParam(value="title") String title,
//                                 @RequestParam(value="length") int length,
//                                 @RequestParam(value="trackNumber") int trackNumber){
//        Album album = albumRepository.findById(albumID).get();
//        Song song=new Song(title,length,trackNumber,album);
//        songRepository.save(song);
//        return new RedirectView("/albumDetails/"+albumID);
//    }

    @PostMapping("/addpost")
    public String addPost(@RequestParam(value = "userId") int userId , @RequestParam(value = "body") String body){
       try {
           ApplicationUser user=applicationUserRepository.findById(userId).get();
           Post post = new Post(body,user);
           postRepository.save(post);
           return "profile";
       }catch (Exception e){
           return "error";
       }
    }
}
