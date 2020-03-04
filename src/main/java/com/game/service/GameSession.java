package com.game.service;

import com.game.controller.MatchMakerController;
import com.game.message.Message;
import com.game.message.Topic;
import com.game.model.Player;
import com.game.model.Replica;
import com.game.tick.Ticker;
import com.game.util.JsonHelper;
import com.game.util.JsonInterface;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static javafx.scene.input.KeyCode.T;

@Component
public class GameSession  {
    private static final Logger log = LoggerFactory.getLogger(MatchMakerController.class);

    private Long id;
    private List<Player> players;

    @Autowired
    GameRepository gameRepository;

    public void setId(Long id) {
        this.id = id;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Long getId() {
        return id;
    }

    public void startGame() throws IOException {

        this.setPlayers(gameRepository.getPlayersBySize(GameRepository.PLAYERS_IN_GAME));

        // отправим POSSES всем игрокам в игре
        Message msg;
        for(Player player: players){
            log.info("++++ Player:" + player.getName() + " ["+player.getId() + "]" + " in game:"+ getId());
//            playersIds.add(player.getId());
            msg = new Message(Topic.POSSESS, JsonInterface.toJson(player.getId()));
            WebSocketSession session = player.getSession();
            session.sendMessage(new TextMessage(JsonInterface.toJson(msg)));

            Boolean res = JsonHelper.isValidJSON(Replica.test_message());
            log.info(">>> REPLICA validate json:" + res.toString());

            session.sendMessage(new TextMessage(Replica.test_message()));

//          session.sendMessage(new TextMessage(JsonInterface.toJson()));
        }



    }

    public List<Player> getPlayers() {
        return players;
    }
}
