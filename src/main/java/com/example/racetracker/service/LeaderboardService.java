package com.example.racetracker.service;

import com.example.racetracker.domain.Driver;
import com.example.racetracker.domain.LapInfo;
import com.example.racetracker.repository.DriverRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaderboardService {
    private final DriverRepository driverRepo;

    public LeaderboardService(DriverRepository driverRepo) {
        this.driverRepo = driverRepo;
    }

    public List<Driver> getLeaderboard() {
        return driverRepo.findAll().stream()
                .sorted(Driver::compare)
                .collect(Collectors.toList());
    }

    public void updateLeaderboard(LapInfo lap) {
        Driver driver = driverRepo.findByName(lap.getDriver());
        driver.setLastLap(lap.getLaptime());
        driver.setTotalTime(driver.getTotalTime() + lap.getLaptime());
        driverRepo.save(driver);
//        int index = leaderboard.indexOf(
//                leaderboard.stream().
//                        filter(entry -> entry.getName().equals(lap.getDriver()))
//                        .findFirst()
//                        .orElse(null)
//        );
//
//        leaderboard.get(index).setLastLap(lap.getLaptime());
//        leaderboard.get(index).setTotalTime(leaderboard.get(index).getTotalTime() + lap.getLaptime());
    }
}
