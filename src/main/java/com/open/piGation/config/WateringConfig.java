package com.open.piGation.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "watering")
public class WateringConfig {
    private List<PlantConfig> plants = new ArrayList<>();

    public List<PlantConfig> getPlants() {
        return plants;
    }

    public void setPlants(List<PlantConfig> plants) {
        this.plants = plants;
    }

    public static class PlantConfig{
        private int id;
        private int duration;
        private String startTime;
        private String endTime;
        private int interval;
        private int mainPumpPin;

        public int getMoistureSensorPin() {
            return moistureSensorPin;
        }

        public void setMoistureSensorPin(int moistureSensorPin) {
            this.moistureSensorPin = moistureSensorPin;
        }

        private int moistureSensorPin;

        public int getSplitterPin() {
            return splitterPin;
        }

        public void setSplitterPin(int splitterPin) {
            this.splitterPin = splitterPin;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getInterval() {
            return interval;
        }

        public void setInterval(int interval) {
            this.interval = interval;
        }

        public int getMainPumpPin() {
            return mainPumpPin;
        }

        public void setMainPumpPin(int mainPumpPin) {
            this.mainPumpPin = mainPumpPin;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private int splitterPin;
        private String name;

        public String toJson() {
            Map<String, Object> plantMap = new HashMap<>();
            plantMap.put("id", id);
            plantMap.put("duration", duration);
            plantMap.put("startTime", startTime);
            plantMap.put("endTime", endTime);
            plantMap.put("interval", interval);
            plantMap.put("mainPumpPin", mainPumpPin);
            plantMap.put("splitterPin", splitterPin);
            plantMap.put("moistureSensorPin", moistureSensorPin);
            plantMap.put("name", name);

            try{
                return new ObjectMapper().writeValueAsString(plantMap);
            } catch (Exception e){
                return "Failed to convert watering config to JSON";
            }
        }
    }

}
