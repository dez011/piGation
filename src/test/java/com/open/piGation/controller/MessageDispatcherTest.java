package com.open.piGation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.open.piGation.itf.MessageHandler;
import com.open.piGation.message.GeneralMessage;
import com.open.piGation.service.GPIOService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MessageDispatcherTest {
    @Autowired
    private MessageDispatcher dispatcher;

    @Autowired
    private List<MessageHandler> handlers;

    @Autowired
    private GPIOService gpioService;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void dispatchError() {
        GeneralMessage message = new GeneralMessage("testError", "action", "subAction", "content");
        Assertions.assertEquals("{\"status\":\"error\",\"message\":\"No handler found for message type\"}", dispatcher.dispatch(message));
    }
    @Test
    void dispatchWaterStart() throws JsonProcessingException {
        GeneralMessage message = new GeneralMessage("watering", "start", "", "{\"plantId\": \"plant123\", \"duration\": 10}"); // water plant123 for 10 seconds
        String result = dispatcher.dispatch(message);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(result);

        String action = jsonNode.get("action").asText();
        String finished = jsonNode.get("finished").asText();

        assertEquals("watering action start", action);
        assertEquals("finished: true", finished);
    }


}