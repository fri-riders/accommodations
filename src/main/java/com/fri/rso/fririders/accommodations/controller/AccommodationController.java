package com.fri.rso.fririders.accommodations.controller;

import com.fri.rso.fririders.accommodations.data.Accommodation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/accommodations")
public class AccommodationController {


    private final RestTemplate restTemplate;

    public AccommodationController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    private static final String basePath = "https://jsonplaceholder.typicode.com";
    private static List<Accommodation> accommodations = new ArrayList<>();
    private Logger log = LogManager.getLogger(AccommodationController.class.getName());

    static {
        accommodations.add(new Accommodation(1, "Hotel Slon", "Ljubljana", 20000000.0, 120.0));
        accommodations.add(new Accommodation(2, "Motel Medno", "Medno", 1000000.0, 40.0));
        accommodations.add(new Accommodation(3, "Hotel Kanu", "Dragočajna", 2000000.0, 50.0));
        accommodations.add(new Accommodation(4, "Hotel K", "Dragočajna", 2000000.0, 50.0));
    }

    @GetMapping
    public Accommodation getByLocation(@RequestParam(value = "location") String location) {
        return accommodations.stream()
                .filter(accommodation -> accommodation.getLocation().equals(location))
                .findFirst()
                .map(accommodation -> {
                    accommodation.setDescription(getPost(accommodation.getId()).getTitle());
                    return accommodation;
                })
                .orElse(null);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Accommodation> getAll() {
        return accommodations;
    }

    // Simple fake api call for demo purpuses
    public Post getPost(long id) {
        return this.restTemplate.getForObject(basePath + "/posts/" + id, Post.class);
    }

    private static class Post {
        private int userId;
        private int id;
        private String title;
        private String body;

        public Post() {
        }

        public Post(int userId, int id, String title, String body) {
            this.userId = userId;
            this.id = id;
            this.title = title;
            this.body = body;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        @Override
        public String toString() {
            return "Post{" +
                    "userId=" + userId +
                    ", id=" + id +
                    ", title='" + title + '\'' +
                    ", body='" + body + '\'' +
                    '}';
        }
    }
}
