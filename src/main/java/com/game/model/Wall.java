package com.game.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.concurrent.atomic.AtomicLong;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
public class Wall {
    Position position;

    private objectType type = objectType.Wood;

    @JsonIgnore
    protected static AtomicLong idGenerator = new AtomicLong();

    private final long id = idGenerator.getAndIncrement();

    public Wall(Position position) {
        this.position = position;
    }

}
