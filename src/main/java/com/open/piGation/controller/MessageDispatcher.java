package com.open.piGation.controller;

import com.open.piGation.itf.MessageHandler;
import com.open.piGation.message.GeneralMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Component
public class MessageDispatcher {
    private static final Logger logger = LoggerFactory.getLogger(MessageDispatcher.class);
    private List<AbstractMessageHandler> handlers = new ArrayList<>();

    @Autowired
    public MessageDispatcher(List<AbstractMessageHandler> messageHandlers){
        this.handlers = messageHandlers;

    }

    public String dispatch(GeneralMessage message){
        for(MessageHandler handler : handlers){
            if(handler.containsMessageType(message.getMessageType())){
                return handler.handleMessage(message);
            } else {
                logger.debug("Handler does not support message type: {}. Checked handler class: {}",
                        message.getMessageType(), handler.getClass().getName());
            }
        }
        logger.warn("Failed to process message: no handler found for type {}", message.getMessageType());

        return "{\"status\":\"error\",\"message\":\"No handler found for message type\"}";
    }
}
