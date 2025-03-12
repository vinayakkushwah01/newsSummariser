package com.newsSummeriser.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newsSummeriser.model.City;
import com.newsSummeriser.model.State;

public interface CityRepository extends JpaRepository<City, Long> {
    boolean existsByNameAndState(String name, State state);
}

