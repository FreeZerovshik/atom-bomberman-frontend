package com.game.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by sergey on 3/14/17.
 */
@Service
public class MatchMakerImp implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(MatchMakerImp.class);
    private ConnectionProducer conn = new ConnectionProducer();


//    @Autowired
//    private ConnectionQueue connectionQueue;

//    @Autowired
//    private GameRepository gameRepository;


//   @PostConstruct
    public Long startThread(String gameName) {
        new Thread(this, gameName).start();
        log.info(">>> Start thread in matchmaker " + gameName);
        return conn.getSessionId();
    }

    @Override
    public void run() {
        conn.setSessionId();
        log.info(">>> MatchMaker Started: " + conn.getSessionId());
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
