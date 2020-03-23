package com.game.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.concurrent.atomic.AtomicLong;

public class Bomb {
    @JsonIgnore
    protected static AtomicLong idGenerator = new AtomicLong();

    private final long id = idGenerator.getAndIncrement();
    private objectType type = objectType.Bomb;
    private Position position;

    public Bomb(objectType type, Position position) {
        this.type = type;
        this.position = position;
    }
}
