package com.game.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.game.service.GameSession;
import com.game.util.JsonInterface;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;

@JsonIgnoreProperties({"lock", "state", "nextExecutionTime", "period"})
public class Bomb  extends TimerTask {
    @JsonIgnore
    protected static AtomicLong idGenerator = new AtomicLong();

    private final long id = idGenerator.getAndIncrement();
    private objectType type = objectType.Bomb;
    private Position position;

    public Bomb(objectType type, Position position) {
        this.type = type;
        this.position = position;
    }

    public Position getPosition(){
        return position;
    }


    @Override
    public void run() {
        System.out.println(">>>> BOOOMMMMM!!!! <<<<<");
    }
}
