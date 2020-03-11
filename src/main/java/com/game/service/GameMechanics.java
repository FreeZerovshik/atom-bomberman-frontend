package com.game.service;

import com.game.controller.MatchMakerController;
import com.game.message.Message;
import com.game.message.MessageObjects;
import com.game.message.Topic;
import com.game.model.Pawn;
import com.game.tick.Tickable;
import com.game.util.JsonInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


public class GameMechanics implements Tickable {
    private static final Logger log = LoggerFactory.getLogger(MatchMakerController.class);

    private GameSession gameSession;
    private GameRepository gameRepository;

    public GameMechanics(GameSession gameSession, GameRepository gameRepository) {
        this.gameSession = gameSession;
        this.gameRepository = gameRepository;
    }

    AtomicLong idGenerator = new AtomicLong();

    @Override
    public void tick(long elapsed) throws IOException {
        //add objects into out queue
        //pawn

        Message msg;
        List<String> objects = new ArrayList<>();
//        List<MessageObjects> objects = new ArrayList<>();

        for (Pawn pawn : gameSession.getPawns()) {
//            MessageObjects messageObjects = new MessageObjects(JsonInterface.toJson(pawn));
            objects.add(JsonInterface.toJson(pawn));
//            objects.add(messageObjects);
//            WebSocketSession session = pawn.getSession();
//            session.sendMessage(new TextMessage(Replica.test_message()));
        }

        MessageObjects messageObjects = new MessageObjects(objects);
        msg = new Message(Topic.REPLICA, messageObjects);

        String json = JsonInterface.toJson(msg);

        gameRepository.put( idGenerator.getAndIncrement(), json);


        log.info("OutQueue size="+gameRepository.outQueueSize());

    }
}
