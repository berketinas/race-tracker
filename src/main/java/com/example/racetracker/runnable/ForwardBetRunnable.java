package com.example.racetracker.runnable;

import com.example.racetracker.manager.TrackerManager;
import org.springframework.integration.channel.AbstractSubscribableChannel;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.support.MessageBuilder;

public class ForwardBetRunnable implements Runnable {
    private static final AbstractSubscribableChannel betApiChannel = (DirectChannel) TrackerManager.getContext().getBean("betApiChannel");
    private final String bet;

    public ForwardBetRunnable(String bet) {
        this.bet = bet;
    }

    public void run() {
        ForwardBetRunnable.betApiChannel.send(MessageBuilder.withPayload(this.bet).build());
    }
}
