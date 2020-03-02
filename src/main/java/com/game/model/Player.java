package com.game.model;

import com.game.message.Message;
import com.game.message.Topic;
import com.game.tick.Tickable;
import com.game.util.StringHelper;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import static com.game.util.JsonInterface.*;


/**
 * Created by sergey on 3/14/17.
 */

public class Player implements Tickable {
    private Long id;
    private String name;
    private WebSocketSession session;


    public Player(Long id) {
        this.id = id;
        this.name = (new StringHelper()).randomAlphaNumeric(10);
    }

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
//       System.out.println(json);
       msg = null;
    }

    public void setSession(WebSocketSession session) {
        this.session = session;
    }

    //    @Override
//    public String toString() {
//        return "GameSession{" +
//                "connections=" + Arrays.toString(connections) +
//                ", id=" + id +
//                '}';
//    }

}
