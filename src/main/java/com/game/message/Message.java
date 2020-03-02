package com.game.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.game.event.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.atomic.AtomicLong;

public class Message {
    private final Topic topic;
    private final String data;
    private static AtomicLong idGenerator = new AtomicLong();
    private final long id = idGenerator.getAndIncrement();

    @Autowired
    private EventHandler eventHandler;

    public Message(Topic topic, String data) {
        this.topic = topic;
        this.data = data;
    }

    @JsonCreator
    public Message(@JsonProperty("topic") Topic topic, @JsonProperty("data") JsonNode data) {
        this.topic = topic;
        this.data = data.toString();
    }

    Topic getTopic() {
        return topic;
    }

    String getData() {
        return data;
    }

    public long getId() {
        return id;
    }
    public void sendMessage(WebSocketSession session){

    }

}
