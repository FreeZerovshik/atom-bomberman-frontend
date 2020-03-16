package com.game.service;

import com.game.model.Pawn;
import com.game.util.JsonInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;

@Component
public class Replicator {

    @Autowired
    GameSession gameSession;

    @Autowired
    GameRepository gameRepository;

    public String getPawnJson() {
        List<Pawn> pawnList =  gameSession.getPawns();
        return null;
    }

    public void sendMessage() throws IOException {
        for (Pawn pawn : gameSession.getPawns()) {
            WebSocketSession session = pawn.getSession();
            session.sendMessage(new TextMessage(gameRepository.getMessage()));
        }
    }

}
