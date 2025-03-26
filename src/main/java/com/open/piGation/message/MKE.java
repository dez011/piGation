package com.open.piGation.message;

import java.util.Objects;

public class MKE {
    private String messageType;
    private String action;
    private String subAction;

    public MKE(String messageType, String action, String subAction) {
        this.messageType = messageType;
        this.action = action;
        this.subAction = subAction;
    }

    public boolean matches(String messageType, String action, String subAction){
        return Objects.equals(this.messageType, messageType) && this.action.equals(action) && this.subAction.equals(subAction);
    }

    public boolean isInterested(String messageType){
        return Objects.equals(this.messageType, messageType);
    }

    public String getMessageType(){
        return messageType;
    }
}
