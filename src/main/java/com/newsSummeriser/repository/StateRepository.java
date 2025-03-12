package com.newsSummeriser.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newsSummeriser.model.State;

public interface StateRepository extends JpaRepository<State, Long> {
    // State findByName(String name);
    Optional<State> findByName(String name);
}