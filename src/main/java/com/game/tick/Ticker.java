package com.game.tick;


import com.game.message.Message;
import com.game.message.Topic;
import okhttp3.WebSocket;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class Ticker {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Ticker.class);
    private static final int FPS = 60;
    private static final long FRAME_TIME = 1000 / FPS;
//    private Set<Tickable> tickables = new ConcurrentSkipListSet<>();
private Set<Tickable> tickables = new LinkedHashSet<>();

    private long tickNumber = 0;

    public void gameLoop() throws IOException {
        while (!Thread.currentThread().isInterrupted()) {
            long started = System.currentTimeMillis();
            Message mess = new Message(Topic.POSSESS, "1");
            act(FRAME_TIME);
            long elapsed = System.currentTimeMillis() - started;
            if (elapsed < FRAME_TIME) {
                log.info("All tick finish at {} ms", elapsed);
                LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(FRAME_TIME - elapsed));
//                session.sendMessage(new TextMessage(msg));
            } else {
                log.warn("tick lag {} ms", elapsed - FRAME_TIME);
            }
            log.info("{}: tick ", tickNumber);
            tickNumber++;
        }
    }

    public void registerTickable(Tickable tickable) {
        tickables.add(tickable);
    }

    public void unregisterTickable(Tickable tickable) {
        tickables.remove(tickable);
    }

    private void act(long elapsed) {

        tickables.forEach(tickable -> {
                            System.out.println(">>>> Tickable:"+ tickable.toString());
                            tickable.tick(elapsed);}
                            );
    }

    public long getTickNumber() {
        return tickNumber;
    }
}
