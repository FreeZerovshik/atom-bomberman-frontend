package com.game.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


/**
 * Created by sergey on 3/14/17.
 */
@JsonTypeInfo (use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
//@JsonSubTypes({@JsonSubTypes.Type(value = Position.class, name = "position")})
public class Pawn {
    @JsonIgnore
    protected static AtomicLong idGenerator = new AtomicLong();


    @JsonIgnore
    private String name;

    private final long id = idGenerator.getAndIncrement();

    private tileType type = tileType.Pawn;
    private Position position;
    private double velocity = .5 ;
    private Integer maxBombs = 1;
    private Integer bombPower =1;
    private double speedModifier = 1.0;
    private Boolean alive = true;

    @JsonIgnore
    protected WebSocketSession session;

    public Pawn(String name, Position position) {
        this.name = name;
        this.position = position;
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

    public void move(String direction){

        switch (direction){
            case "UP":    position.setY(position.getY()+1); break;
            case "DOWN":  position.setY(position.getY()-1); break;
            case "LEFT":  position.setX(position.getX()-1); break;
            case "RIGHT": position.setX(position.getX()+1); break;
        }
    }

}
