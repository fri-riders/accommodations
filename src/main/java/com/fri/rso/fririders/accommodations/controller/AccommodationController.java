package com.fri.rso.fririders.accommodations.controller;

import com.fri.rso.fririders.accommodations.config.CustomProperties;
import com.fri.rso.fririders.accommodations.data.Accommodation;
import com.fri.rso.fririders.accommodations.data.Booking;
import com.fri.rso.fririders.accommodations.data.User;
import com.fri.rso.fririders.accommodations.repository.AccommodationRepository;
import com.fri.rso.fririders.accommodations.service.AccommodationService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RefreshScope
@RestController
@RequestMapping(value = "v1/accommodations")
public class AccommodationController {

    @Autowired
    private CustomProperties properties;

    @Autowired
    private final Environment environment;

    private final AccommodationService accommodationService;

    private final AccommodationRepository repository;

    private final RestTemplate restTemplate;

    private final CounterService counterService;
    private final GaugeService gaugeService;

    @Autowired
    public AccommodationController(Environment environment, RestTemplateBuilder restTemplateBuilder, AccommodationRepository repository,
                                   CounterService counterService, GaugeService gaugeService, AccommodationService accommodationService) {
        this.environment = environment;
        this.restTemplate = restTemplateBuilder.build();
        this.repository = repository;
        this.counterService = counterService;
        this.gaugeService = gaugeService;
        this.accommodationService = accommodationService;
    }

    @GetMapping
    public List<Accommodation> getAll() {
        counterService.increment("meter.services.accommodations.getAll.invoked");
        return Lists.newArrayList(repository.findAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Accommodation get(@PathVariable(value = "id") Long id) {
        return repository.findById(id).get(0);
    }

    @RequestMapping(value = "/{id}/availability", method = RequestMethod.GET)
    public ResponseEntity getAvailability(@PathVariable(value = "id") Long id, @RequestParam long fromTime, @RequestParam long toTime) {
        String host = properties.isLocalhost() ? "localhost" : "bookings";
        final Date startDate = new Date(fromTime);
        final Date endDate = new Date(toTime);
        if (startDate.compareTo(endDate) >= 0) {
            return ResponseEntity.badRequest().build();
        }
        try {
            final List<Booking> bookings = restTemplate.exchange("http://" + host + ":8080/v1/bookings",
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Booking>>() {
                    }).getBody();
            final boolean isAvailableInterval = bookings.stream()
                    .filter(booking -> booking.getIdAccommodation() == id)
                    .noneMatch(booking ->
                            booking.getFromDate().compareTo(startDate) <= 0 &&
                                    booking.getToDate().compareTo(startDate) >= 0
                    );
            gaugeService.submit("services.accommodations.availability.error", 0);
            return ResponseEntity.ok(isAvailableInterval);
        } catch (RestClientException e) {
            gaugeService.submit("services.accommodations.availability.error", 1);
            return ResponseEntity.badRequest().body("Bookings service unavailable!");
        }
    }

    @RequestMapping(value = "/{id}/bookings", method = RequestMethod.GET)
    public ResponseEntity getForBooking(@PathVariable(value = "id") Long id) {
        String host = properties.isLocalhost() ? "localhost" : "bookings";
        final List<Booking> bookings = restTemplate.exchange("http://" + host + ":8080/v1/bookings",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Booking>>() {
                }).getBody();
        final List<Booking> bookingsForAccommodation = bookings.stream()
                .filter(booking -> booking.getIdAccommodation() == id)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookingsForAccommodation);
    }

    @RequestMapping(value = "/location/{location}", method = RequestMethod.GET)
    public List<Accommodation> getByLocation(@PathVariable(value = "location") String location) {
        return repository.findByLocation(location);
    }

    @RequestMapping(value = "/capacity/{capacity}", method = RequestMethod.GET)
    public List<Accommodation> getByCapacity(@PathVariable(value = "capacity") int capacity) {
        return repository.findByCapacity(capacity);
    }


    @PostMapping
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addAccommodation(@ModelAttribute Accommodation accommodation) {
        repository.save(accommodation);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteAccommodation(@PathVariable("id") Long id) {
        repository.delete(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateAccommodation(@PathVariable("id") Long id, @RequestBody Accommodation acc_update) {
        Accommodation accommodation = accommodationService.updateAccommodation(id, acc_update);
        return Optional.of(accommodation)
                .map(a -> {
                    String msg = environment.getProperty("app.message.notify.user.accommodation.changed");
                    final String notificationStatus =
                            accommodationService.sendNotification(
                                    "je1468@student.uni-lj.si",
                                    "Accommodation changed",
                                    msg.concat(" Accommodation name: ").concat(accommodation.getName()));
                    return ResponseEntity.ok().body(a);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "users", method = RequestMethod.GET)
    public ResponseEntity getUsers() {
        String host = properties.isLocalhost() ? "localhost" : "users";
        List<User> users = restTemplate.exchange("http://"+ host +":8082/v1/users/",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
                }).getBody();
        return ResponseEntity.ok(users.get(0));
    }

    @RequestMapping(value = "notification", method = RequestMethod.GET)
    public ResponseEntity<String> sendNotification() {
        // discover service and use it
        return ResponseEntity.ok(accommodationService.sendNotification("je1468@student.uni-lj.si", "Msg from fri-riders", "Test notification"));
    }

    @RequestMapping(value = "notification-test", method = RequestMethod.GET)
    public ResponseEntity<String> testNotification() {
        // discover service and use it
        return ResponseEntity.ok(accommodationService.testNotification());
    }

    @RequestMapping(value = "info", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> friRidersInfo() {
        String data = "{" +
                "\"clani\": [\"ts4293\", \"ub6189\", \"je1468\"]," +
                "\"opis_projekta\": \"Nas projekt implementira aplikacijo za oddajo nepremicnin.\"," +
                "\"mikrostoritve\": " +
                "[\"http://169.51.16.54:30735/v1/users\"," +
                " \"http://169.51.16.54:31558/v1/bookings\"," +
                " \"http://169.51.16.54:32641/v1/accommodations\"," +
                " \"http://169.51.16.54:31726/swagger-ui.html\"]," +
                "\"github\": " +
                "[\"https://github.com/fri-riders/users\"," +
                " \"https://github.com/fri-riders/accommodations\"," +
                " \"https://github.com/fri-riders/display-bookings\"," +
                " \"https://github.com/fri-riders/notifications\"]," +
                "\"travis\": " +
                "[\"https://travis-ci.org/fri-riders/users\"," +
                " \"https://travis-ci.org/fri-riders/accommodations\"," +
                " \"https://travis-ci.org/fri-riders/display-bookings\"," +
                " \"https://travis-ci.org/fri-riders/notifications\"]," +
                "\"dockerhub\":" +
                " [\"https://hub.docker.com/r/tomisebjanic/rso-users\"," +
                " \"https://hub.docker.com/r/janerz6/accommodations\"," +
                " \"https://hub.docker.com/r/urosbajc/display-bookings\"," +
                " \"https://hub.docker.com/r/janerz6/notifications\"]" +
                "}";
        return ResponseEntity.ok(data);
    }
}