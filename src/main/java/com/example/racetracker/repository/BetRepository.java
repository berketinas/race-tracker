package com.example.racetracker.repository;

import com.example.racetracker.domain.Bet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BetRepository extends JpaRepository<Bet, Long> {
    List<Bet> findAllByDriver(String driver);

    @Query("SELECT SUM(bet.amount) FROM Bet bet WHERE bet.driver = :driver")
    double getTotalForDriver(@Param("driver") String driver);
}
