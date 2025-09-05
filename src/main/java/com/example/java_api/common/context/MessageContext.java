package com.example.java_api.common.context;

public class MessageContext {

    private static final ThreadLocal<String> MESSAGE = new ThreadLocal<>();

    public static void setMessage(String message) {
        MESSAGE.set(message);
    }

    public static String getMessage() {
        return MESSAGE.get();
    }

    public static void clear() {
        MESSAGE.remove();
    }
}
