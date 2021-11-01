package com.example.codefellowship.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String body;
    @CreationTimestamp
    private Date createdAt;
    @ManyToOne
    ApplicationUser usersPost;

    public Post(String body, ApplicationUser usersPost) {
        this.body = body;
        this.createdAt = getCreatedAt();
        this.usersPost = usersPost;
    }
    public Post(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public ApplicationUser getUsersPost() {
        return usersPost;
    }

    public void setUsersPost(ApplicationUser usersPost) {
        this.usersPost = usersPost;
    }
}
