package com.example.racetracker.domain;

import javax.persistence.*;

@Entity
@Table(name = "DRIVERS")
public class Driver {
    @Id
    @Column(name = "NAME")
    private String name;

    @Column(name = "LAST_LAP")
    private double lastLap;

    @Column(name = "TOTAL_TIME")
    private double totalTime;

    public Driver() {

    }

    public Driver(String name, double lastLap, double totalTime) {
        this.name = name;
        this.lastLap = lastLap;
        this.totalTime = totalTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLastLap() {
        return lastLap;
    }

    public void setLastLap(double lastLap) {
        this.lastLap = lastLap;
    }

    public double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(double totalTime) {
        this.totalTime = totalTime;
    }

    public static int compare(Driver d1, Driver d2) {
        return Double.compare(d1.getTotalTime(), d2.getTotalTime());
    }
}
