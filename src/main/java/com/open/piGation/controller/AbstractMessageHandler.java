package com.open.piGation.controller;


import com.open.piGation.itf.MessageHandler;
import com.open.piGation.message.GeneralMessage;
import com.open.piGation.message.MKE;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractMessageHandler implements MessageHandler {
    private Set<MKE> MKESet = new HashSet<MKE>();

    public void addMKE(MKE mke){
        MKESet.add(mke);
    }

    public AbstractMessageHandler(){

    }

    public boolean containsMessageType(String messageType){
        for(MKE mke : MKESet){
            if(mke.isInterested(messageType)){
                return true;
            }
        }
        return false;
    }

    public abstract String handleMessage(GeneralMessage message);
}
