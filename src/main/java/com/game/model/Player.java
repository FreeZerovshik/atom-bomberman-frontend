package com.game.model;
import com.game.util.StringHelper;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by sergey on 3/14/17.
 */

public class Player {
    private Long idGenerator;
    private  String name = new String();

    public Player(Long id) {
        this.idGenerator = id;
        this.name = (new StringHelper()).randomAlphaNumeric(10);
    }

    public long getId() {
        return idGenerator;
    }

    public String getName() {
        return name;
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
