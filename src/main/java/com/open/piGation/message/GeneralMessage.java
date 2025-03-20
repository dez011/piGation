package com.open.piGation.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GeneralMessage {
    private String messageType;
    private String action;

    private String subAction;

    private String content;

    public GeneralMessage() {

    }

    @JsonCreator
    public GeneralMessage(@JsonProperty("messageType") String messageType,
                          @JsonProperty("action") String action,
                            @JsonProperty("subAction") String subAction,
                          @JsonProperty("content") String content) {
        this.messageType = messageType;
        this.action = action;
        this.content = content;
    }

    public String getMessageType() {
        return messageType;
    }

    public String getAction() {
        return action;
    }

    public String getContent() {
        return content;
    }

}

