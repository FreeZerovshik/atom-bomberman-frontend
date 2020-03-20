package com.game.network;

import com.game.model.Pawn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//@Component
public class ConnectionPool {
    private static final Logger log = LogManager.getLogger(ConnectionPool.class);
    private static final ConnectionPool instance = new ConnectionPool();
    private static final int PARALLELISM_LEVEL = 4;

    private ConcurrentHashMap<WebSocketSession, Pawn> playerPool;

    public static ConnectionPool getInstance() {
        return instance;
    }

    private ConnectionPool() {
        playerPool = new ConcurrentHashMap<>();
    }

    public ConcurrentHashMap getPlayerPool(){
        return this.playerPool;
    }

    public void newPlayerPool(){
        this.playerPool = new ConcurrentHashMap<>();
    }

    public void send(@NotNull WebSocketSession session, @NotNull String msg) {
        if (session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(msg));
            } catch (IOException ignored) {
            }
        }
    }

    public void broadcast(@NotNull String msg) {
        playerPool.forEachKey(PARALLELISM_LEVEL, session -> send(session, msg));
    }

    public void shutdown() {
        playerPool.forEachKey(PARALLELISM_LEVEL, session -> {
            if (session.isOpen()) {
                try {
                    session.close();
                } catch (IOException ignored) {
                }
            }
        });
    }

    public Pawn getPlayer(WebSocketSession session) {
        return playerPool.get(session);
    }

    public Collection<Pawn> getPlayers() {
//        List<Pawn> pawns = new ArrayList<>();
//        pool.forEach((k,v) -> pawns.add(v));
        return playerPool.values();
    }

    public WebSocketSession getSession(Pawn player) {
        return playerPool.entrySet().stream()
                .filter(entry -> entry.getValue().equals(player))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseGet(null);
    }

    public void add(WebSocketSession session, Pawn player) {
        if (playerPool.putIfAbsent(session, player) == null) {
            log.info("{} joined", player);
        }
    }

    public int size(){
        return playerPool.size();
    }

    public void remove(WebSocketSession session) {
        playerPool.remove(session);
    }
}
