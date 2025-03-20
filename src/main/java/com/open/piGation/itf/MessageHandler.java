package com.open.piGation.itf;

import com.open.piGation.message.GeneralMessage;

public interface MessageHandler {
    boolean containsMessageType(String messageType);

    String handleMessage(GeneralMessage message);
}
