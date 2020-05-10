package com.game.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;

@JsonIgnoreProperties({"lock", "state", "nextExecutionTime", "period"})
public class Fire extends TimerTask {
    @JsonIgnore
    protected static AtomicLong idGenerator = new AtomicLong();

    private final long id = idGenerator.getAndIncrement();
    private objectType type = objectType.Fire;
    private Position position;

    public Fire( Position position) {
        this.position = position;
    }


    @Override
    public void run() {
       System.out.println(">>>> BOOOMMMMM!!!! <<<<<");
    }
}
