package com.fri.rso.fririders.accommodations.service;

import com.fri.rso.fririders.accommodations.data.Accommodation;
import com.fri.rso.fririders.accommodations.feign.clients.NotificationsClient;
import com.fri.rso.fririders.accommodations.repository.AccommodationRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

@Service
public class AccommodationService {
    private NotificationsClient notificationsClient;
    private final AccommodationRepository accommodationRepository;

    public AccommodationService(NotificationsClient notificationsClient, AccommodationRepository accommodationRepository) {
        this.notificationsClient = notificationsClient;
        this.accommodationRepository = accommodationRepository;
    }

    public Accommodation updateAccommodation(long id, final Accommodation acc_update) {
        Accommodation accommodation = accommodationRepository.findOne(id);
        if (accommodation != null) {
            accommodation.setName(acc_update.getName());
            accommodation.setCapacity(acc_update.getCapacity());
            accommodation.setDescription(acc_update.getDescription());
            accommodation.setLocation(acc_update.getLocation());
            accommodation.setPricePerDay(acc_update.getPricePerDay());

            accommodationRepository.save(accommodation);
            return accommodation;
        } else {
            return null;
        }
    }

    @HystrixCommand(fallbackMethod = "sendNotificationFallback", commandProperties = {
            //@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "60000"),
            // when circuit is opened wait for 60s then start excepting requests
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5")
    })
    public String sendNotification(String recipient) {
        return notificationsClient.sendNotification(recipient, "Test-fririders", "Test notification");
    }

    @HystrixCommand(fallbackMethod = "sendNotificationFallback2", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    })
    public String testNotification() {
        return notificationsClient.notificationTest();
    }

    public String sendNotificationFallback(String recipient) {
        return "Notifications service is currently not available.";
    }

    public String sendNotificationFallback2() {
        return "Notifications service is currently not available.";
    }
}
