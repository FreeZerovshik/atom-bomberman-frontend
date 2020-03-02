package com.game.service;

import com.game.controller.MatchMakerController;
import com.game.model.Player;
import com.game.tick.Ticker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

import static com.game.service.GameRepository.PLAYERS_IN_GAME;

@Component
public class GameMechanic implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(MatchMakerController.class);

    @Autowired
    private GameRepository gameRepository;

    public void startGameThread(String gameName) {
        new Thread(this, gameName).start();
        log.info(">>> Start thread in GameMechanic " + gameName);
    }

    @Override
    public void run() {

        log.info(">>> Thread started " + Thread.currentThread().getId());

        Ticker tick = new Ticker();

        List<Player> players = gameRepository.getPlayersBySize(PLAYERS_IN_GAME);
        players.forEach(v -> tick.registerTickable(v));


        try {
            tick.gameLoop();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
