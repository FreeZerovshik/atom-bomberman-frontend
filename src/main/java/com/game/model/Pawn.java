package com.game.model;

import com.game.message.Message;
import com.game.message.Topic;
import com.game.tick.Tickable;
import com.game.util.StringHelper;

import java.util.concurrent.atomic.AtomicLong;

import static com.game.util.JsonInterface.toJson;


/**
 * Created by sergey on 3/14/17.
 */

public class Pawn implements Tickable {
    private static AtomicLong idGenerator = new AtomicLong();
    private final long id = idGenerator.getAndIncrement();
    private String name = (new StringHelper()).randomAlphaNumeric(10);
    private Position position;
    private Long    velocity;
    private Integer maxBombs;
    private Integer bombPower;
    private Long    speedModifier;


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public void tick(long elapsed) {
       Topic topic = Topic.POSSESS;
       Message msg = new Message(topic, toJson(this));
       // session.sendMessage(new TextMessage(toJson(msg)));


//        gameRepository.put(msg);

//
       System.out.println(toJson(msg));
       msg = null;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Long getVelocity() {
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

    public Long getSpeedModifier() {
        return speedModifier;
    }

    public void setSpeedModifier(Long speedModifier) {
        this.speedModifier = speedModifier;
    }

    //    public void setSession(WebSocketSession session) {
//        this.session = session;
//    }

    //    @Override
//    public String toString() {
//        return "GameSession{" +
//                "connections=" + Arrays.toString(connections) +
//                ", id=" + id +
//                '}';
//    }

}
