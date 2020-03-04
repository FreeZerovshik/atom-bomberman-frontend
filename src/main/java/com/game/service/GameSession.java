package com.game.service;

import com.game.controller.MatchMakerController;
import com.game.message.Message;
import com.game.model.Player;
import com.game.tick.Ticker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GameSession  {
    private static final Logger log = LoggerFactory.getLogger(MatchMakerController.class);

    private Long id;
    private List<Player> players;

    public void setId(Long id) {
        this.id = id;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
