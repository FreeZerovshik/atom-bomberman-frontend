package com.game.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


/**
 * Created by sergey on 3/14/17.
 */

public class Pawn {
    @JsonIgnore
    protected static AtomicLong idGenerator = new AtomicLong();

    private final long id = idGenerator.getAndIncrement();
    private String name;
    private Position position;
    private double velocity = .5 ;
    private Integer maxBombs = 1;
    private Integer bombPower =1;
    private double speedModifier = 1.0;

    @JsonIgnore
    protected WebSocketSession session;

    public Pawn(String name) {
        this.name = name;
    }


    public static List<String> getFields(Pawn pawn){
        List<String> list = new ArrayList<>();

//        list.add(this.position.getX().toString());
//        list.add(this.position.getY().toString());
        list.add(pawn.getId().toString());
        list.add(String.valueOf(pawn.getVelocity()));
        list.add(pawn.getMaxBombs().toString());
        list.add(pawn.getBombPower().toString());
        list.add(String.valueOf(pawn.getSpeedModifier()));
        list.add(pawn.getClass().toString());
        return list;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WebSocketSession getSession() {
        return session;
    }

    public void setSession(WebSocketSession session) {
        this.session = session;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(Long velocity) {
        this.velocity = velocity;
    }

    public Integer getMaxBombs() {
        return maxBombs;
    }

    public void setMaxBombs(Integer maxBombs) {
        this.maxBombs = maxBombs;
    }

    public Integer getBombPower() {
        return bombPower;
    }

    public void setBombPower(Integer bombPower) {
        this.bombPower = bombPower;
    }

    public double getSpeedModifier() {
        return speedModifier;
    }

    public void setSpeedModifier(Long speedModifier) {
        this.speedModifier = speedModifier;
    }

}
