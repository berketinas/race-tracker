package com.example.racetracker.service;

import com.example.racetracker.domain.LapInfo;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class LapService {
    private static LapInfo fastestLap;

    private static AtomicInteger lapCount = new AtomicInteger(0);

    public LapService() {
        fastestLap = new LapInfo("N/A", 999.99d);
    }

    public int getLapCount() {
        return lapCount.get();
    }

    public void incrementLap() {
        lapCount.incrementAndGet();
    }

    public LapInfo getFastestLap() {
        return fastestLap;
    }

    public void setFastestLap(String driver, double laptime) {
        fastestLap.setDriver(driver);
        fastestLap.setLaptime(laptime);
    }
}
