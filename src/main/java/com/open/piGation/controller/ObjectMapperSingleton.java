package com.open.piGation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperSingleton {
    private static final ObjectMapper INSTANCE = new ObjectMapper();

    private ObjectMapperSingleton() {
    }

    public static ObjectMapper getInstance(){
        return INSTANCE;
    }
}
