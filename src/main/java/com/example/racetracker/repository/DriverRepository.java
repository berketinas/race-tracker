package com.example.racetracker.repository;

import com.example.racetracker.domain.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, String> {
    Driver findByName(String name);
}
