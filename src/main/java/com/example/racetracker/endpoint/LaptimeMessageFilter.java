package com.example.racetracker.endpoint;

import com.example.racetracker.domain.LapInfo;
import org.springframework.integration.MessageRejectedException;
import org.springframework.integration.core.MessageSelector;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;

public abstract class LaptimeMessageFilter implements MessageSelector {

    @Override
    public boolean accept(Message<?> message) throws MessagingException {
        Object payload = message.getPayload();

        if (payload instanceof LapInfo) {
            return filterMessage((LapInfo) payload);
        } else {
            throw new MessageRejectedException(message, "Unknown data type.");
        }
    }

    protected abstract boolean filterMessage(LapInfo data);
}
