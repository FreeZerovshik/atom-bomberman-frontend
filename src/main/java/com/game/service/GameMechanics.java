package com.game.service;

import com.game.model.Player;
import com.game.tick.Tickable;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;


public class GameMechanics implements Tickable {

    private WebSocketSession session;
    private Player player;

    public GameMechanics(WebSocketSession session){
        this.session = session;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void tick(long elapsed) throws IOException {
//        String replica = "{\"topic\": \"REPLICA\", {\"id\":7,\"type\":\"Wood\",\"position\":{\"y\":20,\"x\":10}}}";
//        System.out.println(replica);
//        session.sendMessage(new TextMessage(replica));
    }
}
