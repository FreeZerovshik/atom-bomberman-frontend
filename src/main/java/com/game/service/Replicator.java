package com.game.service;

import com.game.controller.MatchMakerController;
import com.game.message.Message;
import com.game.message.MessageObjects;
import com.game.message.Topic;
import com.game.model.Pawn;
import com.game.util.JsonInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class Replicator {
    private static final Logger log = LoggerFactory.getLogger(MatchMakerController.class);

    AtomicLong idGenerator = new AtomicLong();

    @Autowired
    GameSession gameSession;

    @Autowired
    GameRepository gameRepository;


    public void sendMessageFromQueue() throws IOException {
        if (gameRepository.outQueueSize() > 0L) {
            String msg = gameRepository.getMessage();
            for (Pawn pawn : gameSession.getPawns()) {
                WebSocketSession session = pawn.getSession();
                session.sendMessage(new TextMessage(msg));
            }
        }
    }

    public void sendReplica(List<Object> objects) throws IOException {

        Message msg = new Message(Topic.REPLICA, new MessageObjects(objects));

        String json = JsonInterface.toJson(msg);

        gameRepository.put( idGenerator.getAndIncrement(), json);

        sendMessageFromQueue();

    }

}
