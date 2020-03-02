package com.game.model;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by sergey on 3/14/17.
 */

public class Session {
    private  Long idGenerator;
    private  String Name = new String();

    public Session(Long id, String name) {
        this.idGenerator = id;
        this.Name = name;
    }


    public long getId() {
        return idGenerator;
    }

    public String getName() {
        return Name;
    }


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
