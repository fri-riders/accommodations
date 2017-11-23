package com.fri.rso.fririders.accommodations.controller;

import com.fri.rso.fririders.accommodations.data.Accommodation;
import com.fri.rso.fririders.accommodations.data.Booking;
import com.fri.rso.fririders.accommodations.repository.AccommodationRepository;
import com.google.common.collect.Lists;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/accommodations")
public class AccommodationController {

    private final AccommodationRepository repository;

    private final RestTemplate restTemplate;

    private final CounterService counterService;
    private final GaugeService gaugeService;

    @Autowired
    public AccommodationController(RestTemplateBuilder restTemplateBuilder, AccommodationRepository repository, CounterService counterService, GaugeService gaugeService) {
        this.restTemplate = restTemplateBuilder.build();
        this.repository = repository;
        this.counterService = counterService;
        this.gaugeService = gaugeService;
    }

    private static final String basePath = "https://jsonplaceholder.typicode.com";
    private static final String bookingsBasePath = "http://localhost:8080/v1/bookings";
    private Logger log = LogManager.getLogger(AccommodationController.class.getName());

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public List<Accommodation> getByLocation(@PathVariable(value = "id") Long id) {
        return repository.findById(id);
    }

    @RequestMapping(value = "/{id}/availability", method = RequestMethod.GET)
    public ResponseEntity getAvailability(@PathVariable(value = "id") int id, @RequestParam long fromTime, @RequestParam long toTime) {
        final Date startDate = new Date(fromTime);
        final Date endDate = new Date(toTime);
        if (startDate.compareTo(endDate) >= 0) {
            return ResponseEntity.badRequest().build();
        }
        try {

//        final URI consul = discoveryClient.getInstances("bookings").stream()
//                .findFirst().map(ServiceInstance::getUri)
//                .orElse(null);
            final List<Booking> bookings = restTemplate.exchange(bookingsBasePath,
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Booking>>() {
                    }).getBody();
            final boolean isAvailableInterval = bookings.stream()
                    .filter(booking -> booking.getIdAccommodation() == id)
                    .noneMatch(booking ->
                            booking.getFromDate().compareTo(startDate) <= 0 &&
                                    booking.getToDate().compareTo(startDate) >= 0
                    );
            gaugeService.submit("services.accommodations.availability.error",0);
            return ResponseEntity.ok(isAvailableInterval);
        } catch (RestClientException e) {
            gaugeService.submit("services.accommodations.availability.error",1);
            return ResponseEntity.badRequest().body("Bookings service unavailable!");
        }

    }

    @RequestMapping(value = "/location/{location}", method = RequestMethod.GET)
    public List<Accommodation> getByLocation(@PathVariable(value = "location") String location) {
        return repository.findByLocation(location);
    }

    @RequestMapping(value = "/capacity/{capacity}", method = RequestMethod.GET)
    public List<Accommodation> getByCapacity(@PathVariable(value = "capacity") int capacity) {
        return repository.findByCapacity(capacity);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Accommodation> getAll() {
        counterService.increment("meter.services.accommodations.getAll.invoked");
        return Lists.newArrayList(repository.findAll());
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
    public ResponseEntity<Accommodation> updateAccommodation(@PathVariable("id") Long id, @ModelAttribute("accommodation") Accommodation acc_update) {
        Accommodation accommodation = repository.findOne(id);
        if (accommodation != null) {
            accommodation.setName(acc_update.getName());
            accommodation.setCapacity(acc_update.getCapacity());
            accommodation.setDescription(acc_update.getDescription());
            accommodation.setLocation(acc_update.getLocation());
            accommodation.setPricePerDay(acc_update.getPricePerDay());

            repository.save(accommodation);
            return ResponseEntity.ok().body(accommodation);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    // Simple fake api call for demo purpuses
    public Accommodation getAccommodations(long id) {
        return this.restTemplate.getForObject(basePath + "/posts/" + id, Accommodation.class);
    }
}
