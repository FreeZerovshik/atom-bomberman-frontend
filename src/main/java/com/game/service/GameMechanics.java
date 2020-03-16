package com.game.service;

import com.game.controller.MatchMakerController;
import com.game.message.Message;
import com.game.message.MessageObjects;
import com.game.message.Topic;
import com.game.model.Pawn;
import com.game.model.Position;
import com.game.model.Wall;
import com.game.tick.Tickable;
import com.game.util.JsonInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
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
        Message msg;
        List<Object> objects = new ArrayList<>();

        // add Pawn json to array
        for (Pawn pawn : gameSession.getPawns()) {
            objects.add(pawn);
        }

        Wall wall = new Wall(new Position(16,12));
        objects.add(wall);

        MessageObjects messageObjects = new MessageObjects(objects);
        msg = new Message(Topic.REPLICA, messageObjects);

        String json = JsonInterface.toJson(msg);

        gameRepository.put( idGenerator.getAndIncrement(), json.replace("\\",""));


        log.info("OutQueue size="+gameRepository.outQueueSize());
        sendMessage();

        log.info("OutQueue size2="+gameRepository.outQueueSize());
    }

    public void sendMessage() throws IOException {
        if (gameRepository.outQueueSize() > 0L) {
            String msg = gameRepository.getMessage();
            for (Pawn pawn : gameSession.getPawns()) {
                WebSocketSession session = pawn.getSession();
                session.sendMessage(new TextMessage(msg));
            }
        }
    }

}
