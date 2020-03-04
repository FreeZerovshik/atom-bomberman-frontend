package com.game.model;

import com.game.message.Message;
import com.game.message.Topic;
import com.game.tick.Tickable;
import com.game.util.StringHelper;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.atomic.AtomicLong;

import static com.game.util.JsonInterface.*;


/**
 * Created by sergey on 3/14/17.
 */

public class Player  {
    private static AtomicLong idGenerator = new AtomicLong();
    private final long id = idGenerator.getAndIncrement();
    private String name;
    // private String name = (new StringHelper()).randomAlphaNumeric(10);

    public Player(String name) {
        this.name = name;
    }

    private WebSocketSession session;

    public long getId() {
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
}
