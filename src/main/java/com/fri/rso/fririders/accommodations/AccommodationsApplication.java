package com.fri.rso.fririders.accommodations;

import com.fri.rso.fririders.accommodations.data.Accommodation;
import com.fri.rso.fririders.accommodations.repository.AccommodationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
@EnableAutoConfiguration
@EnableDiscoveryClient
@SpringBootApplication
public class AccommodationsApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccommodationsApplication.class, args);
    }

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public CommandLineRunner demo(AccommodationRepository repository) {
        return (args) -> {
            // save a couple of customers
            repository.save(new Accommodation(1L, "Hotel Slon", "Ljubljana", "", 4, 120.0));
            repository.save(new Accommodation(2L, "Motel Medno", "Medno", "", 5, 40.0));
            repository.save(new Accommodation(3L, "Hotel Kanu", "Dragočajna", "", 2, 50.0));
            repository.save(new Accommodation(4L, "Hotel K", "Dragočajna", "", 5, 50.0));
        };
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);
        loggingFilter.setBeforeMessagePrefix(
                String.format("BR [%s|%s|%s] ",
                        applicationContext.getId().split(":")[0],
                        applicationContext.getId().split(":")[1],
                        applicationContext.getEnvironment().getProperty("app.version")));
        loggingFilter.setAfterMessagePrefix("AR ");
        return loggingFilter;
    }


}
