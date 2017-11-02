package com.fri.rso.fririders.accommodations.repository;

import com.fri.rso.fririders.accommodations.data.Accommodation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccommodationRepository extends CrudRepository<Accommodation, Long> {
    List<Accommodation> findByCapacity(int capacity);
    List<Accommodation> findById(Long id);
    List<Accommodation> findByLocation(String location);
}

