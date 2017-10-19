package com.fri.rso.fririders.realestate.controller;

import com.fri.rso.fririders.realestate.data.RealEstate;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/estates")
public class RealEstateController {


    private final RestTemplate restTemplate;

    public RealEstateController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    private static final String basePath = "https://jsonplaceholder.typicode.com";
    private static List<RealEstate> realEstates = new ArrayList<>();
    private Logger log = LogManager.getLogger(RealEstateController.class.getName());

    static {
        realEstates.add(new RealEstate(1, "Hotel Slon", "Ljubljana", 20000000.0, 120.0));
        realEstates.add(new RealEstate(2, "Motel Medno", "Medno", 1000000.0, 40.0));
        realEstates.add(new RealEstate(3, "Hotel Kanu", "Dragočajna", 2000000.0, 50.0));
    }

    @GetMapping
    public RealEstate getByLocation(@RequestParam(value = "location") String location) {
        return realEstates.stream()
                .filter(realEstate -> realEstate.getLocation().equals(location))
                .findFirst()
                .map(realEstate -> {
                    realEstate.setDescription(getPost(realEstate.getId()).getTitle());
                    return realEstate;
                })
                .orElse(null);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<RealEstate> getAll() {
        return realEstates;
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
