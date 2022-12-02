package com.example.racetracker.domain;

import javax.persistence.*;

@Entity
@Table(name = "BETS")
public class Bet {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private long id;

    @Column(name = "DRIVER")
    private String driver;

    @Column(name = "AMOUNT")
    private double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "CONDITION")
    private BetCondition condition;

    public Bet() {
        this.driver = "";
        this.amount = 0.00d;
        this.condition = null;
    }

    public Bet(String driver, double amount, BetCondition condition) {
        this.driver = driver;
        this.amount = amount;
        this.condition = condition;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public BetCondition getCondition() {
        return condition;
    }

    public void setCondition(BetCondition condition) {
        this.condition = condition;
    }
}
