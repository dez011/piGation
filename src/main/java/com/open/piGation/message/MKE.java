package com.open.piGation.message;

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
        return this.messageType == messageType && this.action.equals(action) && this.subAction.equals(subAction);
    }

    public boolean isInterested(String messageType){
        return this.messageType == messageType;
    }

    public String getMessageType(){
        return messageType;
    }
}
