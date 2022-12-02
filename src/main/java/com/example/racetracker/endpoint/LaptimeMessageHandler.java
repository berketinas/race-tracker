package com.example.racetracker.endpoint;

import com.example.racetracker.domain.LapInfo;
import org.springframework.integration.MessageRejectedException;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

public abstract class LaptimeMessageHandler implements MessageHandler {
    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        Object payload = message.getPayload();

        if (payload instanceof LapInfo) {
            receive((LapInfo) payload);
        } else {
            throw new MessageRejectedException(message, "Unknown data type.");
        }
    }

    protected abstract void receive(LapInfo data);
}
