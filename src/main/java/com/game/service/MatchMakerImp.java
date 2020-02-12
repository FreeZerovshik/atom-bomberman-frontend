package com.game.service;

import model.Connection;
import model.GameSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.util.ArrayList;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by sergey on 3/14/17.
 */
@Service
public class MatchMakerImp implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(MatchMakerImp.class);


    @Autowired
    private ConnectionQueue connectionQueue;

    @Autowired
    private GameRepository gameRepository;


   @PostConstruct
    public void startThread() {
        new Thread(this, "match-maker").start();
        log.info(">>>Start thread in matchmaker");
    }

    @Override
    public void run() {
        log.info(">>> MatchMaker Started");
//        List<Connection> candidates = new ArrayList<>(GameSession.PLAYERS_IN_GAME);
//
//        while (!Thread.currentThread().isInterrupted()) {
//            try {
//                candidates.add(
//                        connectionQueue.getQueue().poll(10_000, TimeUnit.SECONDS)
//                );
//            } catch (InterruptedException e) {
//                log.warn("Timeout reached");
//            }
//
//            log.info(">>> After try "+ candidates.size() );
//            if (candidates.size() == GameSession.PLAYERS_IN_GAME) {
//                GameSession session = new GameSession(candidates.toArray(new Connection[0]));
//                log.info(session.toString());
//                gameRepository.put(session);
//                candidates.clear();
//            }
//        }
    }
}
