package com.example.racetracker.service;

import com.example.racetracker.domain.Bet;
import com.example.racetracker.repository.BetRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.DoubleStream;

@Service
@EnableAsync
public class BetService {
    private ConcurrentHashMap<String, Double> betAmounts;
    private BetRepository betRepo;
    private String[] drivers = new String[]{"VER", "PER", "HAM", "RUS",
            "LEC", "SAI", "VET", "STR",
            "OCO", "ALO", "RIC", "NOR",
            "ZHO", "BOT", "MAG", "MSC",
            "ALB", "LAT", "TSU", "GAS"
    };

    public BetService(BetRepository betRepo) {
        betAmounts = new ConcurrentHashMap<>(20);
        for (String driver: drivers) {
            betAmounts.put(driver, 0.00d);
        }

        this.betRepo = betRepo;
    }

    @Async
    public void placeBet(Bet bet) {
        betAmounts.compute(bet.getDriver(), (key, value) -> value + bet.getAmount());
        betRepo.save(bet);
    }

    public ConcurrentHashMap<String, Double> getBetTotals() {
        System.out.println(betAmounts.values().stream().mapToDouble(element -> element).sum() + " - " + betRepo.findAll().stream().flatMapToDouble(element -> DoubleStream.of(element.getAmount())).sum());
        return this.betAmounts;
    }
}
