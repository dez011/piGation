package com.open.piGation.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.open.piGation.config.WateringConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class WateringServiceImpl {

    private final GPIOService gpioService;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final WateringConfig wateringConfig;

    @Autowired
    public WateringServiceImpl(GPIOService gpioService, WateringConfig wateringConfig) {
        this.gpioService = gpioService;
        this.wateringConfig = wateringConfig;
        scheduleWatering();
    }

    private void scheduleWatering(){
        for (WateringConfig.PlantConfig plant : wateringConfig.getPlants()) {
            LocalTime startTime = LocalTime.parse(plant.getStartTime());
            LocalTime endTime = LocalTime.parse(plant.getEndTime());
            int interval = plant.getInterval();

            long initialDelay = LocalTime.now().until(startTime, TimeUnit.MINUTES.toChronoUnit());
            long period = interval * 60L;

            scheduler.scheduleAtFixedRate(() -> {
                if (LocalTime.now().isBefore(endTime)) {
                    water(plant.toJson());
                }
            }, initialDelay, period, TimeUnit.MINUTES);
        }
    }

    public boolean water(String jsonContent){
        startPump();
        int duration = parseDuration(jsonContent);
        scheduler.schedule(this::stop, duration, TimeUnit.SECONDS);
        return true;
    }
    private int parseDuration(String jsonContent) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode content = objectMapper.readTree(jsonContent);
            return content.get("duration").asInt();
        } catch (Exception e) {
            return 30; // default duration
        }
    }

    public void startPump(){
        gpioService.setPumpState(true);
    }

    public boolean stop() {
        gpioService.setPumpState(false);
        return true;
    }
}
