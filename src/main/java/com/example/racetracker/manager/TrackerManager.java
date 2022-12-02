package com.example.racetracker.manager;

import com.example.racetracker.domain.Bet;
import com.example.racetracker.domain.Driver;
import com.example.racetracker.domain.LapInfo;
import com.example.racetracker.endpoint.BetMessageHandler;
import com.example.racetracker.endpoint.LaptimeMessageFilter;
import com.example.racetracker.endpoint.LaptimeMessageHandler;
import com.example.racetracker.service.BetService;
import com.example.racetracker.service.LapService;
import com.example.racetracker.service.LeaderboardService;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.channel.AbstractSubscribableChannel;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TrackerManager {
    private static LapService lapService;
    private static BetService betService;
    private final LeaderboardService leaderboardService;

    private static AbstractApplicationContext ctx;

    public TrackerManager(LapService lapService, BetService betService, LeaderboardService leaderboardService) {
        TrackerManager.ctx = new ClassPathXmlApplicationContext("/META-INF/spring/application.xml", TrackerManager.class);
        AbstractSubscribableChannel trackerChannel = (PublishSubscribeChannel) TrackerManager.ctx.getBean("trackerChannel");
        AbstractSubscribableChannel fastestLapChannel = (DirectChannel) TrackerManager.ctx.getBean("fastestLapChannel");
        AbstractSubscribableChannel betChannel = (PublishSubscribeChannel) TrackerManager.ctx.getBean("betChannel");

        TrackerManager.lapService = lapService;
        TrackerManager.betService = betService;
        this.leaderboardService = leaderboardService;

        trackerChannel.subscribe(new LaptimeHandler());
        fastestLapChannel.subscribe(new FastestLapHandler());
        betChannel.subscribe(new BetHandler());
    }

    public static ClassPathXmlApplicationContext getContext() { return (ClassPathXmlApplicationContext) TrackerManager.ctx; }

    public List<Driver> getLeaderboard() {
        return leaderboardService.getLeaderboard();
    }

    public LapInfo getFastestLap() {
        return lapService.getFastestLap();
    }

    public int getLapCount() {
        return lapService.getLapCount();
    }

    public ConcurrentHashMap<String, Double> getBetTotals() {
        return betService.getBetTotals();
    }

    private class LaptimeHandler extends LaptimeMessageHandler {
        @Override
        protected void receive(LapInfo data) {
            leaderboardService.updateLeaderboard(data);
        }
    }

    private class BetHandler extends BetMessageHandler {
        @Override
        protected void receive(Bet bet) {
            betService.placeBet(bet);
        }
    }

    private static class FastestLapHandler extends LaptimeMessageHandler {
        @Override
        protected void receive(LapInfo data) {
            if (data.getLaptime() < lapService.getFastestLap().getLaptime()) {
                lapService.setFastestLap(data.getDriver(), data.getLaptime());
            }
        }
    }

    public static class FastestLapFilter extends LaptimeMessageFilter {
        @Override
        protected boolean filterMessage(LapInfo data) {
            return data.getLaptime() < lapService.getFastestLap().getLaptime();
        }
    }
}
