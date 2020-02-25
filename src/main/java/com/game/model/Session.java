package com.game.model;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by sergey on 3/14/17.
 */
public class Session {
    private static AtomicLong idGenerator = new AtomicLong();
    private static String gameName  = new String();

    public Long getAndIncremetSessionId(){
        return  idGenerator.getAndIncrement();
    }

    public long getId() {
        return idGenerator.get();
    }

    public String getName() {
        return gameName;
    }

    public static void setName(String gameName) {
        Session.gameName = gameName;
    }

    public void createGameSession(String gameName) {
        getAndIncremetSessionId();
        setName(gameName);
    }


//    public static final int PLAYERS_IN_GAME = 4;

//    private final Connection[] connections;
//    public GameSession(Connection[] connections) {
//        this.connections = connections;
//    }

//    @Override
//    public String toString() {
//        return "GameSession{" +
//                "connections=" + Arrays.toString(connections) +
//                ", id=" + id +
//                '}';
//    }

}
