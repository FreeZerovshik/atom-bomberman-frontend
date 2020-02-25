package com.game.service;

import com.game.controller.MatchMakerController;
import com.game.tick.Ticker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class GameMechanic implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(MatchMakerController.class);

    public void startGameThread(String gameName) {
        new Thread(this, gameName).start();
        log.info(">>> Start thread in GameMechanic " + gameName);


//        return gameSession.getId();
    }

    @Override
    public void run() {

        log.info(">>> Thread started " + Thread.currentThread().getId());

        Ticker tick = new Ticker();

        try {
            tick.gameLoop();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
