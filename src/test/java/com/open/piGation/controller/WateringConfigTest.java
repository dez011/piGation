package com.open.piGation.controller;

import com.open.piGation.config.WateringConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WateringConfigTest {

    @Autowired
    private WateringConfig wateringConfig;

    @Test
    void getDuration() {
    }

    @Test
    void setDuration() {
        WateringConfig.PlantConfig e1 = new WateringConfig.PlantConfig();
        wateringConfig.setPlants(
                List.of(
                        e1
                )
        );
        wateringConfig.getPlants().get(0).setDuration(20);
        assertEquals(20, wateringConfig.getPlants().get(0).getDuration());
    }
}