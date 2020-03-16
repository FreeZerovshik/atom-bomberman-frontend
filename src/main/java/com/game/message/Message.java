package com.game.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.concurrent.atomic.AtomicLong;


public class Message {
    private final Topic topic;

    private final MessageObjects data;
//    private Boolean gameOver = false;

    @JsonIgnore
    private static AtomicLong idGenerator = new AtomicLong();

    @JsonIgnore
    private final long id = idGenerator.getAndIncrement();

    @JsonCreator
    public Message(@JsonProperty("topic") Topic topic, @JsonProperty("data") MessageObjects data) {
        this.topic = topic;
        this.data = data;
    }

//    Topic getTopic() {
//        return topic;
//    }

//    String getData() {
//        return data;
//    }

    public long getId() {
        return id;
    }


    @Override
    public String toString() {
        return "Message{" +
                "topic=" + topic +
                ", data='" + data + '\'' +
                ", id=" + id +
                '}';
    }
}
