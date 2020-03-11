package com.game.service;

import com.game.controller.MatchMakerController;
import com.game.message.Message;
import com.game.message.MessageObjects;
import com.game.message.Topic;
import com.game.model.Pawn;
import com.game.tick.Ticker;
import com.game.util.JsonInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class GameSession  {
    private static final Logger log = LoggerFactory.getLogger(MatchMakerController.class);

    private Long id;
    private List<Pawn> pawns;

    @Autowired
    GameRepository gameRepository;

    public void setId(Long id) {
        this.id = id;
    }

    public void setPawns(List<Pawn> pawns) {
        this.pawns = pawns;
    }

    public Long getId() {
        return id;
    }

    public void startGame() throws IOException {

        this.setPawns(gameRepository.getPlayersBySize(GameRepository.PLAYERS_IN_GAME));

        // отправим POSSES всем игрокам в игре
//        sendPossess();
        Ticker tick = new Ticker();
        GameMechanics gameMechanics = new GameMechanics(this,gameRepository);
        tick.registerTickable(gameMechanics);
        tick.gameLoop();

//        pawnToJSON();

    }

    private void pawnToJSON() throws IOException {
        Message msg;
        List<String> objects = new ArrayList<>();
//        List<MessageObjects> objects = new ArrayList<>();

        for (Pawn pawn : getPawns()) {
//            MessageObjects messageObjects = new MessageObjects(JsonInterface.toJson(pawn));
            objects.add(JsonInterface.toJson(pawn));
//            objects.add(messageObjects);
//            WebSocketSession session = pawn.getSession();
//            session.sendMessage(new TextMessage(Replica.test_message()));
        }

        MessageObjects messageObjects = new MessageObjects(objects);
        msg = new Message(Topic.REPLICA, messageObjects);

        String str = JsonInterface.toJson(msg);
        log.info(">>> REPLICA json:" + str.toString());

    }

    private void sendPossess() throws IOException {
        Message msg;
        for (Pawn pawn : getPawns()) {
            log.info("++++ Pawn:" + pawn.getName() + " [" + pawn.getId() + "]" + " in game:" + getId());

//            msg = new Message(Topic.POSSESS, JsonInterface.toJson(pawn.getId()));
//            WebSocketSession session = pawn.getSession();
//            session.sendMessage(new TextMessage(JsonInterface.toJson(msg)));

            // валидация json
//            Boolean res = JsonHelper.isValidJSON(Replica.test_message());
//            log.info(">>> REPLICA validate json:" + res.toString());
            // тестовая реплика для проверки фронта
//            session.sendMessage(new TextMessage(Replica.test_message()));
        }
    }

    public List<Pawn> getPawns() {
        return pawns;
    }
}
