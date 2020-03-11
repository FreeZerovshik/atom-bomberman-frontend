package com.game.model;

import com.game.message.Topic;
import javafx.geometry.Pos;

public class Replica {
    private Boolean gameOver;
    private Topic topic = Topic.REPLICA;

    public static String test_message(){
        return "{\n" +
                "   \"topic\": \"REPLICA\",\n" +
                "   \"data\":\n" +
                "   {\n" +
                "       \"objects\":[" +
                "                       {\"position\":{\"x\":16.0,\"y\":12.0},\"id\":16,\"type\":\"Wall\"}," +
                "                       {\"position\":{\"x\":32.0,\"y\":32.0},\"id\":0,\"velocity\":0.05,\"maxBombs\":1,\"bombPower\":1,\"speedModifier\":1.0,\"type\":\"Pawn\"}," +
                "                       {\"position\":{\"x\":32.0,\"y\":352.0},\"id\":1,\"velocity\":0.05,\"maxBombs\":1,\"bombPower\":1,\"speedModifier\":1.0,\"type\":\"Pawn\"}" +
                "                   ],\n" +
                "       \"gameOver\":false\n" +
                "   }\n" +
                "}";
    }

}
