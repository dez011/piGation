package com.open.piGation.controller;

import com.open.piGation.message.GeneralMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class MessageRouter {
    private final MessageDispatcher dispatcher;

    @Autowired
    public MessageRouter(MessageDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @PostMapping("/dispatch-post")
    public String dispatchPost(@RequestBody GeneralMessage message) {
        return dispatcher.dispatch(message);
    }

    @GetMapping("/dispatch-get")
    public String dispatchMessageGet(
            @RequestParam String action,
            @RequestParam String subAction,
            @RequestParam String messageType,
            @RequestParam String content) {

        GeneralMessage message = new GeneralMessage(action, subAction, messageType, content);
        return dispatcher.dispatch(message);

    }

    }
