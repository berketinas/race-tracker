package com.example.racetracker.domain;

public class LapInfo {
    private String driver;
    private double laptime;

    public LapInfo() {
        this.driver = "";
        this.laptime = 0.00d;
    }

    public LapInfo(String driver, double laptime) {
        this.driver = driver;
        this.laptime = laptime;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public double getLaptime() {
        return laptime;
    }

    public void setLaptime(double laptime) {
        this.laptime = laptime;
    }

    @Override
    public String toString() {
        return "LapInfo{" +
                "driver='" + driver + '\'' +
                ", laptime=" + laptime +
                '}';
    }
}
