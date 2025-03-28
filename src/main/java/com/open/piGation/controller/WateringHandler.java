package com.open.piGation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.open.piGation.message.GeneralMessage;
import com.open.piGation.message.MKE;
import com.open.piGation.service.WateringServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

@Controller
public class WateringHandler extends AbstractMessageHandler {

    private WateringServiceImpl wateringService;

    @Autowired
    public WateringHandler(WateringServiceImpl wateringService){
        this.addMKE(new MKE("watering", "start", ""));
        this.addMKE(new MKE("watering", "stop", ""));
        this.wateringService = wateringService;
    }

    @Override
    public String handleMessage(GeneralMessage message) {
        boolean finished;
        System.out.println("Handling message: " + message);
        Map<String, Object> response = new HashMap<>();

        if(message.getAction().equals("start")){
            finished = wateringService.water(message.getContent());
        } else if(message.getAction().equals("stop")){
            finished = wateringService.stop();
        } else {
            return "Unknown action";
        }
        response.put("action", "watering action " + message.getAction());
        response.put("finished", "finished: " + finished);

        try {
            return ObjectMapperSingleton.getInstance().writeValueAsString(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
