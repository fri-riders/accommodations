package com.fri.rso.fririders.accommodations;

import com.fri.rso.fririders.accommodations.data.Accommodation;
import com.fri.rso.fririders.accommodations.repository.AccommodationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@EnableDiscoveryClient
@SpringBootApplication
public class AccommodationsApplication {

	private static final Logger log = LoggerFactory.getLogger(AccommodationsApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AccommodationsApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(AccommodationRepository repository) {
		return (args) -> {
			// save a couple of customers
			repository.save(new Accommodation(1L, "Hotel Slon", "Ljubljana","", 4, 120.0));
			repository.save(new Accommodation(2L, "Motel Medno", "Medno","", 5, 40.0));
			repository.save(new Accommodation(3L, "Hotel Kanu", "Dragočajna","", 2, 50.0));
			repository.save(new Accommodation(4L, "Hotel K", "Dragočajna","", 5, 50.0));

//			// fetch all customers
//			log.info("Customers found with findAll():");
//			log.info("-------------------------------");
//			for (Accommodation customer : repository.findAll()) {
//				log.info(customer.toString());
//			}
//			log.info("");
//
//			// fetch an individual customer by ID
//			Accommodation customer = repository.findOne(1L);
//			log.info("Customer found with findOne(1L):");
//			log.info("--------------------------------");
//			log.info(customer.toString());
//			log.info("");
//
//			// fetch customers by last name
//			log.info("Customer found with findByCapacity(5):");
//			log.info("--------------------------------------------");
//			for (Accommodation bauer : repository.findByCapacity(5)) {
//				log.info(bauer.toString());
//			}
//			log.info("");
		};
	}

}
